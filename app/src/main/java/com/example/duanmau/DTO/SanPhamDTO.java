package com.example.duanmau.DTO;

public class SanPhamDTO {
    int id;
    String tenSP, soLuong, giaTien, moTa, tenLoai;

    public SanPhamDTO(int id, String tenSP, String soLuong, String giaTien, String moTa, String tenLoai) {
        this.id = id;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.moTa = moTa;
        this.tenLoai = tenLoai;
    }

    public SanPhamDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
