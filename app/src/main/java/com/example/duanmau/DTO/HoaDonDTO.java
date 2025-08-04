package com.example.duanmau.DTO;

public class HoaDonDTO {
    int id;

    private String tenDN;
    private String tenSP;
    private String soLuong;
    private String giaTienMoi;

    public HoaDonDTO() {
    }

    public HoaDonDTO(int id, String tenDN, String tenSP, String soLuong, String giaTienMoi) {
        this.id = id;
        this.tenDN = tenDN;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.giaTienMoi = giaTienMoi;
    }

    public String getTenDN() {
        return tenDN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
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

    public String getGiaTienMoi() {
        return giaTienMoi;
    }

    public void setGiaTienMoi(String giaTienMoi) {
        this.giaTienMoi = giaTienMoi;
    }
}
