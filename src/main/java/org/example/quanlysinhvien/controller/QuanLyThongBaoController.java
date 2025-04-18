package org.example.quanlysinhvien.controller;

import org.example.quanlysinhvien.entity.SinhVien;
import org.example.quanlysinhvien.entity.ThongBao;
import org.example.quanlysinhvien.repository.SinhVienRepository;
import org.example.quanlysinhvien.repository.ThongBaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class QuanLyThongBaoController {
    @Autowired
    private ThongBaoRepository thongBaoRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @GetMapping("/quan-ly-thong-bao")
    public String hienThiFormGuiThongBao(Model model) {
        List<SinhVien> sinhViens = sinhVienRepository.findAll();
        model.addAttribute("sinhViens", sinhViens);
        return "View/quanlythongbao";
    }

    @PostMapping("/gui-thong-bao")
    public String guiThongBao(@ModelAttribute ThongBao thongBao,
                              @RequestParam Long sinhVienId) {
        SinhVien sinhVien = sinhVienRepository.findById(sinhVienId).orElse(null);
        if (sinhVien != null) {
            thongBao.setSinhVien(sinhVien);
            thongBao.setNgayGui(LocalDateTime.now());
            thongBao.setTrangThai(1);
            thongBaoRepository.save(thongBao);
        }
        return "redirect:/quan-ly-thong-bao";
    }

}

