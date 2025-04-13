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
}


