package Controller;

import Modell.User;
import Service.StatisticsService;
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

public class StatisticsController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           //Entity manager 
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("gazdalkodjokosanPU");
            EntityManager em = emf.createEntityManager();
            
            //Statistics service példány
            StatisticsService ss = new StatisticsService();
            //Üres JSONobject amit a frontendnek küldünk vissza
            JSONObject answer = new JSONObject();
            
            // Új statisztika hozzáadása
            if(request.getParameter("task").equals("addNewStatistics")){
             if(!request.getParameter("rank").isEmpty() && !request.getParameter("user").isEmpty() && !request.getParameter("totalScore").isEmpty()) {
                   Integer rank = Integer.parseInt(request.getParameter("rank"));
                   Integer user = Integer.parseInt(request.getParameter("user"));
                   Integer totalScore = Integer.parseInt(request.getParameter("totalScore"));

                   
                   if(ss.addNewStatistics(rank, user, totalScore, em)){
                       answer.put("msg", "Sikeresen hozzáadtad a statisztikát");
                   }
                   else{
                       answer.put("msg", "Hiba");
                   }  
                }
                else{
                    answer.put("msg", "Hiba");
                }
                out.print(answer.toString());
            } 
            
            
            //Összes statisztika kilistázása
            if(request.getParameter("task").equals("selectAllStatistics")){
                JSONArray result = ss.selectAllStatistics(em);
                out.print(result.toString());
            }
            
            
            
            //Statisztika módosítása
            if(request.getParameter("task").equals("updateOneStatistic")){
                    User user = new User();
                   
                   user.setId(Integer.parseInt(request.getParameter("user"))) ;
                   Integer id = Integer.parseInt(request.getParameter("id"));
                   Integer rank = Integer.parseInt(request.getParameter("rank"));
                   Integer totalScore = Integer.parseInt(request.getParameter("totalScore"));
                   
                   
                   if(ss.updateOneStatistic(user, rank, totalScore, id, em)){
                       answer.put("msg", "Sikeres módosítás");
                   }
                   else{
                       answer.put("msg", "Hiba");
                   }
                   out.print(answer.toString());
            }
            
            //Statisztika törlése
            if(request.getParameter("task").equals("deleteOneStatistic")){
               Integer id = Integer.parseInt(request.getParameter("id"));
               if(ss.deleteOneStatistic(id, em)){
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
