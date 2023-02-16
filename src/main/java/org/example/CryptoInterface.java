package org.example;

public interface CryptoInterface {
    public boolean startServer(int port);
    public boolean startClient(String host, int port);
    public void endConnection();
    public void shutdownServer();

    public boolean sendData(String mes);
    public void acceptConnection();
    public void receiveData();
}
