package nl.hu.v1wac.firstapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/Calculatenumber.do")
public class Calculate extends HttpServlet
{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String first = req.getParameter("firstNumber");
		String second = req.getParameter("secondNumber");
		String type = req.getParameter("type");

		if (!tryParseInt(first))
			return;

		if (!tryParseInt(second))
			return;

		if (!tryParseInt(type))
			return;

		int firstNumber = Integer.parseInt(first);
		int secondNumber = Integer.parseInt(second);
		int typeNumber = Integer.parseInt(type);

		int result = 0;
		switch (typeNumber)
		{
			case 0:
			{
				result = firstNumber + secondNumber;
				break;
			}
			case 1:
			{
				result = firstNumber - secondNumber;
				break;
			}
			case 2:
			{
				result = firstNumber * secondNumber;
				break;
			}
			case 3:
			{
				result = firstNumber / secondNumber;
				break;
			}
		}

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println(" <title>Dynamic Example</title>");
		out.println(" <body>");
	
		out.println(" <h1>  " + (result) + " </h1>");
		out.println(" </body>");
		out.println("</html>");
	}

	private boolean tryParseInt(String value)
	{
		try
		{
			Integer.parseInt(value);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
}