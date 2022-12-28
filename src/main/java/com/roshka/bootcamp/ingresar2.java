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

@WebServlet("/ingresar2")
public class ingresar2 extends HttpServlet {
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
        RequestDispatcher rd = req.getRequestDispatcher("./WEB-INF/ingresar2/ingresar2.html");
        rd.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        try {
            String nombreCliente = req.getParameter("nombreCliente");
            String apellidoCliente = req.getParameter("apellidoCliente");
            String nroDeCedula = req.getParameter("nro_de_cedula");
            String telefono = req.getParameter("telefono");
            PreparedStatement stmt = DBManagment.con.prepareStatement("INSERT INTO cliente (id, nombre, apellido, nro_cedula, telefono) VALUES (default, ?, ?, ?, ?);");
            stmt.setString(1, nombreCliente);
            stmt.setString(2, apellidoCliente);
            stmt.setString(3, nroDeCedula);
            stmt.setString(4, telefono);
            stmt.executeUpdate();
            stmt.close();
            resp.sendRedirect("./consulta2");
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
