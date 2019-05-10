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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gabriel
 */
@Entity
@Table(name = "dados_penitenciarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DadosPenitenciarios.findAll", query = "SELECT d FROM DadosPenitenciarios d")
    , @NamedQuery(name = "DadosPenitenciarios.findByIddadosPenitenciarios", query = "SELECT d FROM DadosPenitenciarios d WHERE d.iddadosPenitenciarios = :iddadosPenitenciarios")
    , @NamedQuery(name = "DadosPenitenciarios.findByArtigo", query = "SELECT d FROM DadosPenitenciarios d WHERE d.artigo = :artigo")
    , @NamedQuery(name = "DadosPenitenciarios.findByTempoCadeia", query = "SELECT d FROM DadosPenitenciarios d WHERE d.tempoCadeia = :tempoCadeia")})
public class DadosPenitenciarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddados_penitenciarios")
    private Integer iddadosPenitenciarios;
    @Column(name = "artigo")
    private String artigo;
    @Column(name = "tempoCadeia")
    private String tempoCadeia;
    @OneToOne(mappedBy = "dadosPenitenciarios")
    private Apenado apenado;
    @JoinColumn(name = "penitenciaria", referencedColumnName = "idpenitenciaria")
    @ManyToOne
    private Penitenciaria penitenciaria;

    public DadosPenitenciarios() {
    }

    public DadosPenitenciarios(Integer iddadosPenitenciarios) {
        this.iddadosPenitenciarios = iddadosPenitenciarios;
    }

    public Integer getIddadosPenitenciarios() {
        return iddadosPenitenciarios;
    }

    public void setIddadosPenitenciarios(Integer iddadosPenitenciarios) {
        this.iddadosPenitenciarios = iddadosPenitenciarios;
    }

    public String getArtigo() {
        return artigo;
    }

    public void setArtigo(String artigo) {
        this.artigo = artigo;
    }

    public String getTempoCadeia() {
        return tempoCadeia;
    }

    public void setTempoCadeia(String tempoCadeia) {
        this.tempoCadeia = tempoCadeia;
    }

    public Apenado getApenado() {
        return apenado;
    }

    public void setApenado(Apenado apenado) {
        this.apenado = apenado;
    }

    public Penitenciaria getPenitenciaria() {
        return penitenciaria;
    }

    public void setPenitenciaria(Penitenciaria penitenciaria) {
        this.penitenciaria = penitenciaria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddadosPenitenciarios != null ? iddadosPenitenciarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DadosPenitenciarios)) {
            return false;
        }
        DadosPenitenciarios other = (DadosPenitenciarios) object;
        if ((this.iddadosPenitenciarios == null && other.iddadosPenitenciarios != null) || (this.iddadosPenitenciarios != null && !this.iddadosPenitenciarios.equals(other.iddadosPenitenciarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.DadosPenitenciarios[ iddadosPenitenciarios=" + iddadosPenitenciarios + " ]";
    }
    
}
