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
                    .executeQuery("SELECT moneda.nombre, count(moneda.nombre) cantidad_de_monedas \n" +
                            "FROM moneda " +
                            "GROUP BY (moneda.nombre)\n" +
                            "ORDER BY (cantidad_de_monedas) DESC;\n");

            out.println("<html>");
            out.print(" <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
                    "        integrity=\"sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65\" crossorigin=\"anonymous\">");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<a href='./index.jsp'>VOLVER AL MENU</a>");
            out.println("<div class='row'>");
            out.println("<div class='h3 text-primary col-md-12 col-sm-12'>MONEDAS</div>");
            out.println("<div class='col-md-6 col-sm-12 text-primary'>NOMBRE</div>");
            out.println("<div class='col-md-6 col-sm-12 text-primary'>CANTIDAD DE APARICIONES</div>");
            out.println("</div>");
            while (rs.next()) {
                String moneda_nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad_de_monedas");
                out.println("<div class='row'>");
                out.println("<div class='col-md-6 col-sm-12'>"+moneda_nombre+"</div>");
                out.println("<div class='col-md-6 col-sm-12'>"+cantidad+"</div>");
                out.println("</div>");
            }
            out.println("</div>");
            out.println("</div>");
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
