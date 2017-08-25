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

//		String req = DecryptUtil.getEDSDecryptStr(sb.toString()); // ����ͻ��������ļ�����
		String req = sb.toString();
		//System.out.println(">>>>������"+req);
		
		// ��һ������ȡ �ͻ��� ���������󣬻ָ���Json��ʽ����>��Ҫ�ͻ��˷�����ʱҲ��װ��Json��ʽ
		JSONObject object = JSONObject.fromObject(req);
		// requestCode��ʱ�ò���
		// ע���±��õ���2���ֶ�����requestCode��requestParamҪ�Ϳͻ���CommonRequest��װʱ�������һ��
		String requestCode = object.getString("requestCode");
		System.out.println(">>>>������Ϣ������>>");
		JSONObject requestParam = object.getJSONObject("requestParam");	


 		/* 
 		 * �����ݿ�� ����ɾ���ġ���
 		 *   
 		 */
 	   // �ڶ�������Jsonת��Ϊ������ݽṹ����ʹ�û���ֱ��ʹ�ã��˴�ֱ��ʹ�ã�������ҵ�������ɽ��
 		// ƴ��SQL��ѯ���
 		String opt = requestParam.getString("opt");
 		
		if("1".equals(opt)){  //������Ϣ ������
			String opt2 = requestParam.getString("opt2");
 			
 			String phone = requestParam.getString("phone");
 			String nickname = requestParam.getString("nickname");
 			String address = requestParam.getString("address");
 			String usericon = requestParam.getString("usericon");
 			String account = requestParam.getString("name");
    	    Connection connect = (Connection) DatabaseUtil.getConnection();  
    	    
    	    // �Զ���Ľ����Ϣ��
    		CommonResponse res = new CommonResponse();
	        try {  
	        	String sql1="";
	            Statement statement = (Statement) connect.createStatement(); 
	            String sql2 = String.format("SELECT * FROM %s WHERE account='%s'", 
	    				DBNames.Table_Account, 
	    				account);
	            ResultSet result = DatabaseUtil.query(sql2);
	            if("1".equals(opt2)){ //ע��
	            	
	            	if(result.next()){
	            		res.setResult("100", "���˺���ע��");
	            	}else{
	            		String password = requestParam.getString("password");
		            	sql1 = "insert into " + DBNames.Table_Account + "(account, password,phone,nickname,address,usericon) values('"  
		                        + account + "', '" + password + "', '" + phone + "', '" + nickname + "', '" + address + "', '" + usericon + "')";  
		            	//LogUtil.log(sql1); 
		                if (statement.executeUpdate(sql1) > 0) { // �������ע���߼����������˺����뵽���ݿ� 
		                	res.setResult("200", "ע��ɹ�");
	 	                   
	 	                } else {  
	 	                	res.setResult("300", "ʧ��");  
	 	                }        
	            	}
	     			
	 			}else if("2".equals(opt2)){  //����
	 				sql1 = "UPDATE tb_account SET usericon= '"+usericon+"',nickname='"+nickname
	 						+"',phone='"+phone+"',address='"+address+ "'  WHERE account= '"+account+"'";  	//ƴ��  <<<<<<<<<<<<<<<	                 
	 				if (statement.executeUpdate(sql1) > 0) { // �������ע���߼����������˺����뵽���ݿ� 
	                	res.setResult("200", "���³ɹ�");
 	                   
 	                } else {  
 	                	res.setResult("300", "����ʧ��");  
 	                }  
	 			}
	        } catch (SQLException e) { 
	        	res.setResult("300", "���ݿ��ѯ����");
	            e.printStackTrace();  
	        }  
	        String resStr = JSONObject.fromObject(res).toString();
	        
	        System.out.print(">>>>��Ӧ����>>>������Ϣ���ġ�ע��"); //�˴���ӡ��json��ʽ����
			
			//System.out.println(">>>ע�ᡢ������Ӧ����"+resStr); //�˴���ӡ��json��ʽ����	
			//response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // ���Զ��ַ������м��ܲ�������Ӧ�ĵ��˿ͻ��˾���Ҫ����
			response.getWriter().append(resStr).flush();
	  
	       // res.getWriter().append("code:").append(code).append(";message:").append(message);  
	    }else if("2".equals(opt)){  //������Ϣ��ѯ
 	    		
 		    // �Զ���Ľ����Ϣ��
 			CommonResponse commonresponse = new CommonResponse();
 			HashMap<String, String> map = new HashMap<>();
 			try {
     	    	String sql = String.format("SELECT * FROM %s WHERE account= '%s'", 
     	        	        DBNames.Table_Account,requestParam.getString("name"));
 				ResultSet result = DatabaseUtil.query(sql); // ���ݿ��ѯ����
 				while (result.next()) { 					 					
 					map.put("usericon", result.getString("usericon"));
 					map.put("phone", result.getString("phone"));
 					map.put("nickname", result.getString("nickname"));
 					map.put("address", result.getString("address"));
 					map.put("resCode", "0");
 					//commonresponse.addListItem(map);
 				}
 				//commonresponse.getRequestType().put("type", result.getString("type"));
 				//commonresponse.setResCode("0"); // ����������ˣ���ʾҵ������ȷ
 			} catch (SQLException e) {
 				commonresponse.setResult("300", "���ݿ��ѯ����");
 				e.printStackTrace();
 			}

 			String resStr = JSONObject.fromObject(map).toString();
 			//System.out.print("��������ӡ��json��ʽ���� \n");
 			///System.out.print(">>>>��Ӧ����>>>������Ϣ"+resStr); //�˴���ӡ��json��ʽ����
 			System.out.print(">>>>��Ӧ����>>>������Ϣ��ѯ"); //�˴���ӡ��json��ʽ����
 			//System.out.print("\n��������ӡ��json��ʽ����");
 			response.getWriter().append(resStr).flush(); //���͸��ͷ���
 		}

		//wDatabaseUtil.closeConnection();
 	} 
	
}
