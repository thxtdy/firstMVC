package com.tenco.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TodoDAOImpl implements TodoDAO {

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
			try (PreparedStatement ptmt = conn.prepareStatement(sql)) {
				ptmt.setInt(1, principalId);
				ptmt.setString(2, dto.getTitle());
				ptmt.setString(3, dto.getDescription());
				ptmt.setString(4, dto.getDueDate());
				ptmt.setInt(5, dto.getCompleted() == "true" ? 1 : 0);
				int rowCount = ptmt.executeUpdate();
				System.out.println(dto.getTitle());
				if (rowCount > 0) {
					conn.commit();
				} else {
					conn.rollback();
				}

			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public TodoDTO getTodoById(int id) {

		String query = "SELECT * FROM todos where id = ? ";
		TodoDTO todoDTO = null;
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement ptmt = conn.prepareStatement(query)) {
				ptmt.setInt(1, id);
				ResultSet rs = ptmt.executeQuery();
				if (rs.next()) {
					todoDTO = new TodoDTO();
					todoDTO.setId(rs.getInt("id"));
					todoDTO.setUserId(rs.getInt("user_id"));
					todoDTO.setTitle(rs.getString("title"));
					todoDTO.setDescription(rs.getString("description"));
					todoDTO.setDueDate(rs.getString("due_Date"));
					todoDTO.setCompleted(rs.getString("completed"));
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
		String query = "SELECT * FROM todos where user_id = ?";
		List<TodoDTO> list = new ArrayList<TodoDTO>();

		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement ptmt = conn.prepareStatement(query)) {
				ptmt.setInt(1, userId);
				ResultSet rs = ptmt.executeQuery();
				while (rs.next()) {
					// 객체가 new로 생성이 되어야 저장된다.
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
		return list;
	}

	@Override
	public List<TodoDTO> getAllTodos() {
		String query = " SELECT * FROM todos ";
		List<TodoDTO> list = new ArrayList<TodoDTO>();

		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement ptmt = conn.prepareStatement(query)) {
				ResultSet rs = ptmt.executeQuery();
				while (rs.next()) {
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
		return list;
	}

	// UPDATE todos set title = '이번에는1', description = '가보자고', due_date ='2024-07-11
	// 14:09:37', completed = 1 where id = 5 and user_id = 4;
	@Override
	public void updateTodo(TodoDTO dto, int principalId) {
		String query = " UPDATE todos set title = ?, description = ?, due_date = ?, completed = ? where id = ? and user_id = ?";
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement ptmt = conn.prepareStatement(query)) {
				ptmt.setString(1, dto.getTitle());
				ptmt.setString(2, dto.getDescription());
				ptmt.setString(3, dto.getDueDate());
				System.out.println(dto.getDueDate());
				ptmt.setInt(4, dto.getCompleted() == "true" ? 1 : 0);
				ptmt.setInt(5, dto.getId());
				ptmt.setInt(6, dto.getUserId());
				ptmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 삭제 기능 id - Todos PK principalId - session ID
	 */
	@Override
	public void deleteTodo(int id, int pricipalId) {
		String query = " DELETE FROM todos where id = ? and user_id = ? ";
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement ptmt = conn.prepareStatement(query)) {
				ptmt.setInt(1, id);
				ptmt.setInt(2, pricipalId);
				ptmt.executeUpdate();
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
