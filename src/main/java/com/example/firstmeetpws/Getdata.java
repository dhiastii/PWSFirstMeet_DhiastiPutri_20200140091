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
@Table(name = "getdata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Getdata.findAll", query = "SELECT g FROM Getdata g"),
    @NamedQuery(name = "Getdata.findByIdGetdata", query = "SELECT g FROM Getdata g WHERE g.idGetdata = :idGetdata"),
    @NamedQuery(name = "Getdata.findByNip", query = "SELECT g FROM Getdata g WHERE g.nip = :nip"),
    @NamedQuery(name = "Getdata.findByPeran", query = "SELECT g FROM Getdata g WHERE g.peran = :peran")})
public class Getdata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Getdata")
    private Integer idGetdata;
    @Basic(optional = false)
    @Column(name = "NIP")
    private String nip;
    @Basic(optional = false)
    @Column(name = "Peran")
    private String peran;
    @JoinColumn(name = "Nama_Sekolah", referencedColumnName = "Nama_Sekolah")
    @OneToOne(optional = false)
    private Adminapp namaSekolah;
    @JoinColumn(name = "NISN", referencedColumnName = "NISN")
    @OneToOne(optional = false)
    private Siswa nisn;

    public Getdata() {
    }

    public Getdata(Integer idGetdata) {
        this.idGetdata = idGetdata;
    }

    public Getdata(Integer idGetdata, String nip, String peran) {
        this.idGetdata = idGetdata;
        this.nip = nip;
        this.peran = peran;
    }

    public Integer getIdGetdata() {
        return idGetdata;
    }

    public void setIdGetdata(Integer idGetdata) {
        this.idGetdata = idGetdata;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPeran() {
        return peran;
    }

    public void setPeran(String peran) {
        this.peran = peran;
    }

    public Adminapp getNamaSekolah() {
        return namaSekolah;
    }

    public void setNamaSekolah(Adminapp namaSekolah) {
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
        hash += (idGetdata != null ? idGetdata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Getdata)) {
            return false;
        }
        Getdata other = (Getdata) object;
        if ((this.idGetdata == null && other.idGetdata != null) || (this.idGetdata != null && !this.idGetdata.equals(other.idGetdata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.firstmeetpws.Getdata[ idGetdata=" + idGetdata + " ]";
    }
    
}
