package com.example.duanmau.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.example.duanmau.Adapters.SanPhamAdapter;
import com.example.duanmau.DAO.LoaiTraiCayDAO;
import com.example.duanmau.DAO.SanPhamDAO;
import com.example.duanmau.DTO.LoaiTraiCayDTO;
import com.example.duanmau.DTO.SanPhamDTO;
import com.example.duanmau.R;

import java.util.ArrayList;

public class SanPham_Frag extends Fragment {
    private RecyclerView rcv_sanPham;
    SanPhamDAO sanPhamDAO;
    SanPhamAdapter sanPhamAdapter;
    ArrayList<SanPhamDTO> listSP;
    EditText edt_search_sanPham;

    Spinner sp_sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.san_pham_fragment, container, false);
        rcv_sanPham = view.findViewById(R.id.rcv_sanPham);

        sanPhamDAO = new SanPhamDAO(getContext());

        edt_search_sanPham = view.findViewById(R.id.edt_ser_sanPham);
        listSP = sanPhamDAO.getList();
        sanPhamAdapter = new SanPhamAdapter(getContext(), listSP);
        rcv_sanPham.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_sanPham.setAdapter(sanPhamAdapter);

        ImageView img_add = view.findViewById(R.id.img_add_sanPham);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        if (!isAdmin) {
            img_add.setVisibility(View.GONE); // Ẩn ImageView nếu không phải admin
        }
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd();
            }
        });


        edt_search_sanPham.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().toLowerCase().trim();
                ArrayList<SanPhamDTO> ds = new ArrayList<>();

                if (searchText.isEmpty()) {
                    sanPhamAdapter.updateData(sanPhamDAO.getList()); // Hiển thị lại toàn bộ danh sách khi ô EditText trống
                    return;
                }

                for (SanPhamDTO tenLoai : sanPhamDAO.getList()) {
                    if (tenLoai.getTenLoai().toLowerCase().contains(searchText)) {
                        ds.add(tenLoai);
                    }
                }

                sanPhamAdapter.updateData(ds); // Cập nhật dữ liệu hiển thị trong RecyclerView
            }
        });
        return view;
    }

    private void showAdd() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_san_pham);

        TextInputEditText edt_tenSP = dialog.findViewById(R.id.edt_ten_sanPham);
        TextInputEditText edt_soLuong = dialog.findViewById(R.id.edt_soLuong);
        TextInputEditText edt_giaTien = dialog.findViewById(R.id.edt_giaTien);
        TextInputEditText edt_moTa = dialog.findViewById(R.id.edt_moTa);
        Spinner sp_sp = dialog.findViewById(R.id.spn_sanPham);
        Button btn_add = dialog.findViewById(R.id.btn_add_sanPham);
        Button btn_huy = dialog.findViewById(R.id.btn_huy_sanPham);

        // Lấy danh sách loại sản phẩm và cập nhật Adapter cho Spinner
        ArrayList<LoaiTraiCayDTO> listLoaiSP = new LoaiTraiCayDAO(getContext()).getList();
        ArrayAdapter<LoaiTraiCayDTO> loaiSpAdapter = new ArrayAdapter<LoaiTraiCayDTO>(getContext(), android.R.layout.simple_spinner_item, listLoaiSP) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setText(listLoaiSP.get(position).getTenLoai()); // Sử dụng phương thức getTenLoai() để lấy tên loại sản phẩm
                return textView;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setText(listLoaiSP.get(position).getTenLoai()); // Và ở đây cũng sử dụng getTenLoai()
                return textView;
            }
        };
        sp_sp.setAdapter(loaiSpAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSP = edt_tenSP.getText().toString().trim();
                String soLuongStr = edt_soLuong.getText().toString().trim();
                String giaTienStr = edt_giaTien.getText().toString().trim();
                String moTa = edt_moTa.getText().toString().trim();
                LoaiTraiCayDTO selectedLoaiSP = (LoaiTraiCayDTO) sp_sp.getSelectedItem();

                if (selectedLoaiSP == null) {
                    Toast.makeText(getContext(), "Vui lòng chọn loại sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tenSP.isEmpty() || soLuongStr.isEmpty() || giaTienStr.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                int soLuong;
                double giaTien;

                try {
                    soLuong = Integer.parseInt(soLuongStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Số lượng phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    giaTien = Double.parseDouble(giaTienStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Giá tiền phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (soLuong <= 0) {
                    Toast.makeText(getContext(), "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (giaTien <= 0) {
                    Toast.makeText(getContext(), "Giá tiền phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                SanPhamDTO sanPhamDTO = new SanPhamDTO(-1, tenSP, String.valueOf(soLuong), String.valueOf(giaTien), moTa, selectedLoaiSP.getTenLoai());
                long isSuccess = sanPhamDAO.ThemSanPham(sanPhamDTO);

                if (isSuccess != -1) {
                    Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                    listSP.clear();
                    listSP.addAll(sanPhamDAO.getList());
                    sanPhamAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });




        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
