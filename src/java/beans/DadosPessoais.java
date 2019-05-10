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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gabriel
 */
@Entity
@Table(name = "dados_pessoais")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DadosPessoais.findAll", query = "SELECT d FROM DadosPessoais d")
    , @NamedQuery(name = "DadosPessoais.findByIddadosPessoais", query = "SELECT d FROM DadosPessoais d WHERE d.iddadosPessoais = :iddadosPessoais")
    , @NamedQuery(name = "DadosPessoais.findByNome", query = "SELECT d FROM DadosPessoais d WHERE d.nome = :nome")
    , @NamedQuery(name = "DadosPessoais.findByDataNascimento", query = "SELECT d FROM DadosPessoais d WHERE d.dataNascimento = :dataNascimento")
    , @NamedQuery(name = "DadosPessoais.findByRg", query = "SELECT d FROM DadosPessoais d WHERE d.rg = :rg")
    , @NamedQuery(name = "DadosPessoais.findByCpf", query = "SELECT d FROM DadosPessoais d WHERE d.cpf = :cpf")
    , @NamedQuery(name = "DadosPessoais.findByNomePai", query = "SELECT d FROM DadosPessoais d WHERE d.nomePai = :nomePai")
    , @NamedQuery(name = "DadosPessoais.findByNomeMae", query = "SELECT d FROM DadosPessoais d WHERE d.nomeMae = :nomeMae")
    , @NamedQuery(name = "DadosPessoais.findByNacionalidade", query = "SELECT d FROM DadosPessoais d WHERE d.nacionalidade = :nacionalidade")
    , @NamedQuery(name = "DadosPessoais.findByNaturalidade", query = "SELECT d FROM DadosPessoais d WHERE d.naturalidade = :naturalidade")
    , @NamedQuery(name = "DadosPessoais.findByEscolaridade", query = "SELECT d FROM DadosPessoais d WHERE d.escolaridade = :escolaridade")
    , @NamedQuery(name = "DadosPessoais.findByEstadoCivil", query = "SELECT d FROM DadosPessoais d WHERE d.estadoCivil = :estadoCivil")
    , @NamedQuery(name = "DadosPessoais.findByFoto", query = "SELECT d FROM DadosPessoais d WHERE d.foto = :foto")})
public class DadosPessoais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddadosPessoais")
    private Integer iddadosPessoais;
    @Column(name = "nome")
    private String nome;
    @Column(name = "dataNascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @Column(name = "rg")
    private String rg;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "nomePai")
    private String nomePai;
    @Column(name = "nomeMae")
    private String nomeMae;
    @Column(name = "nacionalidade")
    private String nacionalidade;
    @Column(name = "naturalidade")
    private String naturalidade;
    @Column(name = "escolaridade")
    private String escolaridade;
    @Column(name = "estadoCivil")
    private String estadoCivil;
    @Column(name = "foto")
    private String foto;
    @OneToOne(mappedBy = "dadosPessoais")
    private Apenado apenado;

    public DadosPessoais() {
    }

    public DadosPessoais(Integer iddadosPessoais) {
        this.iddadosPessoais = iddadosPessoais;
    }

    public Integer getIddadosPessoais() {
        return iddadosPessoais;
    }

    public void setIddadosPessoais(Integer iddadosPessoais) {
        this.iddadosPessoais = iddadosPessoais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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
        hash += (iddadosPessoais != null ? iddadosPessoais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DadosPessoais)) {
            return false;
        }
        DadosPessoais other = (DadosPessoais) object;
        if ((this.iddadosPessoais == null && other.iddadosPessoais != null) || (this.iddadosPessoais != null && !this.iddadosPessoais.equals(other.iddadosPessoais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.DadosPessoais[ iddadosPessoais=" + iddadosPessoais + " ]";
    }
    
}
