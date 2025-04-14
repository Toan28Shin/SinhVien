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

        // Tính điểm và điểm chữ cho từng môn học
        for (DangKy dangKy : ketQuaList) {
            // Lấy điểm từ bảng Kết Quả
            List<KetQua> ketQuas = ketQuaService.findBySinhVienAndMonHoc(dangKy.getSinhVien(), dangKy.getMonHoc());
            if (!ketQuas.isEmpty()) {
                KetQua ketQua = ketQuas.get(0); // lấy cái đầu tiên nếu có nhiều
                dangKy.setDiemQuaTrinh(ketQua.getDiemQuaTrinh());
                dangKy.setDiemThi(ketQua.getDiemThi());
            }

            // Tính điểm trung bình
            double diemTB = (dangKy.getDiemQuaTrinh() * 0.4) + (dangKy.getDiemThi() * 0.6);
            dangKy.setDiemTB(diemTB); // Set điểm TB

            // Tính điểm chữ
            String diemChu = dangKyService.tinhDiemChu(diemTB);
            dangKy.setDiemChu(diemChu);
        }

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
            // Tính điểm trung bình
            double diemTB = (dangKy.getDiemQuaTrinh() * 0.4) + (dangKy.getDiemThi() * 0.6);
            dangKy.setDiemTB(diemTB); // Set điểm TB

            // Tính điểm TB thang 4
            double diemTBThang4 = (diemTB / 10) * 4;
            dangKy.setDiemTBThang4(diemTBThang4);

            // Tính điểm chữ
            String diemChu = dangKyService.tinhDiemChu(diemTB);
            dangKy.setDiemChu(diemChu);
        }

        double[] gpa = dangKyService.tinhGPA(dangKys);
        model.addAttribute("gpa10", String.format("%.2f", gpa[0]));
        model.addAttribute("gpa4", String.format("%.2f", gpa[1]));

        model.addAttribute("ketQuaList", dangKys);
        return "view/xemdiem";
    }

}
