package mo.springmvc.defaultconfiguration.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author WindShadow
 * @verion 2020/9/6.
 */

public abstract class BaseEncryptUtil {

    private static final String UTF_8 = "UTF-8";
    public static final String ENCRYPT_SHA_256 = "SHA-256";
    public static final String ENCRYPT_MD5 = "MD5";

    /**
     * SHA-256加密
     * @param plaintext
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String encryptBySHA256(String plaintext) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        return encrypt(plaintext,ENCRYPT_SHA_256,UTF_8);
    }

    /**
     * MD5加密
     * @param plaintext
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String encryptByMD5(String plaintext) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        return encrypt(plaintext,ENCRYPT_MD5,UTF_8);
    }

    /**
     * 散列加密
     * @param plaintext 明文
     * @param encryptWay 加密方式
     * @param encode 编码格式
     * @return 密文
     */
    public static String encrypt(String plaintext, String encryptWay, String encode) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance(encryptWay);
        messageDigest.update(plaintext.getBytes(encode));
        String cipertext = byte2Hex(messageDigest.digest());
        return cipertext;
    }

    /**
     * 字节数组转16进制字符串
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String tempStr = null;

        for(int i = 0; i < bytes.length; ++i) {
            tempStr = Integer.toHexString(bytes[i] & 255);
            if (tempStr.length() == 1) {
                sb.append("0");
            }

            sb.append(tempStr);
        }

        return sb.toString();
    }

}
