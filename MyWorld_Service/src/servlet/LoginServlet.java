package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.CommonRequest;
import beans.CommonResponse;
import constants.DBNames;
import encrypt.DecryptUtil;
import encrypt.EncryptUtil;
import net.sf.json.JSONObject;
import util.DatabaseUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "登录", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("不支持GET方法;");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
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
		//System.out.println(req);
		
		// 第一步：获取 客户端 发来的请求，恢复其Json格式——>需要客户端发请求时也封装成Json格式
		JSONObject object = JSONObject.fromObject(req);
		// requestCode暂时用不上
		// 注意下边用到的2个字段名称requestCode、requestParam要和客户端CommonRequest封装时候的名字一致
		String requestCode = object.getString("requestCode");
		JSONObject requestParam = object.getJSONObject("requestParam");
		

		// 第二步：将Json转化为别的数据结构方便使用或者直接使用（此处直接使用），进行业务处理，生成结果
		// 拼接SQL查询语句
		String sql = String.format("SELECT * FROM %s WHERE account='%s'", 
				DBNames.Table_Account, 
				requestParam.getString("name"));
		//System.out.println(sql);

		// 自定义的结果信息类
		CommonResponse res = new CommonResponse();
		try {
			ResultSet result = DatabaseUtil.query(sql); // 数据库查询操作
//			result.getRow();
			
			if (result.next()) {
				if (result.getString("password").equals(requestParam.getString("password"))) {
					res.setResult("0", "登陆成功");
					res.getProperty().put("custId", result.getString("id"));
				} else {
					res.setResult("100", "登录失败，登录密码错误");
				}
			} else {
				res.setResult("200", "该登陆账号未注册");
			}
		} catch (SQLException e) {
			res.setResult("300", "数据库查询错误");
			e.printStackTrace();
		}

		// 第三步：将结果封装成Json格式准备返回给客户端，但实际网络传输时还是传输json的字符串
		// 和我们之前的String例子一样，只是Json提供了特定的字符串拼接格式
		// 因为服务端JSon是用到经典的第三方JSon包，功能强大，不用像Android中那样自己手动转，直接可以从Bean转到JSon格式
		String resStr = JSONObject.fromObject(res).toString();
		
		
		//System.out.println(">>>>>登陆时的响应报文"+resStr); //此处打印出json格式数据
		
		
//		response.getWriter().append(EncryptUtil.getEDSEncryptStr(resStr)); // 可以对字符串进行加密操作，相应的到了客户端就需要解密
		response.getWriter().append(resStr).flush();
	}

}
