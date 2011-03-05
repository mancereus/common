package de.db12.common.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.inject.Singleton;

@Singleton
public class GuiceServlet extends HttpServlet {
	private static final long serialVersionUID = -3205702127744606596L;

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("GuiceServlet.init");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GuiceServlet.doGet");
	}
}