package com.example.duanmau.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapters.KhachHangAdapter;
import com.example.duanmau.DAO.DangKyDAO;
import com.example.duanmau.DTO.DangKyDTO;
import com.example.duanmau.R;

import java.util.ArrayList;


public class KhachHang_Frag extends Fragment  {
    private RecyclerView rcv_khachHang;
    private DangKyDAO dangKyDAO;
    private KhachHangAdapter khachHangAdapter;
    private ArrayList<DangKyDTO> listKhachHang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.khach_hang_fragment, container, false);
        rcv_khachHang = view.findViewById(R.id.rcv_khachHang);
        dangKyDAO = new DangKyDAO(getContext());
        listKhachHang = dangKyDAO.getList();
        khachHangAdapter = new KhachHangAdapter(getContext(), listKhachHang);

        rcv_khachHang.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_khachHang.setAdapter(khachHangAdapter);
        return view;
    }

}