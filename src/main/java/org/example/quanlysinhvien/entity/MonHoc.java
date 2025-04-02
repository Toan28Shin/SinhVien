package org.example.quanlysinhvien.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="mon_hoc")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String maMonHoc;
    private String tenMon;
    private String soTinChi;
    private int trangThai;


}
