package com.example.duanmau.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.example.duanmau.DAO.DangKyDAO;
import com.example.duanmau.DTO.DangKyDTO;
import com.example.duanmau.R;

public class DangKyActivity extends AppCompatActivity {
    DangKyDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky_activity);

        dao = new DangKyDAO(this);

        TextInputEditText edt_tenDangNhap = findViewById(R.id.edt_TenDangNhap_dky);
        TextInputEditText edtMatKhau = findViewById(R.id.edt_matkhau_dky);
        TextInputEditText edtNhapLaiMk = findViewById(R.id.edt_nhaplaiMk_dky);
        TextInputEditText edt_Gmail = findViewById(R.id.edt_Gmail_dky);
        Button btnOkDangKy = findViewById(R.id.btn_ok_dky);
        Button btnHuy = findViewById(R.id.btnHuy_dky);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class));
            }
        });

        btnOkDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matKhau = edtMatKhau.getText().toString();
                String nhapLaiMk = edtNhapLaiMk.getText().toString();
                String tenDN = edt_tenDangNhap.getText().toString();
                String gmail = edt_Gmail.getText().toString();

                if (matKhau.equals(nhapLaiMk)) {
                    if (isValidEmail(gmail)) {
                        DangKyDTO dto = new DangKyDTO();
                        dto.setMatKhau(matKhau);
                        dto.setTenDN(tenDN);
                        dto.setGmail(gmail);

                        long kq = dao.Them(dto);
                        if (kq > 0) {
                            startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class));
                            Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Địa chỉ Gmail không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean isValidEmail(CharSequence target) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
            }
        });

    }
}
