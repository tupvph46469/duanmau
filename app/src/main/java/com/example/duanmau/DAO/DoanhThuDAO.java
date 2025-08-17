package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.DoanhThuDTO;
import com.example.duanmau.Databases.Dbhelper;

import java.util.ArrayList;

public class DoanhThuDAO {
    SQLiteDatabase database;
    Dbhelper dbHelper;

    public DoanhThuDAO(Context context) {
        dbHelper = new Dbhelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Hàm để tính toán tổng doanh thu
    public double tinhTongDoanhThu() {
        double tongDoanhThu = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(giaTienMoi) FROM tb_hoaDon", null);

        if (cursor != null && cursor.moveToFirst()) {
            tongDoanhThu = cursor.getDouble(0);
            cursor.close();
        }
        return tongDoanhThu;
    }

    public double getTongDoanhThu() {
        double tongDoanhThu = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(giaTienMoi) FROM tb_hoaDon", null);

        if (cursor != null && cursor.moveToFirst()) {
            tongDoanhThu = cursor.getDouble(0);
            cursor.close();
        }

        return tongDoanhThu;
    }

    // Thêm hàm để lấy danh sách doanh thu theo ngày, tháng hoặc năm (tùy theo yêu cầu của bạn)
    public ArrayList<DoanhThuDTO> getListDoanhThu() {
        ArrayList<DoanhThuDTO> listDoanhThu = new ArrayList<>();
        // Viết câu truy vấn để lấy danh sách doanh thu từ cơ sở dữ liệu
        Cursor cursor = database.rawQuery("SELECT * FROM tb_doanhThu", null);
        // Sau đó, thêm các phần tử vào listDoanhThu từ cursor

        return listDoanhThu;
    }

    // Thêm hàm để thêm doanh thu mới vào cơ sở dữ liệu
    public long themDoanhThu(DoanhThuDTO doanhThuDTO) {
        ContentValues values = new ContentValues();
        values.put("ngay", doanhThuDTO.getNgay());
        values.put("tongDoanhThu", doanhThuDTO.getTongDoanhThu());

        long result = database.insert("tb_doanhThu", null, values);
        return result;
    }


    // Thêm các hàm khác tùy theo yêu cầu của bạn, chẳng hạn như cập nhật doanh thu, xóa doanh thu, v.v.
}
