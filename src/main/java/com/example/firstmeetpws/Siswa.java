/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.firstmeetpws;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "siswa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Siswa.findAll", query = "SELECT s FROM Siswa s"),
    @NamedQuery(name = "Siswa.findByNisn", query = "SELECT s FROM Siswa s WHERE s.nisn = :nisn"),
    @NamedQuery(name = "Siswa.findByNamaSiswa", query = "SELECT s FROM Siswa s WHERE s.namaSiswa = :namaSiswa"),
    @NamedQuery(name = "Siswa.findByTanggalLahir", query = "SELECT s FROM Siswa s WHERE s.tanggalLahir = :tanggalLahir"),
    @NamedQuery(name = "Siswa.findByAlamat", query = "SELECT s FROM Siswa s WHERE s.alamat = :alamat"),
    @NamedQuery(name = "Siswa.findByNamaWali", query = "SELECT s FROM Siswa s WHERE s.namaWali = :namaWali"),
    @NamedQuery(name = "Siswa.findByNoHp", query = "SELECT s FROM Siswa s WHERE s.noHp = :noHp"),
    @NamedQuery(name = "Siswa.findByNamaSekolah", query = "SELECT s FROM Siswa s WHERE s.namaSekolah = :namaSekolah")})
public class Siswa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NISN")
    private String nisn;
    @Basic(optional = false)
    @Column(name = "Nama_Siswa")
    private String namaSiswa;
    @Basic(optional = false)
    @Column(name = "Tanggal_Lahir")
    @Temporal(TemporalType.DATE)
    private Date tanggalLahir;
    @Basic(optional = false)
    @Column(name = "Alamat")
    private String alamat;
    @Basic(optional = false)
    @Column(name = "Nama_Wali")
    private String namaWali;
    @Basic(optional = false)
    @Column(name = "No_Hp")
    private String noHp;
    @Basic(optional = false)
    @Column(name = "Nama_Sekolah")
    private String namaSekolah;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "nisn")
    private Hasilkelulusan hasilkelulusan;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "nisn")
    private Getdata getdata;

    public Siswa() {
    }

    public Siswa(String nisn) {
        this.nisn = nisn;
    }

    public Siswa(String nisn, String namaSiswa, Date tanggalLahir, String alamat, String namaWali, String noHp, String namaSekolah) {
        this.nisn = nisn;
        this.namaSiswa = namaSiswa;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
        this.namaWali = namaWali;
        this.noHp = noHp;
        this.namaSekolah = namaSekolah;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public String getNamaSiswa() {
        return namaSiswa;
    }

    public void setNamaSiswa(String namaSiswa) {
        this.namaSiswa = namaSiswa;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaWali() {
        return namaWali;
    }

    public void setNamaWali(String namaWali) {
        this.namaWali = namaWali;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getNamaSekolah() {
        return namaSekolah;
    }

    public void setNamaSekolah(String namaSekolah) {
        this.namaSekolah = namaSekolah;
    }

    public Hasilkelulusan getHasilkelulusan() {
        return hasilkelulusan;
    }

    public void setHasilkelulusan(Hasilkelulusan hasilkelulusan) {
        this.hasilkelulusan = hasilkelulusan;
    }

    public Getdata getGetdata() {
        return getdata;
    }

    public void setGetdata(Getdata getdata) {
        this.getdata = getdata;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nisn != null ? nisn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Siswa)) {
            return false;
        }
        Siswa other = (Siswa) object;
        if ((this.nisn == null && other.nisn != null) || (this.nisn != null && !this.nisn.equals(other.nisn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.firstmeetpws.Siswa[ nisn=" + nisn + " ]";
    }
    
}
