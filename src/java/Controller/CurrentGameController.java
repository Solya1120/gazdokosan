package Controller;

import Modell.User;
import Service.CurrentGameService;
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

public class CurrentGameController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //Entity manager 
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("gazdalkodjokosanPU");
            EntityManager em = emf.createEntityManager();
            
            //CurrenGame service példány 
            CurrentGameService cgs = new CurrentGameService();
            //Üres JSONobject 
            JSONObject answer = new JSONObject();
            
            
            // Új játékos hozzáadása
            if(request.getParameter("task").equals("addNewPlayer")){
             if(!request.getParameter("userId").isEmpty() && !request.getParameter("color").isEmpty() && !request.getParameter("balance").isEmpty() && !request.getParameter("sumBalance").isEmpty()) {
                   String color = request.getParameter("color");
                   Integer userId = Integer.parseInt(request.getParameter("userId"));
                   Integer balance = Integer.parseInt(request.getParameter("balance"));
                   Integer sumBalance = Integer.parseInt(request.getParameter("sumBalance"));

                   if(cgs.addNewPlayer(userId, color, balance, sumBalance, em)){
                       answer.put("msg", "Sikeresen hozzáadtad a játékost");
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
            
            
            //Összes játékos kilistázása
            if(request.getParameter("task").equals("selectAllPlayer")){
                JSONArray result = cgs.selectAllPlayer(em);
                out.print(result.toString());
            }
            
            
            
            
              //Játékos módosítása
            if(request.getParameter("task").equals("updateOnePlayer")){
                    User user = new User();
                    
                   String color = request.getParameter("color");
                   
                   user.setId(Integer.parseInt(request.getParameter("userId"))) ;
                   Integer id = Integer.parseInt(request.getParameter("id"));
                   Integer balance = Integer.parseInt(request.getParameter("balance"));
                   Integer sumBalance = Integer.parseInt(request.getParameter("sumBalance"));
                   
                   
                   if(cgs.updateOnePlayer(user, color, balance, sumBalance, id, em)){
                       answer.put("msg", "Sikeres módosítás");
                   }
                   else{
                       answer.put("msg", "Hiba");
                   }
                   out.print(answer.toString());
            }
            
            
            //Játékos törlése
            if(request.getParameter("task").equals("deleteOnePlayer")){
               Integer id = Integer.parseInt(request.getParameter("id"));
               if(cgs.deleteOnePlayer(id, em)){
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
