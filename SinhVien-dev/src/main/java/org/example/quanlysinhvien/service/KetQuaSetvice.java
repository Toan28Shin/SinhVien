package org.example.quanlysinhvien.service;

import org.example.quanlysinhvien.entity.KetQua;
import org.example.quanlysinhvien.repository.KetQuaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KetQuaSetvice {
    @Autowired
    private KetQuaRepository ketQuaRepository;


    public List<KetQua> getAllKetQua() {
        return ketQuaRepository.findAll();
    }
}
