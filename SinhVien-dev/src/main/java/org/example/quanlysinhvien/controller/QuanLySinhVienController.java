package org.example.quanlysinhvien.controller;

import jakarta.validation.Valid;
import org.example.quanlysinhvien.entity.KetQua;
import org.example.quanlysinhvien.entity.SinhVien;
import org.example.quanlysinhvien.service.KetQuaSetvice;
import org.example.quanlysinhvien.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class QuanLySinhVienController {

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private KetQuaSetvice ketQuaSetvice;

    @GetMapping("/quan-ly-sinh-vien")
    public String quanlysinhvien(Model model) {
        List<SinhVien> sinhViens = sinhVienService.getAllSinhVienDangHoatDong();
        model.addAttribute("sinhViens", sinhViens);
        return "view/quanlysinhvien";
    }

    @GetMapping("/formThem")
    public String formThem(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        return "view/themsinhvien";
    }

    @PostMapping("/them-sinh-vien")
    public String themSinhVien(@Valid @ModelAttribute("sinhVien") SinhVien sinhVien,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Thêm sinh viên thất bại. Vui lòng kiểm tra lại thông tin.");
            return "redirect:/formThem";
        }

        try {
            sinhVienService.themSinhVien(sinhVien);
            redirectAttributes.addFlashAttribute("message", "Thêm sinh viên thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Thêm sinh viên thất bại. Vui lòng thử lại.");
        }

        return "redirect:/quan-ly-sinh-vien";
    }

    @GetMapping("/form-sua-sinh-vien/{maSinhVien}")
    public String formSuaSinhVien(@PathVariable("maSinhVien") String maSinhVien, Model model) {
        SinhVien sv = sinhVienService.getSinhVien(maSinhVien);
        model.addAttribute("sinhVien", sv);
        return "view/suaSinhvien";
    }

    @PostMapping("/sua-sinh-vien")
    public String suaSinhVien(@Valid @ModelAttribute("sinhVien") SinhVien sinhVien,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "view/suaSinhvien";
        }

        try {
            sinhVienService.suaSinhVien(sinhVien);
            redirectAttributes.addFlashAttribute("message", "Cập nhật sinh viên thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cập nhật thất bại. Vui lòng thử lại.");
        }

        return "redirect:/quan-ly-sinh-vien";
    }

    @GetMapping("/chi-tiet-sinh-vien/{maSinhVien}")
    public String chiTietSuaSinhVien(@PathVariable("maSinhVien") String maSinhVien, Model model) {
        model.addAttribute("sinhVien", sinhVienService.getSinhVien(maSinhVien));
        model.addAttribute("ketQua", ketQuaSetvice.getAllKetQua());
        return "view/chitietsinhvien";
    }
    @GetMapping("/xoa/{maSinhVien}")
    public String xoaSinhVien(@PathVariable String maSinhVien) {
        sinhVienService.xoaSinhVien(maSinhVien);
        return "redirect:/quan-ly-sinh-vien"; // Trang danh sách sinh viên sau khi xoá
    }
}
