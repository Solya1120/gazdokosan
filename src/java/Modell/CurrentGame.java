/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modell;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author solya
 */
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
