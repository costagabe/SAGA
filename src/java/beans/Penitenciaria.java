/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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

/**
 *
 * @author gabriel
 */
@Entity
@Table(name = "penitenciaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Penitenciaria.findAll", query = "SELECT p FROM Penitenciaria p")
    , @NamedQuery(name = "Penitenciaria.findByIdpenitenciaria", query = "SELECT p FROM Penitenciaria p WHERE p.idpenitenciaria = :idpenitenciaria")
    , @NamedQuery(name = "Penitenciaria.findByNome", query = "SELECT p FROM Penitenciaria p WHERE p.nome = :nome")
    , @NamedQuery(name = "Penitenciaria.findByTelefone", query = "SELECT p FROM Penitenciaria p WHERE p.telefone = :telefone")
    , @NamedQuery(name = "Penitenciaria.findByEndereco", query = "SELECT p FROM Penitenciaria p WHERE p.endereco = :endereco")})
public class Penitenciaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpenitenciaria")
    private Integer idpenitenciaria;
    @Column(name = "nome")
    private String nome;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "endereco")
    private String endereco;
    @OneToMany(mappedBy = "penitenciaria")
    private List<DadosPenitenciarios> dadosPenitenciariosList;

    public Penitenciaria() {
    }

    public Penitenciaria(Integer idpenitenciaria) {
        this.idpenitenciaria = idpenitenciaria;
    }

    public Integer getIdpenitenciaria() {
        return idpenitenciaria;
    }

    public void setIdpenitenciaria(Integer idpenitenciaria) {
        this.idpenitenciaria = idpenitenciaria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @XmlTransient
    public List<DadosPenitenciarios> getDadosPenitenciariosList() {
        return dadosPenitenciariosList;
    }

    public void setDadosPenitenciariosList(List<DadosPenitenciarios> dadosPenitenciariosList) {
        this.dadosPenitenciariosList = dadosPenitenciariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpenitenciaria != null ? idpenitenciaria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Penitenciaria)) {
            return false;
        }
        Penitenciaria other = (Penitenciaria) object;
        if ((this.idpenitenciaria == null && other.idpenitenciaria != null) || (this.idpenitenciaria != null && !this.idpenitenciaria.equals(other.idpenitenciaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Penitenciaria[ idpenitenciaria=" + idpenitenciaria + " ]";
    }
    
}
