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
import java.util.Objects;

@WebServlet("/ingresar1")
public class ingresar1 extends HttpServlet {
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
        RequestDispatcher rd = req.getRequestDispatcher("./WEB-INF/ingresar1/ingresar1.html");
        rd.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String nombre_moneda = req.getParameter("nombreMoneda");
        if (Objects.equals(nombre_moneda, "")){
            return;
        }
        try {
            resp.sendRedirect("./consulta1");
            PreparedStatement stmt = DBManagment.con.prepareStatement("INSERT INTO moneda (id, nombre) VALUES (default, ?);");
            stmt.setString(1, nombre_moneda);
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
