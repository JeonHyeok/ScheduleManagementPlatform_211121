package com.web.smp.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.web.smp.dao.interf.UserDao;
import com.web.smp.di.entity.User;

public class UserJdbcDao implements UserDao {
	private String driver;
	private String url;
	private String userName;
	private String password;

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	public UserJdbcDao(String driver, String url, String userName, String password) {
		this.driver = driver;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn = DriverManager.getConnection(url, userName, password);
		
		//System.out.println("UserJdbcDao  DB연결성공");
	}

	private void disconnect() throws SQLException {
		//System.out.println("UserJdbcDao  DB연결해제");
		
		if (rs != null && !rs.isClosed()) {
			rs.close();
			rs = null;
		}
		if (stmt != null && !stmt.isClosed()) {
			stmt.close();
			stmt = null;
		}
		if (conn != null && !conn.isClosed()) {
			conn.close();
			conn = null;
		}
	}
	
	@Override
	public boolean loginAvailability(String id, String pwd) {
		boolean TF=false;
		String sql = "select * from user where user_id = ? AND pwd = ?";
		System.out.println("loginAvailability함수 sql>>"+sql);
		try {
			connect();
			
			stmt =  conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, pwd);
			
			rs = stmt.executeQuery();

			if (rs.next()) {
				TF=true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					disconnect();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return TF;
	}

	@Override
	public User getUser(String id) {
		User user=null;
		String sql = "select user_type, user_id, user_name from user where user_id = ?";
		System.out.println("getUser함수 sql>>"+sql);
		try {
			connect();
			
			stmt =  conn.prepareStatement(sql);
			stmt.setString(1, id);
			
			rs = stmt.executeQuery();

			if (rs.next()) {
				user=new User();
				user.setUser_type(rs.getString("user_type"));
				user.setUser_id(rs.getString("user_id"));
				user.setUser_name(rs.getString("user_name"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					disconnect();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return user;
	}

	@Override
	public List<User> getUserList(String query) {
		List<User> userList=null;
		
		String sql = "select user_seq,user_type, user_id, user_name from user";
		sql = sql + (query != null && !query.equals("") ? " WHERE " + query : " ORDER BY user_seq, user_type");
		System.out.println("getUserList함수 sql>>"+sql);
		try {
			connect();
			
			stmt =  conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.isBeforeFirst()) {
				userList=new ArrayList<User>();
				
				while(rs.next()) {
					User user=new User();
					user.setUser_seq(rs.getInt("user_seq"));
					user.setUser_type(rs.getString("user_type"));
					user.setUser_id(rs.getString("user_id"));
					user.setUser_name(rs.getString("user_name"));
					
					userList.add(user);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					disconnect();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return userList;
	}

	@Override
	public String getUserName(String id) {
		String userName=null;
		String sql = "select user_name from user where user_id = ?";
		System.out.println("getUserName함수 sql>>"+sql);
		try {
			connect();
			
			stmt =  conn.prepareStatement(sql);
			stmt.setString(1, id);
			
			rs = stmt.executeQuery();

			if (rs.next()) {
				userName =rs.getString("user_name");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					disconnect();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return userName;
	}

	@Override
	public String getUserType(String id) {
		String userType=null;
		String sql = "select user_type from user where user_id = ?";
		System.out.println("getUserType함수 sql>>"+sql);
		try {
			connect();
			
			stmt =  conn.prepareStatement(sql);
			stmt.setString(1, id);
			
			rs = stmt.executeQuery();

			if (rs.next()) {
				userType =rs.getString("user_type");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					disconnect();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return userType;
	}

}
