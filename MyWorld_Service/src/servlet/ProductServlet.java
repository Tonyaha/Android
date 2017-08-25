package servlet;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import beans.CommonResponse;
import constants.DBNames;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import util.DatabaseUtil;
import util.LogUtil;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	 public String encodeStr(String str) {  
	        try {  
	            return new String(str.getBytes("ISO-8859-1"), "UTF-8");  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String code = "";  
        String message = "";  
		BufferedReader read = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = read.readLine()) != null) {
			sb.append(line);
		}
		String req = sb.toString();
		//System.out.println(">>>>����ı���<<<"+req);
		
		
        
     // ��һ������ȡ �ͻ��� ���������󣬻ָ���Json��ʽ����>��Ҫ�ͻ��˷�����ʱҲ��װ��Json��ʽ
     		JSONObject object = JSONObject.fromObject(req);
     		// requestCode��ʱ�ò���
     		// ע���±��õ���2���ֶ�����requestCode��requestParamҪ�Ϳͻ���CommonRequest��װʱ�������һ��
     		String requestCode = object.getString("requestCode");
     		JSONObject requestParam = object.getJSONObject("requestParam");
     		String sql="";
     		
     		System.out.println(">>>>��Ʒ������>>requestCode:"+requestCode+" opt:"+requestParam.getString("opt")+" opt2:"+requestParam.getString("opt2"));
     		

     		
     		/* 
     		 * �����ݿ�� ����ɾ���ġ���
     		 *   
     		 */
     	   // �ڶ�������Jsonת��Ϊ������ݽṹ����ʹ�û���ֱ��ʹ�ã��˴�ֱ��ʹ�ã�������ҵ�������ɽ��
     		// ƴ��SQL��ѯ���
     		String opt = requestParam.getString("opt");
     		String opt2 = requestParam.getString("opt2");

     			
     		if("1".equals(opt)){
     			
         		String name = requestParam.getString("gname");
         		String describle = requestParam.getString("desc");
         		String oldprice = requestParam.getString("oldprice");
         		String price = requestParam.getString("price");
         		String account = requestParam.getString("account");
         		String image = requestParam.getString("image");
         		String type = requestParam.getString("type");
         		
        
        	    if("1".equals(opt2)){  //��
        	    	//into �������Ҫ�пո񣬷��� �Ҳ���intotb_product,��ȷ��ʽ into tb_product
         			sql="insert into " + "tb_product" + "(g_name,g_desc,g_price,g_oldprice,t_id,account,g_img) values('"
                            + name + "','" + describle +"','"
         					+ price + "','"  + oldprice  +"','"  
                            + type +"','"  + account  +"','"  + image  +"')";
        	    }else if("2".equals(opt2)){  //ɾ��
        	    	sql = String.format("delete from %s where g_name= '%s' AND account='%s'",DBNames.Table_Product,name,account);
        	    }
         	/*	
         		//into �������Ҫ�пո񣬷��� �Ҳ���intotb_product,��ȷ��ʽ into tb_product
     			sql="insert into " + "tb_product" + "(g_name,g_desc,g_price,g_oldprice,t_id,account,g_img) values('"
                        + name + "','" + describle +"','"
     					+ price + "','"  + oldprice  +"','"  
                        + type +"','"  + account  +"','"  + image  +"')";
     			//System.out.println(sql);  */
        	   CommonResponse res = new CommonResponse();
     		   Connection connect = (Connection) DatabaseUtil.getConnection();  
     	        try {  
     	                Statement statement = (Statement) connect.createStatement();  
     	           
     	                //LogUtil.log(sql);  
     	                if (statement.executeUpdate(sql) > 0) { // �������ע���߼����������˺����뵽���ݿ� 
     	                	res.setResult("0", "�������³ɹ�");
     	                    //code = "200";  
     	                   // message = "ע��ɹ�";  
     	                } else {  
     	                	res.setResult("300", "��������ʧ��");
     	                    //code = "300";  
     	                    //message = "ע��ʧ��";  
     	                }  
     	            
     	        } catch (SQLException e) {  
     	        	res.setResult("300", "���ݿ��ѯ����");
     	            e.printStackTrace();  
     	        }  
     	       String resStr = JSONObject.fromObject(res).toString();
     	        System.out.print(">>>��Ʒɾ����������Ӧ����<<<"); //�˴���ӡ��json��ʽ����
     	        response.getWriter().append(resStr).flush();  
     	        
     	    }else if("2".equals(opt)){
     	    	
     		    // �Զ���Ľ����Ϣ��
     	    	CommonResponse commonresponse = new CommonResponse();
     			try {
     				
     				if("1".equals(opt2)){  //����ҳ���ѯ(������Ʒ����)
     					String account = requestParam.getString("account");	
     					sql = "SELECT DISTINCT tb_product.*,phone,usericon FROM tb_product LEFT JOIN tb_account ON tb_account.account=tb_product.account WHERE tb_account.account LIKE '"+account+"'"; //ƴ�Ӹ�ʽ������
     					//sql = "SELECT tb_product.*,usericon,phone FROM tb_product,tb_account WHERE tb_product.account= '"+account+"'"; //ƴ�Ӹ�ʽ������
         	    		
         	    	}else if("2".equals(opt2)){  //�����ѯ
         	    		String type = requestParam.getString("type");
         	    		sql = "SELECT tb_product.*,usericon,phone FROM tb_product,tb_account WHERE tb_product.account= tb_account.account AND t_id='"+type+"'"; //ƴ�Ӹ�ʽ
         	    		//sql = String.format("SELECT  DISTINCT tb_product.*,usericon,phone FROM %s,%s WHERE t_id='%s'", 
         	        	        //DBNames.Table_Product,DBNames.Table_Account, type);
         	    		
         	    	}else if("3".equals(opt2)){  //ȫ��
         	    		sql = String.format("SELECT DISTINCT tb_product.*,usericon,phone FROM %s,%s WHERE tb_product.account=tb_account.account", 
         	        	        DBNames.Table_Product,DBNames.Table_Account);
         	    	}
     				ResultSet result = DatabaseUtil.query(sql); // ���ݿ��ѯ����
     				while (result.next()) {
     					HashMap<String, String> map = new HashMap<>();
     					map.put("phone", result.getString("phone"));
     					map.put("name", result.getString("g_name"));
     					map.put("describle", result.getString("g_desc"));
     					map.put("price", String.valueOf(result.getDouble("g_price")));
     					map.put("oldprice", result.getString("g_oldprice"));
     					map.put("account", result.getString("account"));
     					map.put("image", result.getString("g_img"));
     					map.put("usericon", result.getString("usericon"));
     					map.put("type", result.getString("t_id"));
     					commonresponse.addListItem(map);
     				}
     				//commonresponse.getRequestType().put("type", result.getString("type"));
     				commonresponse.setResCode("0"); // ����������ˣ���ʾҵ������ȷ
     			} catch (SQLException e) {
     				commonresponse.setResult("300", "���ݿ��ѯ����");
     				e.printStackTrace();
     			}

     			String resStr = JSONObject.fromObject(commonresponse).toString();
     			
     			//System.out.print(">>>��Ӧ����<<<"+resStr); //�˴���ӡ��json��ʽ����
     			
     			System.out.print(">>>��Ʒ��ѯ��Ӧ����<<<"); //�˴���ӡ��json��ʽ����
     			
     			response.getWriter().append(resStr).flush(); //���͸��ͷ���
     		}

     		//DatabaseUtil.closeConnection();
     	}
	
     		//System.out.println(sql);	
}
