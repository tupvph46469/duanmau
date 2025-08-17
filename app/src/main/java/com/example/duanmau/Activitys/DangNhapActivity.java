package com.example.duanmau.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.example.duanmau.DAO.DangKyDAO;
import com.example.duanmau.MainActivity;
import com.example.duanmau.R;

public class DangNhapActivity extends AppCompatActivity {
    Button btnDangnhap, btnDangKy;
    TextInputEditText edt_TenDn, edt_matKhau;
    CheckBox chkLuuMK;

    Spinner spn_quyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        btnDangnhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btn_Dangky);
        edt_matKhau = findViewById(R.id.edt_MatKhau);
        edt_TenDn = findViewById(R.id.edt_TenDangNhap);
        chkLuuMK = findViewById(R.id.chk_luumk);
        spn_quyen = findViewById(R.id.spn_quyen);

        String[] quyen = new String[]{"Admin", "Người dùng"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quyen);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_quyen.setAdapter(adapter);

        loadLoginInfo();

        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDN = edt_TenDn.getText().toString();
                String mk = edt_matKhau.getText().toString();
                String selectedRole = spn_quyen.getSelectedItem().toString(); // Lấy giá trị được chọn từ Spinner
                DangKyDAO dao = new DangKyDAO(getApplicationContext());
                boolean loggedIn = dao.checkLogin(tenDN, mk);
                boolean isAdmin = "Admin".equals(selectedRole);

                if (chkLuuMK.isChecked()) {
                    saveLoginInfo(tenDN,mk); // Lưu thông tin đăng nhập
                    saveLoginInfo(isAdmin); // Lưu giá trị quyền
                } else {
                    clearLoginInfo();
                }

                if ((isAdmin && "admin".equalsIgnoreCase(tenDN) && "admin".equalsIgnoreCase(mk)) || (!isAdmin && loggedIn)) {
                    startActivity(new Intent(DangNhapActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_LONG).show();
                }
            }
        });








        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class));
            }
        });
    }

    private void loadLoginInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String tenDN = sharedPreferences.getString("tenDN", "");
        String matKhau = sharedPreferences.getString("matKhau", "");
        String quyen = sharedPreferences.getString("quyen", "Người dùng"); // Mặc định là Người dùng
        boolean isRemembered = sharedPreferences.getBoolean("isRemembered", false);

        edt_TenDn.setText(tenDN);
        edt_matKhau.setText(matKhau);
        chkLuuMK.setChecked(isRemembered);

        if ("Admin".equals(quyen)) {
            spn_quyen.setSelection(0); // Chọn quyền Admin
        } else {
            spn_quyen.setSelection(1); // Chọn quyền Người dùng
        }
    }


    private void saveLoginInfo(String tenDN, String matKhau) {
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tenDN", tenDN);
        editor.putString("matKhau", matKhau);
        editor.putBoolean("isRemembered", true);
        editor.apply();
    }

    private void saveLoginInfo(boolean isAdmin) {
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isAdmin) {
            editor.putString("quyen", "Admin"); // Lưu quyền Admin vào SharedPreferences
            editor.putBoolean("isAdmin", true);
        } else {
            editor.putString("quyen", "Người dùng"); // Lưu quyền Người dùng vào SharedPreferences
            editor.remove("isAdmin"); // Xóa giá trị isAdmin nếu không phải admin
        }
        editor.apply();
    }



    private void clearLoginInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("tenDN");
        editor.remove("matKhau");
        editor.remove("gmail");
        editor.remove("isRemembered");
        editor.apply();
    }



}
