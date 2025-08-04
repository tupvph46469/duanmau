    package com.example.duanmau.DTO;

    public class DangKyDTO {
        int id;
        String tenDN, matKhau, gmail;

        public DangKyDTO(String tenDN, String matKhau, String gmail) {
            this.id = id;
            this.tenDN = tenDN;
            this.matKhau = matKhau;
            this.gmail = gmail;
        }

        public DangKyDTO() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTenDN() {
            return tenDN;
        }

        public void setTenDN(String tenDN) {
            this.tenDN = tenDN;
        }

        public String getMatKhau() {
            return matKhau;
        }

        public void setMatKhau(String matKhau) {
            this.matKhau = matKhau;
        }

        public String getGmail() {
            return gmail;
        }

        public void setGmail(String gmail) {this.gmail = gmail;
        }


    }
    //trl
