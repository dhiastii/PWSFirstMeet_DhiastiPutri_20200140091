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
public class KelasJpaController implements Serializable {

    public KelasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example_firstmeetpws_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Kelas kelas) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hasilkelulusan hasilkelulusan = kelas.getHasilkelulusan();
            if (hasilkelulusan != null) {
                hasilkelulusan = em.getReference(hasilkelulusan.getClass(), hasilkelulusan.getIdKelulusan());
                kelas.setHasilkelulusan(hasilkelulusan);
            }
            em.persist(kelas);
            if (hasilkelulusan != null) {
                Kelas oldNamaSekolahOfHasilkelulusan = hasilkelulusan.getNamaSekolah();
                if (oldNamaSekolahOfHasilkelulusan != null) {
                    oldNamaSekolahOfHasilkelulusan.setHasilkelulusan(null);
                    oldNamaSekolahOfHasilkelulusan = em.merge(oldNamaSekolahOfHasilkelulusan);
                }
                hasilkelulusan.setNamaSekolah(kelas);
                hasilkelulusan = em.merge(hasilkelulusan);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKelas(kelas.getIdKelas()) != null) {
                throw new PreexistingEntityException("Kelas " + kelas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kelas kelas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kelas persistentKelas = em.find(Kelas.class, kelas.getIdKelas());
            Hasilkelulusan hasilkelulusanOld = persistentKelas.getHasilkelulusan();
            Hasilkelulusan hasilkelulusanNew = kelas.getHasilkelulusan();
            List<String> illegalOrphanMessages = null;
            if (hasilkelulusanOld != null && !hasilkelulusanOld.equals(hasilkelulusanNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Hasilkelulusan " + hasilkelulusanOld + " since its namaSekolah field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (hasilkelulusanNew != null) {
                hasilkelulusanNew = em.getReference(hasilkelulusanNew.getClass(), hasilkelulusanNew.getIdKelulusan());
                kelas.setHasilkelulusan(hasilkelulusanNew);
            }
            kelas = em.merge(kelas);
            if (hasilkelulusanNew != null && !hasilkelulusanNew.equals(hasilkelulusanOld)) {
                Kelas oldNamaSekolahOfHasilkelulusan = hasilkelulusanNew.getNamaSekolah();
                if (oldNamaSekolahOfHasilkelulusan != null) {
                    oldNamaSekolahOfHasilkelulusan.setHasilkelulusan(null);
                    oldNamaSekolahOfHasilkelulusan = em.merge(oldNamaSekolahOfHasilkelulusan);
                }
                hasilkelulusanNew.setNamaSekolah(kelas);
                hasilkelulusanNew = em.merge(hasilkelulusanNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = kelas.getIdKelas();
                if (findKelas(id) == null) {
                    throw new NonexistentEntityException("The kelas with id " + id + " no longer exists.");
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
            Kelas kelas;
            try {
                kelas = em.getReference(Kelas.class, id);
                kelas.getIdKelas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kelas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Hasilkelulusan hasilkelulusanOrphanCheck = kelas.getHasilkelulusan();
            if (hasilkelulusanOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Kelas (" + kelas + ") cannot be destroyed since the Hasilkelulusan " + hasilkelulusanOrphanCheck + " in its hasilkelulusan field has a non-nullable namaSekolah field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(kelas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kelas> findKelasEntities() {
        return findKelasEntities(true, -1, -1);
    }

    public List<Kelas> findKelasEntities(int maxResults, int firstResult) {
        return findKelasEntities(false, maxResults, firstResult);
    }

    private List<Kelas> findKelasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kelas.class));
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

    public Kelas findKelas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kelas.class, id);
        } finally {
            em.close();
        }
    }

    public int getKelasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kelas> rt = cq.from(Kelas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
