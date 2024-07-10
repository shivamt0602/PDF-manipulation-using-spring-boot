package com.example.demo.dto;

public class SignPdfRequest {
    private String src;
    private String dest;
    private String keystorePath;
    private String keystorePassword;
    private String alias;

    // Getters and setters
    public String getSrc() { return src; }
    public void setSrc(String src) { this.src = src; }
    public String getDest() { return dest; }
    public void setDest(String dest) { this.dest = dest; }
    public String getKeystorePath() { return keystorePath; }
    public void setKeystorePath(String keystorePath) { this.keystorePath = keystorePath; }
    public String getKeystorePassword() { return keystorePassword; }
    public void setKeystorePassword(String keystorePassword) { this.keystorePassword = keystorePassword; }
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
}
