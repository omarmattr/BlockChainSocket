package model;

import java.io.Serializable;

public class Header implements Serializable {
    private String version;
    private String previousHash;
    //  self.merkle_root = merkle_root
    private Long timestamp;
    private int nonce= 0 ;
    private int difficulty;

    public Header(String version, String previousHash, int difficulty) {
        this.version = version;
        this.previousHash = previousHash;
        this.timestamp = System.currentTimeMillis();

        this.difficulty = difficulty;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    public String getString(){
        return  version + previousHash+ timestamp+nonce+difficulty;
    }

}
