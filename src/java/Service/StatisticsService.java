package Service;


import Modell.Statistics;
import Modell.User;
import java.util.List;
import javax.persistence.EntityManager;
import org.json.JSONArray;
import org.json.JSONObject;

public class StatisticsService {
    // Új statisztika hozzáadása
    public  boolean addNewStatistics( Integer user, Integer totalScore, EntityManager em){
         if(user > 0 && totalScore > 0 ){
            if(Statistics.addNewStatistics(user, totalScore, em)){
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
    
    //Összes statisztika kilistázása
    public JSONArray selectAllStatistics(EntityManager em){
        List<Statistics> statistics = Statistics.selectAllStatistics(em);
        JSONArray allStatistics = new JSONArray();
        for(Statistics statistic : statistics){
            allStatistics.put(statistic.toJson());
        }
        return allStatistics;
    }
    
    //Statisztika módosítása
    public boolean updateOneStatistic(User user,Integer totalScore, Integer id, EntityManager em){
        try{
        Statistics s = em.find(Statistics.class, id);
        
            em.getTransaction().begin();
            s.setUser(user);
            s.setTotalScore(totalScore);
            em.getTransaction().commit();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
    
    
    //Statisztika törlése
    public boolean deleteOneStatistic(Integer id, EntityManager em){
        Statistics s = new Statistics();
        if(s.deleteOneStatistic(id, em)){
            return true;
        }
        else{
            return false;
        }   
    }
    
    
    //Egy statisztika kiírása
    public JSONObject selectOneStatistics(Integer id, EntityManager em){
        Statistics s = Statistics.selectOneStatistics(id, em);
        JSONObject st = s.toJson();
        return st;
    }
    
    //Ranglista kilistázása
    public JSONArray selectAllStatisticsDESC(EntityManager em){
        List<Statistics> statistics = Statistics.selectAllStatisticsDESC(em);
        JSONArray ranking = new JSONArray();
        for(Statistics statistic : statistics){
            ranking.put(statistic.toJson());
        }
        return ranking;
    }
    
    
    //Top 3 felhasználó nevekkel
    public List<Object[]> joinTop3Statistics( EntityManager em){
        return Statistics.joinTop3Statistics(em);
    }
    
    
}
