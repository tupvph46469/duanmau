package com.example.duanmau.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.HoaDonDAO;
import com.example.duanmau.DTO.HoaDonDTO;
import com.example.duanmau.R;

import java.util.ArrayList;

public class HoaDonAdapter extends  RecyclerView.Adapter<HoaDonAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<HoaDonDTO> mList;

    public HoaDonAdapter(Context mContext, ArrayList<HoaDonDTO> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }
    public void updateData(ArrayList<HoaDonDTO> newData) {
        mList.clear();
        mList.addAll(newData);
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hoa_don, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HoaDonDTO hoaDonDTO = mList.get(position);

        holder.txtTenSP.setText("Tên sản phẩm: " + hoaDonDTO.getTenSP());
        holder.txtGiaTien.setText("Thành tiền: " + hoaDonDTO.getGiaTienMoi()); // Sử dụng giaTienMoi thay vì giaTien
        holder.txtSoLuong.setText("Số lượng: " + hoaDonDTO.getSoLuong());
        holder.txtKH.setText("Tên khách Hàng: " + hoaDonDTO.getTenDN());

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        if (isAdmin) {
            holder.img_delete.setVisibility(View.VISIBLE);
        } else {
            holder.img_delete.setVisibility(View.GONE);
        }

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Cảnh báo").setMessage("Bạn có muốn xóa sản phẩm này ?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HoaDonDAO dao = new HoaDonDAO(mContext);
                        int kq = dao.XoaHoaDon(hoaDonDTO);
                        if (kq > 0) {
                            mList.clear();
                            mList.addAll(dao.getList());
                            notifyDataSetChanged();
                            Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                        } else {
                            Toast.makeText(mContext, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtKH, txtTenSP, txtSoLuong,txtGiaTien;
        ImageView img_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSP = itemView.findViewById(R.id.txt_tenSpTT);
            txtGiaTien = itemView.findViewById(R.id.txt_giaTienTT);
            txtSoLuong = itemView.findViewById(R.id.txt_soLuongTT);
            txtKH = itemView.findViewById(R.id.txt_tenKhTT);
            img_delete = itemView.findViewById(R.id.img_xoa_TT);
        }
    }
}
