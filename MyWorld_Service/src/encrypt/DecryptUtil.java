package encrypt;

/**
 * ���ܹ�������
 * 
 * @author WangJ  2016.06.15
 */
public class DecryptUtil {
	
	/**
	 * ��ȡDES���Ľ��ܺ�������ַ���
	 * @param cipherStr ����������
	 */
	public static String getEDSDecryptStr(String cipherStr){
		return DES.decrypt(cipherStr);
	}
	
	
}
