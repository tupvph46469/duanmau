package com.example.duanmau.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmau.DAO.DoanhThuDAO;
import com.example.duanmau.R;

public class DoanhThu_Frag extends Fragment {

    private TextView doanhThuTextView;
    private DoanhThuDAO doanhThuDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doanh_thu_fragment, container, false);
        doanhThuTextView = view.findViewById(R.id.doanhThuTextView);
        doanhThuDAO = new DoanhThuDAO(getActivity());

        double tongDoanhThu = doanhThuDAO.tinhTongDoanhThu();
        doanhThuTextView.setText("Tổng doanh thu: " + tongDoanhThu + " VNĐ");

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
