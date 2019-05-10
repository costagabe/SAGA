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
import beans.Curso;
import beans.PresencaCurso;
import daos.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabriel
 */
public class PresencaCursoDao implements Serializable {

    public PresencaCursoDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PresencaCurso presencaCurso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apenado apenado = presencaCurso.getApenado();
            if (apenado != null) {
                apenado = em.getReference(apenado.getClass(), apenado.getIdapenado());
                presencaCurso.setApenado(apenado);
            }
            Curso curso = presencaCurso.getCurso();
            if (curso != null) {
                curso = em.getReference(curso.getClass(), curso.getIdcurso());
                presencaCurso.setCurso(curso);
            }
            em.persist(presencaCurso);
            if (apenado != null) {
                apenado.getPresencaCursoList().add(presencaCurso);
                apenado = em.merge(apenado);
            }
            if (curso != null) {
                curso.getPresencaCursoList().add(presencaCurso);
                curso = em.merge(curso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PresencaCurso presencaCurso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PresencaCurso persistentPresencaCurso = em.find(PresencaCurso.class, presencaCurso.getIdPresencaCurso());
            Apenado apenadoOld = persistentPresencaCurso.getApenado();
            Apenado apenadoNew = presencaCurso.getApenado();
            Curso cursoOld = persistentPresencaCurso.getCurso();
            Curso cursoNew = presencaCurso.getCurso();
            if (apenadoNew != null) {
                apenadoNew = em.getReference(apenadoNew.getClass(), apenadoNew.getIdapenado());
                presencaCurso.setApenado(apenadoNew);
            }
            if (cursoNew != null) {
                cursoNew = em.getReference(cursoNew.getClass(), cursoNew.getIdcurso());
                presencaCurso.setCurso(cursoNew);
            }
            presencaCurso = em.merge(presencaCurso);
            if (apenadoOld != null && !apenadoOld.equals(apenadoNew)) {
                apenadoOld.getPresencaCursoList().remove(presencaCurso);
                apenadoOld = em.merge(apenadoOld);
            }
            if (apenadoNew != null && !apenadoNew.equals(apenadoOld)) {
                apenadoNew.getPresencaCursoList().add(presencaCurso);
                apenadoNew = em.merge(apenadoNew);
            }
            if (cursoOld != null && !cursoOld.equals(cursoNew)) {
                cursoOld.getPresencaCursoList().remove(presencaCurso);
                cursoOld = em.merge(cursoOld);
            }
            if (cursoNew != null && !cursoNew.equals(cursoOld)) {
                cursoNew.getPresencaCursoList().add(presencaCurso);
                cursoNew = em.merge(cursoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = presencaCurso.getIdPresencaCurso();
                if (findPresencaCurso(id) == null) {
                    throw new NonexistentEntityException("The presencaCurso with id " + id + " no longer exists.");
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
            PresencaCurso presencaCurso;
            try {
                presencaCurso = em.getReference(PresencaCurso.class, id);
                presencaCurso.getIdPresencaCurso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The presencaCurso with id " + id + " no longer exists.", enfe);
            }
            Apenado apenado = presencaCurso.getApenado();
            if (apenado != null) {
                apenado.getPresencaCursoList().remove(presencaCurso);
                apenado = em.merge(apenado);
            }
            Curso curso = presencaCurso.getCurso();
            if (curso != null) {
                curso.getPresencaCursoList().remove(presencaCurso);
                curso = em.merge(curso);
            }
            em.remove(presencaCurso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PresencaCurso> findPresencaCursoEntities() {
        return findPresencaCursoEntities(true, -1, -1);
    }

    public List<PresencaCurso> findPresencaCursoEntities(int maxResults, int firstResult) {
        return findPresencaCursoEntities(false, maxResults, firstResult);
    }

    private List<PresencaCurso> findPresencaCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PresencaCurso.class));
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

    public PresencaCurso findPresencaCurso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PresencaCurso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPresencaCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PresencaCurso> rt = cq.from(PresencaCurso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
