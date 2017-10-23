package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.BrandDAO;
import db.CategoryDAO;
import db.ProductDAO;
import db.UserDAO;
import model.BrandPojo;
import model.CategoryPojo;
import model.ProductPojo;
import model.UserPojo;
import util.PasswordUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// check for login credentials

		System.out.println("did I get here");
		String email = request.getParameter("user");
		String password = request.getParameter("pass");
		System.out.println(email);
		System.out.println(password);
		// check if user exists in db
		UserPojo user = null;
		try {
			String hashedPassword = PasswordUtil.hashPassword(password);
			user = new UserPojo(email, hashedPassword);
			System.out.println(hashedPassword);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}

		try {
			boolean exists = UserDAO.getInstance().userExistsEmailAndPassword(user);
			if (exists) {
				// update session
				UserPojo u = UserDAO.getInstance().getUser(user);
				request.getSession().setAttribute("user", u);
				ServletContext application = getServletConfig().getServletContext();
				synchronized (application) {
					if (application.getAttribute("brands") == null) {
						Map<Integer, BrandPojo> brands = BrandDAO.getInstance().getAllBrands();
						application.setAttribute("brands", brands);
					}
					if (application.getAttribute("categories") == null) {
						Map<Integer, CategoryPojo> categories = CategoryDAO.getInstance().getAllCategories();
						application.setAttribute("categories", categories);
					}
					if (application.getAttribute("products") == null) {
						TreeSet<ProductPojo> products = ProductDAO.getInstance().getAllProducts();
				//		System.out.println(products);
						application.setAttribute("products", products);
					}
					if (application.getAttribute("users") == null) {
						TreeSet<ProductPojo> users = ProductDAO.getInstance().getAllProducts();
						application.setAttribute("users", users);
					}
					
					
					ServletContext context = getServletContext();
					PrintWriter out = null;
					try {
						out = response.getWriter();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Enumeration e = request.getSession().getServletContext().getAttributeNames();
					while (e.hasMoreElements()) {
						String name = (String) e.nextElement();
						out.println(name + "---" + context.getAttribute(name));
						System.out.println(name + "---" + context.getAttribute(name));
					}
				}
				request.getRequestDispatcher("main.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "user does not exist");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage() + e.getErrorCode());
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
