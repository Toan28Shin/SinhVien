package org.example.quanlysinhvien.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mon_hoc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_mon_hoc", unique = true, nullable = false)
    private String maMonHoc;

    @Column(name = "ten_mon", nullable = false)
    private String tenMon;

    @Column(name = "so_tin_chi")
    private Integer soTinChi;

    @ManyToOne
    @JoinColumn(name = "giang_vien_id")
    private GiangVien giangVien;
}
