package org.example.quanlysinhvien.repository;

import org.example.quanlysinhvien.entity.KetQua;
import org.example.quanlysinhvien.entity.MonHoc;
import org.example.quanlysinhvien.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KetQuaRepository extends JpaRepository<KetQua, Long> {
    // Phương thức tìm kết quả học tập của sinh viên theo ID
    List<KetQua> findBySinhVienId(Long sinhVienId);


    // Tìm kết quả (điểm quá trình và điểm thi) của sinh viên theo môn học
    List<KetQua> findBySinhVienAndMonHoc(SinhVien sinhVien, MonHoc monHoc);
}
