/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.firstmeetpws;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "hasilkelulusan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hasilkelulusan.findAll", query = "SELECT h FROM Hasilkelulusan h"),
    @NamedQuery(name = "Hasilkelulusan.findByIdKelulusan", query = "SELECT h FROM Hasilkelulusan h WHERE h.idKelulusan = :idKelulusan"),
    @NamedQuery(name = "Hasilkelulusan.findByHasilSiswa", query = "SELECT h FROM Hasilkelulusan h WHERE h.hasilSiswa = :hasilSiswa"),
    @NamedQuery(name = "Hasilkelulusan.findByIdKelas", query = "SELECT h FROM Hasilkelulusan h WHERE h.idKelas = :idKelas")})
public class Hasilkelulusan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Kelulusan")
    private Integer idKelulusan;
    @Basic(optional = false)
    @Column(name = "Hasil_Siswa")
    private String hasilSiswa;
    @Basic(optional = false)
    @Column(name = "Id_Kelas")
    private int idKelas;
    @JoinColumn(name = "Nama_Sekolah", referencedColumnName = "Nama_Sekolah")
    @OneToOne(optional = false)
    private Kelas namaSekolah;
    @JoinColumn(name = "NISN", referencedColumnName = "NISN")
    @OneToOne(optional = false)
    private Siswa nisn;

    public Hasilkelulusan() {
    }

    public Hasilkelulusan(Integer idKelulusan) {
        this.idKelulusan = idKelulusan;
    }

    public Hasilkelulusan(Integer idKelulusan, String hasilSiswa, int idKelas) {
        this.idKelulusan = idKelulusan;
        this.hasilSiswa = hasilSiswa;
        this.idKelas = idKelas;
    }

    public Integer getIdKelulusan() {
        return idKelulusan;
    }

    public void setIdKelulusan(Integer idKelulusan) {
        this.idKelulusan = idKelulusan;
    }

    public String getHasilSiswa() {
        return hasilSiswa;
    }

    public void setHasilSiswa(String hasilSiswa) {
        this.hasilSiswa = hasilSiswa;
    }

    public int getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(int idKelas) {
        this.idKelas = idKelas;
    }

    public Kelas getNamaSekolah() {
        return namaSekolah;
    }

    public void setNamaSekolah(Kelas namaSekolah) {
        this.namaSekolah = namaSekolah;
    }

    public Siswa getNisn() {
        return nisn;
    }

    public void setNisn(Siswa nisn) {
        this.nisn = nisn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKelulusan != null ? idKelulusan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hasilkelulusan)) {
            return false;
        }
        Hasilkelulusan other = (Hasilkelulusan) object;
        if ((this.idKelulusan == null && other.idKelulusan != null) || (this.idKelulusan != null && !this.idKelulusan.equals(other.idKelulusan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.firstmeetpws.Hasilkelulusan[ idKelulusan=" + idKelulusan + " ]";
    }
    
}
