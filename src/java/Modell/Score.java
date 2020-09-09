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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONObject;


@Entity
@Table(name = "score")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Score.findAll", query = "SELECT s FROM Score s")
    , @NamedQuery(name = "Score.findById", query = "SELECT s FROM Score s WHERE s.id = :id")
    , @NamedQuery(name = "Score.findByScore", query = "SELECT s FROM Score s WHERE s.score = :score")
    , @NamedQuery(name = "Score.findByRank", query = "SELECT s FROM Score s WHERE s.rank = :rank")})
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    private int score;
    @Column(name = "rank")
    private Integer rank;

    public Score() {
    }

    public Score(Integer id) {
        this.id = id;
    }

    public Score(Integer id, int score) {
        this.id = id;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
//Új pontszám hozzáadása
    public static boolean addNewScore(Integer score, Integer rank, EntityManager em){
        try{
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewScore");
            spq.registerStoredProcedureParameter("scoreIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("rankIN", Integer.class, ParameterMode.IN);
            
            
            spq.setParameter("scoreIN", score);
            spq.setParameter("rankIN", rank);
            
            spq.execute();
            em.close();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    
    }
    
    //Összes pontszám kiírása
    
    public static List<Score> selectAllScore(EntityManager em){
        List<Score> scores = new ArrayList();
        StoredProcedureQuery tarolt = em.createStoredProcedureQuery("selectAllScore");
        List<Object[]> list = tarolt.getResultList();
        for(Object[] score : list){
            int id= Integer.parseInt(score[0].toString());
            Score s = em.find(Score.class, id);
            scores.add(s);
        }
        em.close();
        return scores;
    
    }
    
    // JSON formátumu returnölés
    public JSONObject toJson(){
        JSONObject j = new JSONObject();
        j.put("id",this.id);
        j.put("score",this.score);
        j.put("rank",this.rank);

        return j;
    }
    
    //Pontszám törlése
    public boolean deleteOneScore(Integer id, EntityManager em){
        try{
            StoredProcedureQuery spq = em.createStoredProcedureQuery("deleteOneScore");
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Score)) {
            return false;
        }
        Score other = (Score) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Score[ id=" + id + " ]";
    }
    
}
