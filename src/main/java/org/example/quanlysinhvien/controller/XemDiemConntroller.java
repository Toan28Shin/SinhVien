package org.example.quanlysinhvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class XemDiemConntroller {
    @GetMapping("/xem-diem")
    public String quanlysinhvien() {
        return "View/xemdiem";
    }
}
