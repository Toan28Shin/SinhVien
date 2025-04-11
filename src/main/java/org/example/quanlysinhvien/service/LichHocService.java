package org.example.quanlysinhvien.service;

import org.example.quanlysinhvien.entity.LichHoc;
import org.example.quanlysinhvien.repository.LichHocRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LichHocService {
    private final LichHocRepository lichHocRepository;

    public LichHocService(LichHocRepository lichHocRepository) {
        this.lichHocRepository = lichHocRepository;
    }
    public List<LichHoc> getLichHocBySinhVienId(Long sinhVienId) {
        return lichHocRepository.findBySinhVienId(sinhVienId);
    }

    // Lấy lịch học của sinh viên theo khoảng thời gian
    public List<LichHoc> getLichHocBySinhVienIdAndFilter(Long sinhVienId, String thoiGian) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = null;
        LocalDate endDate = null;

        // Xác định khoảng thời gian dựa trên tham số "thoiGian"
        switch (thoiGian) {
            case "7_toi":
                startDate = currentDate;
                endDate = currentDate.plusDays(7);
                break;
            case "14_toi":
                startDate = currentDate;
                endDate = currentDate.plusDays(14);
                break;
            case "30_toi":
                startDate = currentDate;
                endDate = currentDate.plusDays(30);
                break;
            case "7_truoc":
                startDate = currentDate.minusDays(7);
                endDate = currentDate;
                break;
            case "14_truoc":
                startDate = currentDate.minusDays(14);
                endDate = currentDate;
                break;
            case "30_truoc":
                startDate = currentDate.minusDays(30);
                endDate = currentDate;
                break;
            default:
                break;
        }


        // Lấy tất cả lịch học của sinh viên
        List<LichHoc> lichHocList = lichHocRepository.findBySinhVienId(sinhVienId);

        // Lọc lịch học theo khoảng thời gian đã xác định
        if (startDate != null && endDate != null) {
            LocalDate finalStartDate = startDate;
            LocalDate finalEndDate = endDate;
            lichHocList = lichHocList.stream()
                    .filter(lich -> lich.getNgayHoc() != null
                                    && !lich.getNgayHoc().isBefore(finalStartDate)
                                    && !lich.getNgayHoc().isAfter(finalEndDate))
                    .collect(Collectors.toList());
        } else if (endDate != null) {
            LocalDate finalEndDate1 = endDate;
            lichHocList = lichHocList.stream()
                    .filter(lich -> lich.getNgayHoc() != null
                                    && !lich.getNgayHoc().isBefore(currentDate)
                                    && !lich.getNgayHoc().isAfter(finalEndDate1))
                    .collect(Collectors.toList());
        } else if (startDate != null) {
            LocalDate finalStartDate1 = startDate;
            lichHocList = lichHocList.stream()
                    .filter(lich -> lich.getNgayHoc() != null
                                    && !lich.getNgayHoc().isBefore(finalStartDate1))
                    .collect(Collectors.toList());
        }

        // Sắp xếp lịch học từ ngày gần nhất đến ngày xa nhất
        lichHocList.sort((lich1, lich2) -> {
            if (lich1.getNgayHoc() != null && lich2.getNgayHoc() != null) {
                return lich1.getNgayHoc().compareTo(lich2.getNgayHoc());
            }
            return 0; // Nếu ngày học là null, giữ nguyên vị trí của chúng
        });

        return lichHocList;
    }
}
