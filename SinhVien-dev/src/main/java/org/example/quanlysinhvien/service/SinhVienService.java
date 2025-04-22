package org.example.quanlysinhvien.service;

import jakarta.validation.Valid;
import org.example.quanlysinhvien.entity.Quyen;
import org.example.quanlysinhvien.entity.SinhVien;
import org.example.quanlysinhvien.entity.TaiKhoan;
import org.example.quanlysinhvien.repository.QuyenRepository;
import org.example.quanlysinhvien.repository.SinhVienRepository;
import org.example.quanlysinhvien.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private QuyenRepository quyenRepository;


    private String generateMaTaiKhoan() {
        String prefix = "TK";
        int nextId = (int) (taiKhoanRepository.count() + 1); // Đếm số tài khoản hiện tại trong hệ thống
        String maTaiKhoan = String.format("%s%03d", prefix, nextId); // Tạo mã TK001, TK002, TK003,...

        // Kiểm tra xem mã tài khoản đã tồn tại chưa
        while (taiKhoanRepository.existsByMaTaiKhoan(maTaiKhoan)) {
            nextId++;
            maTaiKhoan = String.format("%s%03d", prefix, nextId); // Tạo mã tài khoản mới nếu đã tồn tại
        }

        return maTaiKhoan;
    }

    public SinhVien themSinhVien(@Valid SinhVien sinhVien) {
        // Kiểm tra xem sinh viên có tài khoản chưa
        if (sinhVien.getTaiKhoan() == null) {
            // Tạo tài khoản mới cho sinh viên từ họ tên và email
            String tenTaiKhoan = sinhVien.getHoTen().replace(" ", "").toLowerCase(); // Hoặc sử dụng một phần của tên
            String email = sinhVien.getEmail();  // Email của sinh viên sẽ là duy nhất

            // Tạo tài khoản mới cho sinh viên
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setTenTaiKhoan(tenTaiKhoan);  // Tên tài khoản có thể trùng
            taiKhoan.setMatKhau("pass123"); // Mật khẩu mặc định
            taiKhoan.setEmail(email); // Email là duy nhất

            // Tạo mã tài khoản tự động
            String maTaiKhoan = generateMaTaiKhoan();
            taiKhoan.setMaTaiKhoan(maTaiKhoan); // Gán mã tài khoản tự động

            // Lưu tài khoản vào cơ sở dữ liệu
            taiKhoanRepository.save(taiKhoan);

            Quyen quyenSinhVien = quyenRepository.findById(2L).orElse(null);
            taiKhoan.setQuyen(quyenSinhVien);
            // Gán tài khoản mới cho sinh viên
            sinhVien.setTaiKhoan(taiKhoan);
            sinhVien.setTrangThai(1);


        }

        // Lưu sinh viên vào cơ sở dữ liệu
        return sinhVienRepository.save(sinhVien);
    }

    public List<SinhVien> getAllSinhVienDangHoatDong() {
        return sinhVienRepository.findByTrangThai(1);
    }

    public void suaSinhVien(@Valid SinhVien sinhVien) {
        if (sinhVien.getMaSinhVien() == null || sinhVien.getMaSinhVien().isEmpty()) {
            throw new IllegalArgumentException("Mã sinh viên không hợp lệ");
        }

        SinhVien existing = sinhVienRepository.findByMaSinhVien(sinhVien.getMaSinhVien());
        if (existing == null) {
            throw new IllegalArgumentException("Không tìm thấy sinh viên với mã: " + sinhVien.getMaSinhVien());

        }
        existing.setHoTen(sinhVien.getHoTen());
        existing.setNgaySinh(sinhVien.getNgaySinh());
        existing.setGioiTinh(sinhVien.getGioiTinh());
        existing.setDiaChi(sinhVien.getDiaChi());
        existing.setEmail(sinhVien.getEmail());
        existing.setSoDienThoai(sinhVien.getSoDienThoai());
        existing.setTrangThai(sinhVien.getTrangThai());
        existing.setNgayNhapHoc(sinhVien.getNgayNhapHoc());

        sinhVienRepository.save(existing);
    }

    public SinhVien getSinhVien(String maSinhVien) {
        return sinhVienRepository.findByMaSinhVien(maSinhVien);
    }


    public void xoaSinhVien(String maSinhVien) {
        SinhVien sv = sinhVienRepository.findByMaSinhVien(maSinhVien);
        if (sv != null) {
            sv.setTrangThai(0); // Xoá mềm: chuyển trạng thái sang 0
            sinhVienRepository.save(sv);
        } else {
            throw new IllegalArgumentException("Không tìm thấy sinh viên với mã: " + maSinhVien);
        }
    }
    public Optional<SinhVien> findById(Long id) {
        return sinhVienRepository.findById(id);
    }



}
