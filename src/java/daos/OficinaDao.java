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
import beans.PresencaOficina;
import java.util.ArrayList;
import java.util.List;
import beans.CadastroOficina;
import beans.Oficina;
import daos.exceptions.IllegalOrphanException;
import daos.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabriel
 */
public class OficinaDao implements Serializable {

    public OficinaDao(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Oficina oficina) {
        if (oficina.getPresencaOficinaList() == null) {
            oficina.setPresencaOficinaList(new ArrayList<PresencaOficina>());
        }
        if (oficina.getCadastroOficinaList() == null) {
            oficina.setCadastroOficinaList(new ArrayList<CadastroOficina>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PresencaOficina> attachedPresencaOficinaList = new ArrayList<PresencaOficina>();
            for (PresencaOficina presencaOficinaListPresencaOficinaToAttach : oficina.getPresencaOficinaList()) {
                presencaOficinaListPresencaOficinaToAttach = em.getReference(presencaOficinaListPresencaOficinaToAttach.getClass(), presencaOficinaListPresencaOficinaToAttach.getIdpresencaOficina());
                attachedPresencaOficinaList.add(presencaOficinaListPresencaOficinaToAttach);
            }
            oficina.setPresencaOficinaList(attachedPresencaOficinaList);
            List<CadastroOficina> attachedCadastroOficinaList = new ArrayList<CadastroOficina>();
            for (CadastroOficina cadastroOficinaListCadastroOficinaToAttach : oficina.getCadastroOficinaList()) {
                cadastroOficinaListCadastroOficinaToAttach = em.getReference(cadastroOficinaListCadastroOficinaToAttach.getClass(), cadastroOficinaListCadastroOficinaToAttach.getIdcadastroOficina());
                attachedCadastroOficinaList.add(cadastroOficinaListCadastroOficinaToAttach);
            }
            oficina.setCadastroOficinaList(attachedCadastroOficinaList);
            em.persist(oficina);
            for (PresencaOficina presencaOficinaListPresencaOficina : oficina.getPresencaOficinaList()) {
                Oficina oldOficinaOfPresencaOficinaListPresencaOficina = presencaOficinaListPresencaOficina.getOficina();
                presencaOficinaListPresencaOficina.setOficina(oficina);
                presencaOficinaListPresencaOficina = em.merge(presencaOficinaListPresencaOficina);
                if (oldOficinaOfPresencaOficinaListPresencaOficina != null) {
                    oldOficinaOfPresencaOficinaListPresencaOficina.getPresencaOficinaList().remove(presencaOficinaListPresencaOficina);
                    oldOficinaOfPresencaOficinaListPresencaOficina = em.merge(oldOficinaOfPresencaOficinaListPresencaOficina);
                }
            }
            for (CadastroOficina cadastroOficinaListCadastroOficina : oficina.getCadastroOficinaList()) {
                Oficina oldOficinaOfCadastroOficinaListCadastroOficina = cadastroOficinaListCadastroOficina.getOficina();
                cadastroOficinaListCadastroOficina.setOficina(oficina);
                cadastroOficinaListCadastroOficina = em.merge(cadastroOficinaListCadastroOficina);
                if (oldOficinaOfCadastroOficinaListCadastroOficina != null) {
                    oldOficinaOfCadastroOficinaListCadastroOficina.getCadastroOficinaList().remove(cadastroOficinaListCadastroOficina);
                    oldOficinaOfCadastroOficinaListCadastroOficina = em.merge(oldOficinaOfCadastroOficinaListCadastroOficina);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Oficina oficina) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oficina persistentOficina = em.find(Oficina.class, oficina.getIdoficina());
            List<PresencaOficina> presencaOficinaListOld = persistentOficina.getPresencaOficinaList();
            List<PresencaOficina> presencaOficinaListNew = oficina.getPresencaOficinaList();
            List<CadastroOficina> cadastroOficinaListOld = persistentOficina.getCadastroOficinaList();
            List<CadastroOficina> cadastroOficinaListNew = oficina.getCadastroOficinaList();
            List<String> illegalOrphanMessages = null;
            for (PresencaOficina presencaOficinaListOldPresencaOficina : presencaOficinaListOld) {
                if (!presencaOficinaListNew.contains(presencaOficinaListOldPresencaOficina)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PresencaOficina " + presencaOficinaListOldPresencaOficina + " since its oficina field is not nullable.");
                }
            }
            for (CadastroOficina cadastroOficinaListOldCadastroOficina : cadastroOficinaListOld) {
                if (!cadastroOficinaListNew.contains(cadastroOficinaListOldCadastroOficina)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CadastroOficina " + cadastroOficinaListOldCadastroOficina + " since its oficina field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PresencaOficina> attachedPresencaOficinaListNew = new ArrayList<PresencaOficina>();
            for (PresencaOficina presencaOficinaListNewPresencaOficinaToAttach : presencaOficinaListNew) {
                presencaOficinaListNewPresencaOficinaToAttach = em.getReference(presencaOficinaListNewPresencaOficinaToAttach.getClass(), presencaOficinaListNewPresencaOficinaToAttach.getIdpresencaOficina());
                attachedPresencaOficinaListNew.add(presencaOficinaListNewPresencaOficinaToAttach);
            }
            presencaOficinaListNew = attachedPresencaOficinaListNew;
            oficina.setPresencaOficinaList(presencaOficinaListNew);
            List<CadastroOficina> attachedCadastroOficinaListNew = new ArrayList<CadastroOficina>();
            for (CadastroOficina cadastroOficinaListNewCadastroOficinaToAttach : cadastroOficinaListNew) {
                cadastroOficinaListNewCadastroOficinaToAttach = em.getReference(cadastroOficinaListNewCadastroOficinaToAttach.getClass(), cadastroOficinaListNewCadastroOficinaToAttach.getIdcadastroOficina());
                attachedCadastroOficinaListNew.add(cadastroOficinaListNewCadastroOficinaToAttach);
            }
            cadastroOficinaListNew = attachedCadastroOficinaListNew;
            oficina.setCadastroOficinaList(cadastroOficinaListNew);
            oficina = em.merge(oficina);
            for (PresencaOficina presencaOficinaListNewPresencaOficina : presencaOficinaListNew) {
                if (!presencaOficinaListOld.contains(presencaOficinaListNewPresencaOficina)) {
                    Oficina oldOficinaOfPresencaOficinaListNewPresencaOficina = presencaOficinaListNewPresencaOficina.getOficina();
                    presencaOficinaListNewPresencaOficina.setOficina(oficina);
                    presencaOficinaListNewPresencaOficina = em.merge(presencaOficinaListNewPresencaOficina);
                    if (oldOficinaOfPresencaOficinaListNewPresencaOficina != null && !oldOficinaOfPresencaOficinaListNewPresencaOficina.equals(oficina)) {
                        oldOficinaOfPresencaOficinaListNewPresencaOficina.getPresencaOficinaList().remove(presencaOficinaListNewPresencaOficina);
                        oldOficinaOfPresencaOficinaListNewPresencaOficina = em.merge(oldOficinaOfPresencaOficinaListNewPresencaOficina);
                    }
                }
            }
            for (CadastroOficina cadastroOficinaListNewCadastroOficina : cadastroOficinaListNew) {
                if (!cadastroOficinaListOld.contains(cadastroOficinaListNewCadastroOficina)) {
                    Oficina oldOficinaOfCadastroOficinaListNewCadastroOficina = cadastroOficinaListNewCadastroOficina.getOficina();
                    cadastroOficinaListNewCadastroOficina.setOficina(oficina);
                    cadastroOficinaListNewCadastroOficina = em.merge(cadastroOficinaListNewCadastroOficina);
                    if (oldOficinaOfCadastroOficinaListNewCadastroOficina != null && !oldOficinaOfCadastroOficinaListNewCadastroOficina.equals(oficina)) {
                        oldOficinaOfCadastroOficinaListNewCadastroOficina.getCadastroOficinaList().remove(cadastroOficinaListNewCadastroOficina);
                        oldOficinaOfCadastroOficinaListNewCadastroOficina = em.merge(oldOficinaOfCadastroOficinaListNewCadastroOficina);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = oficina.getIdoficina();
                if (findOficina(id) == null) {
                    throw new NonexistentEntityException("The oficina with id " + id + " no longer exists.");
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
            Oficina oficina;
            try {
                oficina = em.getReference(Oficina.class, id);
                oficina.getIdoficina();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The oficina with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PresencaOficina> presencaOficinaListOrphanCheck = oficina.getPresencaOficinaList();
            for (PresencaOficina presencaOficinaListOrphanCheckPresencaOficina : presencaOficinaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Oficina (" + oficina + ") cannot be destroyed since the PresencaOficina " + presencaOficinaListOrphanCheckPresencaOficina + " in its presencaOficinaList field has a non-nullable oficina field.");
            }
            List<CadastroOficina> cadastroOficinaListOrphanCheck = oficina.getCadastroOficinaList();
            for (CadastroOficina cadastroOficinaListOrphanCheckCadastroOficina : cadastroOficinaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Oficina (" + oficina + ") cannot be destroyed since the CadastroOficina " + cadastroOficinaListOrphanCheckCadastroOficina + " in its cadastroOficinaList field has a non-nullable oficina field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(oficina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Oficina> findOficinaEntities() {
        return findOficinaEntities(true, -1, -1);
    }

    public List<Oficina> findOficinaEntities(int maxResults, int firstResult) {
        return findOficinaEntities(false, maxResults, firstResult);
    }

    private List<Oficina> findOficinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Oficina.class));
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

    public Oficina findOficina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Oficina.class, id);
        } finally {
            em.close();
        }
    }

    public int getOficinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Oficina> rt = cq.from(Oficina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
