package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.MessagingException;

import java.util.UUID;
import javax.servlet.http.HttpSession;
import util.MailUtilGmail;
import db.UserDAO;
import model.UserPojo;

@WebServlet("/send")

public class EmailListServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get current action
		String url = "";
		// get parameters from the request
		String message = "";
		String em = request.getParameter("email");
		boolean emailExists = false;
		try {
			emailExists = UserDAO.getInstance().userExists(new UserPojo(em, "0"));
		} catch (SQLException e1) {
			request.getRequestDispatcher("sqlError.html").forward(request, response);
			e1.printStackTrace();
		}
		if (emailExists == true) {
			HttpSession session = request.getSession();
			String uuid = UUID.randomUUID().toString();
			UserPojo user = null;
			try {
				user = UserDAO.getInstance().getAllUsers().get(em);
			} catch (SQLException e1) {
				request.getRequestDispatcher("sqlError.html").forward(request, response);
				e1.printStackTrace();
			}
			String email = user.getEmail();
			session.setAttribute("email", email);
			String firstName = user.getName();
			String password = user.getPassword();
			System.out.println(password);
			String to = email;
			String from = "pechorinnd@gmail.com";
			String link = "http://localhost/WebDevelopment/resetpwd";
				//	+ "&value=" + uuid + "&value="+ email;
			String subject = "Regarding Password Recovery";
			String body = "Dear " + firstName + ",\n\n" + "Your password Recovery Link is below. Click on that..."
					+ link;

			boolean isBodyHTML = false;

			try {
				System.out.println("inside try");
				// MailUtilLocal.sendMail(to, from, subject, body, isBodyHTML);
				MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);

			} catch (MessagingException e) {
				String errorMessage = "ERROR: Unable to send email. " + "Check Tomcat logs for details.<br>"
						+ "NOTE: You may need to configure your system " + "as described in chapter 14.<br>"
						+ "ERROR MESSAGE: " + e.getMessage();
				request.setAttribute("errorMessage", errorMessage);
				this.log("Unable to send email. \n" + "Here is the email you tried to send: \n"
						+ "=====================================\n" + "TO: " + email + "\n" + "FROM: " + from + "\n"
						+ "SUBJECT: " + subject + "\n" + "\n" + body + "\n\n");

				message = "password has been sent to your email ";
			}

		} else {
			message = "Email doesn't exists in our database";
		}
		request.setAttribute("message", message);
		url = "/forget.jsp";

		getServletContext().getRequestDispatcher(url).forward(request, response);
	}
}
