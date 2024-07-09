package com.tenco.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.tenco.model.UserDAO;
import com.tenco.model.UserDAOImpl;
import com.tenco.model.UserDTO;


// 주소설계
// http://localhost:8080/mvc/user
//http://localhost:8080/mvc/user/xxx
@WebServlet("/user/*")
public class UserController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	
    public UserController() {
        super();
    }
    
	
	 @Override 
	 public void init() throws ServletException {
	  userDAO = new UserDAOImpl(); }
	 
    
    // GET 방식으로 들어 올 때
    // http://localhost:8080/mvc/user/signUp
    // http://localhost:8080/mvc/user/signIn
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println("action : " + action);
		
		switch (action) {
		case "/signIn":
			// 로그인 페이지로 보내는 동작 처리 
			request.getRequestDispatcher("/WEB-INF/views/signIn.jsp").forward(request, response);
			break;
		case "/signUp":
			// 회원 가입 페이지로 보내는 동작 처리 
			request.getRequestDispatcher("/WEB-INF/views/signUp.jsp").forward(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}
	
	// http://localhost:8080/mvc/views/todoForm.jsp
		// http://localhost:8080/mvc/views/signUp.jsp
	// 로그인 기능 요청 (자원에게 요청 -- GET 방식-예외적인 처리_보안)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println("action : " + action);
		switch (action) {
		case "/signIn":
			
			break;
		case "/signUp":
			signUp(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}
	/**
	 * 회원 가입 기능
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인증 검사 필요없는 기능
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		// 방어적 코드 작성
		if(username == null || username.trim().isEmpty()) {
			request.setAttribute("errorMessage", "Username is required");
			request.getRequestDispatcher("/WEB-INF/views/signUp.jsp").forward(request, response);
			return;
		}
		// 방어적 코드 작성 (password) 생략
		// 방어적 코드 작성 (email) 생략
		
		UserDTO userDTO = UserDTO.builder()
				.userName(username)
				.password(password)
				.email(email)
				.build();
		int resultRowCount = userDAO.addUser(userDTO);
		System.out.println("resultRowCount : " + resultRowCount);
		// TODO 나중에 수정
		if(resultRowCount == 1) {
			response.sendRedirect("user/signIn?message=success");
		} else {
			response.sendRedirect("user/signIn?messgae=error");
		}
	}

}
