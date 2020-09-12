package Controller;

import Service.WaresService;
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
public class WaresController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/sjon;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //Entity manager 
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("gazdalkodjokosanPU");
            EntityManager em = emf.createEntityManager();
            
            //Wares service példány 
            WaresService ws = new WaresService();
            //Üres JSONobject 
            JSONObject answer = new JSONObject();
            
            // Új áru hozzáadása
            if(request.getParameter("task").equals("addNewWare")){
             if(!request.getParameter("object").isEmpty() && !request.getParameter("price").isEmpty()) {
                   String object = request.getParameter("object");
                   Integer price = Integer.parseInt(request.getParameter("price"));
                   
                   if(ws.addNewWare(object, price, em)){
                       answer.put("msg", "Sikeresen hozzáadtad a filmet");
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
            
            //Összes áru kilistázása
            if(request.getParameter("task").equals("selectAllWares")){
                JSONArray result = ws.selectAllWares(em);
                out.print(result.toString());
            }
            
            //Áru módosítása
            if(request.getParameter("task").equals("updateOneWare")){
                   String object = request.getParameter("object");
                   Integer price = Integer.parseInt(request.getParameter("price"));
                   Integer id = Integer.parseInt(request.getParameter("id"));
                   
                   if(ws.updateOneWare(object, price, id, em)){
                       answer.put("msg", "Sikeres módosítás");
                   }
                   else{
                       answer.put("msg", "Hiba");
                   }
                   out.print(answer.toString());
            }
            
            
            //Áru törlése
            if(request.getParameter("task").equals("deleteOneWare")){
               Integer id = Integer.parseInt(request.getParameter("id"));
               if(ws.deleteOneWare(id, em)){
                    answer.put("msg", "Sikeres törlés");
               }
               else{
                   answer.put("msg", "Hiba");
               }
               out.print(answer.toString());
            }
            
            //3 Legdrágább áru kilistázása
            if(request.getParameter("task").equals("selectTop3Wares")){
                JSONArray result = ws.selectTop3Wares(em);
                out.print(result.toString());
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
