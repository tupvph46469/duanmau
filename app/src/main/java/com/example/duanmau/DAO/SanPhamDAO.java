package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.SanPhamDTO;
import com.example.duanmau.Databases.Dbhelper;

import java.util.ArrayList;

public class SanPhamDAO {
    SQLiteDatabase database;
    Dbhelper dbHelper;

    public SanPhamDAO(Context context) {
        dbHelper = new Dbhelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public ArrayList<SanPhamDTO> getList(){
        ArrayList<SanPhamDTO> listSP = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tb_sanPham ORDER BY id ASC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                SanPhamDTO sanPhamDTO = new SanPhamDTO();
                sanPhamDTO.setId(cursor.getInt(0));
                sanPhamDTO.setTenSP(cursor.getString(1));
                sanPhamDTO.setSoLuong(cursor.getString(2));
                sanPhamDTO.setGiaTien(cursor.getString(3));
                sanPhamDTO.setMoTa(cursor.getString(4));
                sanPhamDTO.setTenLoai(cursor.getString(5));

                listSP.add(sanPhamDTO);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return listSP;
    }

    public SanPhamDTO getSanPhamById(int id) {
        SanPhamDTO sanPhamDTO = null;
        Cursor cursor = database.rawQuery("SELECT tenSP, giaTien FROM tb_sanPham WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            sanPhamDTO = new SanPhamDTO();
            sanPhamDTO.setTenSP(cursor.getString(0));  // Sử dụng chỉ số cột
            sanPhamDTO.setGiaTien(cursor.getString(1));  // Sử dụng chỉ số cột
            cursor.close();
        }
        return sanPhamDTO;
    }



    public long ThemSanPham(SanPhamDTO obj){
        ContentValues values = new ContentValues();
        values.put("tenSP", obj.getTenSP());
        values.put("soLuong", obj.getSoLuong());
        values.put("giaTien", obj.getGiaTien());
        values.put("moTa", obj.getMoTa());
        values.put("tenLoai", obj.getTenLoai());

        long kq = database.insert("tb_sanPham",null,values);
        return kq;
    }

    public int SuaSanPham(SanPhamDTO obj){
        ContentValues values = new ContentValues();
        values.put("tenSP", obj.getTenSP());
        values.put("soLuong", obj.getSoLuong());
        values.put("giaTien", obj.getGiaTien());
        values.put("moTa", obj.getMoTa());
        values.put("tenLoai", obj.getTenLoai());

        int  kq = database.update("tb_sanPham", values,"id="+obj.getId(),null);
        return kq;
    }

    public int XoaSanPham(SanPhamDTO obj){
        int  kq = database.delete("tb_sanPham", "id="+obj.getId(),null);
        return kq;
    }


}
