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
 * DES�ӽ��ܹ�����
 *
 * @author WangJ 2016.05.16
 */
public class DES {
    private final static String DES = "DES";
    private static String KEY = "surprise#123wj"; // ������Կ

    /**
     * ԭʼ�ַ�������
     *
     * @param data ԭʼ�ַ���
     * @return ���ܺ��Base64�ַ���
     */
    public static String encrypt(String data) {
        byte[] bt = null;
        try {
            bt = encrypt(data.getBytes("utf-8"), KEY.getBytes("utf-8"));
        } catch (Exception e) {
            LogUtil.log("DES�����쳣 >> DES.java");
            e.printStackTrace();
        }
        String strs = Base64.toBase64String(bt);
        return strs;
    }

    /**
     * �����ַ�������
     *
     * @param data ���ܺ��ַ���
     * @return ���ܺ������
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
            LogUtil.log("DES���쳣 >> DES.java");
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
     * �ֽ�����ļ���
     *
     * @param data ���������ݵ��ֽ�����
     * @param key  ��Կ���ֽ�����
     * @return ���ܺ�����ĵ��ֽ�����
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
     * �Ѽ����ֽ�����Ľ���
     *
     * @param data �Ѽ������ݵ��ֽ�����
     * @param key  ��Կ���ֽ�����
     * @return ���ܺ�����ĵ��ֽ�����
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
