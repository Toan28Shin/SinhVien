package org.example.quanlysinhvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuanLyDiemController {
    @GetMapping("/quan-ly-diem")
    public String quanlysinhvien() {
        return "View/quanlydiem";
    }
}
