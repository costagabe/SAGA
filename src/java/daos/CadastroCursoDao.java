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
import beans.Apenado;
import beans.CadastroCurso;
import beans.Curso;
import daos.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabriel
 */
public class CadastroCursoDao implements Serializable {

    public CadastroCursoDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CadastroCurso cadastroCurso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apenado apenado = cadastroCurso.getApenado();
            if (apenado != null) {
                apenado = em.getReference(apenado.getClass(), apenado.getIdapenado());
                cadastroCurso.setApenado(apenado);
            }
            Curso curso = cadastroCurso.getCurso();
            if (curso != null) {
                curso = em.getReference(curso.getClass(), curso.getIdcurso());
                cadastroCurso.setCurso(curso);
            }
            em.persist(cadastroCurso);
            if (apenado != null) {
                apenado.getCadastroCursoList().add(cadastroCurso);
                apenado = em.merge(apenado);
            }
            if (curso != null) {
                curso.getCadastroCursoList().add(cadastroCurso);
                curso = em.merge(curso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CadastroCurso cadastroCurso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CadastroCurso persistentCadastroCurso = em.find(CadastroCurso.class, cadastroCurso.getIdcadastroCurso());
            Apenado apenadoOld = persistentCadastroCurso.getApenado();
            Apenado apenadoNew = cadastroCurso.getApenado();
            Curso cursoOld = persistentCadastroCurso.getCurso();
            Curso cursoNew = cadastroCurso.getCurso();
            if (apenadoNew != null) {
                apenadoNew = em.getReference(apenadoNew.getClass(), apenadoNew.getIdapenado());
                cadastroCurso.setApenado(apenadoNew);
            }
            if (cursoNew != null) {
                cursoNew = em.getReference(cursoNew.getClass(), cursoNew.getIdcurso());
                cadastroCurso.setCurso(cursoNew);
            }
            cadastroCurso = em.merge(cadastroCurso);
            if (apenadoOld != null && !apenadoOld.equals(apenadoNew)) {
                apenadoOld.getCadastroCursoList().remove(cadastroCurso);
                apenadoOld = em.merge(apenadoOld);
            }
            if (apenadoNew != null && !apenadoNew.equals(apenadoOld)) {
                apenadoNew.getCadastroCursoList().add(cadastroCurso);
                apenadoNew = em.merge(apenadoNew);
            }
            if (cursoOld != null && !cursoOld.equals(cursoNew)) {
                cursoOld.getCadastroCursoList().remove(cadastroCurso);
                cursoOld = em.merge(cursoOld);
            }
            if (cursoNew != null && !cursoNew.equals(cursoOld)) {
                cursoNew.getCadastroCursoList().add(cadastroCurso);
                cursoNew = em.merge(cursoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cadastroCurso.getIdcadastroCurso();
                if (findCadastroCurso(id) == null) {
                    throw new NonexistentEntityException("The cadastroCurso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CadastroCurso cadastroCurso;
            try {
                cadastroCurso = em.getReference(CadastroCurso.class, id);
                cadastroCurso.getIdcadastroCurso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cadastroCurso with id " + id + " no longer exists.", enfe);
            }
            Apenado apenado = cadastroCurso.getApenado();
            if (apenado != null) {
                apenado.getCadastroCursoList().remove(cadastroCurso);
                apenado = em.merge(apenado);
            }
            Curso curso = cadastroCurso.getCurso();
            if (curso != null) {
                curso.getCadastroCursoList().remove(cadastroCurso);
                curso = em.merge(curso);
            }
            em.remove(cadastroCurso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CadastroCurso> findCadastroCursoEntities() {
        return findCadastroCursoEntities(true, -1, -1);
    }

    public List<CadastroCurso> findCadastroCursoEntities(int maxResults, int firstResult) {
        return findCadastroCursoEntities(false, maxResults, firstResult);
    }

    private List<CadastroCurso> findCadastroCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CadastroCurso.class));
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

    public CadastroCurso findCadastroCurso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CadastroCurso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCadastroCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CadastroCurso> rt = cq.from(CadastroCurso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
