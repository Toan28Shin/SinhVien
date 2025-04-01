package org.example.quanlysinhvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class XemThongBaoController {
    @GetMapping("/xem-thong-bao")
    public String quanlysinhvien() {
        return "View/xemthongbao";
    }
}
