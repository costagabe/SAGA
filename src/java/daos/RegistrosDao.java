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
import beans.Registros;
import daos.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabriel
 */
public class RegistrosDao implements Serializable {

    public RegistrosDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Registros registros) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apenado apenado = registros.getApenado();
            if (apenado != null) {
                apenado = em.getReference(apenado.getClass(), apenado.getIdapenado());
                registros.setApenado(apenado);
            }
            em.persist(registros);
            if (apenado != null) {
                apenado.getRegistrosList().add(registros);
                apenado = em.merge(apenado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
// retorna o ultimo registro feito de um apenado
    public Registros findRegistrosByMatriculaApenado(String matricula) {
        Query q = getEntityManager().createNamedQuery("Registros.findByMatriculaApenado");
        q.setParameter("matricula", matricula);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (Registros) q.getResultList().get(q.getResultList().size() - 1);
        }
    }

    public void edit(Registros registros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Registros persistentRegistros = em.find(Registros.class, registros.getIdregistros());
            Apenado apenadoOld = persistentRegistros.getApenado();
            Apenado apenadoNew = registros.getApenado();
            if (apenadoNew != null) {
                apenadoNew = em.getReference(apenadoNew.getClass(), apenadoNew.getIdapenado());
                registros.setApenado(apenadoNew);
            }
            registros = em.merge(registros);
            if (apenadoOld != null && !apenadoOld.equals(apenadoNew)) {
                apenadoOld.getRegistrosList().remove(registros);
                apenadoOld = em.merge(apenadoOld);
            }
            if (apenadoNew != null && !apenadoNew.equals(apenadoOld)) {
                apenadoNew.getRegistrosList().add(registros);
                apenadoNew = em.merge(apenadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = registros.getIdregistros();
                if (findRegistros(id) == null) {
                    throw new NonexistentEntityException("The registros with id " + id + " no longer exists.");
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
            Registros registros;
            try {
                registros = em.getReference(Registros.class, id);
                registros.getIdregistros();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registros with id " + id + " no longer exists.", enfe);
            }
            Apenado apenado = registros.getApenado();
            if (apenado != null) {
                apenado.getRegistrosList().remove(registros);
                apenado = em.merge(apenado);
            }
            em.remove(registros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Registros> findRegistrosEntities() {
        return findRegistrosEntities(true, -1, -1);
    }

    public List<Registros> findRegistrosEntities(int maxResults, int firstResult) {
        return findRegistrosEntities(false, maxResults, firstResult);
    }

    private List<Registros> findRegistrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Registros.class));
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

    public Registros findRegistros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Registros.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Registros> rt = cq.from(Registros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
