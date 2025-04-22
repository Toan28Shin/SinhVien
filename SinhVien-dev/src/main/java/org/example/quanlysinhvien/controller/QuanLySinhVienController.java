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

import java.time.format.DateTimeFormatter;
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


    @GetMapping("/sua-sinh-vien/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model){
        SinhVien sinhVien = sinhVienService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên với id: " + id));
        model.addAttribute("ngaySinhFormatted", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(sinhVien.getNgaySinh()));
        model.addAttribute("ngayNhapHocFormatted", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(sinhVien.getNgayNhapHoc()));
        model.addAttribute("sinhVien", sinhVien);
        return "view/suaSinhVien"; // Nhớ đúng tên file JSP/HTML
    }
    @PostMapping("/sua-sinh-vien")
    public String update(SinhVien sinhVien){
        sinhVienService.suaSinhVien(sinhVien);
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
