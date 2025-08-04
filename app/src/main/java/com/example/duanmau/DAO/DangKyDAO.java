package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.duanmau.DTO.DangKyDTO;
import com.example.duanmau.Databases.Dbhelper;

import java.util.ArrayList;

public class DangKyDAO {
    static SQLiteDatabase database;
    Dbhelper dbHelper;

    public DangKyDAO(Context context) {
        dbHelper = new Dbhelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long Them(DangKyDTO dto) {
        ContentValues values = new ContentValues();
        values.put("tenDN", dto.getTenDN());
        values.put("matKhau", dto.getMatKhau());
        values.put("gmail", dto.getGmail());
        long kq = database.insert("tb_dky", null, values);
        return kq;
    }

    // Thêm phương thức update để cập nhật thông tin đăng ký
    public int update(DangKyDTO dto) {
        ContentValues values = new ContentValues();
        values.put("tenDN", dto.getTenDN());
        values.put("matKhau", dto.getMatKhau());
        values.put("gmail", dto.getGmail());
        return database.update("tb_dky", values, "id = ?", new String[]{String.valueOf(dto.getId())});
    }

    public boolean checkLogin(String tenDN, String mk) {
        String[] columns = {"id"};

        String selection = "tenDN = ? AND matKhau = ?";
        String[] selectionArgs = {tenDN, mk};

        Cursor cursor = database.query("tb_dky", columns, selection, selectionArgs, null, null, null);

        boolean loginSuccessful = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }

        return loginSuccessful;
    }


    public ArrayList<DangKyDTO> getList(){
        ArrayList<DangKyDTO> listKH = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tb_dky ORDER BY id ASC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                DangKyDTO dangKyDTO = new DangKyDTO();
                dangKyDTO.setId(cursor.getInt(0));
                dangKyDTO.setTenDN(cursor.getString(1));
                dangKyDTO.setMatKhau(cursor.getString(2));
                dangKyDTO.setGmail(cursor.getString(3));
                listKH.add(dangKyDTO);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return listKH;
    }

    public  int XoaKhachHang(DangKyDTO dangKyDTO){

        int kq = database.delete("tb_dky","id="+dangKyDTO.getId(),null);
        return kq;
    }
    public static boolean checkOldPassword(String tenDN, String oldPassword) {
        String[] columns = {"id"};

        String selection = "tenDN = ? AND matKhau = ?";
        String[] selectionArgs = {tenDN, oldPassword};

        Cursor cursor = database.query("tb_dky", columns, selection, selectionArgs, null, null, null);

        boolean passwordCorrect = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }

        return passwordCorrect;
    }
    public boolean updatePassword(String tenDN, String newPassword) {
        ContentValues values = new ContentValues();
        values.put("matKhau", newPassword);

        int affectedRows = database.update("tb_dky", values, "tenDN = ?", new String[]{tenDN});

        // Kiểm tra xem có bao nhiêu hàng bị ảnh hưởng bởi việc cập nhật
        if (affectedRows > 0) {
            // Cập nhật thành công
            return true;
        } else {
            // Cập nhật thất bại
            return false;
        }
    }
}
