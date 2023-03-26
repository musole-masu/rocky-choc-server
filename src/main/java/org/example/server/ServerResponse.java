package org.example.server;

public interface ServerResponse {
    void promptConnectionOpen();
    void promptConnectionEstablished();
    void showMessageReceived(String m);
    void promptMessageReceived();
    void showPrivateKey(String k);

    void showDecryptedMessage(String decryptedMessage);

    void showEncryptedMessage(String encryptedMessage);
}
