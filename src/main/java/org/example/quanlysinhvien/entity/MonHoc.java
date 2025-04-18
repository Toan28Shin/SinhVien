package org.example.quanlysinhvien.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mon_hoc")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonHoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã môn học không được để trống")
    @Size(min = 2, max = 20, message = "Mã môn học phải có từ 2 đến 20 ký tự")
    @Column(name = "ma_mon_hoc", nullable = false, unique = true)
    private String maMonHoc;

    @NotBlank(message = "Tên môn học không được để trống")
    @Size(min = 2, max = 100, message = "Tên môn học phải có từ 2 đến 100 ký tự")
    @Column(name = "ten_mon", nullable = false)
    private String tenMon;

    @NotBlank(message = "Số tín chỉ không được để trống")
    @Column(name = "so_tin_chi", nullable = false)
    private String soTinChi;

    @Column(name = "trang_thai", nullable = false)
    private int trangThai; // 1: Hoạt động, 0: Ngừng hoạt động
}

