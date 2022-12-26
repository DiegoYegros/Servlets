package com.roshka.bootcamp;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/consulta2")
public class consulta2 extends HttpServlet {
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
                    .executeQuery("SELECT moneda.nombre, count(moneda.nombre) cantidad_de_facturas \n" +
                            "FROM moneda INNER JOIN factura ON moneda.id=factura.moneda_id\n" +
                            "GROUP BY (moneda.nombre)\n" +
                            "ORDER BY (cantidad_de_facturas) DESC;\n");
            out.println("<html>");
            out.println("<body>");
            out.println("<h2>TOP MONEDAS MAS UTILIZADAS</h2>");
            out.println("<a href='./index.jsp'>VOLVER AL MENU</a>");
            while (rs.next()) {
                String moneda_nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad_de_facturas");

                out.println("<p>NOMBRE = " + moneda_nombre + "</p>");
                out.println("<p>CANTIDAD DE APARICIONES = " + cantidad + "</p>");

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
