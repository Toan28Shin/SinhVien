package org.example.quanlysinhvien.service;

import org.example.quanlysinhvien.entity.TaiKhoan;
import org.example.quanlysinhvien.repository.TaiKhoanRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaiKhoanService {
    private final TaiKhoanRepository userRepo;


    public TaiKhoanService(TaiKhoanRepository userRepo) {
        this.userRepo = userRepo;
    }
    public Optional<TaiKhoan> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public TaiKhoan save(TaiKhoan user) {
        return userRepo.save(user); // Lưu user vào database mà không mã hóa mật khẩu
    }
}
