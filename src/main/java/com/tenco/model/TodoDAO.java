package com.tenco.model;

import java.util.List;

public interface TodoDAO {
	
	// 저장하기
	void addTodo(TodoDTO dto, int principalId);
	TodoDTO getTodoById(int id);
	
	// 사용자 아이디 기준으로 todoList
	List<TodoDTO> getTodosByUserId(int userId);
	
	// 관리자 (모두 조회)
	List<TodoDTO> getAllTodos();
	void updateTodo(TodoDTO dto, int principalId);
	void deleteTodo(int id, int pricipalId);
}
