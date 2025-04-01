package org.example.quanlysinhvien.controller;

import jakarta.servlet.http.HttpSession;
import org.example.quanlysinhvien.entity.TaiKhoan;
import org.example.quanlysinhvien.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class TaiKhoanController {
    @Autowired
    private TaiKhoanService userService;


    @GetMapping("/login")
    public String loginPage() {
        return "View/login";
    }

    @GetMapping("/home-giang-vien")
    public String homeGiangVien() {
        return "View/home_giangvien";
    }

    @GetMapping("/home-sinh-vien")
    public String honeSinhVien() {
        return "View/home_sinhvien";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        Optional<TaiKhoan> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            TaiKhoan user = userOpt.get();
            if (password.equals(user.getMat_khau())) { // So sánh trực tiếp mật khẩu
                session.setAttribute("loggedInUser", user);
                model.addAttribute("email", email);
                if (user.getQuyen() != null && "GiangVien".equals(user.getQuyen().getTen_quyen())) {
                    return "view/home_giangvien";
                } else {
                    return "view/home_sinhvien";
                }

            }
        }
        model.addAttribute("error", "Sai email hoặc mật khẩu!");
        return "view/login";
    }

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        TaiKhoan user = (TaiKhoan) session.getAttribute("loggedInUser");

        if (user == null) {
            System.out.println("User is null in session!");
            return "view/home";
        }

        System.out.println("Session Role: " + user.getQuyen());
        model.addAttribute("email", user.getEmail());

        if (user.getQuyen() != null && "GiangVien".equals(user.getQuyen().getTen_quyen())) {
            return "view/home_giangvien";
        } else {
            return "view/home_sinhvien";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa toàn bộ session
        return "redirect:/home";
    }

}
