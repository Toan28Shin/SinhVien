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

    public List<SinhVien> getAllSinhVien() {
        return sinhVienRepository.findAll();
    }


    public SinhVien getSinhVien(Long id) {
        return sinhVienRepository.findById(id).get();
    }
}
