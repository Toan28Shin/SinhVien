package org.example.quanlysinhvien.repository;

import org.example.quanlysinhvien.entity.KetQua;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KetQuaRepository extends JpaRepository<KetQua, Long> {
    // Phương thức tìm kết quả học tập của sinh viên theo ID
    List<KetQua> findBySinhVienId(Long sinhVienId);
}
