package org.example.quanlysinhvien.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="dang_ky")
public class DangKy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String maDangKy;
    @OneToOne
    private SinhVien sinhVien;
    @OneToOne
    private MonHoc monHoc;
    private int hocKy;
    private int namHoc;
    private int trangThai;

}
