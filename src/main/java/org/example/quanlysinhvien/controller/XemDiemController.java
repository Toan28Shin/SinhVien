package org.example.quanlysinhvien.controller;

import org.example.quanlysinhvien.entity.DangKy;
import org.example.quanlysinhvien.entity.KetQua;
import org.example.quanlysinhvien.service.DangKyService;
import org.example.quanlysinhvien.service.KetQuaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class XemDiemController {

    private final DangKyService dangKyService;
    private final KetQuaService ketQuaService;

    public XemDiemController(DangKyService dangKyService, KetQuaService ketQuaService) {
        this.dangKyService = dangKyService;
        this.ketQuaService = ketQuaService;
    }


    @GetMapping("/xem-diem-theo-ky")
    public String XemDiemTheoKy(@SessionAttribute("sinhVienId") Long sinhVienId,
                                @RequestParam(value = "hocKy", required = false) Integer hocKy,
                                Model model) {


        // Gán mặc định học kỳ 1 nếu chưa chọn
        if (hocKy == null) {
            hocKy = 1;
        }


        // Lấy danh sách DangKy đã tính điểm
        List<DangKy> ketQuaList = dangKyService.findDiemTheoKy(sinhVienId, hocKy);

        // Thêm dữ liệu vào model
        model.addAttribute("ketQuaList", ketQuaList);
        model.addAttribute("selectedHocKy", hocKy);
        model.addAttribute("dsHocKy", List.of(1, 2, 3, 4, 5, 6)); // Danh sách học kỳ
        return "view/xemdiemtheoky"; // Trả về trang Thymeleaf
    }

    @GetMapping("/xem-diem")
    public String xemDiem(@SessionAttribute("sinhVienId") Long sinhVienId, Model model) {
        List<DangKy> dangKys = dangKyService.findBySinhVienId(sinhVienId);

        for (DangKy dangKy : dangKys) {
            // Lấy điểm từ bảng Kết Quả
            List<KetQua> ketQuas = ketQuaService.findBySinhVienAndMonHoc(dangKy.getSinhVien(), dangKy.getMonHoc());
            if (!ketQuas.isEmpty()) {
                KetQua ketQua = ketQuas.get(0); // lấy cái đầu tiên nếu có nhiều (tùy DB)
                dangKy.setDiemQuaTrinh(ketQua.getDiemQuaTrinh());
                dangKy.setDiemThi(ketQua.getDiemThi());
            }
        }

        model.addAttribute("ketQuaList", dangKys);
        return "view/xemdiem"; // trỏ đến file HTML
    }

}
