package main;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import model.Block;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;


public class Server {

    public static void main(String[] args) {
        List<Block> blockChain = new ArrayList<>();
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9999);
        SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener(client -> {
            System.out.println("server:" + client.getRemoteAddress() + "client connection success");
        });
        // Add sub events
        server.addEventListener("sub", String.class, (client, data, ackRequest) -> {
            String c = client.getRemoteAddress().toString();
            // Get client URL parameters
            Map params = client.getHandshakeData().getUrlParams();
            System.out.println("server:" + c + ": client: successful subscription, subscription information is - >" + data);
            // Subevent Success Feedback
            client.sendEvent("sub", c + "Hello Client, I am the server, you subscribed successfully");
        });
        server.addEventListener("block", String.class, (client, data, ackRequest) -> {

            Block block = convertFromByteString(data);
            assert block != null;
            System.out.println(block.getIndex());
            if (block.getIndex()> blockChain.get(blockChain.size()-1).getIndex()){
                blockChain.add(block);
                client.sendEvent("block", data);
            }

        });
        // Add client disconnection event
        server.addDisconnectListener(client -> {
            String c = client.getRemoteAddress().toString();
            // get device IP
            String clientIp = c.substring(1, c.indexOf(":"));
            System.out.println("server:" + clientIp + "-----------------------------------" + "client disconnected");
        });
        server.start();
        int i = 0;
        while (true) {
            try {
                Thread.sleep(1500);
                // Broadcast news
                i++;
                server.getBroadcastOperations().sendEvent("msg", "30", "hello" + i, "false");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Block convertFromByteString(String byteString) {
        final byte[] bytes = Base64.getDecoder().decode(byteString);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
            return (Block) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}