package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.LoaiTraiCayDTO;
import com.example.duanmau.Databases.Dbhelper;

import java.util.ArrayList;

public class LoaiTraiCayDAO {
    SQLiteDatabase database;
    Dbhelper dbHelper;

    public LoaiTraiCayDAO(Context context) {
        dbHelper = new Dbhelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<LoaiTraiCayDTO> getList(){
        ArrayList<LoaiTraiCayDTO> listLoaiTraiCay = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tb_loaiTraiCay ORDER BY id ASC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                LoaiTraiCayDTO loaiTraiCay = new LoaiTraiCayDTO();
                loaiTraiCay.setId(cursor.getInt(0));
                loaiTraiCay.setTenLoai(cursor.getString(1));

                listLoaiTraiCay.add(loaiTraiCay);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return listLoaiTraiCay;
    }

    public  long AddTraiCay(LoaiTraiCayDTO loaiTraiCayDTO){
        ContentValues values = new ContentValues();
        values.put("tenLoai ",loaiTraiCayDTO.getTenLoai());
        long kq = database.insert("tb_loaiTraiCay",null,values);
        return kq;
    }

    public  int XoaLoaiTraiCay(LoaiTraiCayDTO loaiTraiCayDTO){

        int kq = database.delete("tb_loaiTraiCay","id="+loaiTraiCayDTO.getId(),null);
        return kq;
    }

    public int SuaLoaiTraiCay(LoaiTraiCayDTO loaiTraiCayDTO) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiTraiCayDTO.getTenLoai());
        int  kq = database.update("tb_loaiTraiCay", values,"id="+loaiTraiCayDTO.getId(),null);
        return kq;
    }
}
