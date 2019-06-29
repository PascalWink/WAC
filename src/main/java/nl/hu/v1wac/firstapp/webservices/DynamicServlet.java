package nl.hu.v1wac.firstapp.webservices;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/DynamicServlet.do")
public class DynamicServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int uitkomst = 0;
		String one = req.getParameter("one");
		String two = req.getParameter("two");
		String reken = req.getParameter("reken");
		if (reken.equals(":")) {
			uitkomst = Integer.parseInt(one) / Integer.parseInt(two);
		}
		if (reken.equals("+")) {
			uitkomst = Integer.parseInt(one) + Integer.parseInt(two);
		}
		if (reken.equals("-")) {
			uitkomst = Integer.parseInt(one) - Integer.parseInt(two);
		}
		if (reken.equals("x")) {
			uitkomst = Integer.parseInt(one) * Integer.parseInt(two);
		}
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println(" <title>De uitkomst</title>");
		out.println(" <body>");
		out.println(" <h2>De uitkomst van de rekenmachine <br>" + "is: " + uitkomst + "!</h2>");
		out.println(" </body>");
		out.println("</html>");
	}
}