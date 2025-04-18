package org.example.quanlysinhvien.repository;

import org.example.quanlysinhvien.entity.SinhVien;
import org.example.quanlysinhvien.entity.ThongBao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThongBaoRepository extends JpaRepository<ThongBao, Integer> {
}
