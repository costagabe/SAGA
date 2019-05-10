/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gabriel
 */
@Entity
@Table(name = "curso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c")
    , @NamedQuery(name = "Curso.findByIdcurso", query = "SELECT c FROM Curso c WHERE c.idcurso = :idcurso")
    , @NamedQuery(name = "Curso.findByNomeCurso", query = "SELECT c FROM Curso c WHERE c.nomeCurso = :nomeCurso")
    , @NamedQuery(name = "Curso.findByTempoDiario", query = "SELECT c FROM Curso c WHERE c.tempoDiario = :tempoDiario")
    , @NamedQuery(name = "Curso.findByQuantidadeDiasCurso", query = "SELECT c FROM Curso c WHERE c.quantidadeDiasCurso = :quantidadeDiasCurso")
    , @NamedQuery(name = "Curso.findByInstrutor", query = "SELECT c FROM Curso c WHERE c.instrutor = :instrutor")
    , @NamedQuery(name = "Curso.findByVagasDisponiveis", query = "SELECT c FROM Curso c WHERE c.vagasDisponiveis = :vagasDisponiveis")
    , @NamedQuery(name = "Curso.findByDataInicio", query = "SELECT c FROM Curso c WHERE c.dataInicio = :dataInicio")
    , @NamedQuery(name = "Curso.findByDataFim", query = "SELECT c FROM Curso c WHERE c.dataFim = :dataFim")})
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcurso")
    private Integer idcurso;
    @Basic(optional = false)
    @Column(name = "nomeCurso")
    private String nomeCurso;
    @Basic(optional = false)
    @Column(name = "tempoDiario")
    private double tempoDiario;
    @Basic(optional = false)
    @Column(name = "quantidadeDiasCurso")
    private int quantidadeDiasCurso;
    @Column(name = "instrutor")
    private String instrutor;
    @Column(name = "vagasDisponiveis")
    private Integer vagasDisponiveis;
    @Column(name = "dataInicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Column(name = "dataFim")
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curso")
    private List<CadastroCurso> cadastroCursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curso")
    private List<PresencaCurso> presencaCursoList;

    public Curso() {
    }

    public Curso(Integer idcurso) {
        this.idcurso = idcurso;
    }

    public Curso(Integer idcurso, String nomeCurso, double tempoDiario, int quantidadeDiasCurso) {
        this.idcurso = idcurso;
        this.nomeCurso = nomeCurso;
        this.tempoDiario = tempoDiario;
        this.quantidadeDiasCurso = quantidadeDiasCurso;
    }

    public Integer getIdcurso() {
        return idcurso;
    }

    public String getDataInicioString() {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        return format.format(dataInicio);
    }

    public String getDataFimString() {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        return format.format(dataFim);
    }

    public void setIdcurso(Integer idcurso) {
        this.idcurso = idcurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public double getTempoDiario() {
        return tempoDiario;
    }

    public void setTempoDiario(double tempoDiario) {
        this.tempoDiario = tempoDiario;
    }

    public int getQuantidadeDiasCurso() {
        return quantidadeDiasCurso;
    }

    public void setQuantidadeDiasCurso(int quantidadeDiasCurso) {
        this.quantidadeDiasCurso = quantidadeDiasCurso;
    }

    public String getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(String instrutor) {
        this.instrutor = instrutor;
    }

    public Integer getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public void setVagasDisponiveis(Integer vagasDisponiveis) {
        this.vagasDisponiveis = vagasDisponiveis;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    @XmlTransient
    public List<CadastroCurso> getCadastroCursoList() {
        return cadastroCursoList;
    }

    public void setCadastroCursoList(List<CadastroCurso> cadastroCursoList) {
        this.cadastroCursoList = cadastroCursoList;
    }

    @XmlTransient
    public List<PresencaCurso> getPresencaCursoList() {
        return presencaCursoList;
    }

    public void setPresencaCursoList(List<PresencaCurso> presencaCursoList) {
        this.presencaCursoList = presencaCursoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcurso != null ? idcurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.idcurso == null && other.idcurso != null) || (this.idcurso != null && !this.idcurso.equals(other.idcurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Curso[ idcurso=" + idcurso + " ]";
    }



}
