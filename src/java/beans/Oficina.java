/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "oficina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oficina.findAll", query = "SELECT o FROM Oficina o")
    , @NamedQuery(name = "Oficina.findByIdoficina", query = "SELECT o FROM Oficina o WHERE o.idoficina = :idoficina")
    , @NamedQuery(name = "Oficina.findByNomeOficina", query = "SELECT o FROM Oficina o WHERE o.nomeOficina = :nomeOficina")
    , @NamedQuery(name = "Oficina.findByTipoOficina", query = "SELECT o FROM Oficina o WHERE o.tipoOficina = :tipoOficina")
    , @NamedQuery(name = "Oficina.findByMonitor", query = "SELECT o FROM Oficina o WHERE o.monitor = :monitor")
    , @NamedQuery(name = "Oficina.findByTempo", query = "SELECT o FROM Oficina o WHERE o.tempo = :tempo")})
public class Oficina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idoficina")
    private Integer idoficina;
    @Basic(optional = false)
    @Column(name = "nomeOficina")
    private String nomeOficina;
    @Column(name = "tipoOficina")
    private String tipoOficina;
    @Column(name = "monitor")
    private String monitor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tempo")
    private Double tempo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "oficina")
    private List<PresencaOficina> presencaOficinaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "oficina")
    private List<CadastroOficina> cadastroOficinaList;

    public Oficina() {
    }

    public Oficina(Integer idoficina) {
        this.idoficina = idoficina;
    }

    public Oficina(Integer idoficina, String nomeOficina) {
        this.idoficina = idoficina;
        this.nomeOficina = nomeOficina;
    }

    public Integer getIdoficina() {
        return idoficina;
    }

    public void setIdoficina(Integer idoficina) {
        this.idoficina = idoficina;
    }

    public String getNomeOficina() {
        return nomeOficina;
    }

    public void setNomeOficina(String nomeOficina) {
        this.nomeOficina = nomeOficina;
    }

    public String getTipoOficina() {
        return tipoOficina;
    }

    public void setTipoOficina(String tipoOficina) {
        this.tipoOficina = tipoOficina;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public Double getTempo() {
        return tempo;
    }

    public void setTempo(Double tempo) {
        this.tempo = tempo;
    }

    @XmlTransient
    public List<PresencaOficina> getPresencaOficinaList() {
        return presencaOficinaList;
    }

    public void setPresencaOficinaList(List<PresencaOficina> presencaOficinaList) {
        this.presencaOficinaList = presencaOficinaList;
    }

    @XmlTransient
    public List<CadastroOficina> getCadastroOficinaList() {
        return cadastroOficinaList;
    }

    public void setCadastroOficinaList(List<CadastroOficina> cadastroOficinaList) {
        this.cadastroOficinaList = cadastroOficinaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idoficina != null ? idoficina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oficina)) {
            return false;
        }
        Oficina other = (Oficina) object;
        if ((this.idoficina == null && other.idoficina != null) || (this.idoficina != null && !this.idoficina.equals(other.idoficina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Oficina[ idoficina=" + idoficina + " ]";
    }

    public Object getCadastroAtivoOficinaList() {
        List<CadastroOficina> ret = new ArrayList<>();
        for (CadastroOficina co : cadastroOficinaList) {
            if (co.getDataSaidaOficina() == null && (co.getApenado().getRegistrosList().get(0).getDataSaida() == null) ) {
                ret.add(co);
            }
        }
        return ret;
    }

}
