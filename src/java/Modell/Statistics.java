/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONObject;

/**
 *
 * @author solya
 */
@Entity
@Table(name = "statistics")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Statistics.findAll", query = "SELECT s FROM Statistics s")
    , @NamedQuery(name = "Statistics.findById", query = "SELECT s FROM Statistics s WHERE s.id = :id")
    , @NamedQuery(name = "Statistics.findByTotalScore", query = "SELECT s FROM Statistics s WHERE s.totalScore = :totalScore")})
public class Statistics implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_score")
    private Integer totalScore;
    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;

    public Statistics() {
    }

    public Statistics(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //Új statisztika hozzáadása
    public static boolean addNewStatistics(Integer user, Integer totalScore, EntityManager em){
        try{
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewStatistics");
            spq.registerStoredProcedureParameter("userIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("totalScoreIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("userIN", user);
            spq.setParameter("totalScoreIN", totalScore);
            
            spq.execute();
            em.close();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    
    }
    
    
    //Összes statisztika kilistázása
    
    public static List<Statistics> selectAllStatistics(EntityManager em){
        List<Statistics> statistics = new ArrayList();
        StoredProcedureQuery tarolt = em.createStoredProcedureQuery("selectAllStatistics");
        List<Object[]> list = tarolt.getResultList();
        for(Object[] statistic : list){
            int id= Integer.parseInt(statistic[0].toString());
            Statistics s = em.find(Statistics.class, id);
            statistics.add(s);
        }
        em.close();
        return statistics;
    
    }
    
    // JSON formátumu returnölés
    public JSONObject toJson(){
        JSONObject j = new JSONObject();
        j.put("id",this.id);
        j.put("user",this.user);
        j.put("totalScore",this.totalScore);

        return j;
    }
    
    //Statisztika törlése
    public boolean deleteOneStatistic(Integer id, EntityManager em){
        try{
            StoredProcedureQuery spq = em.createStoredProcedureQuery("deleteOneStatistic");
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("idIN",id);
            
            spq.execute();
            em.close();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
    
    //Egy statisztika kiírása
    public static Statistics selectOneStatistics(Integer id, EntityManager em){
        Statistics s = new Statistics();
        StoredProcedureQuery spq = em.createStoredProcedureQuery("selectOneStatistics");
        spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
        spq.setParameter("idIN",id);
       
        
        s = em.find(Statistics.class, id);
        spq.execute();
        em.close();
        return s;
    
    }
    
    //Ranglista kilistázása    
    public static List<Statistics> selectAllStatisticsDESC(EntityManager em){
        List<Statistics> statistics = new ArrayList();
        StoredProcedureQuery tarolt = em.createStoredProcedureQuery("selectAllStatisticsDESC");
        List<Object[]> list = tarolt.getResultList();
        for(Object[] statistic : list){
            int id= Integer.parseInt(statistic[0].toString());
            Statistics s = em.find(Statistics.class, id);
            statistics.add(s);
        }
        em.close();
        return statistics;
    
    }
    
    
    //Top 3 felhasználó nevekkel
    public static List<Object[]> joinTop3Statistics(EntityManager em){
        StoredProcedureQuery spq = em.createStoredProcedureQuery("joinTop3Statistics");
        List<Object[]> list = spq.getResultList();
        return list;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Statistics)) {
            return false;
        }
        Statistics other = (Statistics) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Statistics[ id=" + id + " ]";
    }
    
}
