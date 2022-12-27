package com.roshka.bootcamp;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/consulta1")
public class consulta1 extends HttpServlet {
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
                    .executeQuery(" SELECT cliente.nombre, SUM(ROUND(producto.precio*factura_detalle.cantidad)) gasto\n" +
                            " FROM cliente INNER JOIN factura ON cliente.id=factura.cliente_id\n" +
                            " INNER JOIN factura_detalle ON factura.id = factura_detalle.factura_id\n" +
                            " INNER JOIN producto ON producto.id= factura_detalle.producto_id\n" +
                            " GROUP BY (cliente.nombre) \n" +
                            " ORDER BY (gasto) DESC;");
            out.println("<html>");
            out.print(" <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
                    "        integrity=\"sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65\" crossorigin=\"anonymous\">");
            out.println("<body>");
            out.println("<div class='h3 text-primary'>TOP CLIENTES QUE MAS GASTARON</div>");
            out.println("<a href='./index.jsp'>VOLVER AL MENU</a>");
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("gasto");
                out.println("<p>NOMBRE = \\" + nombre + "</p>");
                out.println("<p>CANTIDAD FACTURA = \\" + cantidad + "</p>");

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
