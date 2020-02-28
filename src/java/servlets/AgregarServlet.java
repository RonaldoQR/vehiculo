package servlets;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AgregarServlet extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            PrintWriter out = response.getWriter();
            //String id_vehiculo = request.getParameter("id_vehiculo");
            String marca = request.getParameter("marca");
            double precio = Double.parseDouble(request.getParameter("precio"));
            String color = request.getParameter("color");
            String year = request.getParameter("year");
           //
           
           Class.forName("com.mysql.cj.jdbc.Driver");
           String url = "jdbc:mysql://localhost/tesla?user=root&password=mysqladmin";
           Connection connect = DriverManager.getConnection(url);
           String query = "select max(id_vehiculo) + 1 as new_id FROM vehiculo";
           Statement statement = connect.createStatement();
           ResultSet resultSet = statement.executeQuery(query);
           
           int idProd = 0;
           while(resultSet.next()) {
               idProd = resultSet.getInt("new_id");
           }
           if(idProd == 0) {
               idProd = 1;
           }
           
           query = "INSERT INTO vehiculo VALUES (?,?,?,?,?)";
           PreparedStatement ps = connect.prepareStatement(query);
           
           
           ps.setInt(1, idProd);
           ps.setString(2,marca);
           ps.setDouble(3, precio);
           ps.setString(4,color);
           ps.setString(5,year);
           ps.executeUpdate();
           /*String query = "insert vehiculo values (?,?,?,?)";
           PreparedStatement ps = connect.prepareStatement(query);
           ps.setInt(1, Integer.parseInt(id_vehiculo));
           ps.setString(2, marca);
           ps.setDouble(3, precio);
           ps.setString(4, color);
           ps.executeUpdate();*/
           
           JsonObject gson = new JsonObject();
           gson.addProperty("mensaje", "Vehiculo agregado.");
           out.print(gson.toString());
        }catch(Exception e){
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
