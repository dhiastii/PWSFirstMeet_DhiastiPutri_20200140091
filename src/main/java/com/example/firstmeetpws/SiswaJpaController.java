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

/**
 *
 * @author DELL
 */
public class SiswaJpaController implements Serializable {

    public SiswaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Siswa siswa) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hasilkelulusan hasilkelulusan = siswa.getHasilkelulusan();
            if (hasilkelulusan != null) {
                hasilkelulusan = em.getReference(hasilkelulusan.getClass(), hasilkelulusan.getIdKelulusan());
                siswa.setHasilkelulusan(hasilkelulusan);
            }
            Getdata getdata = siswa.getGetdata();
            if (getdata != null) {
                getdata = em.getReference(getdata.getClass(), getdata.getIdGetdata());
                siswa.setGetdata(getdata);
            }
            em.persist(siswa);
            if (hasilkelulusan != null) {
                Siswa oldNisnOfHasilkelulusan = hasilkelulusan.getNisn();
                if (oldNisnOfHasilkelulusan != null) {
                    oldNisnOfHasilkelulusan.setHasilkelulusan(null);
                    oldNisnOfHasilkelulusan = em.merge(oldNisnOfHasilkelulusan);
                }
                hasilkelulusan.setNisn(siswa);
                hasilkelulusan = em.merge(hasilkelulusan);
            }
            if (getdata != null) {
                Siswa oldNisnOfGetdata = getdata.getNisn();
                if (oldNisnOfGetdata != null) {
                    oldNisnOfGetdata.setGetdata(null);
                    oldNisnOfGetdata = em.merge(oldNisnOfGetdata);
                }
                getdata.setNisn(siswa);
                getdata = em.merge(getdata);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSiswa(siswa.getNisn()) != null) {
                throw new PreexistingEntityException("Siswa " + siswa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Siswa siswa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Siswa persistentSiswa = em.find(Siswa.class, siswa.getNisn());
            Hasilkelulusan hasilkelulusanOld = persistentSiswa.getHasilkelulusan();
            Hasilkelulusan hasilkelulusanNew = siswa.getHasilkelulusan();
            Getdata getdataOld = persistentSiswa.getGetdata();
            Getdata getdataNew = siswa.getGetdata();
            List<String> illegalOrphanMessages = null;
            if (hasilkelulusanOld != null && !hasilkelulusanOld.equals(hasilkelulusanNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Hasilkelulusan " + hasilkelulusanOld + " since its nisn field is not nullable.");
            }
            if (getdataOld != null && !getdataOld.equals(getdataNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Getdata " + getdataOld + " since its nisn field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (hasilkelulusanNew != null) {
                hasilkelulusanNew = em.getReference(hasilkelulusanNew.getClass(), hasilkelulusanNew.getIdKelulusan());
                siswa.setHasilkelulusan(hasilkelulusanNew);
            }
            if (getdataNew != null) {
                getdataNew = em.getReference(getdataNew.getClass(), getdataNew.getIdGetdata());
                siswa.setGetdata(getdataNew);
            }
            siswa = em.merge(siswa);
            if (hasilkelulusanNew != null && !hasilkelulusanNew.equals(hasilkelulusanOld)) {
                Siswa oldNisnOfHasilkelulusan = hasilkelulusanNew.getNisn();
                if (oldNisnOfHasilkelulusan != null) {
                    oldNisnOfHasilkelulusan.setHasilkelulusan(null);
                    oldNisnOfHasilkelulusan = em.merge(oldNisnOfHasilkelulusan);
                }
                hasilkelulusanNew.setNisn(siswa);
                hasilkelulusanNew = em.merge(hasilkelulusanNew);
            }
            if (getdataNew != null && !getdataNew.equals(getdataOld)) {
                Siswa oldNisnOfGetdata = getdataNew.getNisn();
                if (oldNisnOfGetdata != null) {
                    oldNisnOfGetdata.setGetdata(null);
                    oldNisnOfGetdata = em.merge(oldNisnOfGetdata);
                }
                getdataNew.setNisn(siswa);
                getdataNew = em.merge(getdataNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = siswa.getNisn();
                if (findSiswa(id) == null) {
                    throw new NonexistentEntityException("The siswa with id " + id + " no longer exists.");
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
            Siswa siswa;
            try {
                siswa = em.getReference(Siswa.class, id);
                siswa.getNisn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The siswa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Hasilkelulusan hasilkelulusanOrphanCheck = siswa.getHasilkelulusan();
            if (hasilkelulusanOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Siswa (" + siswa + ") cannot be destroyed since the Hasilkelulusan " + hasilkelulusanOrphanCheck + " in its hasilkelulusan field has a non-nullable nisn field.");
            }
            Getdata getdataOrphanCheck = siswa.getGetdata();
            if (getdataOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Siswa (" + siswa + ") cannot be destroyed since the Getdata " + getdataOrphanCheck + " in its getdata field has a non-nullable nisn field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(siswa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Siswa> findSiswaEntities() {
        return findSiswaEntities(true, -1, -1);
    }

    public List<Siswa> findSiswaEntities(int maxResults, int firstResult) {
        return findSiswaEntities(false, maxResults, firstResult);
    }

    private List<Siswa> findSiswaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Siswa.class));
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

    public Siswa findSiswa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Siswa.class, id);
        } finally {
            em.close();
        }
    }

    public int getSiswaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Siswa> rt = cq.from(Siswa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
