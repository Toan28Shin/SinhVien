package org.example.quanlysinhvien.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "thong_bao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThongBao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma_thong_bao", nullable = false, unique = true, length = 20)
    private String maThongBao;

    @Column(name = "tieu_de", nullable = false, length = 200)
    private String tieuDe;

    @Column(name = "noi_dung", columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "ngay_gui")
    private LocalDateTime ngayGui = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "sinh_vien_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_thong_bao_sinh_vien"))
    private SinhVien sinhVien;

    @Column(name = "trang_thai", nullable = false)
    private int trangThai = 1; // 1: Active, 0: Inactive

}
