package Controller;

import Service.ScoreService;
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

public class ScoreController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //Entity Manager
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("gazdalkodjokosanPU");
            EntityManager em = emf.createEntityManager();
            
            //ScoreService példány
            ScoreService ss = new ScoreService();
            //Üres JSONobject amit a frontendnek küldünk vissza
            JSONObject answer = new JSONObject();
            
            // Új film hozzáadása
            if(request.getParameter("task").equals("addNewScore")){
             if(!request.getParameter("score").isEmpty() && !request.getParameter("rank").isEmpty()) {
                   Integer score = Integer.parseInt(request.getParameter("score"));
                   Integer rank = Integer.parseInt(request.getParameter("rank"));
                   
                   if(ss.addNewScore(score, rank, em)){
                       answer.put("msg", "Sikeresen hozzáadtad a pontszámot");
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
            
            //Összes pontszám kiírása
            if(request.getParameter("task").equals("selectAllScore")){
                JSONArray result = ss.selectAllScore(em);
                out.print(result.toString());
            }
            
            //Pontszám módosítása
            if(request.getParameter("task").equals("updateOneScore")){
                   Integer score = Integer.parseInt(request.getParameter("score"));
                   Integer id = Integer.parseInt(request.getParameter("id"));
                   Integer rank = Integer.parseInt(request.getParameter("rank"));

                   
                   if(ss.updateOneScore(score, rank, id, em)){
                       answer.put("msg", "Sikeres módosítás");
                   }
                   else{
                       answer.put("msg", "Hiba");
                   }
                   out.print(answer.toString());
            }
            
            //Pontszám törlése
            
            if(request.getParameter("task").equals("deleteOneScore")){
               Integer id = Integer.parseInt(request.getParameter("id"));
               if(ss.deleteOneScore(id, em)){
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
