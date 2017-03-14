package com.fangcloud.noah.service.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by jeffjiang on 16/3/21.
 */
public final class RSAUtil {

    private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    private static BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();


    /**
     * 加密
     *
     * @param publicKey 加密的密钥
     * @param text      待加密的明文数据
     * @return 加密后的数据
     * @throws Exception
     */
    public static byte[] encrypt(String publicKey, String text) throws Exception {
        if (text == null) {
            return null;
        }
        byte data[] = text.getBytes();
        byte[] raw = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding", bouncyCastleProvider);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            //获得加密块大小，如:加密前数据为128个byte，而key_size=1024 加密块大小为127 byte,加密后为128个byte;
            //因此共有2个加密块，第一个127 byte第二个为1个byte
            int blockSize = cipher.getBlockSize();
            int outputSize = cipher.getOutputSize(data.length);//获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize)
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                else
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                //这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到ByteArrayOutputStream中
                //，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了OutputSize所以只好用dofinal方法。
                i++;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return raw;
    }

    /**
     * 加密
     *
     * @param publicKey 加密的密钥
     * @param text      待加密的明文数据
     * @return 加密后的数据
     * @throws Exception
     */
    public static String encryptWithBase64Encoded(String publicKey, String text) throws Exception {
        String encryted = null;
        try {
            encryted = new String(Base64.encode(encrypt(publicKey, text), Base64.DEFAULT));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return encryted;
    }

    /**
     * 解密
     *
     * @param privateKey 解密的密钥
     * @param raw        已经加密的数据
     * @return 解密后的明文
     * @throws Exception
     */
    public static byte[] decrypt(String privateKey, byte[] raw) throws Exception {

        ByteArrayOutputStream bout = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding", bouncyCastleProvider);
            cipher.init(cipher.DECRYPT_MODE, getPrivateKey(privateKey));
            int blockSize = cipher.getBlockSize();

            bout = new ByteArrayOutputStream(64);
            int j = 0;
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            if (bout != null) {
                try {
                    bout.close();
                } catch (Exception e) {
                    bout = null;
                    logger.error("ByteArrayOutputStream close exception", e);
                }
            }
        }
    }

    /**
     * 解密
     *
     * @param privateKey        解密的密钥
     * @param textBase64Encoded 已经加密的数据
     * @return 解密后的明文
     * @throws Exception
     */
    public static byte[] decryptWithBase64Encoded(String privateKey, String textBase64Encoded) throws Exception {
        return decrypt(privateKey, Base64.decode(textBase64Encoded.getBytes(), Base64.DEFAULT));
    }

    public static Key getPublicKey(String keyString) throws Exception {
        byte[] keyBytes = Base64.decode(keyString.getBytes(), Base64.DEFAULT);
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(pubKeySpec);
    }

    public static Key getPrivateKey(String keyString) throws Exception {
        byte[] keyBytes = Base64.decode(keyString.getBytes(), Base64.DEFAULT);
        PKCS8EncodedKeySpec pubKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(pubKeySpec);
    }


    public static void main(String[] args) throws Exception {

        String privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAp9FF4LHCKM6f0P8J\n" +
                "ILPb9E9tj/EJnz4LuF9947icqymBwAhDfLEuTVC+gfZOjx8x4XZCpeCLXYlRX/w3\n" +
                "OE9weQIDAQABAkALklOWLcuskqrUd3GCrsRA8WUlosI+F8IpIXSFykoLR0HT71B2\n" +
                "kQa1yOJN03g/AqPQ9HsH0LEtuBn6WBtx3SCBAiEA0dv8D/Mg1QL9HOOF0yFVTq1n\n" +
                "uUqkwP5zgJabWntUanECIQDMtvJKNDjAr88t5mW28MTpg7B/tYyERb83IJ00xgMa\n" +
                "iQIhAJxotdP5ZBX+tUFrvhketeL+0NjD7kk9HD7RgRYN51uxAiARrHG6ikukBwmh\n" +
                "8tQxORQm/OGOBQR+nu7lOYGY/sdh0QIgCeiayFKXZt2kDiiAJFc+/WYV083rpUD9\n" +
                "F8Gn8PFyvRk=";


        String encryptContext = "hYs39o/e4Q/cLMqTMqSc+Z6MMCxMnG0OZlSEsgkQSTAdzfMT+21tUJCUFhMu6YysFCxIo8GT2+k/peaFFSCt1iLawUk5AYJfZW7qJj2CTk8kW8UcNt6l4xUBvQKH8ih3tP9q+at8RTCdK34JoxvyZS5/N8lUyZc2ycIZ0ZH3jsU=";

        encryptContext = encryptContext.replaceAll("\\ ", "+");
        System.out.println(encryptContext);
        String decryptContext = new String(RSAUtil.decryptWithBase64Encoded(privateKey, encryptContext));

        System.out.println(decryptContext);

    }
}

