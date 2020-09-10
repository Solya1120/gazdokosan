package Service;

import Modell.CurrentGame;
import Modell.User;
import java.util.List;
import javax.persistence.EntityManager;
import org.json.JSONArray;

public class CurrentGameService {
     // Új játékos hozzáadása
    public  boolean addNewPlayer(Integer userId, String color, Integer balance,Integer sumBalance, EntityManager em){
         if(userId > 0 && (color.length() > 2) && balance > 0 && sumBalance >= balance){
            if(CurrentGame.addNewPlayer(userId, color, balance, sumBalance, em)){
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
    
    //Összes játékos kilistázása
    public JSONArray selectAllPlayer(EntityManager em){
        List<CurrentGame> players = CurrentGame.selectAllPlayer(em);
        JSONArray allPlayers = new JSONArray();
        for(CurrentGame player : players){
            allPlayers.put(player.toJson());
        }
        return allPlayers;
    }
    
    
    
    //Játékos módosítása
    public boolean updateOnePlayer(User userId, String color, Integer balance,Integer sumBalance, Integer id, EntityManager em){
        try{
        CurrentGame cg = em.find(CurrentGame.class, id);
        
            em.getTransaction().begin();
            cg.setUserId(userId);
            cg.setColor(color);
            cg.setBalance(balance);
            cg.setSumBalance(sumBalance);
            em.getTransaction().commit();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
    
    
    
    //Játékos törlése
    public boolean deleteOnePlayer(Integer id, EntityManager em){
        CurrentGame cg = new CurrentGame();
        if(cg.deleteOnePlayer(id, em)){
            return true;
        }
        else{
            return false;
        }
        
    }
    
    
}
