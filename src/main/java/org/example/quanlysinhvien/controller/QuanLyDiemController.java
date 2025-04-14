package org.example.quanlysinhvien.controller;

import jakarta.validation.Valid;
import org.example.quanlysinhvien.entity.KetQua;
import org.example.quanlysinhvien.service.KetQuaSetvice;
import org.example.quanlysinhvien.service.MonHocService;
import org.example.quanlysinhvien.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuanLyDiemController {

    @Autowired
    private KetQuaSetvice ketQuaSetvice;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private MonHocService monHocService;

    @GetMapping("/quan-ly-diem")
    public String danhSachDiem(@RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String subject,
                               @RequestParam(required = false) String sapXep,
                               Model model) {

        List<KetQua> ketQuas = ketQuaSetvice.getAllKetQua();

        // Lọc theo keyword (mã sinh viên)
        if (keyword != null && !keyword.isEmpty()) {
            ketQuas = ketQuas.stream()
                    .filter(kq -> kq.getSinhVien().getMaSinhVien().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();
        }

        // Lọc theo môn học
        if (subject != null && !subject.isEmpty()) {
            ketQuas = ketQuas.stream()
                    .filter(kq -> kq.getMonHoc().getTenMon().equalsIgnoreCase(subject))
                    .toList();
        }
        // Sắp xếp theo học lực (giỏi -> yếu hoặc yếu -> giỏi)
        if (sapXep != null && !sapXep.isEmpty()) {
            if (sapXep.equals("asc")) {
                ketQuas = ketQuas.stream()
                        .sorted((k1, k2) -> k1.getTongDiem().compareTo(k2.getTongDiem()))
                        .toList();
            } else if (sapXep.equals("desc")) {
                ketQuas = ketQuas.stream()
                        .sorted((k1, k2) -> k2.getTongDiem().compareTo(k1.getTongDiem()))
                        .toList();
            }
        }
        model.addAttribute("ketQuas", ketQuas);
        model.addAttribute("keyword", keyword);
        model.addAttribute("subject", subject);
        model.addAttribute("sapXep", sapXep);
        return "view/quanlydiem";
    }

    @GetMapping("/form-sua/{id}")
    public String formSuaDiem(@PathVariable("id") Long id, Model model) {
        KetQua ketQua = ketQuaSetvice.getKetQuaById(id);
        model.addAttribute("ketQua", ketQua);
        model.addAttribute("sinhViens", sinhVienService.getAllSinhVien());
        model.addAttribute("monHocs", monHocService.getAllMonHoc());
        return "view/suadiem";
    }

    @PostMapping("/sua/{id}")
    public String suaDiem(@Valid @ModelAttribute("ketQua") KetQua ketQua,
                          BindingResult bindingResult,
                          @PathVariable Long id,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sinhViens", sinhVienService.getAllSinhVien());
            model.addAttribute("monHocs", monHocService.getAllMonHoc());
            return "view/suadiem";
        }

        KetQua ketQuaCu = ketQuaSetvice.getKetQuaById(id);
        ketQua.setId(id);
        ketQua.setSinhVien(ketQuaCu.getSinhVien());
        ketQua.setMonHoc(ketQuaCu.getMonHoc());

        ketQuaSetvice.suaKetQua(ketQua);
        return "redirect:/quan-ly-diem";
    }


    @GetMapping("/chi-tiet/{id}")
    public String chiTietDiem(@PathVariable("id") Long id, Model model) {
        KetQua ketQua = ketQuaSetvice.getKetQuaById(id);
        model.addAttribute("ketQua", ketQua);
        return "view/chitietdiem";
    }

}


