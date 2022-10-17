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
@Table(name = "adminapp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adminapp.findAll", query = "SELECT a FROM Adminapp a"),
    @NamedQuery(name = "Adminapp.findByNip", query = "SELECT a FROM Adminapp a WHERE a.nip = :nip"),
    @NamedQuery(name = "Adminapp.findByNamaAdmin", query = "SELECT a FROM Adminapp a WHERE a.namaAdmin = :namaAdmin"),
    @NamedQuery(name = "Adminapp.findByNamaSekolah", query = "SELECT a FROM Adminapp a WHERE a.namaSekolah = :namaSekolah")})
public class Adminapp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NIP")
    private String nip;
    @Basic(optional = false)
    @Column(name = "Nama_Admin")
    private String namaAdmin;
    @Basic(optional = false)
    @Column(name = "Nama_Sekolah")
    private String namaSekolah;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "namaSekolah")
    private Getdata getdata;

    public Adminapp() {
    }

    public Adminapp(String nip) {
        this.nip = nip;
    }

    public Adminapp(String nip, String namaAdmin, String namaSekolah) {
        this.nip = nip;
        this.namaAdmin = namaAdmin;
        this.namaSekolah = namaSekolah;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNamaAdmin() {
        return namaAdmin;
    }

    public void setNamaAdmin(String namaAdmin) {
        this.namaAdmin = namaAdmin;
    }

    public String getNamaSekolah() {
        return namaSekolah;
    }

    public void setNamaSekolah(String namaSekolah) {
        this.namaSekolah = namaSekolah;
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
        hash += (nip != null ? nip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adminapp)) {
            return false;
        }
        Adminapp other = (Adminapp) object;
        if ((this.nip == null && other.nip != null) || (this.nip != null && !this.nip.equals(other.nip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.firstmeetpws.Adminapp[ nip=" + nip + " ]";
    }
    
}
