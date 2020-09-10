package Service;


import Modell.Statistics;
import Modell.User;
import java.util.List;
import javax.persistence.EntityManager;
import org.json.JSONArray;

public class StatisticsService {
    // Új statisztika hozzáadása
    public  boolean addNewStatistics(Integer rank, Integer user, Integer totalScore, EntityManager em){
         if(rank > 0 && user > 0 && totalScore > 0 ){
            if(Statistics.addNewStatistics(rank, user, totalScore, em)){
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
    public boolean updateOneStatistic(User user, Integer rank,Integer totalScore, Integer id, EntityManager em){
        try{
        Statistics s = em.find(Statistics.class, id);
        
            em.getTransaction().begin();
            s.setUser(user);
            s.setRank(rank);
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
}
