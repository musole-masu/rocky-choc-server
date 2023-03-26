package org.example.d_hellman;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import static org.example.util.CipherUtils.*;


public class SecretKeyExchange {
    private X509EncodedKeySpec x509EncodedKeySpec;
    private KeyPairGenerator serverKeyPair;
    private KeyPair serverPair;
    private KeyAgreement serverKeyAgreement;
    private KeyFactory keyFactory;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    private byte[] commonSecret;

    public void init(){
        try {
            generateDHKeyPair();
            initDHAgreement();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    public DHParameterSpec retrieveDHParamFromPB(PublicKey key){
        return ((DHPublicKey)key).getParams();
    }

    public void generateDHKeyPair() throws NoSuchAlgorithmException {
        serverKeyPair = KeyPairGenerator.getInstance("DH");
        serverKeyPair.initialize(2048);

        serverPair = serverKeyPair.generateKeyPair();
        privateKey = serverPair.getPrivate();
        publicKey = serverPair.getPublic();

        System.out.println("PU"+ encodeBase64(privateKey.getEncoded()));

        System.out.println("G: "+retrieveDHParamFromPB(publicKey).getG());
        System.out.println("L: "+retrieveDHParamFromPB(publicKey).getL());
        System.out.println("P: "+retrieveDHParamFromPB(publicKey).getP());
    }

    public void initDHAgreement() throws NoSuchAlgorithmException, InvalidKeyException {
        serverKeyAgreement = KeyAgreement.getInstance("Dh");
        serverKeyAgreement.init(privateKey);
    }

    public String getPublicKeyEncoded() {
        return encodeBase64(publicKey.getEncoded());
    }
    public String getPrivateKeyEncoded(){
        return encodeBase64(privateKey.getEncoded());
    }

    public void receivePBKFromClient(String publicKeyBase64) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        byte[] pbkDecoded = decodeBase64(publicKeyBase64);

        keyFactory = KeyFactory.getInstance("DH");
        x509EncodedKeySpec = new X509EncodedKeySpec(pbkDecoded);

        PublicKey publicKey1 = keyFactory.generatePublic(x509EncodedKeySpec);

        serverKeyAgreement.doPhase(publicKey1, true);

        this.commonSecret = serverKeyAgreement.generateSecret();

        System.out.println("Your common secret is: ".toUpperCase() + encodeBase64(getAESKey().getEncoded()));
        System.out.println("Secret: ".toUpperCase()+commonSecret);
    }

    public SecretKeySpec getAESKey(){
        return generateAESKey(commonSecret);
    }

    public byte[] getCommonSecret(){
        return commonSecret;
    }
}
