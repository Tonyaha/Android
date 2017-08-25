package encrypt;

/**
 * 加密工具总类
 * 
 * @author WangJ
 * @date 2016.06.15
 */
public class EncryptUtil {
	
	/**
	 * 获取DES加密字符串
	 * @param sourceStr 待加密明文
	 */
	public static String getEDSEncryptStr(String sourceStr){
		return DES.encrypt(sourceStr);
	}
	
	
}
