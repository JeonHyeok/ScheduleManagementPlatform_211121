package com.web.smp.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.web.smp.dao.interf.FaqDao;
import com.web.smp.di.entity.AllViewEntity;
import com.web.smp.di.entity.Faq;

public class FaqJdbcDao implements FaqDao {
	private String driver;
	private String url;
	private String userName;
	private String password;

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;

	public FaqJdbcDao(String driver, String url, String userName, String password) {
		this.driver = driver;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn = DriverManager.getConnection(url, userName, password);

		// System.out.println("ContentJdbcDao DB연결성공");
	}

	private void disconnect() throws SQLException {
		// System.out.println("ContentJdbcDao DB연결해제");

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
	public List<Faq> getFaq() {
		List<Faq> getfaq = null;

		String sql = "SELECT * FROM FAQ";
		System.out.println("getFaq함수 sql>>" + sql);
		try {
			connect();

			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.isBeforeFirst()) {
				getfaq = new ArrayList<Faq>();

				while (rs.next()) {
					Faq faq = new Faq();
					faq.setFqa_seq(rs.getInt("FAQ_seq"));
					faq.setQuestion(rs.getString("FAQ_question"));
					faq.setAnswer(rs.getString("FAQ_answer"));
					getfaq.add(faq);
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
		return getfaq;
	}

	@Override
	public int insertFaq(String question, String answer) {
		int result = 0;
		
		String sql = "INSERT INTO FAQ(FAQ_question, FAQ_answer) VALUES (?, ?)";
		
		try {
			connect();
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, question);
			stmt.setString(2, answer);
			
			result = stmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

}
