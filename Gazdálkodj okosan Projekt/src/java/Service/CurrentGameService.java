package Service;

import Modell.CurrentGame;
import Modell.User;
import java.util.List;
import javax.persistence.EntityManager;
import org.json.JSONArray;
import org.json.JSONObject;

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
    
    
    //Játékosok aktuális ranglistája készpénzt kilistázása    
    public JSONArray selectAllPlayerDESC(EntityManager em){
        List<CurrentGame> players = CurrentGame.selectAllPlayerDESC(em);
        JSONArray ranking = new JSONArray();
        for(CurrentGame player : players){
            ranking.put(player.toJson());
        }
        return ranking;
    }
    
    
    //Játékos módosítása
    public boolean updateOnePlayer(User userId, String color, Integer balance,Integer sumBalance, Integer id, EntityManager em){
        try{
        CurrentGame cg = em.find(CurrentGame.class, id);
        
        if(balance <= sumBalance){
            em.getTransaction().begin();
            cg.setBalance(balance);
            cg.setSumBalance(sumBalance);
            cg.setUserId(userId);
            cg.setColor(color); 
            em.getTransaction().commit();
            return true;
        } else {
            return false;
        }   
            
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
    
    //Egy játékos kiírása
    public JSONObject selectOnePlayer(Integer id, EntityManager em){
        CurrentGame player = CurrentGame.selectOnePlayer(id,em);
        JSONObject p = player.toJson();
        return p;
    }

    //Aktuális játékos adatai felhasználó név alapján
    public List<Object[]> checkPlayerByUsername(String username, EntityManager em){
        return CurrentGame.checkPlayerByUsername(username, em);
    }
    
    //Azok a felhasználók, akik éppen nincsenek játékban
    public List<Object[]> selectUserNotPlayer( EntityManager em){
        return CurrentGame.selectUserNotPlayer(em);
    }
    
    
}
