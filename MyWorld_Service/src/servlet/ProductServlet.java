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
		//System.out.println(">>>>请求的报文<<<"+req);
		
		
        
     // 第一步：获取 客户端 发来的请求，恢复其Json格式——>需要客户端发请求时也封装成Json格式
     		JSONObject object = JSONObject.fromObject(req);
     		// requestCode暂时用不上
     		// 注意下边用到的2个字段名称requestCode、requestParam要和客户端CommonRequest封装时候的名字一致
     		String requestCode = object.getString("requestCode");
     		JSONObject requestParam = object.getJSONObject("requestParam");
     		String sql="";
     		
     		System.out.println(">>>>产品请求报文>>requestCode:"+requestCode+" opt:"+requestParam.getString("opt")+" opt2:"+requestParam.getString("opt2"));
     		

     		
     		/* 
     		 * 对数据库的 增、删、改、查
     		 *   
     		 */
     	   // 第二步：将Json转化为别的数据结构方便使用或者直接使用（此处直接使用），进行业务处理，生成结果
     		// 拼接SQL查询语句
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
         		
        
        	    if("1".equals(opt2)){  //增
        	    	//into 后面必须要有空格，否者 找不到intotb_product,正确形式 into tb_product
         			sql="insert into " + "tb_product" + "(g_name,g_desc,g_price,g_oldprice,t_id,account,g_img) values('"
                            + name + "','" + describle +"','"
         					+ price + "','"  + oldprice  +"','"  
                            + type +"','"  + account  +"','"  + image  +"')";
        	    }else if("2".equals(opt2)){  //删除
        	    	sql = String.format("delete from %s where g_name= '%s' AND account='%s'",DBNames.Table_Product,name,account);
        	    }
         	/*	
         		//into 后面必须要有空格，否者 找不到intotb_product,正确形式 into tb_product
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
     	                if (statement.executeUpdate(sql) > 0) { // 否则进行注册逻辑，插入新账号密码到数据库 
     	                	res.setResult("0", "插入或更新成功");
     	                    //code = "200";  
     	                   // message = "注册成功";  
     	                } else {  
     	                	res.setResult("300", "插入或更改失败");
     	                    //code = "300";  
     	                    //message = "注册失败";  
     	                }  
     	            
     	        } catch (SQLException e) {  
     	        	res.setResult("300", "数据库查询错误");
     	            e.printStackTrace();  
     	        }  
     	       String resStr = JSONObject.fromObject(res).toString();
     	        System.out.print(">>>产品删除、更新响应报文<<<"); //此处打印出json格式数据
     	        response.getWriter().append(resStr).flush();  
     	        
     	    }else if("2".equals(opt)){
     	    	
     		    // 自定义的结果信息类
     	    	CommonResponse commonresponse = new CommonResponse();
     			try {
     				
     				if("1".equals(opt2)){  //详情页面查询(个人商品管理)
     					String account = requestParam.getString("account");	
     					sql = "SELECT DISTINCT tb_product.*,phone,usericon FROM tb_product LEFT JOIN tb_account ON tb_account.account=tb_product.account WHERE tb_account.account LIKE '"+account+"'"; //拼接格式。。。
     					//sql = "SELECT tb_product.*,usericon,phone FROM tb_product,tb_account WHERE tb_product.account= '"+account+"'"; //拼接格式。。。
         	    		
         	    	}else if("2".equals(opt2)){  //分类查询
         	    		String type = requestParam.getString("type");
         	    		sql = "SELECT tb_product.*,usericon,phone FROM tb_product,tb_account WHERE tb_product.account= tb_account.account AND t_id='"+type+"'"; //拼接格式
         	    		//sql = String.format("SELECT  DISTINCT tb_product.*,usericon,phone FROM %s,%s WHERE t_id='%s'", 
         	        	        //DBNames.Table_Product,DBNames.Table_Account, type);
         	    		
         	    	}else if("3".equals(opt2)){  //全部
         	    		sql = String.format("SELECT DISTINCT tb_product.*,usericon,phone FROM %s,%s WHERE tb_product.account=tb_account.account", 
         	        	        DBNames.Table_Product,DBNames.Table_Account);
         	    	}
     				ResultSet result = DatabaseUtil.query(sql); // 数据库查询操作
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
     				commonresponse.setResCode("0"); // 这个不能忘了，表示业务结果正确
     			} catch (SQLException e) {
     				commonresponse.setResult("300", "数据库查询错误");
     				e.printStackTrace();
     			}

     			String resStr = JSONObject.fromObject(commonresponse).toString();
     			
     			//System.out.print(">>>响应报文<<<"+resStr); //此处打印出json格式数据
     			
     			System.out.print(">>>产品查询响应报文<<<"); //此处打印出json格式数据
     			
     			response.getWriter().append(resStr).flush(); //发送给客服端
     		}

     		//DatabaseUtil.closeConnection();
     	}
	
     		//System.out.println(sql);	
}
