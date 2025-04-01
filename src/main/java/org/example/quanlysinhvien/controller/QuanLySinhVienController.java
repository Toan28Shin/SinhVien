package org.example.quanlysinhvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class QuanLySinhVienController {
    @GetMapping("/quan-ly-sinh-vien")
    public String quanlysinhvien() {
        return "View/quanlysinhvien";
    }
}
