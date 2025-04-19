package org.example.quanlysinhvien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class XemLichHocController {
    @GetMapping("/xen-lich-hoc")
    public String quanlysinhvien() {
        return "View/xemlichhoc";
    }
}
