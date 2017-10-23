package controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.MailUtilGmail;

/**
 * Servlet implementation class Subscribe
 */
@WebServlet("/Subscribe")
public class Subscribe extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String em = request.getParameter("email");
		String url = null;
		String to = em;
		String from = "email_list@emag.com";
		String subject = "Subscription";
		String body = "Dear " + em + ",\n\n" + "You are subscribed to new updates";

		boolean isBodyHTML = false;

		try {
			System.out.println("indise try");
			// MailUtilLocal.sendMail(to, from, subject, body, isBodyHTML);
			MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);

		} catch (MessagingException e) {
			String errorMessage = "ERROR: Unable to send email. " + "Check Tomcat logs for details.<br>"
					+ "NOTE: You may need to configure your system " + "as described in chapter 14.<br>"
					+ "ERROR MESSAGE: " + e.getMessage();
			request.setAttribute("errorMessage", errorMessage);
			this.log("Unable to send email. \n" + "Here is the email you tried to send: \n"
					+ "=====================================\n" + "TO: " + em + "\n" + "FROM: " + from + "\n"
					+ "SUBJECT: " + subject + "\n" + "\n" + body + "\n\n");
		}

		String uri = request.getRequestURI();
		String pageName = uri.substring(uri.lastIndexOf("/") + 1);
		System.out.println("pagename:" + pageName + "and URL:" + uri);
		url = "/index.jsp";

		getServletContext().getRequestDispatcher(url).forward(request, response);
	}
}
