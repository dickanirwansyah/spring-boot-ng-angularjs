package com.spring.app.springng.repository;

import com.spring.app.springng.entity.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Long>{
}
