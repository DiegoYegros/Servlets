package com.roshka.bootcamp;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/consulta3")
public class consulta3 extends HttpServlet {
    Connection connection;
    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/bootcamp_market",
                            "postgres", "postgres");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            Statement stmt = connection.createStatement();
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            ResultSet rs = stmt
                    .executeQuery("SELECT producto.nombre, ROUND(SUM(factura_detalle.cantidad)) cantidad FROM producto\n" +
                            "INNER JOIN factura_detalle ON factura_detalle.producto_id = producto.id \n" +
                            "GROUP BY (producto.nombre)\n" +
                            "ORDER BY (cantidad) DESC;\n");
            out.println("<html>");
            out.println("<body>");
            out.println("<h2>PRODUCTOS MAS VENDIDOS</h2>");
            out.println("<a href='./index.jsp'>VOLVER AL MENU</a>");
            while (rs.next()) {
                String producto_nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");

                out.println("<p>NOMBRE DEL PRODUCTO = " + producto_nombre + "</p>");
                out.println("<p>CANTIDAD DE FACTURAS = " + cantidad + "</p>");

            }
            out.println("</body>");
            out.println("</html>");
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }


    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
