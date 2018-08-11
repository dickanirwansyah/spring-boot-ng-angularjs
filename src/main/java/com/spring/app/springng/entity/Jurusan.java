package com.spring.app.springng.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jurusan",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
        })
public class Jurusan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idjurusan;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "jurusan", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Mahasiswa> mahasiswas = new HashSet<>();

    public Set<Mahasiswa> getMahasiswas(){
        return mahasiswas;
    }

    public void setMahasiswas(Set<Mahasiswa> mahasiswas){
        this.mahasiswas = mahasiswas;
    }

    public Long getIdjurusan(){
        return idjurusan;
    }

    public void setIdjurusan(Long idjurusan){
        this.idjurusan = idjurusan;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }
}
