package org.example.quanlysinhvien.controller;

import jakarta.validation.Valid;
import org.example.quanlysinhvien.entity.SinhVien;
import org.example.quanlysinhvien.repository.SinhVienRepository;
import org.example.quanlysinhvien.service.KetQuaService;
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
    private KetQuaService ketQuaSetvice;
    @Autowired
    private SinhVienRepository sinhVienRepository;

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
    public String themSinhVien(@Valid @ModelAttribute("sinhVien") SinhVien sinhVien,
                               BindingResult bindingResult,
                               Model model) {

        // Lỗi validate từ @Valid (trống, sai định dạng, v.v)
        if (bindingResult.hasErrors()) {
            return "view/themsinhvien";
        }

        // Kiểm tra trùng mã sinh viên
        if (sinhVienRepository.existsByMaSinhVien(sinhVien.getMaSinhVien())) {
            model.addAttribute("errorMessageMa", "Mã sinh viên đã tồn tại!");
            return "view/themsinhvien";
        }

        // Kiểm tra trùng email
        if (sinhVienRepository.existsByEmail(sinhVien.getEmail())) {
            model.addAttribute("errorMessageEmail", "Email đã tồn tại!");
            return "view/themsinhvien";
        }

        // Nếu không có lỗi, tiến hành thêm
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

    @GetMapping("/xoa/{maSinhVien}")
    public String xoaSinhVien(@PathVariable String maSinhVien) {
        sinhVienService.xoaSinhVien(maSinhVien);
        return "redirect:/quan-ly-sinh-vien"; // Trang danh sách sinh viên sau khi xoá
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

}
