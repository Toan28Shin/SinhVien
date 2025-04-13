package org.example.quanlysinhvien.repository;

import org.example.quanlysinhvien.entity.DangKy;
import org.example.quanlysinhvien.entity.KetQua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DangkyRepository extends JpaRepository<DangKy, Long> {

    // Phương thức tìm kết quả học tập của sinh viên theo ID

    List<DangKy> findBySinhVienId(Long sinhVienId);

    @Query("""
    SELECT dk FROM DangKy dk
    LEFT JOIN FETCH dk.monHoc m
    LEFT JOIN FETCH dk.sinhVien sv
    LEFT JOIN FETCH KetQua kq ON kq.sinhVien.id = sv.id AND kq.monHoc.id = m.id
    WHERE sv.id = :sinhVienId
    AND (:hocKy IS NULL OR dk.hocKy = :hocKy)
    """)
    List<DangKy> findDiemTheoKy(@Param("sinhVienId") Long sinhVienId, @Param("hocKy") Integer hocKy);

}
