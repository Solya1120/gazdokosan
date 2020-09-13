package Service;

import Modell.User;
import java.util.List;
import javax.persistence.EntityManager;
import org.json.JSONArray;

public class UserService {
    //Új felhasználó létrehozása
    public boolean addNewUser(String username, String password, EntityManager em){
        if(username.length() > 4 && password.length() > 8){
            if(User.addNewUser(username, password, em)){
                return true;
            }
            else{
                return false;
            }
        } else {
            return false;
        }
    }
    
    
    //Összes felhasználó kilistázása
    public JSONArray selectAllUser(EntityManager em){
        List<User> users = User.selectAllUser(em);
        JSONArray allUser = new JSONArray();
        for(User user : users){
            allUser.put(user.toJson());
        }
        return allUser;
    }
    
    //Felhazsnáló adatainak módosítása
    public boolean updateOneUser(String username, String password, Integer id, EntityManager em){
        try{
            User u = em.find(User.class, id);
            
            if(username.length() > 4 && password.length() > 8){
                em.getTransaction().begin();
                u.setUsername(username);
                u.setPassword(password);
                em.getTransaction().commit();
                return true;
            }
            else{
                return false;
            }
            
        }
        catch(Exception ex){
            return false;
        }
    }
    
    //Felhasználó törlése
    public boolean deleteOneUser(Integer id, EntityManager em){
        User u = new User();
        if(u.deleteOneUser(id, em)){
            return true;
        }
        else{
            return false;
        }
    }
}