package org.example.quanlysinhvien.repository;

import org.example.quanlysinhvien.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SinhVienRepository extends JpaRepository<SinhVien, Long> {

    SinhVien findByEmail(String email);
}
