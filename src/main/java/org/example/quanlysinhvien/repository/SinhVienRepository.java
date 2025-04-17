package org.example.quanlysinhvien.repository;

import org.example.quanlysinhvien.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SinhVienRepository extends JpaRepository<SinhVien, Long> {
    SinhVien findByMaSinhVien(String maSinhVien);

    SinhVien findByEmail(String email);

//    List<SinhVien> findByMaSinhVienThongBao(String keyword);
}

