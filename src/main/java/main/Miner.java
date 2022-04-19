package main;

import io.socket.client.IO;
import io.socket.client.Socket;
import model.Block;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;

public class Miner {
    private Socket socket;
    public Miner() {
        String url = "http://localhost:9991";
        try {
            IO.Options options = new IO.Options();
            options.transports = new String[]{"websocket"};
            options.reconnectionAttempts = 10;
            options.reconnectionDelay = 1000;
            options.timeout = 500;
            socket = IO.socket(url, options);
            socket.on ("sub", objects -> System.out.println ("client:"+ "successful subscription, feedback - >" + Arrays.toString (objects)));
            socket.on(Socket.EVENT_CONNECT, objects -> {
                socket. emit ("sub", "I'm a subscriber");
                System. out. println ("client:"+ "successful connection");
            });
            socket.on (Socket.EVENT_CONNECT, objects-> System.out.println ("client:"+"in connection"));
            socket.on (Socket.EVENT_CONNECT_ERROR, objects-> System.out.println ("client:"+"connection failure"));
            socket.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void sendBlock(Block block){
        socket.emit("block",convertToByteString(block));
    }
    void receive(BlockDelegate blockDelegate){
        socket.on ("block", objects-> {
            Block block = convertFromByteString(objects[0].toString());
            blockDelegate.delegate(block);
            System.out.println ("client: received block->" + block.getIndex());
        });
    }
    interface BlockDelegate {
        void delegate(Block block);
    }

    public static String convertToByteString(Block object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            final byte[] byteArray = bos.toByteArray();
            return Base64.getEncoder().encodeToString(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
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
