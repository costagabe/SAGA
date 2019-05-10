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
@Table(name = "cadastro_oficina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CadastroOficina.findAll", query = "SELECT c FROM CadastroOficina c")
    , @NamedQuery(name = "CadastroOficina.findByMatriculaApenado", query = "SELECT c FROM CadastroOficina c where c.apenado.matricula = :matricula")
    , @NamedQuery(name = "CadastroOficina.findByIdcadastroOficina", query = "SELECT c FROM CadastroOficina c WHERE c.idcadastroOficina = :idcadastroOficina")
    , @NamedQuery(name = "CadastroOficina.findByDataEntradaOficina", query = "SELECT c FROM CadastroOficina c WHERE c.dataEntradaOficina = :dataEntradaOficina")
    , @NamedQuery(name = "CadastroOficina.findByDataSaidaOficina", query = "SELECT c FROM CadastroOficina c WHERE c.dataSaidaOficina = :dataSaidaOficina")})
public class CadastroOficina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcadastro_oficina")
    private Integer idcadastroOficina;
    @Basic(optional = false)
    @Column(name = "dataEntradaOficina")
    @Temporal(TemporalType.DATE)
    private Date dataEntradaOficina;
    @Column(name = "dataSaidaOficina")
    @Temporal(TemporalType.DATE)
    private Date dataSaidaOficina;
    @JoinColumn(name = "apenado", referencedColumnName = "idapenado")
    @ManyToOne(optional = false)
    private Apenado apenado;
    @JoinColumn(name = "oficina", referencedColumnName = "idoficina")
    @ManyToOne(optional = false)
    private Oficina oficina;

    public CadastroOficina() {
    }

    public CadastroOficina(Integer idcadastroOficina) {
        this.idcadastroOficina = idcadastroOficina;
    }

    public CadastroOficina(Integer idcadastroOficina, Date dataEntradaOficina) {
        this.idcadastroOficina = idcadastroOficina;
        this.dataEntradaOficina = dataEntradaOficina;
    }

    public Integer getIdcadastroOficina() {
        return idcadastroOficina;
    }

    public void setIdcadastroOficina(Integer idcadastroOficina) {
        this.idcadastroOficina = idcadastroOficina;
    }

    public Date getDataEntradaOficina() {
        return dataEntradaOficina;
    }

    public void setDataEntradaOficina(Date dataEntradaOficina) {
        this.dataEntradaOficina = dataEntradaOficina;
    }

    public Date getDataSaidaOficina() {
        return dataSaidaOficina;
    }

    public void setDataSaidaOficina(Date dataSaidaOficina) {
        this.dataSaidaOficina = dataSaidaOficina;
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
        hash += (idcadastroOficina != null ? idcadastroOficina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CadastroOficina)) {
            return false;
        }
        CadastroOficina other = (CadastroOficina) object;
        if ((this.idcadastroOficina == null && other.idcadastroOficina != null) || (this.idcadastroOficina != null && !this.idcadastroOficina.equals(other.idcadastroOficina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.CadastroOficina[ idcadastroOficina=" + idcadastroOficina + " ]";
    }
    
}
