    package com.example.duanmau.Databases;

    import android.content.Context;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;

    public class Dbhelper extends SQLiteOpenHelper {
        public Dbhelper( Context context) {
            super(context, "duan1.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Đăng ký, đăng nhập nhé
            db.execSQL("CREATE TABLE tb_dky (id INTEGER PRIMARY KEY AUTOINCREMENT, tenDN TEXT NOT NULL, matKhau TEXT NOT NULL, gmail TEXT NOT NULL)");
            db.execSQL("INSERT INTO tb_dky (tenDN, matKhau, gmail) VALUES ('admin', '123' , 'admin@gmail.com')");
            db.execSQL("INSERT INTO tb_dky (tenDN, matKhau, gmail) VALUES ('tu', '123', 'tu@gmail.com')");

            //bảng snar phẩm
            db.execSQL("CREATE TABLE tb_sanPham (id INTEGER PRIMARY KEY AUTOINCREMENT, tenSP TEXT, soLuong TEXT, giaTien TEXT, moTa TEXT, tenLoai TEXT);");
            db.execSQL("INSERT INTO tb_sanPham (tenSP, soLuong, giaTien,moTa,tenLoai) VALUES ('Táo xanh', '200', '200000', 'Táo ngon', 'Táo')");
            db.execSQL("INSERT INTO tb_sanPham (tenSP, soLuong, giaTien,moTa,tenLoai) VALUES ('Nho tàu', '10', '24500', 'Nho ngon', 'Nho')");

            //bảng giỏ hàng
            db.execSQL("CREATE TABLE tb_gioHang (id INTEGER PRIMARY KEY AUTOINCREMENT, tenSP TEXT, giaTien TEXT, soLuongGioHang TEXT, giaTienMoi TEXT);");

    //        //bảng thanh toán
            db.execSQL("CREATE TABLE tb_hoaDon (id INTEGER PRIMARY KEY AUTOINCREMENT, tenDN TEXT, tenSP TEXT, soLuong TEXT, giaTienMoi TEXT);");



            // loại trái cây
            String sql_loaiTraiCay = "CREATE TABLE tb_loaiTraiCay (id INTEGER PRIMARY KEY AUTOINCREMENT, tenLoai TEXT NOT NULL)";
            db.execSQL(sql_loaiTraiCay);

            String sql_insertData1 = "INSERT INTO tb_loaiTraiCay (tenLoai) VALUES ('Nho')";
            String sql_insertData2 = "INSERT INTO tb_loaiTraiCay (tenLoai) VALUES ('Táo')";
            db.execSQL(sql_insertData1);
            db.execSQL(sql_insertData2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
