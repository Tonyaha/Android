package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DatabaseUtil {

	private static Connection mConnection;

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @return Ψһ���ݿ�����
	 */
	public static Connection getConnection() {
		if (mConnection == null) {
			String url = "jdbc:mysql://localhost:3306/database"; // ���ݿ��Url
			try {
				Class.forName("com.mysql.jdbc.Driver"); // java���䣬�̶�д��
				mConnection = (Connection) DriverManager.getConnection(url, "root", "12345");
				LogUtil.log("�������ݿ�����");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("VendorError: " + e.getErrorCode());
			}
		}
		return mConnection;
	}

	/**
	 * ��ѯ����
	 * 
	 * @param querySql
	 *            ��ѯ����SQL���
	 * @return ��ѯ
	 * @throws SQLException
	 */
	public static ResultSet query(String querySql) throws SQLException {
		Statement stateMent = (Statement) getConnection().createStatement();
		return stateMent.executeQuery(querySql);
	}

	/**
	 * ���롢���¡�ɾ������
	 * 
	 * @param insertSql
	 *            ���������SQL���
	 * @return
	 * @throws SQLException
	 */
	public static int update(String insertSql) throws SQLException {
		Statement stateMent = (Statement) getConnection().createStatement();
		return stateMent.executeUpdate(insertSql);
	}

	/**
	 * �ر����ݿ�����
	 */
	public static void closeConnection() {
		if (mConnection != null) {
			try {
				mConnection.close();
				mConnection = null;
			} catch (SQLException e) {
				LogUtil.log("���ݿ�ر��쳣��[" + e.getErrorCode() + "]" + e.getMessage());
			}
		}
	}
}
