package com.roshka.bootcamp;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/ingresar3")
public class ingresar3 extends HttpServlet {
    public void init() {
        try {
            DBManagment.initializeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("./WEB-INF/ingresar3/ingresar3.html");
        rd.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String pais = req.getParameter("nombrePais");
        if (pais == null){
            return;
        }
        try {
            resp.sendRedirect("./consulta3");
            PreparedStatement stmt = DBManagment.con.prepareStatement("INSERT INTO pais (id, nombre) VALUES (default, ?);");
            stmt.setString(1, pais);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void destroy() {
        try {
            DBManagment.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
