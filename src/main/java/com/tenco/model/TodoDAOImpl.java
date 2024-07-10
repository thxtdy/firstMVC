package com.tenco.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TodoDAOImpl implements TodoDAO{
	
	private DataSource dataSource;
	
	public TodoDAOImpl() {
		
		try {
			InitialContext ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addTodo(TodoDTO dto, int principalId) {
		String sql = "insert into todos(user_id, title, description, due_date, completed) values (?, ?, ?, ?, ?) ";
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement ptmt = conn.prepareStatement(sql)){
				ptmt.setInt(1, principalId);
				ptmt.setString(2, dto.getTitle());
				ptmt.setString(3, dto.getDescription());
				ptmt.setString(4, dto.getDueDate());
				ptmt.setString(5, dto.getCompleted());
				int rowCount = ptmt.executeUpdate();
				System.out.println(dto.getTitle());
				if(rowCount > 0 ) {
					conn.commit();
				} else {
					conn.rollback();
				}
				
				
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
			
		} catch (Exception e) {

		}
	}

	@Override
	public TodoDTO getTodoById(int id) {
		
		String query = "SELECT * FROM todos where id = ? ";
		TodoDTO todoDTO = null;
		try (Connection conn = dataSource.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement ptmt = conn.prepareStatement(query)){
				ptmt.setInt(1, todoDTO.getId());
				ResultSet rs = ptmt.executeQuery();
				if(rs.next()) {
					todoDTO = new TodoDTO();
					todoDTO.setId(rs.getInt("id"));
					todoDTO.setUserId(rs.getInt("userid"));
					todoDTO.setTitle(rs.getString("title"));
					todoDTO.setDescription(rs.getString("description"));
					todoDTO.setDueDate(rs.getString("dueDate"));
					todoDTO.setCompleted(rs.getString("completed"));
				} else {
					conn.rollback();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("TODODTO : " + todoDTO.toString());
		return todoDTO;
	}

	@Override
	public List<TodoDTO> getTodosByUserId(int userId) {
		String query = "SELECT * FROM todos where id = ?";
		List<TodoDTO> list = new ArrayList<TodoDTO>();
		
		try (Connection conn = dataSource.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement ptmt = conn.prepareStatement(query)){
				ptmt.setInt(1, userId);
				ResultSet rs = ptmt.executeQuery();
				while(rs.next()) {
					TodoDTO todoDTO = new TodoDTO();
					todoDTO.setId(rs.getInt("id"));
					todoDTO.setUserId(rs.getInt("user_id"));
					todoDTO.setTitle(rs.getString("title"));
					todoDTO.setDescription(rs.getString("description"));
					todoDTO.setDueDate(rs.getString("due_Date"));
					todoDTO.setCompleted(rs.getString("completed"));
					list.add(todoDTO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("TodoList All : " + list.toString());
		return list;
	}

	@Override
	public List<TodoDTO> getAllTodos() {
		String query = " SELECT * FROM todos ";
		List<TodoDTO> list = new ArrayList<TodoDTO>();
		
		try (Connection conn = dataSource.getConnection()){
			try (PreparedStatement ptmt = conn.prepareStatement(query)){
				ResultSet rs = ptmt.executeQuery();
				while(rs.next()) {
					TodoDTO todoDTO = new TodoDTO();
					todoDTO.setId(rs.getInt("id"));
					todoDTO.setUserId(rs.getInt("user_id")); 
					todoDTO.setTitle(rs.getString("title"));
					todoDTO.setDescription(rs.getString("description"));
					todoDTO.setDueDate(rs.getString("due_date"));
					todoDTO.setCompleted(rs.getString("completed"));
					list.add(todoDTO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("TodoList All : " + list.toString());
		return list;
	}

	@Override
	public void updateTodo(TodoDTO dto, int principalId) {
		String query = " UPDATE todos set title ?, description = ? where userid = ?";
		int rowCount = 0;
		try (Connection conn = dataSource.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement ptmt = conn.prepareStatement(query)){
				ptmt.setString(1, dto.getTitle());
				ptmt.setString(2, dto.getDescription());
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
	}

	@Override
	public void deleteTodo(int id, int pricipalId) {
		String query = " DELETE FROM todos where id = ? ";
		int rowCount = 0;
		try (Connection conn = dataSource.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement ptmt = conn.prepareStatement(query)){
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
	}

}
