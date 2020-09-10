package Service;

import Modell.Wares;
import java.util.List;
import javax.persistence.EntityManager;
import org.json.JSONArray;

public class WaresService {
    // Új film hozzáadása
    public  boolean addNewWare(String object, Integer price, EntityManager em){
         if(object.length() > 4 && price > 1000){
            if(Wares.addNewWare(object, price, em)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    //Összes áru kilistázása
    public JSONArray selectAllWares(EntityManager em){
        List<Wares> wares = Wares.selectAllWares(em);
        JSONArray allWares = new JSONArray();
        for(Wares ware : wares){
            allWares.put(ware.toJson());
        }
        return allWares;
    }
    
    
    //Áru módosítása
    public boolean updateOneWare(String object, Integer price, Integer id, EntityManager em){
        try{
        Wares w = em.find(Wares.class, id);

            em.getTransaction().begin();
            w.setObject(object);
            w.setPrice(price);
            em.getTransaction().commit();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
    
    //Áru törlése
     public boolean deleteOneWare(Integer id, EntityManager em){
        Wares w = new Wares();
        if(w.deleteOneWare(id, em)){
            return true;
        }
        else{
            return false;
        }
        
    }
}
