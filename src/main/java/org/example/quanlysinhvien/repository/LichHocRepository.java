package org.example.quanlysinhvien.repository;

import org.example.quanlysinhvien.entity.LichHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface LichHocRepository extends JpaRepository<LichHoc, Long> {
    // Lấy lịch học của sinh viên theo ID sinh viên


    @Query("SELECT lh FROM LichHoc lh " +
           "JOIN lh.monHoc mh " +
           "JOIN DangKy dk ON dk.monHoc = mh " +
           "WHERE dk.sinhVien.id = :sinhVienId")
    List<LichHoc> findBySinhVienId(Long sinhVienId);

    @Query("SELECT lh FROM LichHoc lh " +
           "JOIN lh.monHoc mh " +
           "JOIN DangKy dk ON dk.monHoc = mh " +
           "WHERE dk.sinhVien.id = :sinhVienId " +
           "AND lh.ngayHoc BETWEEN :startDate AND :endDate")
    List<LichHoc> findBySinhVienIdAndNgayHocBetween(
            @Param("sinhVienId") Long sinhVienId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
