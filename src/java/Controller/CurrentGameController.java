package Controller;

import Modell.User;
import Service.CurrentGameService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
            
            //Egy játékos kirása
            if(request.getParameter("task").equals("selectOnePlayer")){
                Integer id = Integer.parseInt(request.getParameter("id"));
                JSONObject result = cgs.selectOnePlayer(id,em);
                out.print(result.toString());
            }
            
            
            
            //Ranglista kilistázása
            if(request.getParameter("task").equals("selectAllPlayerDESC")){
                JSONArray result = cgs.selectAllPlayerDESC(em);
                out.print(result.toString());
            }
            
            
            //Aktuális játékos adatai felhasználó név alapján
            if(request.getParameter("task").equals("checkPlayerByUsername")){
                String username = request.getParameter("username");
                List<Object[]> list = cgs.checkPlayerByUsername(username, em);
                JSONArray valasz =new JSONArray();
                if(list.size() > 0){
                    for(Object[] o : list){
                        JSONObject player = new JSONObject();
                        player.put("Username",o[0].toString());
                        player.put("Color",o[1].toString());
                        player.put("Balance",o[2].toString());
                        valasz.put(player);
                    }
                } 
                else{
                    valasz.put("Nincs ilyen nevű felhasználó");
                }
                out.print(valasz.toString());
            }
            
            
            
            //Azok a felhasználók, akik éppen nincsenek játékban
            if(request.getParameter("task").equals("selectUserNotPlayer")){
                List<Object[]> list = cgs.selectUserNotPlayer(em);
                JSONArray valasz = new JSONArray();
                if(list.size() > 0){
                    for(Object[] o : list){
                        JSONObject users = new JSONObject();
                        users.put("username",o[0].toString());
                        valasz.put(users);
                    }
                }
                else{
                    valasz.put("Minden felhasználó játékban van.");
                }
                out.print(valasz.toString());
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
