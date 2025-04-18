package org.example.quanlysinhvien.service;

import org.example.quanlysinhvien.entity.MonHoc;
import org.example.quanlysinhvien.repository.MonHocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonHocService {
    @Autowired
    private MonHocRepository monHocRepository;

    // Lấy tất cả môn học
    public List<MonHoc> getAllMonHoc() {
        return monHocRepository.findAll();
    }

    // Lấy môn học theo mã môn
    public MonHoc getMonHocByMa(String maMonHoc) {
        return monHocRepository.findByMaMonHoc(maMonHoc);
    }

    // Lấy môn học theo ID
    public MonHoc getMonHocById(Integer id) {
        return monHocRepository.findById(id).orElse(null);
    }

    // Thêm hoặc cập nhật môn học
    public MonHoc saveMonHoc(MonHoc monHoc) {
        return monHocRepository.save(monHoc);
    }

    // Xóa môn học
    public void deleteMonHoc(Integer id) {
        monHocRepository.deleteById(id);
    }
}
