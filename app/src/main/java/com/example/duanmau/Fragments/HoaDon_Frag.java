package com.example.duanmau.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapters.HoaDonAdapter;
import com.example.duanmau.DAO.HoaDonDAO;
import com.example.duanmau.DTO.HoaDonDTO;
import com.example.duanmau.R;

import java.util.ArrayList;


public class HoaDon_Frag extends Fragment {

    private RecyclerView rcv_hoaDon;
    private HoaDonDAO hoaDonDAO;
    private HoaDonAdapter hoaDonAdapter;
    private ArrayList<HoaDonDTO> listHoaDon;
    private EditText edt_search_hoaDon;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hoa_don_fragment, container, false);

        rcv_hoaDon = view.findViewById(R.id.rcv_hoaDon);
        hoaDonDAO = new HoaDonDAO(getContext());
        edt_search_hoaDon = view.findViewById(R.id.edt_ser_hoaDon);

        listHoaDon = hoaDonDAO.getList();
        hoaDonAdapter = new HoaDonAdapter(getContext(), listHoaDon);
        rcv_hoaDon.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_hoaDon.setAdapter(hoaDonAdapter);


        edt_search_hoaDon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().toLowerCase().trim();
                ArrayList<HoaDonDTO> ds = new ArrayList<>();

                if (searchText.isEmpty()) {
                    hoaDonAdapter.updateData(hoaDonDAO.getList()); // Hiển thị lại toàn bộ danh sách khi ô EditText trống
                    return;
                }

                for (HoaDonDTO hoaDonDTO : hoaDonDAO.getList()) {
                    if (hoaDonDTO.getTenDN().toLowerCase().contains(searchText)) {
                        ds.add(hoaDonDTO);
                    }
                }

                hoaDonAdapter.updateData(ds); // Cập nhật dữ liệu hiển thị trong RecyclerView
            }
        });
        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
