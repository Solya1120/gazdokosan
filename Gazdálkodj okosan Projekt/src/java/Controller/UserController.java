package Controller;

import Service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //Entity Manager létrehozása
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("gazdalkodjokosanPU");
            EntityManager em = emf.createEntityManager();
            
            //UserService példány
            UserService us = new UserService();
            
            //Üres JSONObject
            JSONObject answer = new JSONObject();
            
            
            //Új felhasználó létrehozása
            if(request.getParameter("task").equals("addNewUser")){
                if(!request.getParameter("username").isEmpty() && !request.getParameter("password").isEmpty()){
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    if(us.addNewUser(username, password, em)){
                        answer.put("msg", "Sikeresen létrehozta az új felhasználót!");
                    }
                    else{
                        answer.put("msg", "Hiba");
                    }
                }
                else {
                    answer.put("msg", "Hiba");
                }
                out.print(answer.toString());
            }
            
            
            //Összes felhasználó kilistázása
            if(request.getParameter("task").equals("selectAllUser")){
                JSONArray result = us.selectAllUser(em);
                out.print(result.toString());
            }
            
            //Felhasználó adatainak módosítása
            if(request.getParameter("task").equals("updateOneUser")){
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                Integer id = Integer.parseInt(request.getParameter("id"));
                
                if(us.updateOneUser(username, password, id, em)){
                    answer.put("msg", "Sikeres módosítás");
                }
                else{
                    answer.put("msg", "Sikertelen módosítás");
                }
                out.print(answer.toString());
            }
            
            
            //Felhasználó törlése
            if(request.getParameter("task").equals("deleteOneUser")){
               Integer id = Integer.parseInt(request.getParameter("id"));
               if(us.deleteOneUser(id, em)){
                    answer.put("msg", "Sikeres törlés");
               }
               else{
                    answer.put("msg", "Hiba");
               }
               out.print(answer.toString());
            }
            
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
