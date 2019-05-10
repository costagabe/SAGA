/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gabriel
 */
@Entity
@Table(name = "cadastro_curso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadastroCurso.findAll", query = "SELECT c FROM CadastroCurso c"),
    @NamedQuery(name = "CadastroCurso.findByIdcadastroCurso", query = "SELECT c FROM CadastroCurso c WHERE c.idcadastroCurso = :idcadastroCurso")})
public class CadastroCurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcadastro_curso")
    private Integer idcadastroCurso;
    @JoinColumn(name = "apenado", referencedColumnName = "idapenado")
    @ManyToOne(optional = false)
    private Apenado apenado;
    @JoinColumn(name = "curso", referencedColumnName = "idcurso")
    @ManyToOne(optional = false)
    private Curso curso;

    public CadastroCurso() {
    }

    public CadastroCurso(Integer idcadastroCurso) {
        this.idcadastroCurso = idcadastroCurso;
    }

    public Integer getIdcadastroCurso() {
        return idcadastroCurso;
    }

    public void setIdcadastroCurso(Integer idcadastroCurso) {
        this.idcadastroCurso = idcadastroCurso;
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
        hash += (idcadastroCurso != null ? idcadastroCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadastroCurso)) {
            return false;
        }
        CadastroCurso other = (CadastroCurso) object;
        if ((this.idcadastroCurso == null && other.idcadastroCurso != null) || (this.idcadastroCurso != null && !this.idcadastroCurso.equals(other.idcadastroCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.CadastroCurso[ idcadastroCurso=" + idcadastroCurso + " ]";
    }
    
}
