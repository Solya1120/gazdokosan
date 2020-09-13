package Modell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.json.JSONObject;


@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id")
    , @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByRegTime", query = "SELECT u FROM User u WHERE u.regTime = :regTime")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reg_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regTime;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<CurrentGame> currentGameCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Statistics> statisticsCollection;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String username, String password, Date regTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.regTime = regTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    @XmlTransient
    public Collection<CurrentGame> getCurrentGameCollection() {
        return currentGameCollection;
    }

    public void setCurrentGameCollection(Collection<CurrentGame> currentGameCollection) {
        this.currentGameCollection = currentGameCollection;
    }

    @XmlTransient
    public Collection<Statistics> getStatisticsCollection() {
        return statisticsCollection;
    }

    public void setStatisticsCollection(Collection<Statistics> statisticsCollection) {
        this.statisticsCollection = statisticsCollection;
    }

    
    /////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    
    //Új felhasználó hozzáadása
    public static boolean addNewUser(String username, String password, EntityManager em){
        try{
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewUser");
            spq.registerStoredProcedureParameter("usernameIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("passwordIN", String.class, ParameterMode.IN);
            
            spq.setParameter("usernameIN", username);
            spq.setParameter("passwordIN", password);
            
            spq.execute();
            em.close();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
    
    //Összes felhasználó adatainak lekérése
    public static List<User> selectAllUser(EntityManager em){
        List<User> users = new ArrayList();
        StoredProcedureQuery tarolt = em.createStoredProcedureQuery("selectAllUser");
        List<Object[]> list = tarolt.getResultList();
        for(Object[] user : list){
            int id = Integer.parseInt(user[0].toString());
            User u = em.find(User.class, id);
            users.add(u);
        }
        em.close();
        return users;
    }
    
    // JSON formátumu returnölés

    public JSONObject toJson(){
        JSONObject j = new JSONObject();
        j.put("id", this.id);
        j.put("username", this.username);
        j.put("password", this.password);
        j.put("regTime", this.regTime);
        return j;
    }
    
    public boolean deleteOneUser(Integer id, EntityManager em){
        try{
            StoredProcedureQuery spq = em.createStoredProcedureQuery("deleteOneUser");
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.User[ id=" + id + " ]";
    }
    
}
