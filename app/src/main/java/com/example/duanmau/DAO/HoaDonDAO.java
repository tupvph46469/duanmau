package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.HoaDonDTO;
import com.example.duanmau.Databases.Dbhelper;

import java.util.ArrayList;

public class HoaDonDAO {
    SQLiteDatabase database;
    Dbhelper dbHelper;

    public HoaDonDAO(Context context) {
        dbHelper = new Dbhelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<HoaDonDTO> getList(){
        ArrayList<HoaDonDTO> listHoaDon = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM tb_hoaDon ORDER BY id ASC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                HoaDonDTO hoaDonDTO = new HoaDonDTO();
                hoaDonDTO.setId(cursor.getInt(0));
                hoaDonDTO.setTenDN(cursor.getString(1));
                hoaDonDTO.setTenSP(cursor.getString(2));
                hoaDonDTO.setSoLuong(cursor.getString(3));
                hoaDonDTO.setGiaTienMoi(cursor.getString(4)); // Thêm giá tiền mới

                listHoaDon.add(hoaDonDTO);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return listHoaDon;
    }

    public long themHoaDon(HoaDonDTO hoaDonDTO) {
        ContentValues values = new ContentValues();
        values.put("tenDN", hoaDonDTO.getTenDN()); // Tên đăng nhập (nếu bạn muốn lưu)
        values.put("tenSP", hoaDonDTO.getTenSP());
        values.put("soLuong", hoaDonDTO.getSoLuong());
        values.put("giaTienMoi", hoaDonDTO.getGiaTienMoi());

        long kq = database.insert("tb_hoaDon",null,values);
        return kq;
    }

    public int updateHoaDon(HoaDonDTO dto) {
        ContentValues values = new ContentValues();
        values.put("tenSP", dto.getTenSP());
        values.put("soLuong", dto.getSoLuong());
        values.put("giaTienMoi", dto.getGiaTienMoi());
        return database.update("tb_hoaDon", values, "id = ?", new String[]{String.valueOf(dto.getId())});
    }



    public int XoaHoaDon(HoaDonDTO obj){
        int  kq = database.delete("tb_hoaDon", "id="+obj.getId(),null);
        return kq;
    }


}
