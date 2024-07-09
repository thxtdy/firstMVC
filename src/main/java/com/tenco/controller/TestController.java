package com.tenco.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.tenco.model.UserDAO;
import com.tenco.model.UserDAOImpl;
import com.tenco.model.UserDTO;
@WebServlet("/test/*")
public class TestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
       
    public TestController() {

    }
    
    @Override
    public void init() throws ServletException {
    	userDAO = new UserDAOImpl(); // 이거 쓰면 dataSource null값 뜨던듸
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		case "/byId":
			// http://localhost:8080/mvc/test/byId
//			userDAO.getUserByUsername("아아");
			System.out.println(userDAO.deleteUser(1));
			
			UserDTO dto = UserDTO.builder().password("2323").email("wer@naver.com").build();
			userDAO.updateUser(dto, 3);
//			List<UserDTO> list = userDAO.getAllUsers();
//			
//			if(list.isEmpty()) {
//				
//			}
			break;

		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
