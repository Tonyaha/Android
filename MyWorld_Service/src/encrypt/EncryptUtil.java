package encrypt;

/**
 * ���ܹ�������
 * 
 * @author WangJ
 * @date 2016.06.15
 */
public class EncryptUtil {
	
	/**
	 * ��ȡDES�����ַ���
	 * @param sourceStr ����������
	 */
	public static String getEDSEncryptStr(String sourceStr){
		return DES.encrypt(sourceStr);
	}
	
	
}
