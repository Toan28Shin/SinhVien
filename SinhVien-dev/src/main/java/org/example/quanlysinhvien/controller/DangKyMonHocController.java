package org.example.quanlysinhvien.controller;

import org.example.quanlysinhvien.entity.LichHoc;
import org.example.quanlysinhvien.repository.LichHocRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DangKyMonHocController {
    @Autowired
    private LichHocRepo lichHocRepository;

    @GetMapping("/dang-ky-mon-hoc")
    public String hienThiDanhSachMonHoc(Model model) {
        List<LichHoc> danhSachLichHoc = lichHocRepository.findAll();
        model.addAttribute("danhSachLichHoc", danhSachLichHoc);
        return "view/dangkymonhoc";
    }
}
