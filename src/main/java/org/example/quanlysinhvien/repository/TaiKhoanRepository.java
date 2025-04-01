package org.example.quanlysinhvien.repository;

import org.example.quanlysinhvien.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Long> {
    @Query("SELECT u FROM TaiKhoan u JOIN FETCH u.quyen WHERE u.email = :email")
    Optional<TaiKhoan> findByEmail(@Param("email") String email);
}
