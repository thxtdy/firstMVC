package com.tenco.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.tenco.model.TodoDAO;
import com.tenco.model.TodoDAOImpl;
import com.tenco.model.TodoDTO;
import com.tenco.model.UserDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// .../mvc/todo/*
@WebServlet("/todo/*")
public class TodoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TodoDAO todoDAO;

	public TodoController() {
		todoDAO = new TodoDAOImpl();
	}

	// http://localhost:8080/mvc/todo/todoForm (MZ 세대에선 권장하지 않음 ㅋㅋ)
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println(session);
		UserDTO principal = (UserDTO) session.getAttribute("principal");
		String action = request.getPathInfo();
		System.out.println("Action : " + action);

		if (principal == null) {
			response.sendRedirect(request.getContextPath() + "/user/signIn?message=invalid");
			return;
		}

		switch (action) {
		case "/todoForm":
			todoFormPage(request, response);
			break;
		case "/list":
			todoListPage(request, response);
			break;
		case "/detail":
			todoDetailPage(request, response, principal.getId());
			break;
		case "/delete":
			deleteTodo(request, response, principal.getId());
			break;

		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}

	}
	/**
	 * todo 삭제 기능
	 * @param request
	 * @param response
	 * @param principalId
	 * @throws IOException
	 */
	private void deleteTodo(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
		try {
			int todoId = Integer.parseInt(request.getParameter("id"));
			todoDAO.deleteTodo(todoId, principalId);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/todo/list?error=invalid");
		}
		response.sendRedirect(request.getContextPath() + "/todo/list");

	}

	/**
	 * TODO 상세 보기 화면 - Detail
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void todoDetailPage(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
		// url 요청 시 getparmeter, getpathinfo
		try {
			// todo - pk - 
			int todoId = Integer.parseInt(request.getParameter("id"));
			TodoDTO dto = todoDAO.getTodoById(todoId);
			if(dto.getUserId() == principalId) {
				request.setAttribute("todo", dto);
				request.getRequestDispatcher("/WEB-INF/views/todoDetail.jsp").forward(request, response);
			} else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('남의 Todo를 훔쳐보려는걸 보니 당신의 인생도 보입니다.'); history.back();</script>");
				
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "todo/list?error=invalid");
		}
		
	}

	/**
	 * 사용자별 TodoList 조회
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void todoListPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// 인증 검사
		HttpSession session = request.getSession();
		UserDTO principal = (UserDTO) session.getAttribute("principal");

		System.out.println("request.getContextPath(); : " + request.getContextPath());
		if (principal == null) {
			response.sendRedirect(request.getContextPath() + "/user/signIn?message=invalid");
			return;
		}
		// 데이터를 담아서 던질 예정 (DB조회)
		// request.getPathInfo() -> URL 요청에 있어 데이터를 추출
		// request.getParameter() -> URL 요청에 있어 데이터를 추출
		// request.getAttribute() -> 뷰를 내릴 속성에 값을 담아서 뷰로 내릴 때 사용

		// List<TodoDTO> list : TodoDAOImpl --> 사용자별 todoList Select

		// todoList.jsp 페이지로 내부에서 이동 처리
		List<TodoDTO> todolist = todoDAO.getTodosByUserId(principal.getId());
		request.setAttribute("todoList", todolist);
		request.getRequestDispatcher("/WEB-INF/views/todoList.jsp").forward(request, response);

	}

	/**
	 * TODO 작성 페이지 이동
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void todoFormPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.getRequestDispatcher("/WEB-INF/views/todoForm.jsp").forward(request, response);

	}

	// ================================================================================================
	// ================================================================================================

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO principal = (UserDTO) session.getAttribute("principal");
		if (principal == null) {
			response.sendRedirect(request.getContextPath() + "/user/signIn?error=invalid");
			return;
		}
		String action = request.getPathInfo();
		System.out.println("action " + action);
		switch (action) {
		case "/add":
			addTodo(request, response, principal.getId());
			break;
		// TODO - Update
		case "/update":
			updateTodo(request, response, principal.getId());
			break;

		default:
			break;
		}

	}
	/**
	 * todo 수정 기능
	 * @param request
	 * @param response
	 * @param principalId - 세션 ID 값
	 * @throws IOException
	 */
	private void updateTodo(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
		try {
			int todoId = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String dueDate = request.getParameter("dueDate");
			boolean completed = "on".equalsIgnoreCase(request.getParameter("completed"));

			TodoDTO todoDTO = TodoDTO.builder()
					.id(todoId)
					.userId(principalId)
					.title(title)
					.description(description)
					.dueDate(dueDate)
					.completed(String.valueOf(completed))
					.build();
			
			todoDAO.updateTodo(todoDTO, principalId);
		
		
		} catch (Exception e) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('꺼져!!!!'); history.back(); </script>");
		}
		response.sendRedirect(request.getContextPath() + "/todo/list");
		
	}

	/**
	 * 세션별 사용자 todo 등록
	 * 
	 * @param request
	 * @param response
	 * @param principalId : 세션에 담겨있는 userId값
	 * @throws IOException
	 */
	private void addTodo(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {

		// principal -- null 이라면 -> 로그인 페이지 이동 처리
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String dueDate = request.getParameter("dueDate");

		// checkBox는 여러개 선택 가능한 태그 : String[] 배열로 선언했음
		// 이번에 checkBox 하나만 사용 중
		// 체크박스가 선택되지 않았으면 null을 반환하고 체크가 되어있다면 "on" 으로 넘어온다랄까?
		boolean completed = "on".equalsIgnoreCase(request.getParameter("completed"));
		System.out.println("completed :" + completed);

		TodoDTO todoDTO = TodoDTO.builder().userId(principalId).title(title).description(description).dueDate(dueDate)
				.completed(String.valueOf(completed)).build();
		todoDAO.addTodo(todoDTO, principalId);
		response.sendRedirect(request.getContextPath() + "/todo/list");

		List<TodoDTO> list = new ArrayList<TodoDTO>();
		request.setAttribute("todosList", list);
		// http://localhost:8080/mvc/todo/list

	}

}
