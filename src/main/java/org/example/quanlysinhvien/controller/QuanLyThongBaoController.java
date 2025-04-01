package org.example.quanlysinhvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuanLyThongBaoController {
    @GetMapping("/quan-ly-thong-bao")
    public String quanlysinhvien() {
        return "View/quanlythongbao";
    }
}
