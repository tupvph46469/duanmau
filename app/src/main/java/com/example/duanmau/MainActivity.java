package com.example.duanmau;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.example.duanmau.Activitys.DangNhapActivity;
import com.example.duanmau.DAO.DangKyDAO;
import com.example.duanmau.Fragments.ChinhSach_Frag;
import com.example.duanmau.Fragments.DoanhThu_Frag;
import com.example.duanmau.Fragments.Facebook_Frag;
import com.example.duanmau.Fragments.GioHang_Frag;
import com.example.duanmau.Fragments.HoaDon_Frag;
import com.example.duanmau.Fragments.KhachHang_Frag;
import com.example.duanmau.Fragments.LoaiTraiCay_Frag;
import com.example.duanmau.Fragments.SanPham_Frag;

    public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout001;
    Toolbar mtoolbar001;
    NavigationView nav001;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout001 = findViewById(R.id.drawerlayout001);
        mtoolbar001 = findViewById(R.id.mtoolbar001);
        nav001 = findViewById(R.id.nav001);
        context = this;
        setSupportActionBar(mtoolbar001);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadMenuBasedOnUserRole();

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout001,
                mtoolbar001,
                R.string.open,
                R.string.close
        );
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout001.addDrawerListener(drawerToggle);



        nav001.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fr = null;

                if (item.getItemId() == R.id.nav_loaiTraiCay) {
                    fr = new LoaiTraiCay_Frag();
                    mtoolbar001.setTitle("Loại trái cây");
                } else if (item.getItemId() == R.id.nav_sanPham) {
                    fr = new SanPham_Frag();
                    mtoolbar001.setTitle("Sản phẩm");
                } else if (item.getItemId() == R.id.nav_dieuKhoan) {
                    fr = new ChinhSach_Frag();
                    mtoolbar001.setTitle("Chính sách và điều khoản");
                } else if (item.getItemId() == R.id.nav_khachHang) {
                    fr = new KhachHang_Frag();
                    mtoolbar001.setTitle("Khách hàng");
                } else if (item.getItemId() == R.id.nav_hoaDon) {
                    fr = new HoaDon_Frag();
                    mtoolbar001.setTitle("Hóa đơn");
                } else if (item.getItemId() == R.id.nav_gioHang) {
                    fr = new GioHang_Frag();
                    mtoolbar001.setTitle("Giỏ hàng");
                }
                else if (item.getItemId() == R.id.nav_facebook) {
                    fr = new Facebook_Frag();
                    mtoolbar001.setTitle("Facebook");
                }else if (item.getItemId() == R.id.nav_doanhThu) {
                    fr = new DoanhThu_Frag();
                    mtoolbar001.setTitle("Doanh thu");
                } else if (item.getItemId() == R.id.nav_hotline) {
                    openPhoneDialer("0376669102");
                    return true;
                } else if (item.getItemId() == R.id.nav_dangXuat) {
                    DangXuat();
                    return true;
                } else if (item.getItemId() == R.id.nav_doiMK) {
                    ShowDialog();
                    return true;
                } else {
                    fr = new SanPham_Frag(); // Default to NhanVien_Frag
                    mtoolbar001.setTitle("Sản phẩm");
                }

                if (fr != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frag_container001, fr)
                            .commit();
                    drawerLayout001.close();
                    return true;
                }
                return false;
            }

            private void openPhoneDialer(String phoneNumber) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }

            private void DangXuat() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Bạn có muốn thoát?").setTitle("CẢNH BÁO !!!!!")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(MainActivity.this, DangNhapActivity.class));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            private void ShowDialog() {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_doipass);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                EditText oldPasswordEditText = dialog.findViewById(R.id.edt_mkCu);
                EditText newPasswordEditText = dialog.findViewById(R.id.edt_mkMoi);
                EditText confirmPasswordEditText = dialog.findViewById(R.id.edt_nlaiMkMoi);

                Button changePasswordButton = dialog.findViewById(R.id.btn_add_mk);
                changePasswordButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        String tenDN = sharedPreferences.getString("tenDN", "");

                        String oldPassword = oldPasswordEditText.getText().toString().trim();
                        String newPassword = newPasswordEditText.getText().toString().trim();
                        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                        DangKyDAO dangKyDAO = new DangKyDAO(MainActivity.this);

                        // Kiểm tra mật khẩu cũ
                        if (!DangKyDAO.checkOldPassword(tenDN, oldPassword)) {
                            Toast.makeText(MainActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Kiểm tra tính hợp lệ của mật khẩu mới
                        if (newPassword.length() < 3) {
                            Toast.makeText(MainActivity.this, "Mật khẩu mới phải chứa ít nhất 3 ký tự", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Kiểm tra mật khẩu mới và mật khẩu xác nhận có khớp nhau không
                        if (!newPassword.equals(confirmPassword)) {
                            Toast.makeText(MainActivity.this, "Mật khẩu mới và mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Thực hiện cập nhật mật khẩu mới vào cơ sở dữ liệu
                        boolean updateResult = dangKyDAO.updatePassword(tenDN, newPassword);

                        if (updateResult) {
                            Toast.makeText(MainActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                        // Đóng dialog sau khi xử lý xong
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void loadMenuBasedOnUserRole() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);
        if (isAdmin) {
            nav001.inflateMenu(R.menu.menu_drawer);
        } else {
            nav001.inflateMenu(R.menu.menu_user);
        }
    }
}
