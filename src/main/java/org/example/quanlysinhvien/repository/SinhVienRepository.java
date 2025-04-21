package org.example.quanlysinhvien.repository;

import org.example.quanlysinhvien.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SinhVienRepository extends JpaRepository<SinhVien, Long> {

    SinhVien findByEmail(String email);
    List<SinhVien> findByTrangThai(int trangThai);
}

