package com.tenco.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.tenco.model.TodoDAO;
import com.tenco.model.TodoDAOImpl;
import com.tenco.model.TodoDTO;
import com.tenco.model.UserDTO;
// .../mvc/todo/*
@WebServlet("/todo/*")
public class TodoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TodoDAO todoDAO;
       
    public TodoController() {
    	todoDAO = new TodoDAOImpl();
    }
    // http://localhost:8080/mvc/todo/todoForm (MZ 세대에선 권장하지 않음 ㅋㅋ)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println("Action : " + action);
		
		switch (action) {
		case "/todoForm":
			todoFormPage(request, response);
			
			break;
		case "/list":
			todoListPage(request, response);
			break;

		default:
			break;
		}
		
	}
	// http://localhost:8080/mvc/todo/list
	private void todoListPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		// 인증 검사
		HttpSession session = request.getSession();
		UserDTO principal = (UserDTO) session.getAttribute("principal");
		
		System.out.println("request.getContextPath(); : " + request.getContextPath());
		if(principal == null) {
			response.sendRedirect(request.getContextPath() + "/user/signIn?message=invalid");
			return;
		}
		
		// List<TodoDTO> list  : TodoDAOImpl --> 사용자별 todoList Select 
		
		// todoList.jsp 페이지로 내부에서 이동 처리
		List<TodoDTO> todolist =  todoDAO.getTodosByUserId(principal.getId());
		request.setAttribute("todoList", todolist);
		System.out.println(todolist.toString());
		request.getRequestDispatcher("/WEB-INF/views/todoList.jsp").forward(request, response);
		
	}
	
	private void todoFormPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		// 로그인한 사용자만 접근하도록 설계
		HttpSession session = request.getSession();
		UserDTO principal = (UserDTO) session.getAttribute("principal");
		
		// 인증 검사
		if(principal == null) {
			// 로그인을 안함
			response.sendRedirect("../user/signUp?message=invalid");
			return;
		}
		
		request.getRequestDispatcher("/WEB-INF/views/todoForm.jsp").forward(request, response);
		
	}
	
	// ================================================================================================
	// ================================================================================================
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println("action " + action);
		
		switch (action) {
		case "/add":
			addTodo(request, response);
			break;
	
		default:
			break;
		}
		
		// TODO - 수정 예정 (TodoController Post)
		
	}
	
	
	// 
	//
	// [] main   
	private void addTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		UserDTO principal =  (UserDTO) session.getAttribute("principal");
		
		// principal -- null 이라면 -> 로그인 페이지 이동 처리
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String dueDate= request.getParameter("dueDate");
		String completed = request.getParameter("completed");
		
		if(principal == null) {
			response.sendRedirect("signIn.jsp");
			return;
		}
		TodoDTO todoDTO = TodoDTO.builder()
				.title(title)
				.description(description)
				.dueDate(dueDate)
				.completed(completed)
				.build();
		request.getAttribute("userid");
		todoDAO.addTodo(todoDTO, principal.getId());
		List<TodoDTO> list = new ArrayList<TodoDTO>();
		request.setAttribute("todosList", list);
		// http://localhost:8080/mvc/todo/list
		response.sendRedirect("list");
		
	}

}
