package controller;

import java.io.IOException;
import java.math.BigDecimal; // for prices
import java.sql.SQLException; // what's thrown when the JDBC operations fail
import java.util.TreeSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.ProductDAO;
import model.ProductPojo;

@WebServlet("/dataVerifier")
public class DataVerifier extends HttpServlet {
	private static final int MinLength = 3;

	public DataVerifier() {
		System.out.println("===========================================================================");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) {

		System.out.println("Data verifier.");
		verifyUserInputsAndUpdateDB(req, res);

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("Samo tuk ne sam bil.");
		/**
		 * The 'nuclear option': throw new
		 * HTTPException(HttpServletResponse.SC_METHOD_NOT_ALLOWED); // HTTP 405
		 */
		// sendResponse(req, res, "Only POST requests are accepted.", true);
	}

	private void verifyUserInputsAndUpdateDB(HttpServletRequest req, HttpServletResponse res) {
		
		System.out.println("veriffying user.");
		String productId = req.getParameter("id");
		String productName = req.getParameter("name");
		String price = req.getParameter("price");
		String description = req.getParameter("description");
		String categoryId = req.getParameter("categoryId");
		String brandId = req.getParameter("brandId");
		String availability = req.getParameter("availability");
		String imageUrl = req.getParameter("imageUrl");

		// data verification checks
		if (productName == null || price == null || description == null || categoryId == null || brandId == null
				|| availability == null || imageUrl == null) { // bad
			// inputs?
			
			System.out.println(productName);
			System.out.println(price);
			System.out.println(description);
			System.out.println(categoryId);
			System.out.println(brandId);
			System.out.println(availability);
			System.out.println(imageUrl);
			
			
			sendResponse(req, res, "One or more bad input items.", true);
			return;
		}

		if (productName.length() < MinLength) {
			sendResponse(req, res, "A product's name must be at least " + MinLength + " characters.", true);
			return;
		}	

		BigDecimal priceBD = convertPrice(price);
		if (priceBD == null) {
			sendResponse(req, res, "Price entered contains invalid characters: +/-, ., and decimal digits only.", true);
			return;
		}

		if (!categoryId.matches("^-?\\d+$") && Integer.parseInt(categoryId) > 0) {
			sendResponse(req, res,
					"A category id must be a positive integer and should not contain any special characters.", true);
			return;
		}

		if (!brandId.matches("^-?\\d+$") && Integer.parseInt(brandId) > 0) {
			sendResponse(req, res, "A brand id must be an integer and should not contain any special characters.",
					true);
			return;
		}

		if (!availability.matches("^-?\\d+$") && Integer.parseInt(availability) >= 0) {
			sendResponse(req, res, "Availability must be an integer and should not contain any special characters.",
					true);
			return;
		}

		// Capitalize product name
		productName = capitalizeParts(productName);
		System.out.println("The product name " + productName
				+ "=====================================================================");

		if (productId == null) { // create rather than edit
			if (productNameInUse(productName, req, res))
				sendResponse(req, res, "The name '" + productName + "' is already in use.", true);
			else if (handleCreate(productName, priceBD, description, Integer.parseInt(categoryId),
					Integer.parseInt(brandId), Integer.parseInt(availability), imageUrl))
				sendResponse(req, res, productName + " added to the DB.", false);
			else
				sendResponse(req, res, "Problem saving " + productName + " to the DB", true);
		} else { // edit rather than create
			if (handleEdit(req.getParameter("productId"), productName, priceBD, description,
					Integer.parseInt(categoryId), Integer.parseInt(brandId), Integer.parseInt(availability), imageUrl))
				sendResponse(req, res, productName + " updated successfully.", false);
			else
				sendResponse(req, res, "Problem updating " + productName, true);
		}
	}

	private boolean handleCreate(String productName, BigDecimal priceBD, String description, int categoryId,
			int brandId, int availability, String imageUrl) {
		boolean flag = true;
		try {
		 ProductDAO.getInstance().addProduct(productName, priceBD, description, categoryId, brandId, availability,
					imageUrl);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return flag;
	}

	private boolean handleEdit(String productId, String productName, BigDecimal priceBD, String description,
			int categoryId, int brandId, int availability, String imageUrl) {
		try {
			ProductDAO.getInstance().handleEdit(productId, productName, priceBD, description, categoryId, brandId,
					availability, imageUrl);
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}

	private void sendResponse(HttpServletRequest req, HttpServletResponse res, String msg, boolean error) {

		try {
			HttpSession session = req.getSession();
			session.setAttribute("result", msg);
			if (error)
				res.sendRedirect("admin/badResult.jsp");
			else
				res.sendRedirect("admin/goodResult.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private BigDecimal convertPrice(String price) {
		BigDecimal result = null;
		try {
			result = new BigDecimal(price);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String capitalizeParts(String str) {
		if (str.length() < 1)
			return str;
		String[] parts = str.split(" "); // split on blanks
		String result = "";

		for (String part : parts) {
			if (part.length() > 0)
				result += new String(Character.toUpperCase(part.charAt(0)) + part.substring(1) + " ");
		}
		result = result.trim();
		return result;
	}

	private boolean productNameInUse(String productName, HttpServletRequest req, HttpServletResponse resp) {
		boolean flag = false;
		TreeSet<ProductPojo> products = null;
		try {
			products = ProductDAO.getInstance().getAllProducts();

			for (ProductPojo product : products) {
				if (product.getName().equals(productName)) {
					flag = true;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// System.out.println(products);
           // for testing purposes
		/*
		 * ServletContext context = getServletContext(); PrintWriter out = null;
		 * try { out = resp.getWriter(); } catch (IOException e1) {
		 * e1.printStackTrace(); } Enumeration e =
		 * req.getSession().getServletContext().getAttributeNames(); while
		 * (e.hasMoreElements()) { String name = (String) e.nextElement();
		 * out.println(name + "---" + context.getAttribute(name));
		 * System.out.println(context.getAttribute(name)); } for (ProductPojo
		 * product : products) { if
		 * (product.getName().equalsIgnoreCase(productName)) { flag = true;
		 * break; } }
		 */
		return flag;
	}
}
