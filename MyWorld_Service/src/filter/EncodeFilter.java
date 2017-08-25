package filter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import util.LogUtil;

/**
 * Servlet Filter implementation class EncodeFilter
 */
@WebFilter(description = "报文编码Filter", urlPatterns = { "/*" })
public class EncodeFilter implements Filter {

	private final String ReqEncodeType = "utf-8";
	private final String ResContentType = "text/json;charset=utf-8";
	
    /**
     * Default constructor. 
     */
    public EncodeFilter() {
    	LogUtil.log("EncodeFilter contruct...");
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		LogUtil.log("EncodeFilter 销毁...");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		LogUtil.log("EncodeFilter 开始设置编码格式");
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		httpRequest.setCharacterEncoding(ReqEncodeType);
		
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpResponse.setContentType(ResContentType);
		LogUtil.log("EncodeFilter 设置编码格式完成");
		
		chain.doFilter(request, response);
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		LogUtil.log("EncodeFilter 初始化...");
	}
	
	/* 
	 * http://blog.csdn.net/u014675553/article/details/49962537   
	 * base64编码后，后台 ‘+’ 变 ‘空格’ 问题
	 *  
	 */
	public static byte[] replace(JSONObject object){
		JSONArray array=object.getJSONArray("image");  //base64 编码文件
	    String imgStr = array.getString(0);
	    byte[] imagArray = null;
	    if (imgStr != null && !("").equals(imgStr) ) {
	    //解决后台接收base64编码出现空格的问题
	       String replace=imgStr.replace(" ", "+");
	      // byte[] imagArray;
	        try {
	        	imagArray= new BASE64Decoder().decodeBuffer(replace);
	             for (int i = 0; i < imagArray.length; ++i) {  
	                    if (imagArray[i] < 0) {  
	                        // 调整异常数据  
	                    	imagArray[i] += 256;  
	                    }  
	                } 
	            OutputStream os = new FileOutputStream("D:\\" + "name" ); //(文件夹路径+文件名);
	            os.write(imagArray);
	            os.flush();
	            os.close();
	           } catch (IOException e2) {
	            e2.printStackTrace();
	        }
	    }
		
		return imagArray;		
	}

}
