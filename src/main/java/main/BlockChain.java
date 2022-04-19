package main;


import java.util.ArrayList;
import java.util.Scanner;


public class BlockChain {
    public static void main(String[] args){
        //read in file before end system
        Miner miner = new Miner();
        BlockUtils blockUtils = new BlockUtils(new ArrayList<>());
        System.out.println(blockUtils.getBlockChain().size());
        //add new Transaction to blockchain
        blockUtils.addNewTransaction("100");
        blockUtils.addNewTransaction("100");
        blockUtils.addNewTransaction("100");
        //mineBlock to blockchain
        blockUtils.mineBlock();
        blockUtils.addNewTransaction("200");
        blockUtils.addNewTransaction("200");
        miner.receive(blockUtils::addNewBlock);
        new Scanner(System.in).next();
        miner.sendBlock(blockUtils.mineBlock());
        System.out.println(blockUtils.getBlockChain().size());

    }



//    public static List<Block> read() {
//        Scanner s = null;
//        try {
//            s = new Scanner(new File("output.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        ArrayList<Block> list = new ArrayList<>();
//        while (true) {
//            assert s != null;
//            if (!s.hasNext()) break;
//            list.add(convertFromByteString(s.next()));
//        }
//        s.close();
//        return list;
//    }
//
//    public static void writer(List<Block> blockChain) {
//        File file = new File("output.txt");
//        FileOutputStream fo = null;
//        try {
//            fo = new FileOutputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        assert fo != null;
//        PrintWriter pw = new PrintWriter(fo);
//        for (Block elem : blockChain) {
//            pw.println(convertToByteString(elem));
//        }
//        pw.close();
//        try {
//            fo.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
