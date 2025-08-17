package com.example.duanmau.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapters.GioHangAdapter;
import com.example.duanmau.Adapters.HoaDonAdapter;
import com.example.duanmau.DAO.GioHangDAO;
import com.example.duanmau.DAO.HoaDonDAO;
import com.example.duanmau.DTO.GioHangDTO;
import com.example.duanmau.DTO.HoaDonDTO;
import com.example.duanmau.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class GioHang_Frag extends Fragment {

    private RecyclerView rcv_gioHang;
    private GioHangDAO gioHangDAO;
    private GioHangAdapter gioHangAdapter;
    private ArrayList<GioHangDTO> listGioHang;
    private EditText edt_search_gioHang;
    private TextView tv_tongTien; // Thêm biến tham chiếu đến TextView tổng tiền
    HoaDonDAO hoaDonDAO;

    Button btn_thanhToan;
    private ArrayList<HoaDonDTO> listHoaDon = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gio_hang_fragment, container, false);

        rcv_gioHang = view.findViewById(R.id.rcv_gioHang);
        gioHangDAO = new GioHangDAO(getContext());
        edt_search_gioHang = view.findViewById(R.id.edt_ser_gioHang);
        tv_tongTien = view.findViewById(R.id.tv_tongTien); // Khởi tạo tham chiếu đến TextView tổng tiền
        btn_thanhToan = view.findViewById(R.id.btn_thanhToan);

        listGioHang = gioHangDAO.getList();
        gioHangAdapter = new GioHangAdapter(getContext(), listGioHang,this);
        rcv_gioHang.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_gioHang.setAdapter(gioHangAdapter);

        updateTotalPrice(); // Cập nhật tổng tiền khi Fragment được khởi tạo

        btn_thanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo").setMessage("Bạn có muốn thanh toán không ?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Thực hiện thanh toán ở đây
                        // Tạo hóa đơn từ giỏ hàng
                        hoaDonDAO = new HoaDonDAO(getContext());
                        for (GioHangDTO gioHangDTO : listGioHang) {
                            HoaDonDTO hoaDonDTO = new HoaDonDTO();
                            hoaDonDTO.setTenDN("Tho");
                            hoaDonDTO.setTenSP(gioHangDTO.getTenSP());
                            hoaDonDTO.setSoLuong(gioHangDTO.getSoLuongGioHang());
                            hoaDonDTO.setGiaTienMoi(gioHangDTO.getGiaTienMoi());
                            // Thêm hóa đơn vào cơ sở dữ liệu
                            long result = hoaDonDAO.themHoaDon(hoaDonDTO);
                            if (result != -1) {
                                listHoaDon.add(hoaDonDTO);
                                Toast.makeText(getContext(),"Thanh toán thành công",Toast.LENGTH_SHORT).show();
                            }
                        }
                        // Hiển thị danh sách hóa đơn trong RecyclerView
                        HoaDonAdapter hoaDonAdapter = new HoaDonAdapter(getContext(), listHoaDon);
                        rcv_gioHang.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv_gioHang.setAdapter(hoaDonAdapter);

                        // Xóa giỏ hàng sau khi đã thanh toán
                        gioHangDAO.xoaTatCa();
                        // Sau khi thanh toán, bạn cần cập nhật lại danh sách giỏ hàng
                        listGioHang.clear();
                        listGioHang.addAll(gioHangDAO.getList());
                        gioHangAdapter.notifyDataSetChanged();

                        // Cập nhật tổng tiền sau khi thanh toán
                        updateTotalPrice();

                        // Chuyển sang fragment hóa đơn
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        HoaDon_Frag newFragment = new HoaDon_Frag(); // Khởi tạo fragment mới (cần thay thế bằng tên fragment thực tế của bạn)
                        fragmentTransaction.replace(R.id.frag_container001, newFragment); // R.id.fragment_container là ID của container fragment trong layout của bạn
                        fragmentTransaction.addToBackStack(null); // Để có thể quay trở lại fragment trước đó
                        fragmentTransaction.commit();
                    }
                }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });




        edt_search_gioHang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().toLowerCase().trim();
                ArrayList<GioHangDTO> ds = new ArrayList<>();

                if (searchText.isEmpty()) {
                    gioHangAdapter.updateData(gioHangDAO.getList());
                    updateTotalPrice(); // Cập nhật tổng tiền khi danh sách sản phẩm thay đổi
                    return;
                }

                for (GioHangDTO gioHangDTO : gioHangDAO.getList()) {
                    if (gioHangDTO.getTenSP().toLowerCase().contains(searchText)) {
                        ds.add(gioHangDTO);
                    }
                }

                gioHangAdapter.updateData(ds);
                updateTotalPrice(); // Cập nhật tổng tiền khi danh sách sản phẩm thay đổi
            }
        });

        return view;
    }
// Phương thức để tính và cập nhật tổng tiền
public void updateTotalPrice() {
        double totalPrice = 0;

        for (GioHangDTO gioHangDTO : listGioHang) {
            try {
                double price = Double.parseDouble(gioHangDTO.getGiaTienMoi());
                totalPrice += price;
            } catch (NumberFormatException e) {
                Log.e("GioHang_Frag", "Lỗi khi chuyển đổi giá tiền mới sang kiểu số", e);
            }
        }

        // Tạo một NumberFormat để định dạng số tiền
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        String formattedTotalPrice = numberFormat.format(totalPrice);

        // Hiển thị tổng tiền trong TextView với cách phân cách hàng nghìn
        tv_tongTien.setText(String.format(Locale.getDefault(), "%s VND", formattedTotalPrice));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

