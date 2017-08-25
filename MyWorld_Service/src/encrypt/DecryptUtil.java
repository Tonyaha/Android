package encrypt;

/**
 * 解密工具总类
 * 
 * @author WangJ  2016.06.15
 */
public class DecryptUtil {
	
	/**
	 * 获取DES密文解密后的明文字符串
	 * @param cipherStr 待解密密文
	 */
	public static String getEDSDecryptStr(String cipherStr){
		return DES.decrypt(cipherStr);
	}
	
	
}
