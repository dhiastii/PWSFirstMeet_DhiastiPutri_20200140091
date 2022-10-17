/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.firstmeetpws;

import com.example.firstmeetpws.exceptions.IllegalOrphanException;
import com.example.firstmeetpws.exceptions.NonexistentEntityException;
import com.example.firstmeetpws.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author DELL
 */
public class AdminappJpaController implements Serializable {

    public AdminappJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example_firstmeetpws_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public AdminappJpaController() {
    }
    
    

    public void create(Adminapp adminapp) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Getdata getdata = adminapp.getGetdata();
            if (getdata != null) {
                getdata = em.getReference(getdata.getClass(), getdata.getIdGetdata());
                adminapp.setGetdata(getdata);
            }
            em.persist(adminapp);
            if (getdata != null) {
                Adminapp oldNamaSekolahOfGetdata = getdata.getNamaSekolah();
                if (oldNamaSekolahOfGetdata != null) {
                    oldNamaSekolahOfGetdata.setGetdata(null);
                    oldNamaSekolahOfGetdata = em.merge(oldNamaSekolahOfGetdata);
                }
                getdata.setNamaSekolah(adminapp);
                getdata = em.merge(getdata);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdminapp(adminapp.getNip()) != null) {
                throw new PreexistingEntityException("Adminapp " + adminapp + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Adminapp adminapp) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Adminapp persistentAdminapp = em.find(Adminapp.class, adminapp.getNip());
            Getdata getdataOld = persistentAdminapp.getGetdata();
            Getdata getdataNew = adminapp.getGetdata();
            List<String> illegalOrphanMessages = null;
            if (getdataOld != null && !getdataOld.equals(getdataNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Getdata " + getdataOld + " since its namaSekolah field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (getdataNew != null) {
                getdataNew = em.getReference(getdataNew.getClass(), getdataNew.getIdGetdata());
                adminapp.setGetdata(getdataNew);
            }
            adminapp = em.merge(adminapp);
            if (getdataNew != null && !getdataNew.equals(getdataOld)) {
                Adminapp oldNamaSekolahOfGetdata = getdataNew.getNamaSekolah();
                if (oldNamaSekolahOfGetdata != null) {
                    oldNamaSekolahOfGetdata.setGetdata(null);
                    oldNamaSekolahOfGetdata = em.merge(oldNamaSekolahOfGetdata);
                }
                getdataNew.setNamaSekolah(adminapp);
                getdataNew = em.merge(getdataNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = adminapp.getNip();
                if (findAdminapp(id) == null) {
                    throw new NonexistentEntityException("The adminapp with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Adminapp adminapp;
            try {
                adminapp = em.getReference(Adminapp.class, id);
                adminapp.getNip();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adminapp with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Getdata getdataOrphanCheck = adminapp.getGetdata();
            if (getdataOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Adminapp (" + adminapp + ") cannot be destroyed since the Getdata " + getdataOrphanCheck + " in its getdata field has a non-nullable namaSekolah field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(adminapp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Adminapp> findAdminappEntities() {
        return findAdminappEntities(true, -1, -1);
    }

    public List<Adminapp> findAdminappEntities(int maxResults, int firstResult) {
        return findAdminappEntities(false, maxResults, firstResult);
    }

    private List<Adminapp> findAdminappEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Adminapp.class));
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

    public Adminapp findAdminapp(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Adminapp.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdminappCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Adminapp> rt = cq.from(Adminapp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
