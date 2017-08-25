package beans;

import java.util.HashMap;

public class CommonRequest {

	private String requestCode;
	private HashMap<String, String> requestParam;
	
	public String getRequestCode() {
		return requestCode;
	}
	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}
	public HashMap<String, String> getRequestParam() {
		return requestParam;
	}
	public void setRequestParam(HashMap<String, String> requestParam) {
		this.requestParam = requestParam;
	}
	
	

}
