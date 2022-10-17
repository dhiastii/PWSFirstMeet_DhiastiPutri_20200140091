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
public class GetdataJpaController implements Serializable {

    public GetdataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
   private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example_firstmeetpws_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public GetdataJpaController() {
    }

    
    
    public void create(Getdata getdata) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Adminapp namaSekolahOrphanCheck = getdata.getNamaSekolah();
        if (namaSekolahOrphanCheck != null) {
            Getdata oldGetdataOfNamaSekolah = namaSekolahOrphanCheck.getGetdata();
            if (oldGetdataOfNamaSekolah != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Adminapp " + namaSekolahOrphanCheck + " already has an item of type Getdata whose namaSekolah column cannot be null. Please make another selection for the namaSekolah field.");
            }
        }
        Siswa nisnOrphanCheck = getdata.getNisn();
        if (nisnOrphanCheck != null) {
            Getdata oldGetdataOfNisn = nisnOrphanCheck.getGetdata();
            if (oldGetdataOfNisn != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Siswa " + nisnOrphanCheck + " already has an item of type Getdata whose nisn column cannot be null. Please make another selection for the nisn field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Adminapp namaSekolah = getdata.getNamaSekolah();
            if (namaSekolah != null) {
                namaSekolah = em.getReference(namaSekolah.getClass(), namaSekolah.getNip());
                getdata.setNamaSekolah(namaSekolah);
            }
            Siswa nisn = getdata.getNisn();
            if (nisn != null) {
                nisn = em.getReference(nisn.getClass(), nisn.getNisn());
                getdata.setNisn(nisn);
            }
            em.persist(getdata);
            if (namaSekolah != null) {
                namaSekolah.setGetdata(getdata);
                namaSekolah = em.merge(namaSekolah);
            }
            if (nisn != null) {
                nisn.setGetdata(getdata);
                nisn = em.merge(nisn);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGetdata(getdata.getIdGetdata()) != null) {
                throw new PreexistingEntityException("Getdata " + getdata + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Getdata getdata) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Getdata persistentGetdata = em.find(Getdata.class, getdata.getIdGetdata());
            Adminapp namaSekolahOld = persistentGetdata.getNamaSekolah();
            Adminapp namaSekolahNew = getdata.getNamaSekolah();
            Siswa nisnOld = persistentGetdata.getNisn();
            Siswa nisnNew = getdata.getNisn();
            List<String> illegalOrphanMessages = null;
            if (namaSekolahNew != null && !namaSekolahNew.equals(namaSekolahOld)) {
                Getdata oldGetdataOfNamaSekolah = namaSekolahNew.getGetdata();
                if (oldGetdataOfNamaSekolah != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Adminapp " + namaSekolahNew + " already has an item of type Getdata whose namaSekolah column cannot be null. Please make another selection for the namaSekolah field.");
                }
            }
            if (nisnNew != null && !nisnNew.equals(nisnOld)) {
                Getdata oldGetdataOfNisn = nisnNew.getGetdata();
                if (oldGetdataOfNisn != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Siswa " + nisnNew + " already has an item of type Getdata whose nisn column cannot be null. Please make another selection for the nisn field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (namaSekolahNew != null) {
                namaSekolahNew = em.getReference(namaSekolahNew.getClass(), namaSekolahNew.getNip());
                getdata.setNamaSekolah(namaSekolahNew);
            }
            if (nisnNew != null) {
                nisnNew = em.getReference(nisnNew.getClass(), nisnNew.getNisn());
                getdata.setNisn(nisnNew);
            }
            getdata = em.merge(getdata);
            if (namaSekolahOld != null && !namaSekolahOld.equals(namaSekolahNew)) {
                namaSekolahOld.setGetdata(null);
                namaSekolahOld = em.merge(namaSekolahOld);
            }
            if (namaSekolahNew != null && !namaSekolahNew.equals(namaSekolahOld)) {
                namaSekolahNew.setGetdata(getdata);
                namaSekolahNew = em.merge(namaSekolahNew);
            }
            if (nisnOld != null && !nisnOld.equals(nisnNew)) {
                nisnOld.setGetdata(null);
                nisnOld = em.merge(nisnOld);
            }
            if (nisnNew != null && !nisnNew.equals(nisnOld)) {
                nisnNew.setGetdata(getdata);
                nisnNew = em.merge(nisnNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = getdata.getIdGetdata();
                if (findGetdata(id) == null) {
                    throw new NonexistentEntityException("The getdata with id " + id + " no longer exists.");
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
            Getdata getdata;
            try {
                getdata = em.getReference(Getdata.class, id);
                getdata.getIdGetdata();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The getdata with id " + id + " no longer exists.", enfe);
            }
            Adminapp namaSekolah = getdata.getNamaSekolah();
            if (namaSekolah != null) {
                namaSekolah.setGetdata(null);
                namaSekolah = em.merge(namaSekolah);
            }
            Siswa nisn = getdata.getNisn();
            if (nisn != null) {
                nisn.setGetdata(null);
                nisn = em.merge(nisn);
            }
            em.remove(getdata);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Getdata> findGetdataEntities() {
        return findGetdataEntities(true, -1, -1);
    }

    public List<Getdata> findGetdataEntities(int maxResults, int firstResult) {
        return findGetdataEntities(false, maxResults, firstResult);
    }

    private List<Getdata> findGetdataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Getdata.class));
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

    public Getdata findGetdata(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Getdata.class, id);
        } finally {
            em.close();
        }
    }

    public int getGetdataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Getdata> rt = cq.from(Getdata.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
