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
@Table(name = "presenca_curso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PresencaCurso.findAll", query = "SELECT p FROM PresencaCurso p")
    , @NamedQuery(name = "PresencaCurso.findByIdPresencaCurso", query = "SELECT p FROM PresencaCurso p WHERE p.idPresencaCurso = :idPresencaCurso")
    , @NamedQuery(name = "PresencaCurso.findByData", query = "SELECT p FROM PresencaCurso p WHERE p.data = :data")
    , @NamedQuery(name = "PresencaCurso.findBySituacao", query = "SELECT p FROM PresencaCurso p WHERE p.situacao = :situacao")
    , @NamedQuery(name = "PresencaCurso.findByJustificativaFalta", query = "SELECT p FROM PresencaCurso p WHERE p.justificativaFalta = :justificativaFalta")})
public class PresencaCurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPresencaCurso")
    private Integer idPresencaCurso;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @Column(name = "situacao")
    private int situacao;
    @Column(name = "justificativaFalta")
    private String justificativaFalta;
    @JoinColumn(name = "apenado", referencedColumnName = "idapenado")
    @ManyToOne(optional = false)
    private Apenado apenado;
    @JoinColumn(name = "curso", referencedColumnName = "idcurso")
    @ManyToOne(optional = false)
    private Curso curso;

    public PresencaCurso() {
    }

    public PresencaCurso(Integer idPresencaCurso) {
        this.idPresencaCurso = idPresencaCurso;
    }

    public PresencaCurso(Integer idPresencaCurso, Date data, int situacao) {
        this.idPresencaCurso = idPresencaCurso;
        this.data = data;
        this.situacao = situacao;
    }

    public Integer getIdPresencaCurso() {
        return idPresencaCurso;
    }

    public void setIdPresencaCurso(Integer idPresencaCurso) {
        this.idPresencaCurso = idPresencaCurso;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public String getJustificativaFalta() {
        return justificativaFalta;
    }

    public void setJustificativaFalta(String justificativaFalta) {
        this.justificativaFalta = justificativaFalta;
    }

    public Apenado getApenado() {
        return apenado;
    }

    public void setApenado(Apenado apenado) {
        this.apenado = apenado;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPresencaCurso != null ? idPresencaCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresencaCurso)) {
            return false;
        }
        PresencaCurso other = (PresencaCurso) object;
        if ((this.idPresencaCurso == null && other.idPresencaCurso != null) || (this.idPresencaCurso != null && !this.idPresencaCurso.equals(other.idPresencaCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.PresencaCurso[ idPresencaCurso=" + idPresencaCurso + " ]";
    }
    
}
