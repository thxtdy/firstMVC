package com.tenco.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserDAOImpl implements UserDAO {
	
	private DataSource dataSource;

	public UserDAOImpl() {
		
		try {
			InitialContext ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int addUser(UserDTO userDTO) {
		int resultCount = 0;

		String sql = " INSERT INTO users(username, password, email) VALUES (?,?,?) ";
		String check = " SELECT username from users where username = ? ";
		try (Connection conn = dataSource.getConnection()) {
			// 트랜잭션 시작
			conn.setAutoCommit(false);
			
			// 중복 username Exception 처리
			PreparedStatement checkptmt = conn.prepareStatement(check);
			checkptmt.setString(1, userDTO.getUserName());
			ResultSet rs =  checkptmt.executeQuery();
			if(rs.next()) {
				conn.rollback();
			} else {
				try (PreparedStatement ptmt = conn.prepareStatement(sql)) {
					ptmt.setString(1, userDTO.getUserName());
					ptmt.setString(2, userDTO.getPassword());
					ptmt.setString(3, userDTO.getEmail());
					resultCount = ptmt.executeUpdate();
					
					conn.commit();
				} catch (Exception e) {
					conn.rollback();
					e.printStackTrace();
				}
				
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultCount;
	}
	/*
	 * SELECT 에서는 일단 트랜잭션 처리를 하지 말자
	 * 하지만 랜텀 리드현상(정합성을 위해서 처리하는 것도 옳은 방법이다)
	 */
	@Override
	public UserDTO getUserById(int id) {
		
		String query = "SELECT * FROM users where id = ? ";
		UserDTO userDTO = null;
		try (Connection conn = dataSource.getConnection()){
			conn.setAutoCommit(false);
			
			try (PreparedStatement ptmt = conn.prepareStatement(query)){
				ptmt.setInt(1, userDTO.getId());
				ResultSet rs =  ptmt.executeQuery();
				if(rs.next()) {
					userDTO = new UserDTO();
					userDTO.setId(rs.getInt("id"));
					userDTO.setUserName(rs.getString("username"));
					userDTO.setPassword(rs.getString("password"));
					userDTO.setEmail(rs.getString("email"));
					userDTO.setCreatedAt(rs.getString("created_at"));
					
				} else {
					conn.rollback();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("USERDTO : " + userDTO.toString());
		return userDTO;
	}

	@Override
	public UserDTO getUserByUsername(String username) {
		String query = "SELECT * FROM users where username = ? ";
		UserDTO userDTO = null;
		try (Connection conn = dataSource.getConnection()){
			conn.setAutoCommit(false);
			
			try (PreparedStatement ptmt = conn.prepareStatement(query)){
				ptmt.setString(1, userDTO.getUserName());
				ResultSet rs =  ptmt.executeQuery();
				if(rs.next()) {
					userDTO = new UserDTO();
					userDTO.setId(rs.getInt("id"));
					userDTO.setUserName(rs.getString("username"));
					userDTO.setPassword(rs.getString("password"));
					userDTO.setEmail(rs.getString("email"));
					userDTO.setCreatedAt(rs.getString("created_at"));
					
				} else {
					conn.rollback();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Username : " + userDTO.toString());
		return userDTO;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		String query = "SELECT * FROM users";
		// 자료구조를 사용할때 일단 생성시키자. 메모리적으로는 비효율적이긴 하나 큰 차이 없음 - root
		List<UserDTO> list = new ArrayList<UserDTO>();

		try (Connection conn = dataSource.getConnection()){
			try (PreparedStatement ptmt = conn.prepareStatement(query)){
				ResultSet rs =  ptmt.executeQuery();
				while(rs.next()) {
					UserDTO userDTO = new UserDTO();
					userDTO.setId(rs.getInt("id"));
					userDTO.setUserName(rs.getString("username"));
					userDTO.setPassword(rs.getString("password"));
					userDTO.setEmail(rs.getString("email"));
					userDTO.setCreatedAt(rs.getString("created_at"));
					list.add(userDTO);
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("UserList All : " + list.toString());
		return list;
	}

	@Override
	public int updateUser(UserDTO user, int principalId) {
		String sql = " UPDATE users set password = ?, email = ? where id = ? ";
		int rowCount = 0;
		try (Connection conn = dataSource.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement ptmt = conn.prepareStatement(sql)){
				ptmt.setString(1, user.getPassword());
				ptmt.setString(2, user.getEmail());
				ptmt.setInt(3, principalId);
				rowCount = ptmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rowCount;
	}

	@Override
	public int deleteUser(int id) {
		String deleteQuery = " DELETE FROM users where id = ? ";
		int rowCount = 0;
		try (Connection conn = dataSource.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement ptmt = conn.prepareStatement(deleteQuery)){
				ptmt.setInt(1, id);
				rowCount = ptmt.executeUpdate();
					conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rowCount;
	}

}
