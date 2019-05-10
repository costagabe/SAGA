/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gabriel
 */
@Entity
@Table(name = "presenca_oficina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PresencaOficina.findAll", query = "SELECT p FROM PresencaOficina p")
    , @NamedQuery(name = "PresencaOficina.findByIdpresencaOficina", query = "SELECT p FROM PresencaOficina p WHERE p.idpresencaOficina = :idpresencaOficina")
    , @NamedQuery(name = "PresencaOficina.findByData", query = "SELECT p FROM PresencaOficina p WHERE p.data = :data")
    , @NamedQuery(name = "PresencaOficina.findByJustificativaFalta", query = "SELECT p FROM PresencaOficina p WHERE p.justificativaFalta = :justificativaFalta")
    , @NamedQuery(name = "PresencaOficina.findBySituacao", query = "SELECT p FROM PresencaOficina p WHERE p.situacao = :situacao")})
public class PresencaOficina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpresenca_oficina")
    private Integer idpresencaOficina;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "justificativaFalta")
    private String justificativaFalta;
    @Basic(optional = false)
    @Column(name = "situacao")
    private int situacao;
    @JoinColumn(name = "apenado", referencedColumnName = "idapenado")
    @ManyToOne(optional = false)
    private Apenado apenado;
    @JoinColumn(name = "oficina", referencedColumnName = "idoficina")
    @ManyToOne(optional = false)
    private Oficina oficina;

    public PresencaOficina() {
    }

    public PresencaOficina(Integer idpresencaOficina) {
        this.idpresencaOficina = idpresencaOficina;
    }

    public PresencaOficina(Integer idpresencaOficina, Date data, int situacao) {
        this.idpresencaOficina = idpresencaOficina;
        this.data = data;
        this.situacao = situacao;
    }

    public Integer getIdpresencaOficina() {
        return idpresencaOficina;
    }

    public void setIdpresencaOficina(Integer idpresencaOficina) {
        this.idpresencaOficina = idpresencaOficina;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getJustificativaFalta() {
        return justificativaFalta;
    }

    public void setJustificativaFalta(String justificativaFalta) {
        this.justificativaFalta = justificativaFalta;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public Apenado getApenado() {
        return apenado;
    }

    public void setApenado(Apenado apenado) {
        this.apenado = apenado;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public void setOficina(Oficina oficina) {
        this.oficina = oficina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpresencaOficina != null ? idpresencaOficina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresencaOficina)) {
            return false;
        }
        PresencaOficina other = (PresencaOficina) object;
        if ((this.idpresencaOficina == null && other.idpresencaOficina != null) || (this.idpresencaOficina != null && !this.idpresencaOficina.equals(other.idpresencaOficina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.PresencaOficina[ idpresencaOficina=" + idpresencaOficina + " ]";
    }
    
}
