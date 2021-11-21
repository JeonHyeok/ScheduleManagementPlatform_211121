package com.web.smp.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.web.smp.dao.interf.ContentDao;
import com.web.smp.di.entity.Content;

public class ContentJdbcDao implements ContentDao {
	private String driver;
	private String url;
	private String userName;
	private String password;

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	public ContentJdbcDao(String driver, String url, String userName, String password) {
		this.driver = driver;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn = DriverManager.getConnection(url, userName, password);
		
		//System.out.println("ContentJdbcDao  DB연결성공");
	}

	private void disconnect() throws SQLException {
		//System.out.println("ContentJdbcDao  DB연결해제");
		
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
	public Content getContent(int content_seq) {
		return null;
	}

	@Override
	public List<Content> getContentList(String query) {
		return null;
	}

	@Override
	public List<String> getMainCategory(int category) {
		List<String> mainCategoryList=null;
		
		String sql = "select DISTINCT main_content from content where category= ? ";
		System.out.println("getMainCategory함수 sql>>"+sql);
		try {
			connect();
			
			stmt =  conn.prepareStatement(sql);
			stmt.setInt(1, category);
			rs = stmt.executeQuery();
			
			if (rs.isBeforeFirst()) {
				mainCategoryList=new ArrayList<String>();
				
				while(rs.next()) {
					mainCategoryList.add(rs.getString("main_content"));
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
		return mainCategoryList;
	}

	@Override
	public List<String> getSubCategory(String query) {
		List<String> subCategoryList = null;

		String sql = "select sub_content from content";
		sql = sql + (query != null && !query.equals("") ? " WHERE " + query : " ORDER BY sub_content");
		//String sql = "select sub_content from content WHERE "+ query;
		System.out.println("getSubCategory함수1 sql>>" + sql);
		try {
			connect();

			stmt = conn.prepareStatement( sql);
			rs = stmt.executeQuery();
			if (rs.isBeforeFirst()) {
				subCategoryList = new ArrayList<String>();
				while (rs.next()) {
					subCategoryList.add(rs.getString("sub_content"));
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
		return subCategoryList;
	}

}
