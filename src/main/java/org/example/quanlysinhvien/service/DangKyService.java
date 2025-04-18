package org.example.quanlysinhvien.service;

import org.example.quanlysinhvien.entity.DangKy;
import org.example.quanlysinhvien.entity.KetQua;
import org.example.quanlysinhvien.repository.DangkyRepository;
import org.example.quanlysinhvien.repository.KetQuaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DangKyService {
    private final DangkyRepository dangkyRepository;
    private final KetQuaRepository ketQuaRepository;

    public DangKyService(DangkyRepository dangkyRepository, KetQuaRepository ketQuaRepository) {
        this.dangkyRepository = dangkyRepository;
        this.ketQuaRepository = ketQuaRepository;
    }

    // Phương thức tìm điểm theo học kỳ
    public List<DangKy> findDiemTheoKy(Long sinhVienId, Integer hocKy) {
        // Lấy danh sách DangKy của sinh viên theo học kỳ
        List<DangKy> dangKys = dangkyRepository.findDiemTheoKy(sinhVienId, hocKy);

        // Duyệt qua từng DangKy để tính toán điểm
        for (DangKy dangKy : dangKys) {
            // Lấy thông tin điểm quá trình và điểm thi từ bảng KetQua
            List<KetQua> ketQuas = ketQuaRepository.findBySinhVienAndMonHoc(dangKy.getSinhVien(), dangKy.getMonHoc());

            if (!ketQuas.isEmpty()) {
                KetQua ketQua = ketQuas.get(0); // Giả sử chỉ lấy một kết quả

                // Tính điểm trung bình (10)
                double diemTB = (ketQua.getDiemQuaTrinh() * 0.4) + (ketQua.getDiemThi() * 0.6);
                double diemTBThang4 = (diemTB / 10) * 4;

                // Cập nhật các giá trị vào DangKy (chỉ tính toán, không cần lưu vào Entity)
                dangKy.setDiemQuaTrinh(ketQua.getDiemQuaTrinh());
                dangKy.setDiemThi(ketQua.getDiemThi());
                dangKy.setDiemTB(diemTB);
                dangKy.setDiemTBThang4(diemTBThang4);

                dangKy.setDiemChu(tinhDiemChu(diemTB));

                // Tính trạng thái
                String trangThaiHoc = diemTB >= 5 ? "Đạt" : "Trượt";
                dangKy.setTrangThaiHoc(trangThaiHoc);
            }
        }
        return dangKys;
    }

    public List<DangKy> findBySinhVienId(Long sinhVienId) {
        return dangkyRepository.findBySinhVienId(sinhVienId);
    }

    public String tinhDiemChu(double diemTB) {
        if (diemTB >= 9) {
            return "A+";
        } else if (diemTB >= 8) {
            return "A";
        } else if (diemTB >= 7) {
            return "B+";
        } else if (diemTB >= 6) {
            return "B";
        } else if (diemTB >= 5) {
            return "C+";
        } else if (diemTB >= 4) {
            return "C";
        } else if (diemTB >= 3) {
            return "D+";
        } else if (diemTB >= 2) {
            return "D";
        } else {
            return "F";
        }
    }

    public double[] tinhGPA(List<DangKy> dangKys) {
        double tongDiem10 = 0;
        double tongDiem4 = 0;
        int tongTinChi = 0;

        for (DangKy dangKy : dangKys) {
            if (dangKy.getDiemTB() != null && dangKy.getDiemTBThang4() != null) {
                int tinChi = dangKy.getMonHoc().getSoTinChi();
                tongDiem10 += dangKy.getDiemTB() * tinChi;
                tongDiem4 += dangKy.getDiemTBThang4() * tinChi;
                tongTinChi += tinChi;
            }
        }

        if (tongTinChi == 0) return new double[]{0.0, 0.0};

        double gpa10 = tongDiem10 / tongTinChi;
        double gpa4 = tongDiem4 / tongTinChi;

        return new double[]{gpa10, gpa4};
    }


}


