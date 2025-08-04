package com.example.duanmau.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanmau.Databases.Dbhelper;
import com.example.duanmau.MainActivity;
import com.example.duanmau.R;

public class DangNhapActivity extends AppCompatActivity {
    Dbhelper dbHelper;
    SQLiteDatabase db;

    Button btnDangNhap, btnDangky;
    EditText edtTenDN, edtMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new Dbhelper(this);
        db = dbHelper.getReadableDatabase();

        setContentView(R.layout.activity_dang_nhap);

        // Ánh xạ view
        edtTenDN = findViewById(R.id.edt_TenDangNhap);
        edtMatKhau = findViewById(R.id.edt_MatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangky = findViewById(R.id.btn_Dangky);

        // Sự kiện nút Đăng nhập
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDN = edtTenDN.getText().toString().trim();
                String matKhau = edtMatKhau.getText().toString().trim();

                if (tenDN.isEmpty() || matKhau.isEmpty()) {
                    Toast.makeText(DangNhapActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor cursor = db.rawQuery("SELECT * FROM tb_dky WHERE tenDN = ? AND matKhau = ?", new String[]{tenDN, matKhau});
                if (cursor.getCount() > 0) {
                    Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DangNhapActivity.this, "Tên đăng nhập hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        });

        // Sự kiện nút Đăng ký
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });
    }
}
