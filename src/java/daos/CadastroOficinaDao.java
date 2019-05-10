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
import beans.CadastroOficina;
import beans.Oficina;
import daos.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabriel
 */
public class CadastroOficinaDao implements Serializable {

    public CadastroOficinaDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CadastroOficina cadastroOficina) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apenado apenado = cadastroOficina.getApenado();
            if (apenado != null) {
                apenado = em.getReference(apenado.getClass(), apenado.getIdapenado());
                cadastroOficina.setApenado(apenado);
            }
            Oficina oficina = cadastroOficina.getOficina();
            if (oficina != null) {
                oficina = em.getReference(oficina.getClass(), oficina.getIdoficina());
                cadastroOficina.setOficina(oficina);
            }
            em.persist(cadastroOficina);
            if (apenado != null) {
                apenado.getCadastroOficinaList().add(cadastroOficina);
                apenado = em.merge(apenado);
            }
            if (oficina != null) {
                oficina.getCadastroOficinaList().add(cadastroOficina);
                oficina = em.merge(oficina);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public CadastroOficina findCadastroOficinaByMatriculaApenado(String matricula) {
        Query q = getEntityManager().createNamedQuery("CadastroOficina.findByMatriculaApenado");
        q.setParameter("matricula", matricula);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (CadastroOficina) q.getResultList().get(0);
        }

    }

    public void edit(CadastroOficina cadastroOficina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CadastroOficina persistentCadastroOficina = em.find(CadastroOficina.class, cadastroOficina.getIdcadastroOficina());
            Apenado apenadoOld = persistentCadastroOficina.getApenado();
            Apenado apenadoNew = cadastroOficina.getApenado();
            Oficina oficinaOld = persistentCadastroOficina.getOficina();
            Oficina oficinaNew = cadastroOficina.getOficina();
            if (apenadoNew != null) {
                apenadoNew = em.getReference(apenadoNew.getClass(), apenadoNew.getIdapenado());
                cadastroOficina.setApenado(apenadoNew);
            }
            if (oficinaNew != null) {
                oficinaNew = em.getReference(oficinaNew.getClass(), oficinaNew.getIdoficina());
                cadastroOficina.setOficina(oficinaNew);
            }
            cadastroOficina = em.merge(cadastroOficina);
            if (apenadoOld != null && !apenadoOld.equals(apenadoNew)) {
                apenadoOld.getCadastroOficinaList().remove(cadastroOficina);
                apenadoOld = em.merge(apenadoOld);
            }
            if (apenadoNew != null && !apenadoNew.equals(apenadoOld)) {
                apenadoNew.getCadastroOficinaList().add(cadastroOficina);
                apenadoNew = em.merge(apenadoNew);
            }
            if (oficinaOld != null && !oficinaOld.equals(oficinaNew)) {
                oficinaOld.getCadastroOficinaList().remove(cadastroOficina);
                oficinaOld = em.merge(oficinaOld);
            }
            if (oficinaNew != null && !oficinaNew.equals(oficinaOld)) {
                oficinaNew.getCadastroOficinaList().add(cadastroOficina);
                oficinaNew = em.merge(oficinaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cadastroOficina.getIdcadastroOficina();
                if (findCadastroOficina(id) == null) {
                    throw new NonexistentEntityException("The cadastroOficina with id " + id + " no longer exists.");
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
            CadastroOficina cadastroOficina;
            try {
                cadastroOficina = em.getReference(CadastroOficina.class, id);
                cadastroOficina.getIdcadastroOficina();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cadastroOficina with id " + id + " no longer exists.", enfe);
            }
            Apenado apenado = cadastroOficina.getApenado();
            if (apenado != null) {
                apenado.getCadastroOficinaList().remove(cadastroOficina);
                apenado = em.merge(apenado);
            }
            Oficina oficina = cadastroOficina.getOficina();
            if (oficina != null) {
                oficina.getCadastroOficinaList().remove(cadastroOficina);
                oficina = em.merge(oficina);
            }
            em.remove(cadastroOficina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CadastroOficina> findCadastroOficinaEntities() {
        return findCadastroOficinaEntities(true, -1, -1);
    }

    public List<CadastroOficina> findCadastroOficinaEntities(int maxResults, int firstResult) {
        return findCadastroOficinaEntities(false, maxResults, firstResult);
    }

    private List<CadastroOficina> findCadastroOficinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CadastroOficina.class));
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

    public CadastroOficina findCadastroOficina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CadastroOficina.class, id);
        } finally {
            em.close();
        }
    }

    public int getCadastroOficinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CadastroOficina> rt = cq.from(CadastroOficina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
