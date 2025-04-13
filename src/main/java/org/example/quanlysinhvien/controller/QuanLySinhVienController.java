package org.example.quanlysinhvien.controller;

import jakarta.validation.Valid;
import org.example.quanlysinhvien.entity.SinhVien;
import org.example.quanlysinhvien.service.KetQuaService;
import org.example.quanlysinhvien.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuanLySinhVienController {

    @Autowired
    private SinhVienService sinhVienService;
    @Autowired
    private KetQuaService ketQuaSetvice;

    @GetMapping("/quan-ly-sinh-vien")
    public String quanlysinhvien(Model model) {
        List<SinhVien> sinhViens = sinhVienService.getAllSinhVien();
        model.addAttribute("sinhViens", sinhViens);
        return "view/quanlysinhvien";
    }

    @GetMapping("/formThem")
    public String formThem(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        return "view/themsinhvien";
    }

    @PostMapping("/them-sinh-vien")
    public String themSinhVien(@Valid @ModelAttribute("sinhVien") SinhVien sinhVien, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "view/themsinhvien";
        }
        sinhVienService.themSinhVien(sinhVien);
        return "redirect:/quan-ly-sinh-vien";
    }

    @GetMapping("/chi-tiet-sinh-vien/{id}")
    public String chiTietSuaSinhVien(@PathVariable("id") Long id, Model model) {
        SinhVien sinhVien = sinhVienService.getSinhVien(id);
        if (sinhVien.getTaiKhoan() == null) {
            // Nếu sinh viên không có tài khoản, chuyển hướng về trang quản lý sinh viên và thông báo lỗi
            model.addAttribute("error", "Sinh viên này chưa có tài khoản.");
            return "redirect:/quan-ly-sinh-vien";
        }
        model.addAttribute("sinhVien", sinhVienService.getSinhVien(id));
        model.addAttribute("ketQua", ketQuaSetvice.findBySinhVienId(id));
        return "view/chitietsinhvien";
    }
}
