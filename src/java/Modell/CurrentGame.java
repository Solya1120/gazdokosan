
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Table(name = "current_game")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CurrentGame.findAll", query = "SELECT c FROM CurrentGame c")
    , @NamedQuery(name = "CurrentGame.findById", query = "SELECT c FROM CurrentGame c WHERE c.id = :id")
    , @NamedQuery(name = "CurrentGame.findByColor", query = "SELECT c FROM CurrentGame c WHERE c.color = :color")
    , @NamedQuery(name = "CurrentGame.findByBalance", query = "SELECT c FROM CurrentGame c WHERE c.balance = :balance")
    , @NamedQuery(name = "CurrentGame.findBySumBalance", query = "SELECT c FROM CurrentGame c WHERE c.sumBalance = :sumBalance")})
public class CurrentGame implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "color")
    private String color;
    @Column(name = "balance")
    private Integer balance;
    @Column(name = "sum_balance")
    private Integer sumBalance;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public CurrentGame() {
    }

    public CurrentGame(Integer id) {
        this.id = id;
    }

    public CurrentGame(Integer id, String color) {
        this.id = id;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getSumBalance() {
        return sumBalance;
    }

    public void setSumBalance(Integer sumBalance) {
        this.sumBalance = sumBalance;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
    
    //Új játékos létrehozása
    public static boolean addNewPlayer(Integer userId, String color, Integer balance,Integer sumBalance, EntityManager em){
        try{
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewPlayer");
            spq.registerStoredProcedureParameter("userIdIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("colorIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("balanceIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("sumBalanceIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("userIdIN", userId);
            spq.setParameter("colorIN", color);
            spq.setParameter("balanceIN", balance);
            spq.setParameter("sumBalanceIN", sumBalance);
            
            spq.execute();
            em.close();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    
    }
    
    
    //Összes játékos kilistázása    
    public static List<CurrentGame> selectAllPlayer(EntityManager em){
        List<CurrentGame> players = new ArrayList();
        StoredProcedureQuery tarolt = em.createStoredProcedureQuery("selectAllPlayer");
        List<Object[]> list = tarolt.getResultList();
        for(Object[] player : list){
            int id= Integer.parseInt(player[0].toString());
            CurrentGame cg = em.find(CurrentGame.class, id);
            players.add(cg);
        }
        em.close();
        return players;
    
    }
    
    // JSON formátumu returnölés
    public JSONObject toJson(){
        JSONObject j = new JSONObject();
        j.put("id",this.id);
        j.put("userId",this.userId);
        j.put("color",this.color);
        j.put("balance",this.balance);
        j.put("sumBalance",this.sumBalance);
        
        return j;
    }
    
    //Játékos törlése
    public boolean deleteOnePlayer(Integer id, EntityManager em){
        try{
            StoredProcedureQuery spq = em.createStoredProcedureQuery("deleteOnePlayer");
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
    
    //Egy játékos kilistázása
    public static CurrentGame selectOnePlayer(Integer id, EntityManager em){
        CurrentGame player = new CurrentGame();
        StoredProcedureQuery spq = em.createStoredProcedureQuery("selectOnePlayer");
        spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
        spq.setParameter("idIN",id);
       
        
        player = em.find(CurrentGame.class, id);
        spq.execute();
        em.close();
        return player;
    
    }

    
    //Játékosok aktuális ranglistája készpénzt kilistázása    
    public static List<CurrentGame> selectAllPlayerDESC(EntityManager em){
        List<CurrentGame> players = new ArrayList();
        StoredProcedureQuery tarolt = em.createStoredProcedureQuery("selectAllPlayerDESC");
        List<Object[]> list = tarolt.getResultList();
        for(Object[] player : list){
            int id= Integer.parseInt(player[0].toString());
            CurrentGame cg = em.find(CurrentGame.class, id);
            players.add(cg);
        }
        em.close();
        return players;
    
    }
    
    
    //Aktuális játékos adatai felhasználó név alapján
    public static List<Object[]> checkPlayerByUsername(String username, EntityManager em){
        StoredProcedureQuery spq = em.createStoredProcedureQuery("checkPlayerByUsername");
        spq.registerStoredProcedureParameter("usernameIN", String.class, ParameterMode.IN);
        spq.setParameter("usernameIN", username);
        List<Object[]> list = spq.getResultList();
        return list;
    }
    
    
   
    //Azok a felhasználók, akik éppen nincsenek játékban
    public static List<Object[]> selectUserNotPlayer(EntityManager em){
        StoredProcedureQuery spq = em.createStoredProcedureQuery("selectUserNotPlayer");
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
        if (!(object instanceof CurrentGame)) {
            return false;
        }
        CurrentGame other = (CurrentGame) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.CurrentGame[ id=" + id + " ]";
    }
    
}
