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
    public String themSinhVien(@Valid @ModelAttribute("sinhVien") SinhVien sinhVien, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Thêm thông báo thất bại nếu có lỗi validate
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
        model.addAttribute("sinhVien", sinhVienService.getSinhVien(maSinhVien));
        return "view/suaSinhvien";
    }

    @PostMapping("/form-sua-sinh-vien/{maSinhVien}")
    public String suaSinhVien(@Valid @ModelAttribute("sinhVien") SinhVien sinhVien, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sinhVien", sinhVien);
            return "view/suaSinhVien";
        }
        sinhVienService.suaSinhVien(sinhVien);
        return "redirect:quan-ly-sinh-vien";
    }
    @GetMapping("/chi-tiet-sinh-vien/{maSinhVien}")
    public String chiTietSuaSinhVien(@PathVariable("maSinhVien") String maSinhVien, Model model) {
        model.addAttribute("sinhVien", sinhVienService.getSinhVien(maSinhVien));
        model.addAttribute("ketQua", ketQuaSetvice.getAllKetQua());
        return "view/chitietsinhvien";
    }
}
