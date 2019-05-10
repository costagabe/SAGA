/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import beans.CadastroCurso;
import beans.Curso;
import java.util.ArrayList;
import java.util.List;
import beans.PresencaCurso;
import daos.exceptions.IllegalOrphanException;
import daos.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabriel
 */
public class CursoDao implements Serializable {

    public CursoDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curso curso) {
        if (curso.getCadastroCursoList() == null) {
            curso.setCadastroCursoList(new ArrayList<CadastroCurso>());
        }
        if (curso.getPresencaCursoList() == null) {
            curso.setPresencaCursoList(new ArrayList<PresencaCurso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CadastroCurso> attachedCadastroCursoList = new ArrayList<CadastroCurso>();
            for (CadastroCurso cadastroCursoListCadastroCursoToAttach : curso.getCadastroCursoList()) {
                cadastroCursoListCadastroCursoToAttach = em.getReference(cadastroCursoListCadastroCursoToAttach.getClass(), cadastroCursoListCadastroCursoToAttach.getIdcadastroCurso());
                attachedCadastroCursoList.add(cadastroCursoListCadastroCursoToAttach);
            }
            curso.setCadastroCursoList(attachedCadastroCursoList);
            List<PresencaCurso> attachedPresencaCursoList = new ArrayList<PresencaCurso>();
            for (PresencaCurso presencaCursoListPresencaCursoToAttach : curso.getPresencaCursoList()) {
                presencaCursoListPresencaCursoToAttach = em.getReference(presencaCursoListPresencaCursoToAttach.getClass(), presencaCursoListPresencaCursoToAttach.getIdPresencaCurso());
                attachedPresencaCursoList.add(presencaCursoListPresencaCursoToAttach);
            }
            curso.setPresencaCursoList(attachedPresencaCursoList);
            em.persist(curso);
            for (CadastroCurso cadastroCursoListCadastroCurso : curso.getCadastroCursoList()) {
                Curso oldCursoOfCadastroCursoListCadastroCurso = cadastroCursoListCadastroCurso.getCurso();
                cadastroCursoListCadastroCurso.setCurso(curso);
                cadastroCursoListCadastroCurso = em.merge(cadastroCursoListCadastroCurso);
                if (oldCursoOfCadastroCursoListCadastroCurso != null) {
                    oldCursoOfCadastroCursoListCadastroCurso.getCadastroCursoList().remove(cadastroCursoListCadastroCurso);
                    oldCursoOfCadastroCursoListCadastroCurso = em.merge(oldCursoOfCadastroCursoListCadastroCurso);
                }
            }
            for (PresencaCurso presencaCursoListPresencaCurso : curso.getPresencaCursoList()) {
                Curso oldCursoOfPresencaCursoListPresencaCurso = presencaCursoListPresencaCurso.getCurso();
                presencaCursoListPresencaCurso.setCurso(curso);
                presencaCursoListPresencaCurso = em.merge(presencaCursoListPresencaCurso);
                if (oldCursoOfPresencaCursoListPresencaCurso != null) {
                    oldCursoOfPresencaCursoListPresencaCurso.getPresencaCursoList().remove(presencaCursoListPresencaCurso);
                    oldCursoOfPresencaCursoListPresencaCurso = em.merge(oldCursoOfPresencaCursoListPresencaCurso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Curso curso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso persistentCurso = em.find(Curso.class, curso.getIdcurso());
            List<CadastroCurso> cadastroCursoListOld = persistentCurso.getCadastroCursoList();
            List<CadastroCurso> cadastroCursoListNew = curso.getCadastroCursoList();
            List<PresencaCurso> presencaCursoListOld = persistentCurso.getPresencaCursoList();
            List<PresencaCurso> presencaCursoListNew = curso.getPresencaCursoList();
            List<String> illegalOrphanMessages = null;
            for (CadastroCurso cadastroCursoListOldCadastroCurso : cadastroCursoListOld) {
                if (!cadastroCursoListNew.contains(cadastroCursoListOldCadastroCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CadastroCurso " + cadastroCursoListOldCadastroCurso + " since its curso field is not nullable.");
                }
            }
            for (PresencaCurso presencaCursoListOldPresencaCurso : presencaCursoListOld) {
                if (!presencaCursoListNew.contains(presencaCursoListOldPresencaCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PresencaCurso " + presencaCursoListOldPresencaCurso + " since its curso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CadastroCurso> attachedCadastroCursoListNew = new ArrayList<CadastroCurso>();
            for (CadastroCurso cadastroCursoListNewCadastroCursoToAttach : cadastroCursoListNew) {
                cadastroCursoListNewCadastroCursoToAttach = em.getReference(cadastroCursoListNewCadastroCursoToAttach.getClass(), cadastroCursoListNewCadastroCursoToAttach.getIdcadastroCurso());
                attachedCadastroCursoListNew.add(cadastroCursoListNewCadastroCursoToAttach);
            }
            cadastroCursoListNew = attachedCadastroCursoListNew;
            curso.setCadastroCursoList(cadastroCursoListNew);
            List<PresencaCurso> attachedPresencaCursoListNew = new ArrayList<PresencaCurso>();
            for (PresencaCurso presencaCursoListNewPresencaCursoToAttach : presencaCursoListNew) {
                presencaCursoListNewPresencaCursoToAttach = em.getReference(presencaCursoListNewPresencaCursoToAttach.getClass(), presencaCursoListNewPresencaCursoToAttach.getIdPresencaCurso());
                attachedPresencaCursoListNew.add(presencaCursoListNewPresencaCursoToAttach);
            }
            presencaCursoListNew = attachedPresencaCursoListNew;
            curso.setPresencaCursoList(presencaCursoListNew);
            curso = em.merge(curso);
            for (CadastroCurso cadastroCursoListNewCadastroCurso : cadastroCursoListNew) {
                if (!cadastroCursoListOld.contains(cadastroCursoListNewCadastroCurso)) {
                    Curso oldCursoOfCadastroCursoListNewCadastroCurso = cadastroCursoListNewCadastroCurso.getCurso();
                    cadastroCursoListNewCadastroCurso.setCurso(curso);
                    cadastroCursoListNewCadastroCurso = em.merge(cadastroCursoListNewCadastroCurso);
                    if (oldCursoOfCadastroCursoListNewCadastroCurso != null && !oldCursoOfCadastroCursoListNewCadastroCurso.equals(curso)) {
                        oldCursoOfCadastroCursoListNewCadastroCurso.getCadastroCursoList().remove(cadastroCursoListNewCadastroCurso);
                        oldCursoOfCadastroCursoListNewCadastroCurso = em.merge(oldCursoOfCadastroCursoListNewCadastroCurso);
                    }
                }
            }
            for (PresencaCurso presencaCursoListNewPresencaCurso : presencaCursoListNew) {
                if (!presencaCursoListOld.contains(presencaCursoListNewPresencaCurso)) {
                    Curso oldCursoOfPresencaCursoListNewPresencaCurso = presencaCursoListNewPresencaCurso.getCurso();
                    presencaCursoListNewPresencaCurso.setCurso(curso);
                    presencaCursoListNewPresencaCurso = em.merge(presencaCursoListNewPresencaCurso);
                    if (oldCursoOfPresencaCursoListNewPresencaCurso != null && !oldCursoOfPresencaCursoListNewPresencaCurso.equals(curso)) {
                        oldCursoOfPresencaCursoListNewPresencaCurso.getPresencaCursoList().remove(presencaCursoListNewPresencaCurso);
                        oldCursoOfPresencaCursoListNewPresencaCurso = em.merge(oldCursoOfPresencaCursoListNewPresencaCurso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = curso.getIdcurso();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getIdcurso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CadastroCurso> cadastroCursoListOrphanCheck = curso.getCadastroCursoList();
            for (CadastroCurso cadastroCursoListOrphanCheckCadastroCurso : cadastroCursoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the CadastroCurso " + cadastroCursoListOrphanCheckCadastroCurso + " in its cadastroCursoList field has a non-nullable curso field.");
            }
            List<PresencaCurso> presencaCursoListOrphanCheck = curso.getPresencaCursoList();
            for (PresencaCurso presencaCursoListOrphanCheckPresencaCurso : presencaCursoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the PresencaCurso " + presencaCursoListOrphanCheckPresencaCurso + " in its presencaCursoList field has a non-nullable curso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(curso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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

    public Curso findCurso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
