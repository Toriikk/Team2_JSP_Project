package com.wclass.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wmember.model.WFavoriteDAO;
import com.wmember.model.WFavoriteDTO;

/**
 * Servlet implementation class WFavoriteCheck
 */
@WebServlet("/class/favoriteCheck")
public class WFavoriteCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WFavoriteCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 RequestDispatcher rd=request.getRequestDispatcher("courseDetail.jsp");
         rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        int classnum=Integer.parseInt(request.getParameter("classnum"));
        String userid=request.getParameter("userid");
        WFavoriteDAO dao=WFavoriteDAO.getInstance();
        int flag=dao.favoriteCheck(classnum, userid); //이미 userid에 classnum이 동일하게 있다면 flag=1;
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println(flag);
	}

}
