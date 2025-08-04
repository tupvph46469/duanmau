package com.example.duanmau.DTO;

public class DoanhThuDTO {
    private int id;
    private String ngay;
    private double tongDoanhThu;

    public DoanhThuDTO() {
    }

    public DoanhThuDTO(String ngay, double tongDoanhThu) {
        this.ngay = ngay;
        this.tongDoanhThu = tongDoanhThu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public double getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(double tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }
}
