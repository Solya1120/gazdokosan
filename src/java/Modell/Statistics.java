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
import javax.xml.bind.annotation.XmlRootElement;

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
    , @NamedQuery(name = "Statistics.findByRank", query = "SELECT s FROM Statistics s WHERE s.rank = :rank")
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
    @Column(name = "rank")
    private int rank;
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

    public Statistics(Integer id, int rank) {
        this.id = id;
        this.rank = rank;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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
