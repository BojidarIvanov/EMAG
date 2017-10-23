package controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.UserDAO;
import model.UserPojo;
import util.PasswordUtil;

@WebServlet("/register")

public class RegisterServlet extends HttpServlet {

	public static final String AVATAR_URL = "/Users/Daskalski/Desktop/uploads/users/";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// check for register credentials
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String dob = request.getParameter("DOB");
		String password = request.getParameter("pass");
		String password2 = request.getParameter("pass2");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		System.out.println(password);
		System.out.println(password2);

		if (!password.equals(password2)) {
			request.setAttribute("error", "passwords missmatch");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}

		try {
			PasswordUtil.checkPasswordStrength(password2);
		} catch (Exception e2) {
			request.setAttribute("error", "password is too short");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}

		try {

			if (Integer.parseInt(dob) < 1900 || Integer.parseInt(dob) > 2018) {
				request.setAttribute("error", "Please provide adequate date of birth");
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
		} catch (NumberFormatException e) {
			request.setAttribute("error", " Please enter correct year of birth.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}

		try {
			UserPojo customer = null;
			try {
				customer = new UserPojo(name, email, phone, LocalDate.of(Integer.parseInt(dob), 1, 1),
						PasswordUtil.hashPassword(password), address, false);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			if (!UserDAO.getInstance().userExists(customer)) {
				try {
					UserDAO.getInstance().addUser(customer);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				request.getSession().setAttribute("user", customer);
				request.getSession().setAttribute("newUser", customer);
				request.getRequestDispatcher("main.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "user already registered");
				request.getRequestDispatcher("register.jsp").forward(request, response);
				return;
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
