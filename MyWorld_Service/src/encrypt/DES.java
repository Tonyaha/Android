package encrypt;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.bouncycastle.util.encoders.Base64;

import util.LogUtil;

/**
 * DES加解密工具类
 *
 * @author WangJ 2016.05.16
 */
public class DES {
    private final static String DES = "DES";
    private static String KEY = "surprise#123wj"; // 加密密钥

    /**
     * 原始字符串加密
     *
     * @param data 原始字符串
     * @return 加密后的Base64字符串
     */
    public static String encrypt(String data) {
        byte[] bt = null;
        try {
            bt = encrypt(data.getBytes("utf-8"), KEY.getBytes("utf-8"));
        } catch (Exception e) {
            LogUtil.log("DES加密异常 >> DES.java");
            e.printStackTrace();
        }
        String strs = Base64.toBase64String(bt);
        return strs;
    }

    /**
     * 密文字符串解密
     *
     * @param data 加密后字符串
     * @return 解密后的明文
     */
    public static String decrypt(String data) {
        if (data == null) {
            return null;
        }
        byte[] buf = Base64.decode(data);
        byte[] bt = null;
        try {
            bt = decrypt(buf, KEY.getBytes("utf-8"));
        } catch (Exception e) {
            LogUtil.log("DES解异常 >> DES.java");
            e.printStackTrace();
        }
        try {
			return new String(bt, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return null;
    }

    /**
     * 字节数组的加密
     *
     * @param data 待加密内容的字节数组
     * @param key  密钥的字节数组
     * @return 加密后的密文的字节数组
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * 已加密字节数组的解密
     *
     * @param data 已加密数据的字节数组
     * @param key  密钥的字节数组
     * @return 解密后的明文的字节数组
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
}
