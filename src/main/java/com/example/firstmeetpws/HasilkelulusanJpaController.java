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
public class HasilkelulusanJpaController implements Serializable {

    public HasilkelulusanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example_firstmeetpws_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hasilkelulusan hasilkelulusan) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Kelas namaSekolahOrphanCheck = hasilkelulusan.getNamaSekolah();
        if (namaSekolahOrphanCheck != null) {
            Hasilkelulusan oldHasilkelulusanOfNamaSekolah = namaSekolahOrphanCheck.getHasilkelulusan();
            if (oldHasilkelulusanOfNamaSekolah != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Kelas " + namaSekolahOrphanCheck + " already has an item of type Hasilkelulusan whose namaSekolah column cannot be null. Please make another selection for the namaSekolah field.");
            }
        }
        Siswa nisnOrphanCheck = hasilkelulusan.getNisn();
        if (nisnOrphanCheck != null) {
            Hasilkelulusan oldHasilkelulusanOfNisn = nisnOrphanCheck.getHasilkelulusan();
            if (oldHasilkelulusanOfNisn != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Siswa " + nisnOrphanCheck + " already has an item of type Hasilkelulusan whose nisn column cannot be null. Please make another selection for the nisn field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kelas namaSekolah = hasilkelulusan.getNamaSekolah();
            if (namaSekolah != null) {
                namaSekolah = em.getReference(namaSekolah.getClass(), namaSekolah.getIdKelas());
                hasilkelulusan.setNamaSekolah(namaSekolah);
            }
            Siswa nisn = hasilkelulusan.getNisn();
            if (nisn != null) {
                nisn = em.getReference(nisn.getClass(), nisn.getNisn());
                hasilkelulusan.setNisn(nisn);
            }
            em.persist(hasilkelulusan);
            if (namaSekolah != null) {
                namaSekolah.setHasilkelulusan(hasilkelulusan);
                namaSekolah = em.merge(namaSekolah);
            }
            if (nisn != null) {
                nisn.setHasilkelulusan(hasilkelulusan);
                nisn = em.merge(nisn);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHasilkelulusan(hasilkelulusan.getIdKelulusan()) != null) {
                throw new PreexistingEntityException("Hasilkelulusan " + hasilkelulusan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hasilkelulusan hasilkelulusan) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hasilkelulusan persistentHasilkelulusan = em.find(Hasilkelulusan.class, hasilkelulusan.getIdKelulusan());
            Kelas namaSekolahOld = persistentHasilkelulusan.getNamaSekolah();
            Kelas namaSekolahNew = hasilkelulusan.getNamaSekolah();
            Siswa nisnOld = persistentHasilkelulusan.getNisn();
            Siswa nisnNew = hasilkelulusan.getNisn();
            List<String> illegalOrphanMessages = null;
            if (namaSekolahNew != null && !namaSekolahNew.equals(namaSekolahOld)) {
                Hasilkelulusan oldHasilkelulusanOfNamaSekolah = namaSekolahNew.getHasilkelulusan();
                if (oldHasilkelulusanOfNamaSekolah != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Kelas " + namaSekolahNew + " already has an item of type Hasilkelulusan whose namaSekolah column cannot be null. Please make another selection for the namaSekolah field.");
                }
            }
            if (nisnNew != null && !nisnNew.equals(nisnOld)) {
                Hasilkelulusan oldHasilkelulusanOfNisn = nisnNew.getHasilkelulusan();
                if (oldHasilkelulusanOfNisn != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Siswa " + nisnNew + " already has an item of type Hasilkelulusan whose nisn column cannot be null. Please make another selection for the nisn field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (namaSekolahNew != null) {
                namaSekolahNew = em.getReference(namaSekolahNew.getClass(), namaSekolahNew.getIdKelas());
                hasilkelulusan.setNamaSekolah(namaSekolahNew);
            }
            if (nisnNew != null) {
                nisnNew = em.getReference(nisnNew.getClass(), nisnNew.getNisn());
                hasilkelulusan.setNisn(nisnNew);
            }
            hasilkelulusan = em.merge(hasilkelulusan);
            if (namaSekolahOld != null && !namaSekolahOld.equals(namaSekolahNew)) {
                namaSekolahOld.setHasilkelulusan(null);
                namaSekolahOld = em.merge(namaSekolahOld);
            }
            if (namaSekolahNew != null && !namaSekolahNew.equals(namaSekolahOld)) {
                namaSekolahNew.setHasilkelulusan(hasilkelulusan);
                namaSekolahNew = em.merge(namaSekolahNew);
            }
            if (nisnOld != null && !nisnOld.equals(nisnNew)) {
                nisnOld.setHasilkelulusan(null);
                nisnOld = em.merge(nisnOld);
            }
            if (nisnNew != null && !nisnNew.equals(nisnOld)) {
                nisnNew.setHasilkelulusan(hasilkelulusan);
                nisnNew = em.merge(nisnNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = hasilkelulusan.getIdKelulusan();
                if (findHasilkelulusan(id) == null) {
                    throw new NonexistentEntityException("The hasilkelulusan with id " + id + " no longer exists.");
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
            Hasilkelulusan hasilkelulusan;
            try {
                hasilkelulusan = em.getReference(Hasilkelulusan.class, id);
                hasilkelulusan.getIdKelulusan();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hasilkelulusan with id " + id + " no longer exists.", enfe);
            }
            Kelas namaSekolah = hasilkelulusan.getNamaSekolah();
            if (namaSekolah != null) {
                namaSekolah.setHasilkelulusan(null);
                namaSekolah = em.merge(namaSekolah);
            }
            Siswa nisn = hasilkelulusan.getNisn();
            if (nisn != null) {
                nisn.setHasilkelulusan(null);
                nisn = em.merge(nisn);
            }
            em.remove(hasilkelulusan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Hasilkelulusan> findHasilkelulusanEntities() {
        return findHasilkelulusanEntities(true, -1, -1);
    }

    public List<Hasilkelulusan> findHasilkelulusanEntities(int maxResults, int firstResult) {
        return findHasilkelulusanEntities(false, maxResults, firstResult);
    }

    private List<Hasilkelulusan> findHasilkelulusanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hasilkelulusan.class));
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

    public Hasilkelulusan findHasilkelulusan(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hasilkelulusan.class, id);
        } finally {
            em.close();
        }
    }

    public int getHasilkelulusanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hasilkelulusan> rt = cq.from(Hasilkelulusan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
