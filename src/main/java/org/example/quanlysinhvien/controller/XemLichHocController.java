package org.example.quanlysinhvien.controller;

import org.example.quanlysinhvien.entity.LichHoc;
import org.example.quanlysinhvien.service.LichHocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class XemLichHocController {

    private final LichHocService lichHocService;

    public XemLichHocController(LichHocService lichHocService) {
        this.lichHocService = lichHocService;
    }


    @GetMapping("/xem-lich-hoc")
    public String getLichHoc(
            @SessionAttribute("sinhVienId") Long sinhVienId,
            @RequestParam(name = "thoiGian", required = false, defaultValue = "7_toi") String thoiGian,
            Model model) {

        // Gọi service để lấy danh sách lịch học đã lọc
        List<LichHoc> lichHocList = lichHocService.getLichHocBySinhVienIdAndFilter(sinhVienId, thoiGian);

        // Thêm dữ liệu vào model để hiển thị trong view
        model.addAttribute("lichHocList", lichHocList);
        model.addAttribute("thoiGian", thoiGian);  // Thêm tham số thời gian vào model

        return "view/xemlichhoc";  // Trả về view "xemlichhoc"
    }





}
