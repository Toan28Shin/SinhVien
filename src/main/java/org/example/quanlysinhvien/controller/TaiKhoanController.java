package org.example.quanlysinhvien.controller;

import jakarta.servlet.http.HttpSession;
import org.example.quanlysinhvien.entity.LichHoc;
import org.example.quanlysinhvien.entity.TaiKhoan;
import org.example.quanlysinhvien.service.TaiKhoanService;
import org.example.quanlysinhvien.service.LichHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class TaiKhoanController {

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private LichHocService lichHocService; // Inject service để lấy lịch học

    // Trang đăng nhập
    @GetMapping("/login")
    public String loginPage() {
        return "View/login"; // Trả về trang đăng nhập
    }

    // Xử lý đăng nhập
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        // Tìm kiếm tài khoản từ cơ sở dữ liệu theo email
        Optional<TaiKhoan> userOpt = taiKhoanService.findByEmail(email);

        if (userOpt.isPresent()) {
            TaiKhoan user = userOpt.get();

            // Kiểm tra mật khẩu
            if (!user.getMatKhau().equals(password)) {
                model.addAttribute("error", "Mật khẩu không chính xác!");
                return "View/login"; // Nếu mật khẩu không đúng
            }

            // Lưu thông tin người dùng vào session
            session.setAttribute("loggedInUser", user);

            // Kiểm tra quyền của người dùng và chuyển hướng đến trang phù hợp
            if (user.getQuyen() != null && "GiangVien".equals(user.getQuyen().getTenQuyen())) {
                return "View/home_giangvien"; // Giảng viên
            } else if (user.getQuyen() != null && "SinhVien".equals(user.getQuyen().getTenQuyen())) {
                // Nếu là sinh viên, lấy lịch học
                if (user.getSinhVien() == null) {
                    model.addAttribute("error", "Tài khoản chưa liên kết với sinh viên.");
                    return "View/login";
                }

                // ✅ Lưu id sinh viên vào session (QUAN TRỌNG)
                session.setAttribute("sinhVienId", user.getSinhVien().getId());
                List<LichHoc> lichHocList = lichHocService.getLichHocBySinhVienId(user.getSinhVien().getId());
                model.addAttribute("lichHocList", lichHocList); // Thêm lịch học vào model
                return "View/home_sinhvien"; // Sinh viên
            }
        }

        // Nếu không tìm thấy tài khoản hoặc mật khẩu sai
        model.addAttribute("error", "Email hoặc mật khẩu không chính xác!");
        return "View/login"; // Quay lại trang đăng nhập
    }

    // Trang home sau khi đăng nhập
    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        TaiKhoan user = (TaiKhoan) session.getAttribute("loggedInUser");

        if (user == null) {
            return "View/home"; // Nếu chưa đăng nhập, đưa về trang home (hoặc login)
        }

        // Kiểm tra quyền người dùng trong session và chuyển hướng tương ứng
        if (user.getQuyen() != null && "GiangVien".equals(user.getQuyen().getTenQuyen())) {
            return "View/home_giangvien"; // Giảng viên
        } else if (user.getQuyen() != null && "SinhVien".equals(user.getQuyen().getTenQuyen())) {
            return "View/home_sinhvien"; // Sinh viên
        }

        return "View/home"; // Trường hợp mặc định, có thể tạo trang mặc định
    }

    // Xử lý logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa toàn bộ session
        return "redirect:/home"; // Quay về trang chủ
    }
}
