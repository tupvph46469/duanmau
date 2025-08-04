package com.example.duanmau.DTO;

public class GioHangDTO {
    private int id;
    private String tenSP;
    private String giaTien;
    private String soLuongGioHang;
    private String giaTienMoi;

    public GioHangDTO() {
    }

    public GioHangDTO(int id, String tenSP, String giaTien, String soLuongGioHang) {
        this.id = id;
        this.tenSP = tenSP;
        this.giaTien = giaTien;
        this.soLuongGioHang = soLuongGioHang;
        calculateGiaTienMoi(); // Tính giá tiền mới khi tạo đối tượng
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

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
        calculateGiaTienMoi(); // Tính giá tiền mới khi giá tiền cũ thay đổi
    }

    public String getSoLuongGioHang() {
        return soLuongGioHang;
    }

    public void setSoLuongGioHang(String soLuongGioHang) {
        this.soLuongGioHang = soLuongGioHang;
        calculateGiaTienMoi(); // Tính giá tiền mới khi số lượng thay đổi
    }

    public String getGiaTienMoi() {
        return giaTienMoi;
    }

    public void setGiaTienMoi(String giaTienMoi) {
        this.giaTienMoi = giaTienMoi;
    }

    private void calculateGiaTienMoi() {
        try {
            int soLuong = Integer.parseInt(soLuongGioHang);
            double gia = Double.parseDouble(giaTien);
            double giaTienMoiValue = soLuong * gia;
            this.giaTienMoi = String.valueOf(giaTienMoiValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.giaTienMoi = "0"; // Giá trị mặc định khi có lỗi
        }
    }
}
