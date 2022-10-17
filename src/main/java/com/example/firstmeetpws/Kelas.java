/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.firstmeetpws;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "kelas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kelas.findAll", query = "SELECT k FROM Kelas k"),
    @NamedQuery(name = "Kelas.findByIdKelas", query = "SELECT k FROM Kelas k WHERE k.idKelas = :idKelas"),
    @NamedQuery(name = "Kelas.findByTingkatanKelas", query = "SELECT k FROM Kelas k WHERE k.tingkatanKelas = :tingkatanKelas"),
    @NamedQuery(name = "Kelas.findByWaliKelas", query = "SELECT k FROM Kelas k WHERE k.waliKelas = :waliKelas"),
    @NamedQuery(name = "Kelas.findByNamaSekolah", query = "SELECT k FROM Kelas k WHERE k.namaSekolah = :namaSekolah"),
    @NamedQuery(name = "Kelas.findByNisn", query = "SELECT k FROM Kelas k WHERE k.nisn = :nisn")})
public class Kelas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Kelas")
    private Integer idKelas;
    @Basic(optional = false)
    @Column(name = "Tingkatan_Kelas")
    private String tingkatanKelas;
    @Basic(optional = false)
    @Column(name = "Wali_Kelas")
    private String waliKelas;
    @Basic(optional = false)
    @Column(name = "Nama_Sekolah")
    private String namaSekolah;
    @Basic(optional = false)
    @Column(name = "NISN")
    private String nisn;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "namaSekolah")
    private Hasilkelulusan hasilkelulusan;

    public Kelas() {
    }

    public Kelas(Integer idKelas) {
        this.idKelas = idKelas;
    }

    public Kelas(Integer idKelas, String tingkatanKelas, String waliKelas, String namaSekolah, String nisn) {
        this.idKelas = idKelas;
        this.tingkatanKelas = tingkatanKelas;
        this.waliKelas = waliKelas;
        this.namaSekolah = namaSekolah;
        this.nisn = nisn;
    }

    public Integer getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(Integer idKelas) {
        this.idKelas = idKelas;
    }

    public String getTingkatanKelas() {
        return tingkatanKelas;
    }

    public void setTingkatanKelas(String tingkatanKelas) {
        this.tingkatanKelas = tingkatanKelas;
    }

    public String getWaliKelas() {
        return waliKelas;
    }

    public void setWaliKelas(String waliKelas) {
        this.waliKelas = waliKelas;
    }

    public String getNamaSekolah() {
        return namaSekolah;
    }

    public void setNamaSekolah(String namaSekolah) {
        this.namaSekolah = namaSekolah;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public Hasilkelulusan getHasilkelulusan() {
        return hasilkelulusan;
    }

    public void setHasilkelulusan(Hasilkelulusan hasilkelulusan) {
        this.hasilkelulusan = hasilkelulusan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKelas != null ? idKelas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kelas)) {
            return false;
        }
        Kelas other = (Kelas) object;
        if ((this.idKelas == null && other.idKelas != null) || (this.idKelas != null && !this.idKelas.equals(other.idKelas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.firstmeetpws.Kelas[ idKelas=" + idKelas + " ]";
    }
    
}
