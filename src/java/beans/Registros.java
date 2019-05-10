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
@Table(name = "registros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registros.findAll", query = "SELECT r FROM Registros r")
    , @NamedQuery(name = "Registros.findByIdregistros", query = "SELECT r FROM Registros r WHERE r.idregistros = :idregistros")
    , @NamedQuery(name = "Registros.findByDataEntrada", query = "SELECT r FROM Registros r WHERE r.dataEntrada = :dataEntrada")
    , @NamedQuery(name = "Registros.findByDataSaida", query = "SELECT r FROM Registros r WHERE r.dataSaida = :dataSaida")
    , @NamedQuery(name = "Registros.findByMatriculaApenado", query = "SELECT r FROM Registros r WHERE r.apenado.matricula = :matricula")
    , @NamedQuery(name = "Registros.findByMotivoSaida", query = "SELECT r FROM Registros r WHERE r.motivoSaida = :motivoSaida")})
public class Registros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idregistros")
    private Integer idregistros;
    @Column(name = "dataEntrada")
    @Temporal(TemporalType.DATE)
    private Date dataEntrada;
    @Column(name = "dataSaida")
    @Temporal(TemporalType.DATE)
    private Date dataSaida;
    @Basic(optional = false)
    @Column(name = "motivoSaida")
    private String motivoSaida;
    @JoinColumn(name = "apenado", referencedColumnName = "idapenado")
    @ManyToOne(optional = false)
    private Apenado apenado;

    public Registros() {
    }

    public Registros(Integer idregistros) {
        this.idregistros = idregistros;
    }

    public Registros(Integer idregistros, String motivoSaida) {
        this.idregistros = idregistros;
        this.motivoSaida = motivoSaida;
    }

    public Integer getIdregistros() {
        return idregistros;
    }

    public void setIdregistros(Integer idregistros) {
        this.idregistros = idregistros;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getMotivoSaida() {
        return motivoSaida;
    }

    public void setMotivoSaida(String motivoSaida) {
        this.motivoSaida = motivoSaida;
    }

    public Apenado getApenado() {
        return apenado;
    }

    public void setApenado(Apenado apenado) {
        this.apenado = apenado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idregistros != null ? idregistros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registros)) {
            return false;
        }
        Registros other = (Registros) object;
        if ((this.idregistros == null && other.idregistros != null) || (this.idregistros != null && !this.idregistros.equals(other.idregistros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Registros[ idregistros=" + idregistros + " ]";
    }
    
}
