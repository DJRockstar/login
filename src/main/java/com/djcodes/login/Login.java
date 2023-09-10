package com.djcodes.login;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        //Get all parameters from req
        String uemail = req.getParameter("username");
        String upwd = req.getParameter("password");
        RequestDispatcher dispatcher = null;
        HttpSession session = req.getSession();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginapp", "root", "Spring123");
            //Query
            String q = "select * from users where uemail=? AND upwd=?";

            PreparedStatement pst = con.prepareStatement(q);
            pst.setString(1, uemail);
            pst.setString(2, upwd);

            //since the query is Select type we use executeQuery method
            ResultSet set = pst.executeQuery();

            if (set.next()){
                //means there's a user in the db with above uemail and upwd
                //we session obj to make it non null in index.jsp
                session.setAttribute("name", set.getString("uname"));
                dispatcher = req.getRequestDispatcher("index.jsp");
            }
            else {
                req.setAttribute("status", "failed");
                dispatcher = req.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

















