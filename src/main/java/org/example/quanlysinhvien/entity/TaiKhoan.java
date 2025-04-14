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
@Table(name="tai_khoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String maTaiKhoan;
    private String tenTaiKhoan;
    private String matKhau;
    private String email;
    @ManyToOne
    @JoinColumn(name="quyen_id")
    private Quyen quyen;
    @OneToOne(mappedBy = "taiKhoan")
    private SinhVien sinhVien;

}
