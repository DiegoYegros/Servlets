package com.roshka.bootcamp;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/consulta2")
public class consulta2 extends HttpServlet {
    public void init() {
        try {
            DBManagment.initializeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {

        try {
            Statement stmt = DBManagment.con.createStatement();
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            out.println("<html>");
            out.print(" <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
                    "        integrity=\"sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65\" crossorigin=\"anonymous\">");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<a href='./index.jsp' class='h2 text-primary'>VOLVER AL MENU</a>");
            out.println("<div class='row'>");
            ResultSet rs = stmt
                    .executeQuery(
                            " SELECT cliente.nombre, cliente.apellido, cliente.nro_cedula, cliente.telefono\n" +
                                    " FROM cliente;");

            out.println("<div class='h3 text-primary col-md-12 col-sm-12'>DATOS DEL CLIENTE</div>");
            out.println("<div class='col-md-3 col-sm-12 text-primary'>NOMBRE</div>");
            out.println("<div class='col-md-3 col-sm-12 text-primary'>APELLIDO</div>");
            out.println("<div class='col-md-3 col-sm-12 text-primary'>CEDULA</div>");
            out.println("<div class='col-md-3 col-sm-12 text-primary'>TELEFONO</div>");
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String nro_cedula = rs.getString("nro_cedula");
                String telefono = rs.getString("telefono");
                out.println("<div class='col-md-3 col-sm-12'>"+nombre+"</div>");
                out.println("<div class='col-md-3 col-sm-12'>"+apellido+"</div>");
                out.println("<div class='col-md-3 col-sm-12'>"+nro_cedula+"</div>");
                out.println("<div class='col-md-3 col-sm-12'>"+telefono+"</div>");
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
            DBManagment.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
