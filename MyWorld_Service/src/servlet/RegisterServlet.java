package servlet;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import beans.CommonResponse;
import constants.DBNames;
import filter.EncodeFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import util.DatabaseUtil;
import util.LogUtil;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String code = "";  
    String message = "";  
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader read = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = read.readLine()) != null) {
			sb.append(line);
		}

//		String req = DecryptUtil.getEDSDecryptStr(sb.toString()); // 如果客户端请求报文加密了
		String req = sb.toString();
		//System.out.println(">>>>请求报文"+req);
		
		// 第一步：获取 客户端 发来的请求，恢复其Json格式——>需要客户端发请求时也封装成Json格式
		JSONObject object = JSONObject.fromObject(req);
		// requestCode暂时用不上
		// 注意下边用到的2个字段名称requestCode、requestParam要和客户端CommonRequest封装时候的名字一致
		String requestCode = object.getString("requestCode");
		System.out.println(">>>>个人信息请求报文>>");
		JSONObject requestParam = object.getJSONObject("requestParam");	


 		/* 
 		 * 对数据库的 增、删、改、查
 		 *   
 		 */
 	   // 第二步：将Json转化为别的数据结构方便使用或者直接使用（此处直接使用），进行业务处理，生成结果
 		// 拼接SQL查询语句
 		String opt = requestParam.getString("opt");
 		
		if("1".equals(opt)){  //个人信息 增、改
			String opt2 = requestParam.getString("opt2");
 			
 			String phone = requestParam.getString("phone");
 			String nickname = requestParam.getString("nickname");
 			String address = requestParam.getString("address");
 			String usericon = requestParam.getString("usericon");
 			String account = requestParam.getString("name");
    	    Connection connect = (Connection) DatabaseUtil.getConnection();  
    	    
    	    // 自定义的结果信息类
    		CommonResponse res = new CommonResponse();
	        try {  
	        	String sql1="";
	            Statement statement = (Statement) connect.createStatement(); 
	            String sql2 = String.format("SELECT * FROM %s WHERE account='%s'", 
	    				DBNames.Table_Account, 
	    				account);
	            ResultSet result = DatabaseUtil.query(sql2);
	            if("1".equals(opt2)){ //注册
	            	
	            	if(result.next()){
	            		res.setResult("100", "该账号已注册");
	            	}else{
	            		String password = requestParam.getString("password");
		            	sql1 = "insert into " + DBNames.Table_Account + "(account, password,phone,nickname,address,usericon) values('"  
		                        + account + "', '" + password + "', '" + phone + "', '" + nickname + "', '" + address + "', '" + usericon + "')";  
		            	//LogUtil.log(sql1); 
		                if (statement.executeUpdate(sql1) > 0) { // 否则进行注册逻辑，插入新账号密码到数据库 
		                	res.setResult("200", "注册成功");
	 	                   
	 	                } else {  
	 	                	res.setResult("300", "失败");  
	 	                }        
	            	}
	     			
	 			}else if("2".equals(opt2)){  //更新
	 				sql1 = "UPDATE tb_account SET usericon= '"+usericon+"',nickname='"+nickname
	 						+"',phone='"+phone+"',address='"+address+ "'  WHERE account= '"+account+"'";  	//拼接  <<<<<<<<<<<<<<<	                 
	 				if (statement.executeUpdate(sql1) > 0) { // 否则进行注册逻辑，插入新账号密码到数据库 
	                	res.setResult("200", "更新成功");
 	                   
 	                } else {  
 	                	res.setResult("300", "更新失败");  
 	                }  
	 			}
	        } catch (SQLException e) { 
	        	res.setResult("300", "数据库查询错误");
	            e.printStackTrace();  
	        }  
	        String resStr = JSONObject.fromObject(res).toString();
	        
	        System.out.print(">>>>响应报文>>>个人信息更改、注册"); //此处打印出json格式数据
			
			//System.out.println(">>>注册、更新响应报文"+resStr); //此处打印出json格式数据	
			//response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
			response.getWriter().append(resStr).flush();
	  
	       // res.getWriter().append("code:").append(code).append(";message:").append(message);  
	    }else if("2".equals(opt)){  //个人信息查询
 	    		
 		    // 自定义的结果信息类
 			CommonResponse commonresponse = new CommonResponse();
 			HashMap<String, String> map = new HashMap<>();
 			try {
     	    	String sql = String.format("SELECT * FROM %s WHERE account= '%s'", 
     	        	        DBNames.Table_Account,requestParam.getString("name"));
 				ResultSet result = DatabaseUtil.query(sql); // 数据库查询操作
 				while (result.next()) { 					 					
 					map.put("usericon", result.getString("usericon"));
 					map.put("phone", result.getString("phone"));
 					map.put("nickname", result.getString("nickname"));
 					map.put("address", result.getString("address"));
 					map.put("resCode", "0");
 					//commonresponse.addListItem(map);
 				}
 				//commonresponse.getRequestType().put("type", result.getString("type"));
 				//commonresponse.setResCode("0"); // 这个不能忘了，表示业务结果正确
 			} catch (SQLException e) {
 				commonresponse.setResult("300", "数据库查询错误");
 				e.printStackTrace();
 			}

 			String resStr = JSONObject.fromObject(map).toString();
 			//System.out.print("下面这句打印出json格式数据 \n");
 			///System.out.print(">>>>响应报文>>>个人信息"+resStr); //此处打印出json格式数据
 			System.out.print(">>>>响应报文>>>个人信息查询"); //此处打印出json格式数据
 			//System.out.print("\n上面这句打印出json格式数据");
 			response.getWriter().append(resStr).flush(); //发送给客服端
 		}

		//wDatabaseUtil.closeConnection();
 	} 
	
}
