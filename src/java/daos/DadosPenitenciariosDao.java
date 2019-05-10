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
import beans.DadosPenitenciarios;
import beans.Penitenciaria;
import daos.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabriel
 */
public class DadosPenitenciariosDao implements Serializable {

    public DadosPenitenciariosDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DadosPenitenciarios dadosPenitenciarios) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apenado apenado = dadosPenitenciarios.getApenado();
            if (apenado != null) {
                apenado = em.getReference(apenado.getClass(), apenado.getIdapenado());
                dadosPenitenciarios.setApenado(apenado);
            }
            Penitenciaria penitenciaria = dadosPenitenciarios.getPenitenciaria();
            if (penitenciaria != null) {
                penitenciaria = em.getReference(penitenciaria.getClass(), penitenciaria.getIdpenitenciaria());
                dadosPenitenciarios.setPenitenciaria(penitenciaria);
            }
            em.persist(dadosPenitenciarios);
            if (apenado != null) {
                DadosPenitenciarios oldDadosPenitenciariosOfApenado = apenado.getDadosPenitenciarios();
                if (oldDadosPenitenciariosOfApenado != null) {
                    oldDadosPenitenciariosOfApenado.setApenado(null);
                    oldDadosPenitenciariosOfApenado = em.merge(oldDadosPenitenciariosOfApenado);
                }
                apenado.setDadosPenitenciarios(dadosPenitenciarios);
                apenado = em.merge(apenado);
            }
            if (penitenciaria != null) {
                penitenciaria.getDadosPenitenciariosList().add(dadosPenitenciarios);
                penitenciaria = em.merge(penitenciaria);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DadosPenitenciarios dadosPenitenciarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DadosPenitenciarios persistentDadosPenitenciarios = em.find(DadosPenitenciarios.class, dadosPenitenciarios.getIddadosPenitenciarios());
            Apenado apenadoOld = persistentDadosPenitenciarios.getApenado();
            Apenado apenadoNew = dadosPenitenciarios.getApenado();
            Penitenciaria penitenciariaOld = persistentDadosPenitenciarios.getPenitenciaria();
            Penitenciaria penitenciariaNew = dadosPenitenciarios.getPenitenciaria();
            if (apenadoNew != null) {
                apenadoNew = em.getReference(apenadoNew.getClass(), apenadoNew.getIdapenado());
                dadosPenitenciarios.setApenado(apenadoNew);
            }
            if (penitenciariaNew != null) {
                penitenciariaNew = em.getReference(penitenciariaNew.getClass(), penitenciariaNew.getIdpenitenciaria());
                dadosPenitenciarios.setPenitenciaria(penitenciariaNew);
            }
            dadosPenitenciarios = em.merge(dadosPenitenciarios);
            if (apenadoOld != null && !apenadoOld.equals(apenadoNew)) {
                apenadoOld.setDadosPenitenciarios(null);
                apenadoOld = em.merge(apenadoOld);
            }
            if (apenadoNew != null && !apenadoNew.equals(apenadoOld)) {
                DadosPenitenciarios oldDadosPenitenciariosOfApenado = apenadoNew.getDadosPenitenciarios();
                if (oldDadosPenitenciariosOfApenado != null) {
                    oldDadosPenitenciariosOfApenado.setApenado(null);
                    oldDadosPenitenciariosOfApenado = em.merge(oldDadosPenitenciariosOfApenado);
                }
                apenadoNew.setDadosPenitenciarios(dadosPenitenciarios);
                apenadoNew = em.merge(apenadoNew);
            }
            if (penitenciariaOld != null && !penitenciariaOld.equals(penitenciariaNew)) {
                penitenciariaOld.getDadosPenitenciariosList().remove(dadosPenitenciarios);
                penitenciariaOld = em.merge(penitenciariaOld);
            }
            if (penitenciariaNew != null && !penitenciariaNew.equals(penitenciariaOld)) {
                penitenciariaNew.getDadosPenitenciariosList().add(dadosPenitenciarios);
                penitenciariaNew = em.merge(penitenciariaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dadosPenitenciarios.getIddadosPenitenciarios();
                if (findDadosPenitenciarios(id) == null) {
                    throw new NonexistentEntityException("The dadosPenitenciarios with id " + id + " no longer exists.");
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
            DadosPenitenciarios dadosPenitenciarios;
            try {
                dadosPenitenciarios = em.getReference(DadosPenitenciarios.class, id);
                dadosPenitenciarios.getIddadosPenitenciarios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dadosPenitenciarios with id " + id + " no longer exists.", enfe);
            }
            Apenado apenado = dadosPenitenciarios.getApenado();
            if (apenado != null) {
                apenado.setDadosPenitenciarios(null);
                apenado = em.merge(apenado);
            }
            Penitenciaria penitenciaria = dadosPenitenciarios.getPenitenciaria();
            if (penitenciaria != null) {
                penitenciaria.getDadosPenitenciariosList().remove(dadosPenitenciarios);
                penitenciaria = em.merge(penitenciaria);
            }
            em.remove(dadosPenitenciarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DadosPenitenciarios> findDadosPenitenciariosEntities() {
        return findDadosPenitenciariosEntities(true, -1, -1);
    }

    public List<DadosPenitenciarios> findDadosPenitenciariosEntities(int maxResults, int firstResult) {
        return findDadosPenitenciariosEntities(false, maxResults, firstResult);
    }

    private List<DadosPenitenciarios> findDadosPenitenciariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DadosPenitenciarios.class));
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

    public DadosPenitenciarios findDadosPenitenciarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DadosPenitenciarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getDadosPenitenciariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DadosPenitenciarios> rt = cq.from(DadosPenitenciarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
