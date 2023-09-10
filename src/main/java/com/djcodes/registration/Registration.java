package com.djcodes.registration;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name ="register", value ="/register") //val should be same as the form action="" in reg jsp
public class Registration extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        //Now we need to caputre all the params from reg jsp and store in a variable where name should be
        //same as the column inside the database

        String uname = req.getParameter("name");
        String uemail = req.getParameter("email");
        String upwd = req.getParameter("pass");
        String umobile = req.getParameter("contact");

        RequestDispatcher dispatcher = null;
        //Create a JDBC Connection
        //loader

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginapp", "root", "Spring123");
            //Query
            String q = "insert into users(uname, upwd, uemail, umobile) values(?,?,?,?)";
            //tell con to create a prepared statement
            PreparedStatement pst = con.prepareStatement(q);
            //set values
            pst.setString(1, uname);
            pst.setString(2,upwd);
            pst.setString(3, uemail);
            pst.setString(4, umobile);

            //executeUpdate since it's a non-Select query
            int rowCount = pst.executeUpdate();

            dispatcher = req.getRequestDispatcher("registration.jsp");
            if (rowCount>0){
                //We redirect to registration page with a message for that we need request dispatcher
                req.setAttribute("status", "success");

            }else {
                req.setAttribute("status", "failed");
            }
            dispatcher.forward(req,resp);

        } catch (Exception e) {
            e.printStackTrace();
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
        }
    }
}






















