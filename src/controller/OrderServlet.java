package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.OrderPojo;
import model.UserPojo;

@WebServlet("/sortOrders")
public class OrderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sort = request.getParameter("sort");
		Object o = request.getSession().getAttribute("user");
		if (o == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		UserPojo u = (UserPojo) o;
		TreeSet<OrderPojo> set = new TreeSet<>(new Comparator<OrderPojo>() {
			@Override
			public int compare(OrderPojo o1, OrderPojo o2) {

				if (sort.equals("asc")) {
					if (o1.getDate().isBefore(o2.getDate())) {
						return -1;
					} else if (o2.getDate().isBefore(o1.getDate())) {
						return 1;
					}
				} else {
					if (sort.equals("desc")) {
						if (o1.getDate().isBefore(o2.getDate())) {
							return 1;
						} else if (o2.getDate().isBefore(o1.getDate())) {
							return -1;
						}
					}
				}
				return 0;
			}
		});

		set.addAll(u.getOrders());
		u.setOrders(set);

		request.getRequestDispatcher("orders.jsp").forward(request, response);
	}
}