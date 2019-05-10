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
import beans.DadosPenitenciarios;
import beans.Penitenciaria;
import daos.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabriel
 */
public class PenitenciariaDao implements Serializable {

    public PenitenciariaDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Penitenciaria penitenciaria) {
        if (penitenciaria.getDadosPenitenciariosList() == null) {
            penitenciaria.setDadosPenitenciariosList(new ArrayList<DadosPenitenciarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DadosPenitenciarios> attachedDadosPenitenciariosList = new ArrayList<DadosPenitenciarios>();
            for (DadosPenitenciarios dadosPenitenciariosListDadosPenitenciariosToAttach : penitenciaria.getDadosPenitenciariosList()) {
                dadosPenitenciariosListDadosPenitenciariosToAttach = em.getReference(dadosPenitenciariosListDadosPenitenciariosToAttach.getClass(), dadosPenitenciariosListDadosPenitenciariosToAttach.getIddadosPenitenciarios());
                attachedDadosPenitenciariosList.add(dadosPenitenciariosListDadosPenitenciariosToAttach);
            }
            penitenciaria.setDadosPenitenciariosList(attachedDadosPenitenciariosList);
            em.persist(penitenciaria);
            for (DadosPenitenciarios dadosPenitenciariosListDadosPenitenciarios : penitenciaria.getDadosPenitenciariosList()) {
                Penitenciaria oldPenitenciariaOfDadosPenitenciariosListDadosPenitenciarios = dadosPenitenciariosListDadosPenitenciarios.getPenitenciaria();
                dadosPenitenciariosListDadosPenitenciarios.setPenitenciaria(penitenciaria);
                dadosPenitenciariosListDadosPenitenciarios = em.merge(dadosPenitenciariosListDadosPenitenciarios);
                if (oldPenitenciariaOfDadosPenitenciariosListDadosPenitenciarios != null) {
                    oldPenitenciariaOfDadosPenitenciariosListDadosPenitenciarios.getDadosPenitenciariosList().remove(dadosPenitenciariosListDadosPenitenciarios);
                    oldPenitenciariaOfDadosPenitenciariosListDadosPenitenciarios = em.merge(oldPenitenciariaOfDadosPenitenciariosListDadosPenitenciarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Penitenciaria penitenciaria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Penitenciaria persistentPenitenciaria = em.find(Penitenciaria.class, penitenciaria.getIdpenitenciaria());
            List<DadosPenitenciarios> dadosPenitenciariosListOld = persistentPenitenciaria.getDadosPenitenciariosList();
            List<DadosPenitenciarios> dadosPenitenciariosListNew = penitenciaria.getDadosPenitenciariosList();
            List<DadosPenitenciarios> attachedDadosPenitenciariosListNew = new ArrayList<DadosPenitenciarios>();
            for (DadosPenitenciarios dadosPenitenciariosListNewDadosPenitenciariosToAttach : dadosPenitenciariosListNew) {
                dadosPenitenciariosListNewDadosPenitenciariosToAttach = em.getReference(dadosPenitenciariosListNewDadosPenitenciariosToAttach.getClass(), dadosPenitenciariosListNewDadosPenitenciariosToAttach.getIddadosPenitenciarios());
                attachedDadosPenitenciariosListNew.add(dadosPenitenciariosListNewDadosPenitenciariosToAttach);
            }
            dadosPenitenciariosListNew = attachedDadosPenitenciariosListNew;
            penitenciaria.setDadosPenitenciariosList(dadosPenitenciariosListNew);
            penitenciaria = em.merge(penitenciaria);
            for (DadosPenitenciarios dadosPenitenciariosListOldDadosPenitenciarios : dadosPenitenciariosListOld) {
                if (!dadosPenitenciariosListNew.contains(dadosPenitenciariosListOldDadosPenitenciarios)) {
                    dadosPenitenciariosListOldDadosPenitenciarios.setPenitenciaria(null);
                    dadosPenitenciariosListOldDadosPenitenciarios = em.merge(dadosPenitenciariosListOldDadosPenitenciarios);
                }
            }
            for (DadosPenitenciarios dadosPenitenciariosListNewDadosPenitenciarios : dadosPenitenciariosListNew) {
                if (!dadosPenitenciariosListOld.contains(dadosPenitenciariosListNewDadosPenitenciarios)) {
                    Penitenciaria oldPenitenciariaOfDadosPenitenciariosListNewDadosPenitenciarios = dadosPenitenciariosListNewDadosPenitenciarios.getPenitenciaria();
                    dadosPenitenciariosListNewDadosPenitenciarios.setPenitenciaria(penitenciaria);
                    dadosPenitenciariosListNewDadosPenitenciarios = em.merge(dadosPenitenciariosListNewDadosPenitenciarios);
                    if (oldPenitenciariaOfDadosPenitenciariosListNewDadosPenitenciarios != null && !oldPenitenciariaOfDadosPenitenciariosListNewDadosPenitenciarios.equals(penitenciaria)) {
                        oldPenitenciariaOfDadosPenitenciariosListNewDadosPenitenciarios.getDadosPenitenciariosList().remove(dadosPenitenciariosListNewDadosPenitenciarios);
                        oldPenitenciariaOfDadosPenitenciariosListNewDadosPenitenciarios = em.merge(oldPenitenciariaOfDadosPenitenciariosListNewDadosPenitenciarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = penitenciaria.getIdpenitenciaria();
                if (findPenitenciaria(id) == null) {
                    throw new NonexistentEntityException("The penitenciaria with id " + id + " no longer exists.");
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
            Penitenciaria penitenciaria;
            try {
                penitenciaria = em.getReference(Penitenciaria.class, id);
                penitenciaria.getIdpenitenciaria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The penitenciaria with id " + id + " no longer exists.", enfe);
            }
            List<DadosPenitenciarios> dadosPenitenciariosList = penitenciaria.getDadosPenitenciariosList();
            for (DadosPenitenciarios dadosPenitenciariosListDadosPenitenciarios : dadosPenitenciariosList) {
                dadosPenitenciariosListDadosPenitenciarios.setPenitenciaria(null);
                dadosPenitenciariosListDadosPenitenciarios = em.merge(dadosPenitenciariosListDadosPenitenciarios);
            }
            em.remove(penitenciaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Penitenciaria> findPenitenciariaEntities() {
        return findPenitenciariaEntities(true, -1, -1);
    }

    public List<Penitenciaria> findPenitenciariaEntities(int maxResults, int firstResult) {
        return findPenitenciariaEntities(false, maxResults, firstResult);
    }

    private List<Penitenciaria> findPenitenciariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Penitenciaria.class));
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

    public Penitenciaria findPenitenciaria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Penitenciaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getPenitenciariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Penitenciaria> rt = cq.from(Penitenciaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
