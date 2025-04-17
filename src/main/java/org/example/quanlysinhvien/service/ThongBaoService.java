package org.example.quanlysinhvien.service;

import org.example.quanlysinhvien.entity.SinhVien;
import org.example.quanlysinhvien.entity.ThongBao;
import org.example.quanlysinhvien.repository.SinhVienRepository;
import org.example.quanlysinhvien.repository.ThongBaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ThongBaoService {
    private final ThongBaoRepository thongBaoRepository;
    private final SinhVienRepository sinhVienRepository;

    public ThongBaoService(ThongBaoRepository thongBaoRepository,
                           SinhVienRepository sinhVienRepository) {
        this.thongBaoRepository = thongBaoRepository;
        this.sinhVienRepository = sinhVienRepository;
    }

    public void guiThongBaoChoSinhVien(ThongBao thongBao, Long sinhVienId) {
        SinhVien sinhVien = sinhVienRepository.findById(sinhVienId).orElse(null);
        if (sinhVien != null) {
            thongBao.setSinhVien(sinhVien);
            thongBao.setNgayGui(LocalDateTime.now());
            thongBao.setTrangThai(1);
            thongBaoRepository.save(thongBao);
        }
    }
}
