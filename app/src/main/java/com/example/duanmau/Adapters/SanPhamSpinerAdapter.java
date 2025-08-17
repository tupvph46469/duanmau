package com.example.duanmau.Adapters;
//package com.example.duanmau.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duanmau.DTO.SanPhamDTO;
import com.example.duanmau.R;

import java.util.ArrayList;

public class SanPhamSpinerAdapter extends ArrayAdapter<SanPhamDTO> {
    private Context context;
    private ArrayList<SanPhamDTO> list;
    TextView tvMaSP,tvTenSP;

    public SanPhamSpinerAdapter(@NonNull Context context,  ArrayList<SanPhamDTO> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.san_pham_item_spiner, parent, false);
        }

        SanPhamDTO item = getItem(position);
        if (item != null) {
            tvMaSP = convertView.findViewById(R.id.tv_maSP);
            tvMaSP.setText(item.getTenLoai() + ". ");

            tvTenSP = convertView.findViewById(R.id.tvTenSP);
            tvTenSP.setText(item.getTenSP());
        }

        return convertView;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}
