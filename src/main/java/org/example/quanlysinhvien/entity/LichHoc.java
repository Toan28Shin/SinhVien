package org.example.quanlysinhvien.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="lich_hoc")
public class LichHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maLichHoc;

    @ManyToOne
    private MonHoc monHoc;

    @ManyToOne
    private GiangVien giangVien;

    private LocalDate ngayHoc;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;
    private String phongHoc;
    private int trangThai;
}
