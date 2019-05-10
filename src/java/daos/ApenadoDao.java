/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import beans.Apenado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import beans.DadosPenitenciarios;
import beans.DadosPessoais;
import beans.CadastroCurso;
import java.util.ArrayList;
import java.util.List;
import beans.PresencaCurso;
import beans.PresencaOficina;
import beans.Registros;
import beans.CadastroOficina;
import daos.exceptions.IllegalOrphanException;
import daos.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabriel
 */
public class ApenadoDao implements Serializable {

    public ApenadoDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Apenado findApenadoByMatricula(String matricula) {
        Query q = getEntityManager().createNamedQuery("Apenado.findByMatricula");
        q.setParameter("matricula", matricula);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (Apenado) q.getResultList().get(0);
        }
    }

    public void create(Apenado apenado) {
        if (apenado.getCadastroCursoList() == null) {
            apenado.setCadastroCursoList(new ArrayList<CadastroCurso>());
        }
        if (apenado.getPresencaCursoList() == null) {
            apenado.setPresencaCursoList(new ArrayList<PresencaCurso>());
        }
        if (apenado.getPresencaOficinaList() == null) {
            apenado.setPresencaOficinaList(new ArrayList<PresencaOficina>());
        }
        if (apenado.getRegistrosList() == null) {
            apenado.setRegistrosList(new ArrayList<Registros>());
        }
        if (apenado.getCadastroOficinaList() == null) {
            apenado.setCadastroOficinaList(new ArrayList<CadastroOficina>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DadosPenitenciarios dadosPenitenciarios = apenado.getDadosPenitenciarios();
            if (dadosPenitenciarios != null) {
                dadosPenitenciarios = em.getReference(dadosPenitenciarios.getClass(), dadosPenitenciarios.getIddadosPenitenciarios());
                apenado.setDadosPenitenciarios(dadosPenitenciarios);
            }
            DadosPessoais dadosPessoais = apenado.getDadosPessoais();
            if (dadosPessoais != null) {
                dadosPessoais = em.getReference(dadosPessoais.getClass(), dadosPessoais.getIddadosPessoais());
                apenado.setDadosPessoais(dadosPessoais);
            }
            List<CadastroCurso> attachedCadastroCursoList = new ArrayList<CadastroCurso>();
            for (CadastroCurso cadastroCursoListCadastroCursoToAttach : apenado.getCadastroCursoList()) {
                cadastroCursoListCadastroCursoToAttach = em.getReference(cadastroCursoListCadastroCursoToAttach.getClass(), cadastroCursoListCadastroCursoToAttach.getIdcadastroCurso());
                attachedCadastroCursoList.add(cadastroCursoListCadastroCursoToAttach);
            }
            apenado.setCadastroCursoList(attachedCadastroCursoList);
            List<PresencaCurso> attachedPresencaCursoList = new ArrayList<PresencaCurso>();
            for (PresencaCurso presencaCursoListPresencaCursoToAttach : apenado.getPresencaCursoList()) {
                presencaCursoListPresencaCursoToAttach = em.getReference(presencaCursoListPresencaCursoToAttach.getClass(), presencaCursoListPresencaCursoToAttach.getIdPresencaCurso());
                attachedPresencaCursoList.add(presencaCursoListPresencaCursoToAttach);
            }
            apenado.setPresencaCursoList(attachedPresencaCursoList);
            List<PresencaOficina> attachedPresencaOficinaList = new ArrayList<PresencaOficina>();
            for (PresencaOficina presencaOficinaListPresencaOficinaToAttach : apenado.getPresencaOficinaList()) {
                presencaOficinaListPresencaOficinaToAttach = em.getReference(presencaOficinaListPresencaOficinaToAttach.getClass(), presencaOficinaListPresencaOficinaToAttach.getIdpresencaOficina());
                attachedPresencaOficinaList.add(presencaOficinaListPresencaOficinaToAttach);
            }
            apenado.setPresencaOficinaList(attachedPresencaOficinaList);
            List<Registros> attachedRegistrosList = new ArrayList<Registros>();
            for (Registros registrosListRegistrosToAttach : apenado.getRegistrosList()) {
                registrosListRegistrosToAttach = em.getReference(registrosListRegistrosToAttach.getClass(), registrosListRegistrosToAttach.getIdregistros());
                attachedRegistrosList.add(registrosListRegistrosToAttach);
            }
            apenado.setRegistrosList(attachedRegistrosList);
            List<CadastroOficina> attachedCadastroOficinaList = new ArrayList<CadastroOficina>();
            for (CadastroOficina cadastroOficinaListCadastroOficinaToAttach : apenado.getCadastroOficinaList()) {
                cadastroOficinaListCadastroOficinaToAttach = em.getReference(cadastroOficinaListCadastroOficinaToAttach.getClass(), cadastroOficinaListCadastroOficinaToAttach.getIdcadastroOficina());
                attachedCadastroOficinaList.add(cadastroOficinaListCadastroOficinaToAttach);
            }
            apenado.setCadastroOficinaList(attachedCadastroOficinaList);
            em.persist(apenado);
            if (dadosPenitenciarios != null) {
                Apenado oldApenadoOfDadosPenitenciarios = dadosPenitenciarios.getApenado();
                if (oldApenadoOfDadosPenitenciarios != null) {
                    oldApenadoOfDadosPenitenciarios.setDadosPenitenciarios(null);
                    oldApenadoOfDadosPenitenciarios = em.merge(oldApenadoOfDadosPenitenciarios);
                }
                dadosPenitenciarios.setApenado(apenado);
                dadosPenitenciarios = em.merge(dadosPenitenciarios);
            }
            if (dadosPessoais != null) {
                Apenado oldApenadoOfDadosPessoais = dadosPessoais.getApenado();
                if (oldApenadoOfDadosPessoais != null) {
                    oldApenadoOfDadosPessoais.setDadosPessoais(null);
                    oldApenadoOfDadosPessoais = em.merge(oldApenadoOfDadosPessoais);
                }
                dadosPessoais.setApenado(apenado);
                dadosPessoais = em.merge(dadosPessoais);
            }
            for (CadastroCurso cadastroCursoListCadastroCurso : apenado.getCadastroCursoList()) {
                Apenado oldApenadoOfCadastroCursoListCadastroCurso = cadastroCursoListCadastroCurso.getApenado();
                cadastroCursoListCadastroCurso.setApenado(apenado);
                cadastroCursoListCadastroCurso = em.merge(cadastroCursoListCadastroCurso);
                if (oldApenadoOfCadastroCursoListCadastroCurso != null) {
                    oldApenadoOfCadastroCursoListCadastroCurso.getCadastroCursoList().remove(cadastroCursoListCadastroCurso);
                    oldApenadoOfCadastroCursoListCadastroCurso = em.merge(oldApenadoOfCadastroCursoListCadastroCurso);
                }
            }
            for (PresencaCurso presencaCursoListPresencaCurso : apenado.getPresencaCursoList()) {
                Apenado oldApenadoOfPresencaCursoListPresencaCurso = presencaCursoListPresencaCurso.getApenado();
                presencaCursoListPresencaCurso.setApenado(apenado);
                presencaCursoListPresencaCurso = em.merge(presencaCursoListPresencaCurso);
                if (oldApenadoOfPresencaCursoListPresencaCurso != null) {
                    oldApenadoOfPresencaCursoListPresencaCurso.getPresencaCursoList().remove(presencaCursoListPresencaCurso);
                    oldApenadoOfPresencaCursoListPresencaCurso = em.merge(oldApenadoOfPresencaCursoListPresencaCurso);
                }
            }
            for (PresencaOficina presencaOficinaListPresencaOficina : apenado.getPresencaOficinaList()) {
                Apenado oldApenadoOfPresencaOficinaListPresencaOficina = presencaOficinaListPresencaOficina.getApenado();
                presencaOficinaListPresencaOficina.setApenado(apenado);
                presencaOficinaListPresencaOficina = em.merge(presencaOficinaListPresencaOficina);
                if (oldApenadoOfPresencaOficinaListPresencaOficina != null) {
                    oldApenadoOfPresencaOficinaListPresencaOficina.getPresencaOficinaList().remove(presencaOficinaListPresencaOficina);
                    oldApenadoOfPresencaOficinaListPresencaOficina = em.merge(oldApenadoOfPresencaOficinaListPresencaOficina);
                }
            }
            for (Registros registrosListRegistros : apenado.getRegistrosList()) {
                Apenado oldApenadoOfRegistrosListRegistros = registrosListRegistros.getApenado();
                registrosListRegistros.setApenado(apenado);
                registrosListRegistros = em.merge(registrosListRegistros);
                if (oldApenadoOfRegistrosListRegistros != null) {
                    oldApenadoOfRegistrosListRegistros.getRegistrosList().remove(registrosListRegistros);
                    oldApenadoOfRegistrosListRegistros = em.merge(oldApenadoOfRegistrosListRegistros);
                }
            }
            for (CadastroOficina cadastroOficinaListCadastroOficina : apenado.getCadastroOficinaList()) {
                Apenado oldApenadoOfCadastroOficinaListCadastroOficina = cadastroOficinaListCadastroOficina.getApenado();
                cadastroOficinaListCadastroOficina.setApenado(apenado);
                cadastroOficinaListCadastroOficina = em.merge(cadastroOficinaListCadastroOficina);
                if (oldApenadoOfCadastroOficinaListCadastroOficina != null) {
                    oldApenadoOfCadastroOficinaListCadastroOficina.getCadastroOficinaList().remove(cadastroOficinaListCadastroOficina);
                    oldApenadoOfCadastroOficinaListCadastroOficina = em.merge(oldApenadoOfCadastroOficinaListCadastroOficina);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Apenado> getListaApenados(int ultimoId, int max) {
        Query q = getEntityManager().createNamedQuery("Apenado.findApenadoList");
        q.setParameter("idapenado", ultimoId);
        q.setMaxResults(max);
        return q.getResultList();
    }

    public void edit(Apenado apenado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apenado persistentApenado = em.find(Apenado.class, apenado.getIdapenado());
            DadosPenitenciarios dadosPenitenciariosOld = persistentApenado.getDadosPenitenciarios();
            DadosPenitenciarios dadosPenitenciariosNew = apenado.getDadosPenitenciarios();
            DadosPessoais dadosPessoaisOld = persistentApenado.getDadosPessoais();
            DadosPessoais dadosPessoaisNew = apenado.getDadosPessoais();
            List<CadastroCurso> cadastroCursoListOld = persistentApenado.getCadastroCursoList();
            List<CadastroCurso> cadastroCursoListNew = apenado.getCadastroCursoList();
            List<PresencaCurso> presencaCursoListOld = persistentApenado.getPresencaCursoList();
            List<PresencaCurso> presencaCursoListNew = apenado.getPresencaCursoList();
            List<PresencaOficina> presencaOficinaListOld = persistentApenado.getPresencaOficinaList();
            List<PresencaOficina> presencaOficinaListNew = apenado.getPresencaOficinaList();
            List<Registros> registrosListOld = persistentApenado.getRegistrosList();
            List<Registros> registrosListNew = apenado.getRegistrosList();
            List<CadastroOficina> cadastroOficinaListOld = persistentApenado.getCadastroOficinaList();
            List<CadastroOficina> cadastroOficinaListNew = apenado.getCadastroOficinaList();
            List<String> illegalOrphanMessages = null;
            for (CadastroCurso cadastroCursoListOldCadastroCurso : cadastroCursoListOld) {
                if (!cadastroCursoListNew.contains(cadastroCursoListOldCadastroCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CadastroCurso " + cadastroCursoListOldCadastroCurso + " since its apenado field is not nullable.");
                }
            }
            for (PresencaCurso presencaCursoListOldPresencaCurso : presencaCursoListOld) {
                if (!presencaCursoListNew.contains(presencaCursoListOldPresencaCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PresencaCurso " + presencaCursoListOldPresencaCurso + " since its apenado field is not nullable.");
                }
            }
            for (PresencaOficina presencaOficinaListOldPresencaOficina : presencaOficinaListOld) {
                if (!presencaOficinaListNew.contains(presencaOficinaListOldPresencaOficina)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PresencaOficina " + presencaOficinaListOldPresencaOficina + " since its apenado field is not nullable.");
                }
            }
            for (Registros registrosListOldRegistros : registrosListOld) {
                if (!registrosListNew.contains(registrosListOldRegistros)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Registros " + registrosListOldRegistros + " since its apenado field is not nullable.");
                }
            }
            for (CadastroOficina cadastroOficinaListOldCadastroOficina : cadastroOficinaListOld) {
                if (!cadastroOficinaListNew.contains(cadastroOficinaListOldCadastroOficina)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CadastroOficina " + cadastroOficinaListOldCadastroOficina + " since its apenado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (dadosPenitenciariosNew != null) {
                dadosPenitenciariosNew = em.getReference(dadosPenitenciariosNew.getClass(), dadosPenitenciariosNew.getIddadosPenitenciarios());
                apenado.setDadosPenitenciarios(dadosPenitenciariosNew);
            }
            if (dadosPessoaisNew != null) {
                dadosPessoaisNew = em.getReference(dadosPessoaisNew.getClass(), dadosPessoaisNew.getIddadosPessoais());
                apenado.setDadosPessoais(dadosPessoaisNew);
            }
            List<CadastroCurso> attachedCadastroCursoListNew = new ArrayList<CadastroCurso>();
            for (CadastroCurso cadastroCursoListNewCadastroCursoToAttach : cadastroCursoListNew) {
                cadastroCursoListNewCadastroCursoToAttach = em.getReference(cadastroCursoListNewCadastroCursoToAttach.getClass(), cadastroCursoListNewCadastroCursoToAttach.getIdcadastroCurso());
                attachedCadastroCursoListNew.add(cadastroCursoListNewCadastroCursoToAttach);
            }
            cadastroCursoListNew = attachedCadastroCursoListNew;
            apenado.setCadastroCursoList(cadastroCursoListNew);
            List<PresencaCurso> attachedPresencaCursoListNew = new ArrayList<PresencaCurso>();
            for (PresencaCurso presencaCursoListNewPresencaCursoToAttach : presencaCursoListNew) {
                presencaCursoListNewPresencaCursoToAttach = em.getReference(presencaCursoListNewPresencaCursoToAttach.getClass(), presencaCursoListNewPresencaCursoToAttach.getIdPresencaCurso());
                attachedPresencaCursoListNew.add(presencaCursoListNewPresencaCursoToAttach);
            }
            presencaCursoListNew = attachedPresencaCursoListNew;
            apenado.setPresencaCursoList(presencaCursoListNew);
            List<PresencaOficina> attachedPresencaOficinaListNew = new ArrayList<PresencaOficina>();
            for (PresencaOficina presencaOficinaListNewPresencaOficinaToAttach : presencaOficinaListNew) {
                presencaOficinaListNewPresencaOficinaToAttach = em.getReference(presencaOficinaListNewPresencaOficinaToAttach.getClass(), presencaOficinaListNewPresencaOficinaToAttach.getIdpresencaOficina());
                attachedPresencaOficinaListNew.add(presencaOficinaListNewPresencaOficinaToAttach);
            }
            presencaOficinaListNew = attachedPresencaOficinaListNew;
            apenado.setPresencaOficinaList(presencaOficinaListNew);
            List<Registros> attachedRegistrosListNew = new ArrayList<Registros>();
            for (Registros registrosListNewRegistrosToAttach : registrosListNew) {
                registrosListNewRegistrosToAttach = em.getReference(registrosListNewRegistrosToAttach.getClass(), registrosListNewRegistrosToAttach.getIdregistros());
                attachedRegistrosListNew.add(registrosListNewRegistrosToAttach);
            }
            registrosListNew = attachedRegistrosListNew;
            apenado.setRegistrosList(registrosListNew);
            List<CadastroOficina> attachedCadastroOficinaListNew = new ArrayList<CadastroOficina>();
            for (CadastroOficina cadastroOficinaListNewCadastroOficinaToAttach : cadastroOficinaListNew) {
                cadastroOficinaListNewCadastroOficinaToAttach = em.getReference(cadastroOficinaListNewCadastroOficinaToAttach.getClass(), cadastroOficinaListNewCadastroOficinaToAttach.getIdcadastroOficina());
                attachedCadastroOficinaListNew.add(cadastroOficinaListNewCadastroOficinaToAttach);
            }
            cadastroOficinaListNew = attachedCadastroOficinaListNew;
            apenado.setCadastroOficinaList(cadastroOficinaListNew);
            apenado = em.merge(apenado);
            if (dadosPenitenciariosOld != null && !dadosPenitenciariosOld.equals(dadosPenitenciariosNew)) {
                dadosPenitenciariosOld.setApenado(null);
                dadosPenitenciariosOld = em.merge(dadosPenitenciariosOld);
            }
            if (dadosPenitenciariosNew != null && !dadosPenitenciariosNew.equals(dadosPenitenciariosOld)) {
                Apenado oldApenadoOfDadosPenitenciarios = dadosPenitenciariosNew.getApenado();
                if (oldApenadoOfDadosPenitenciarios != null) {
                    oldApenadoOfDadosPenitenciarios.setDadosPenitenciarios(null);
                    oldApenadoOfDadosPenitenciarios = em.merge(oldApenadoOfDadosPenitenciarios);
                }
                dadosPenitenciariosNew.setApenado(apenado);
                dadosPenitenciariosNew = em.merge(dadosPenitenciariosNew);
            }
            if (dadosPessoaisOld != null && !dadosPessoaisOld.equals(dadosPessoaisNew)) {
                dadosPessoaisOld.setApenado(null);
                dadosPessoaisOld = em.merge(dadosPessoaisOld);
            }
            if (dadosPessoaisNew != null && !dadosPessoaisNew.equals(dadosPessoaisOld)) {
                Apenado oldApenadoOfDadosPessoais = dadosPessoaisNew.getApenado();
                if (oldApenadoOfDadosPessoais != null) {
                    oldApenadoOfDadosPessoais.setDadosPessoais(null);
                    oldApenadoOfDadosPessoais = em.merge(oldApenadoOfDadosPessoais);
                }
                dadosPessoaisNew.setApenado(apenado);
                dadosPessoaisNew = em.merge(dadosPessoaisNew);
            }
            for (CadastroCurso cadastroCursoListNewCadastroCurso : cadastroCursoListNew) {
                if (!cadastroCursoListOld.contains(cadastroCursoListNewCadastroCurso)) {
                    Apenado oldApenadoOfCadastroCursoListNewCadastroCurso = cadastroCursoListNewCadastroCurso.getApenado();
                    cadastroCursoListNewCadastroCurso.setApenado(apenado);
                    cadastroCursoListNewCadastroCurso = em.merge(cadastroCursoListNewCadastroCurso);
                    if (oldApenadoOfCadastroCursoListNewCadastroCurso != null && !oldApenadoOfCadastroCursoListNewCadastroCurso.equals(apenado)) {
                        oldApenadoOfCadastroCursoListNewCadastroCurso.getCadastroCursoList().remove(cadastroCursoListNewCadastroCurso);
                        oldApenadoOfCadastroCursoListNewCadastroCurso = em.merge(oldApenadoOfCadastroCursoListNewCadastroCurso);
                    }
                }
            }
            for (PresencaCurso presencaCursoListNewPresencaCurso : presencaCursoListNew) {
                if (!presencaCursoListOld.contains(presencaCursoListNewPresencaCurso)) {
                    Apenado oldApenadoOfPresencaCursoListNewPresencaCurso = presencaCursoListNewPresencaCurso.getApenado();
                    presencaCursoListNewPresencaCurso.setApenado(apenado);
                    presencaCursoListNewPresencaCurso = em.merge(presencaCursoListNewPresencaCurso);
                    if (oldApenadoOfPresencaCursoListNewPresencaCurso != null && !oldApenadoOfPresencaCursoListNewPresencaCurso.equals(apenado)) {
                        oldApenadoOfPresencaCursoListNewPresencaCurso.getPresencaCursoList().remove(presencaCursoListNewPresencaCurso);
                        oldApenadoOfPresencaCursoListNewPresencaCurso = em.merge(oldApenadoOfPresencaCursoListNewPresencaCurso);
                    }
                }
            }
            for (PresencaOficina presencaOficinaListNewPresencaOficina : presencaOficinaListNew) {
                if (!presencaOficinaListOld.contains(presencaOficinaListNewPresencaOficina)) {
                    Apenado oldApenadoOfPresencaOficinaListNewPresencaOficina = presencaOficinaListNewPresencaOficina.getApenado();
                    presencaOficinaListNewPresencaOficina.setApenado(apenado);
                    presencaOficinaListNewPresencaOficina = em.merge(presencaOficinaListNewPresencaOficina);
                    if (oldApenadoOfPresencaOficinaListNewPresencaOficina != null && !oldApenadoOfPresencaOficinaListNewPresencaOficina.equals(apenado)) {
                        oldApenadoOfPresencaOficinaListNewPresencaOficina.getPresencaOficinaList().remove(presencaOficinaListNewPresencaOficina);
                        oldApenadoOfPresencaOficinaListNewPresencaOficina = em.merge(oldApenadoOfPresencaOficinaListNewPresencaOficina);
                    }
                }
            }
            for (Registros registrosListNewRegistros : registrosListNew) {
                if (!registrosListOld.contains(registrosListNewRegistros)) {
                    Apenado oldApenadoOfRegistrosListNewRegistros = registrosListNewRegistros.getApenado();
                    registrosListNewRegistros.setApenado(apenado);
                    registrosListNewRegistros = em.merge(registrosListNewRegistros);
                    if (oldApenadoOfRegistrosListNewRegistros != null && !oldApenadoOfRegistrosListNewRegistros.equals(apenado)) {
                        oldApenadoOfRegistrosListNewRegistros.getRegistrosList().remove(registrosListNewRegistros);
                        oldApenadoOfRegistrosListNewRegistros = em.merge(oldApenadoOfRegistrosListNewRegistros);
                    }
                }
            }
            for (CadastroOficina cadastroOficinaListNewCadastroOficina : cadastroOficinaListNew) {
                if (!cadastroOficinaListOld.contains(cadastroOficinaListNewCadastroOficina)) {
                    Apenado oldApenadoOfCadastroOficinaListNewCadastroOficina = cadastroOficinaListNewCadastroOficina.getApenado();
                    cadastroOficinaListNewCadastroOficina.setApenado(apenado);
                    cadastroOficinaListNewCadastroOficina = em.merge(cadastroOficinaListNewCadastroOficina);
                    if (oldApenadoOfCadastroOficinaListNewCadastroOficina != null && !oldApenadoOfCadastroOficinaListNewCadastroOficina.equals(apenado)) {
                        oldApenadoOfCadastroOficinaListNewCadastroOficina.getCadastroOficinaList().remove(cadastroOficinaListNewCadastroOficina);
                        oldApenadoOfCadastroOficinaListNewCadastroOficina = em.merge(oldApenadoOfCadastroOficinaListNewCadastroOficina);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = apenado.getIdapenado();
                if (findApenado(id) == null) {
                    throw new NonexistentEntityException("The apenado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apenado apenado;
            try {
                apenado = em.getReference(Apenado.class, id);
                apenado.getIdapenado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The apenado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CadastroCurso> cadastroCursoListOrphanCheck = apenado.getCadastroCursoList();
            for (CadastroCurso cadastroCursoListOrphanCheckCadastroCurso : cadastroCursoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Apenado (" + apenado + ") cannot be destroyed since the CadastroCurso " + cadastroCursoListOrphanCheckCadastroCurso + " in its cadastroCursoList field has a non-nullable apenado field.");
            }
            List<PresencaCurso> presencaCursoListOrphanCheck = apenado.getPresencaCursoList();
            for (PresencaCurso presencaCursoListOrphanCheckPresencaCurso : presencaCursoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Apenado (" + apenado + ") cannot be destroyed since the PresencaCurso " + presencaCursoListOrphanCheckPresencaCurso + " in its presencaCursoList field has a non-nullable apenado field.");
            }
            List<PresencaOficina> presencaOficinaListOrphanCheck = apenado.getPresencaOficinaList();
            for (PresencaOficina presencaOficinaListOrphanCheckPresencaOficina : presencaOficinaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Apenado (" + apenado + ") cannot be destroyed since the PresencaOficina " + presencaOficinaListOrphanCheckPresencaOficina + " in its presencaOficinaList field has a non-nullable apenado field.");
            }
            List<Registros> registrosListOrphanCheck = apenado.getRegistrosList();
            for (Registros registrosListOrphanCheckRegistros : registrosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Apenado (" + apenado + ") cannot be destroyed since the Registros " + registrosListOrphanCheckRegistros + " in its registrosList field has a non-nullable apenado field.");
            }
            List<CadastroOficina> cadastroOficinaListOrphanCheck = apenado.getCadastroOficinaList();
            for (CadastroOficina cadastroOficinaListOrphanCheckCadastroOficina : cadastroOficinaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Apenado (" + apenado + ") cannot be destroyed since the CadastroOficina " + cadastroOficinaListOrphanCheckCadastroOficina + " in its cadastroOficinaList field has a non-nullable apenado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            DadosPenitenciarios dadosPenitenciarios = apenado.getDadosPenitenciarios();
            if (dadosPenitenciarios != null) {
                dadosPenitenciarios.setApenado(null);
                dadosPenitenciarios = em.merge(dadosPenitenciarios);
            }
            DadosPessoais dadosPessoais = apenado.getDadosPessoais();
            if (dadosPessoais != null) {
                dadosPessoais.setApenado(null);
                dadosPessoais = em.merge(dadosPessoais);
            }
            em.remove(apenado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Apenado> findApenadoEntities() {
        return findApenadoEntities(true, -1, -1);
    }

    public List<Apenado> findApenadoEntities(int maxResults, int firstResult) {
        return findApenadoEntities(false, maxResults, firstResult);
    }

    private List<Apenado> findApenadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Apenado.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Apenado findApenado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Apenado.class, id);
        } finally {
            em.close();
        }
    }

    public int getApenadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Apenado> rt = cq.from(Apenado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Apenado> findApenadosAtivos() {
       List<Apenado> ret = new ArrayList<Apenado>();
       for(Apenado a : findApenadoEntities()){
           if(a.getRegistrosList().get(0).getDataSaida() == null){
               ret.add(a);
           }
       }
       return ret;
    }

}
