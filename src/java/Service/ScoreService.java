package Service;

import Modell.Score;
import java.util.List;
import javax.persistence.EntityManager;
import org.json.JSONArray;

public class ScoreService {
    //Új pontszám hozzáadása
    public  boolean addNewScore(Integer score, Integer rank, EntityManager em){
            if(Score.addNewScore(score, rank, em)){
                return true;
            }
            else{
                return false;
            }
    }
    
    //Összes pontszám kiírása
    public JSONArray selectAllScore(EntityManager em){
        List<Score> scores = Score.selectAllScore(em);
        JSONArray allScore = new JSONArray();
        for(Score score : scores){
            allScore.put(score.toJson());
        }
        return allScore;
    }
    
    //Pontszám módosítása
    public boolean updateOneScore(Integer score, Integer rank, Integer id, EntityManager em){
        try{
        Score s = em.find(Score.class, id);

            em.getTransaction().begin();
            s.setScore(score);
            s.setRank(rank);
            em.getTransaction().commit();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
    
    //Pontszám törlése
    public boolean deleteOneScore(Integer id, EntityManager em){
        Score s = new Score();
        if(s.deleteOneScore(id, em)){
            return true;
        }
        else{
            return false;
        }
        
    }
}
