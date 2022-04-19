package model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Block implements Serializable {
    private int index;
    private Header header;
    private List<String> data;
    private String hash;

    public Block(int index, Header header, List<String> data) {
        this.index = index;
        this.data = data;
        this.header = header;
        this.hash = calculateHash();
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getIndex() {
        return index;
    }

    public List<String> getData() {
        return data;
    }

    public String getHash() {
        return hash;
    }

    public  String calculateHash() {
        String text = index + header.getString()+ data.hashCode();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        final byte[] bytes = digest.digest(text.getBytes());
        final StringBuilder hexString = new StringBuilder();
        for (final byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }




}
