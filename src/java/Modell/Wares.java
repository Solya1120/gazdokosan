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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONObject;

@Entity
@Table(name = "wares")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wares.findAll", query = "SELECT w FROM Wares w")
    , @NamedQuery(name = "Wares.findById", query = "SELECT w FROM Wares w WHERE w.id = :id")
    , @NamedQuery(name = "Wares.findByObject", query = "SELECT w FROM Wares w WHERE w.object = :object")
    , @NamedQuery(name = "Wares.findByPrice", query = "SELECT w FROM Wares w WHERE w.price = :price")})
public class Wares implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "object")
    private String object;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private int price;

    public Wares() {
    }

    public Wares(Integer id) {
        this.id = id;
    }

    public Wares(Integer id, String object, int price) {
        this.id = id;
        this.object = object;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    

    //Új áru hozzáadása
    public static boolean addNewWare(String object, Integer price, EntityManager em){
        try{
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewWare");
            spq.registerStoredProcedureParameter("objectIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("priceIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("objectIN", object);
            spq.setParameter("priceIN", price);
            
            spq.execute();
            em.close();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    
    }
    
    
    
    //Áruk kilistázása
    
    public static List<Wares> selectAllWares(EntityManager em){
        List<Wares> wares = new ArrayList();
        StoredProcedureQuery tarolt = em.createStoredProcedureQuery("selectAllWares");
        List<Object[]> list = tarolt.getResultList();
        for(Object[] ware : list){
            int id= Integer.parseInt(ware[0].toString());
            Wares w = em.find(Wares.class, id);
            wares.add(w);
        }
        em.close();
        return wares;
    
    }
    
    // JSON formátumu returnölés
    public JSONObject toJson(){
        JSONObject j = new JSONObject();
        j.put("id",this.id);
        j.put("object",this.object);
        j.put("price",this.price);
       
        return j;
    }
    
    //Áru törlése
     public boolean deleteOneWare(Integer id, EntityManager em){
        try{
            StoredProcedureQuery spq = em.createStoredProcedureQuery("deleteOneWare");
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
        if (!(object instanceof Wares)) {
            return false;
        }
        Wares other = (Wares) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Wares[ id=" + id + " ]";
    }
    
}
