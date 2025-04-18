package org.example.quanlysinhvien.service;

import org.example.quanlysinhvien.entity.LichHoc;
import org.example.quanlysinhvien.entity.TaiKhoan;
import org.example.quanlysinhvien.repository.LichHocRepository;
import org.example.quanlysinhvien.repository.TaiKhoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaiKhoanService {
    private final TaiKhoanRepository userRepo;
    private final LichHocRepository lichHocRepository;


    public TaiKhoanService(TaiKhoanRepository userRepo, LichHocRepository lichHocRepository) {
        this.userRepo = userRepo;
        this.lichHocRepository = lichHocRepository;
    }
    public Optional<TaiKhoan> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public TaiKhoan save(TaiKhoan user) {
        return userRepo.save(user); // Lưu user vào database mà không mã hóa mật khẩu
    }

}
