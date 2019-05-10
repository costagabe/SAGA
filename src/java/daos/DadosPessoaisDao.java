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
import beans.DadosPessoais;
import daos.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabriel
 */
public class DadosPessoaisDao implements Serializable {

    public DadosPessoaisDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DadosPessoais dadosPessoais) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apenado apenado = dadosPessoais.getApenado();
            if (apenado != null) {
                apenado = em.getReference(apenado.getClass(), apenado.getIdapenado());
                dadosPessoais.setApenado(apenado);
            }
            em.persist(dadosPessoais);
            if (apenado != null) {
                DadosPessoais oldDadosPessoaisOfApenado = apenado.getDadosPessoais();
                if (oldDadosPessoaisOfApenado != null) {
                    oldDadosPessoaisOfApenado.setApenado(null);
                    oldDadosPessoaisOfApenado = em.merge(oldDadosPessoaisOfApenado);
                }
                apenado.setDadosPessoais(dadosPessoais);
                apenado = em.merge(apenado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DadosPessoais dadosPessoais) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DadosPessoais persistentDadosPessoais = em.find(DadosPessoais.class, dadosPessoais.getIddadosPessoais());
            Apenado apenadoOld = persistentDadosPessoais.getApenado();
            Apenado apenadoNew = dadosPessoais.getApenado();
            if (apenadoNew != null) {
                apenadoNew = em.getReference(apenadoNew.getClass(), apenadoNew.getIdapenado());
                dadosPessoais.setApenado(apenadoNew);
            }
            dadosPessoais = em.merge(dadosPessoais);
            if (apenadoOld != null && !apenadoOld.equals(apenadoNew)) {
                apenadoOld.setDadosPessoais(null);
                apenadoOld = em.merge(apenadoOld);
            }
            if (apenadoNew != null && !apenadoNew.equals(apenadoOld)) {
                DadosPessoais oldDadosPessoaisOfApenado = apenadoNew.getDadosPessoais();
                if (oldDadosPessoaisOfApenado != null) {
                    oldDadosPessoaisOfApenado.setApenado(null);
                    oldDadosPessoaisOfApenado = em.merge(oldDadosPessoaisOfApenado);
                }
                apenadoNew.setDadosPessoais(dadosPessoais);
                apenadoNew = em.merge(apenadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dadosPessoais.getIddadosPessoais();
                if (findDadosPessoais(id) == null) {
                    throw new NonexistentEntityException("The dadosPessoais with id " + id + " no longer exists.");
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
            DadosPessoais dadosPessoais;
            try {
                dadosPessoais = em.getReference(DadosPessoais.class, id);
                dadosPessoais.getIddadosPessoais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dadosPessoais with id " + id + " no longer exists.", enfe);
            }
            Apenado apenado = dadosPessoais.getApenado();
            if (apenado != null) {
                apenado.setDadosPessoais(null);
                apenado = em.merge(apenado);
            }
            em.remove(dadosPessoais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DadosPessoais> findDadosPessoaisEntities() {
        return findDadosPessoaisEntities(true, -1, -1);
    }

    public List<DadosPessoais> findDadosPessoaisEntities(int maxResults, int firstResult) {
        return findDadosPessoaisEntities(false, maxResults, firstResult);
    }

    private List<DadosPessoais> findDadosPessoaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DadosPessoais.class));
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

    public DadosPessoais findDadosPessoais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DadosPessoais.class, id);
        } finally {
            em.close();
        }
    }

    public int getDadosPessoaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DadosPessoais> rt = cq.from(DadosPessoais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
