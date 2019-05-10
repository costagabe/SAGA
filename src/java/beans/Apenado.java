package beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gabriel
 */
@Entity
@Table(name = "apenado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apenado.findAll", query = "SELECT a FROM Apenado a")
    , @NamedQuery(name = "Apenado.findByIdapenado", query = "SELECT a FROM Apenado a WHERE a.idapenado = :idapenado")
    , @NamedQuery(name = "Apenado.findApenadoList", query = "SELECT a FROM Apenado a WHERE a.idapenado > :idapenado")
    , @NamedQuery(name = "Apenado.findByMatricula", query = "SELECT a FROM Apenado a WHERE a.matricula = :matricula")})
public class Apenado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idapenado")
    private Integer idapenado;
    @Column(name = "matricula")
    private String matricula;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apenado")
    private List<CadastroCurso> cadastroCursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apenado")
    private List<PresencaCurso> presencaCursoList;
    @JoinColumn(name = "dadosPenitenciarios", referencedColumnName = "iddados_penitenciarios")
    @OneToOne
    private DadosPenitenciarios dadosPenitenciarios;
    @JoinColumn(name = "dadosPessoais", referencedColumnName = "iddadosPessoais")
    @OneToOne
    private DadosPessoais dadosPessoais;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apenado")
    private List<PresencaOficina> presencaOficinaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apenado")
    private List<Registros> registrosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apenado")
    private List<CadastroOficina> cadastroOficinaList;

    public Apenado() {
    }

    public Apenado(Integer idapenado) {
        this.idapenado = idapenado;
    }

    public Integer getIdapenado() {
        return idapenado;
    }

    public void setIdapenado(Integer idapenado) {
        this.idapenado = idapenado;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso(){
        if (cadastroCursoList.isEmpty()){
            return "-----";
        }
        return cadastroCursoList.get(cadastroCursoList.size()-1).getCurso().getNomeCurso();
    }
    public String getOficina(){
        if(cadastroOficinaList.isEmpty()){
            return "-----";
        }
        return cadastroOficinaList.get(cadastroOficinaList.size()-1).getOficina().getNomeOficina();
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public String getDataNascimento() {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        return format.format(dadosPessoais.getDataNascimento());
    }

    public DadosPenitenciarios getDadosPenitenciarios() {
        return dadosPenitenciarios;
    }

    public void setDadosPenitenciarios(DadosPenitenciarios dadosPenitenciarios) {
        this.dadosPenitenciarios = dadosPenitenciarios;
    }

    public DadosPessoais getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoais dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    @XmlTransient
    public List<PresencaOficina> getPresencaOficinaList() {
        return presencaOficinaList;
    }

    public void setPresencaOficinaList(List<PresencaOficina> presencaOficinaList) {
        this.presencaOficinaList = presencaOficinaList;
    }

    @XmlTransient
    public List<Registros> getRegistrosList() {
        return registrosList;
    }

    public void setRegistrosList(List<Registros> registrosList) {
        this.registrosList = registrosList;
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
        hash += (idapenado != null ? idapenado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apenado)) {
            return false;
        }
        Apenado other = (Apenado) object;
        if ((this.idapenado == null && other.idapenado != null) || (this.idapenado != null && !this.idapenado.equals(other.idapenado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Apenado[ idapenado=" + idapenado + " ]";
    }

}
