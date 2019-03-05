package com.LerniaLabb2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
	private final String url = "jdbc:postgresql://localhost:5432/Labb2";
	private final String user = "postgres";
	private final String password = "postgres1";

	public UserDao() {
	};

	private Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection(url, user, password);
	}

	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<User>();
		String SQL = "SELECT * FROM users";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				User newUser = new User(rs.getInt("id"), rs.getString("name"), rs.getString("profession"));
				userList.add(newUser);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return userList;
	}

	public User getUser(int id) {
		String SQL = "SELECT * FROM users WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("profession"));
			return user;
		} catch (SQLException ex) {
			// System.out.println(ex.getMessage());
			return null;
		}
	}

	public int createUser(User user) {

		String SQL = "INSERT INTO users (name, profession) VALUES (?,?);";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getProfession());
			pstmt.executeUpdate();
			return 1;

		} catch (SQLException ex) {
			// System.out.println(ex.getMessage());
			return 0;
		}
	}

	public int deleteUser(int id) {

		String SQL = "DELETE FROM users WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			return 1;

		} catch (SQLException ex) {
			// System.out.println(ex.getMessage());
			return 0;
		}
	}

	public int updateUser(User user) {

		String SQL = "UPDATE users SET name = ?, profession = ? WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getProfession());
			pstmt.setInt(3, user.getId());
			pstmt.executeUpdate();
			return 1;

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return 0;
		}
	}

}
