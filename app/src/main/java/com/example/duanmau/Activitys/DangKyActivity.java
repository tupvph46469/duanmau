
package com.example.duanmau.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanmau.R;
import com.google.android.material.textfield.TextInputEditText;

public class DangKyActivity extends AppCompatActivity {

    private TextInputEditText edtTenDangNhap, edtMatKhau, edtNhapLaiMk, edtGmail;
    private Button btnDangKy, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky_activity); // sửa lại tên layout nếu cần

        // Ánh xạ view
        edtTenDangNhap = findViewById(R.id.edt_TenDangNhap_dky);
        edtMatKhau = findViewById(R.id.edt_matkhau_dky);
        edtNhapLaiMk = findViewById(R.id.edt_nhaplaiMk_dky);
        edtGmail = findViewById(R.id.edt_Gmail_dky);
        btnDangKy = findViewById(R.id.btn_ok_dky);
        btnHuy = findViewById(R.id.btnHuy_dky);

        // Xử lý nút "Đăng ký"
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKy();
            }
        });

        // Xử lý nút "Hủy"
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Quay lại màn trước
            }
        });
    }

    private void dangKy() {
        String tenDangNhap = edtTenDangNhap.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();
        String nhapLaiMk = edtNhapLaiMk.getText().toString().trim();
        String gmail = edtGmail.getText().toString().trim();

        // Kiểm tra dữ liệu nhập
        if (tenDangNhap.isEmpty() || matKhau.isEmpty() || nhapLaiMk.isEmpty() || gmail.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!matKhau.equals(nhapLaiMk)) {
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(gmail).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Nếu có API, bạn gọi Retrofit hoặc POST ở đây
        // Nếu không, ta chỉ giả lập thành công
        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

        // Có thể chuyển về màn login
        Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
        startActivity(intent);
        finish();
    }
}
