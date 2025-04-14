package org.example.quanlysinhvien.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.Transient;

@Entity
@Table(name = "ket_qua")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KetQua {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Sinh viên không được để trống")
    @ManyToOne
    @JoinColumn(name = "sinh_vien_id", nullable = false)
    private SinhVien sinhVien;

    @NotNull(message = "Môn học không được để trống")
    @ManyToOne
    @JoinColumn(name = "mon_hoc_id", nullable = false)
    private MonHoc monHoc;

    @NotNull(message = "Điểm quá trình không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Điểm quá trình không được nhỏ hơn 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Điểm quá trình không được lớn hơn 10")
    private Double diemQuaTrinh;

    @NotNull(message = "Điểm thi không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Điểm thi không được nhỏ hơn 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Điểm thi không được lớn hơn 10")
    private Double diemThi;

    @Transient
    public Double getTongDiem() {
        double tong = (diemQuaTrinh * 0.4) + (diemThi * 0.6);
        return Math.round(tong * 10) / 10.0; // làm tròn đến 1 chữ số sau dấu phẩy
    }

    @Transient
    public String getXepLoai() {
        double tong = getTongDiem();
        if (tong >= 8.0) {
            return "Giỏi";
        } else if (tong >= 7.0) {
            return "Khá";
        } else if (tong >= 5.0) {
            return "Trung bình";
        } else {
            return "Yếu";
        }
    }

    @NotNull(message = "Trạng thái không được để trống")
    @Min(value = 0, message = "Giá trị trạng thái không hợp lệ")
    @Max(value = 1, message = "Giá trị trạng thái không hợp lệ")
    private Integer trangThai;
}

