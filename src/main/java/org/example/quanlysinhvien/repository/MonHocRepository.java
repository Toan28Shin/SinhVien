package org.example.quanlysinhvien.repository;

import org.example.quanlysinhvien.entity.MonHoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonHocRepository extends JpaRepository<MonHoc, Integer> {
    MonHoc findByMaMonHoc(String maMonHoc);
}