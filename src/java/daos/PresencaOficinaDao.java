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
import beans.Oficina;
import beans.PresencaOficina;
import daos.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabriel
 */
public class PresencaOficinaDao implements Serializable {

    public PresencaOficinaDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PresencaOficina presencaOficina) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apenado apenado = presencaOficina.getApenado();
            if (apenado != null) {
                apenado = em.getReference(apenado.getClass(), apenado.getIdapenado());
                presencaOficina.setApenado(apenado);
            }
            Oficina oficina = presencaOficina.getOficina();
            if (oficina != null) {
                oficina = em.getReference(oficina.getClass(), oficina.getIdoficina());
                presencaOficina.setOficina(oficina);
            }
            em.persist(presencaOficina);
            if (apenado != null) {
                apenado.getPresencaOficinaList().add(presencaOficina);
                apenado = em.merge(apenado);
            }
            if (oficina != null) {
                oficina.getPresencaOficinaList().add(presencaOficina);
                oficina = em.merge(oficina);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PresencaOficina presencaOficina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PresencaOficina persistentPresencaOficina = em.find(PresencaOficina.class, presencaOficina.getIdpresencaOficina());
            Apenado apenadoOld = persistentPresencaOficina.getApenado();
            Apenado apenadoNew = presencaOficina.getApenado();
            Oficina oficinaOld = persistentPresencaOficina.getOficina();
            Oficina oficinaNew = presencaOficina.getOficina();
            if (apenadoNew != null) {
                apenadoNew = em.getReference(apenadoNew.getClass(), apenadoNew.getIdapenado());
                presencaOficina.setApenado(apenadoNew);
            }
            if (oficinaNew != null) {
                oficinaNew = em.getReference(oficinaNew.getClass(), oficinaNew.getIdoficina());
                presencaOficina.setOficina(oficinaNew);
            }
            presencaOficina = em.merge(presencaOficina);
            if (apenadoOld != null && !apenadoOld.equals(apenadoNew)) {
                apenadoOld.getPresencaOficinaList().remove(presencaOficina);
                apenadoOld = em.merge(apenadoOld);
            }
            if (apenadoNew != null && !apenadoNew.equals(apenadoOld)) {
                apenadoNew.getPresencaOficinaList().add(presencaOficina);
                apenadoNew = em.merge(apenadoNew);
            }
            if (oficinaOld != null && !oficinaOld.equals(oficinaNew)) {
                oficinaOld.getPresencaOficinaList().remove(presencaOficina);
                oficinaOld = em.merge(oficinaOld);
            }
            if (oficinaNew != null && !oficinaNew.equals(oficinaOld)) {
                oficinaNew.getPresencaOficinaList().add(presencaOficina);
                oficinaNew = em.merge(oficinaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = presencaOficina.getIdpresencaOficina();
                if (findPresencaOficina(id) == null) {
                    throw new NonexistentEntityException("The presencaOficina with id " + id + " no longer exists.");
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
            PresencaOficina presencaOficina;
            try {
                presencaOficina = em.getReference(PresencaOficina.class, id);
                presencaOficina.getIdpresencaOficina();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The presencaOficina with id " + id + " no longer exists.", enfe);
            }
            Apenado apenado = presencaOficina.getApenado();
            if (apenado != null) {
                apenado.getPresencaOficinaList().remove(presencaOficina);
                apenado = em.merge(apenado);
            }
            Oficina oficina = presencaOficina.getOficina();
            if (oficina != null) {
                oficina.getPresencaOficinaList().remove(presencaOficina);
                oficina = em.merge(oficina);
            }
            em.remove(presencaOficina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PresencaOficina> findPresencaOficinaEntities() {
        return findPresencaOficinaEntities(true, -1, -1);
    }

    public List<PresencaOficina> findPresencaOficinaEntities(int maxResults, int firstResult) {
        return findPresencaOficinaEntities(false, maxResults, firstResult);
    }

    private List<PresencaOficina> findPresencaOficinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PresencaOficina.class));
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

    public PresencaOficina findPresencaOficina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PresencaOficina.class, id);
        } finally {
            em.close();
        }
    }

    public int getPresencaOficinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PresencaOficina> rt = cq.from(PresencaOficina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
