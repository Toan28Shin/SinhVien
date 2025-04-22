package org.example.quanlysinhvien.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sinh_vien")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SinhVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(min = 4, max = 20, message = "Mã sinh viên phải có từ 4 đến 20 ký tự")
    private String maSinhVien;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 3, max = 50, message = "Họ tên phải có từ 3 đến 50 ký tự")
    private String hoTen;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate ngaySinh;

    @NotBlank(message = "Giới tính không được để trống")
    private String gioiTinh;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "0\\d{9,10}", message = "Số điện thoại phải bắt đầu bằng 0 và có từ 10 đến 11 chữ số")
    private String soDienThoai;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(min = 5, max = 255, message = "Địa chỉ phải có từ 5 đến 255 ký tự")
    private String diaChi;

    @NotNull(message = "Ngày nhập học không được để trống")
    private LocalDate ngayNhapHoc;

    private int trangThai; // 1: Hoạt động, 0: Ngừng hoạt động

    @OneToOne
    @JoinColumn(name = "tai_khoan_id", unique = true)
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "sinhVien")
    private List<KetQua> ketQua;

}
