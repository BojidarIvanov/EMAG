package controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.UserDAO;
import model.UserPojo;
import util.PasswordUtil;

/**
 * Servlet implementation class ResetPass
 */
@WebServlet("/resetPass")
public class ResetPass extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String confirm = request.getParameter("confirm");
		String pass = request.getParameter("password");
		String saltedAndHashedPassword;
		String hashedPassword = "";
		String salt = "";
		try {
			hashedPassword = PasswordUtil.hashPassword(pass);
		} catch (NoSuchAlgorithmException ex) {
			saltedAndHashedPassword = ex.getMessage();
		}
		if (pass.equals(confirm)) {
		String email1 =	(String) request.getSession().getAttribute("email");
			UserPojo u = new UserPojo(email1, "0");
			u.setPassword(hashedPassword);
			try {
				UserDAO.getInstance().updatePass(u);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String mesage = "Password has been reset";
			request.setAttribute("msg", mesage);
		} else {
			String mesage = "Password doesnt matcht";
			request.setAttribute("msg", mesage);
		}
                 
		request.getRequestDispatcher("/reset.jsp").forward(request, response);
		HttpSession session = request.getSession(false);
		if(session!=null)
		session.invalidate();
	}
}
