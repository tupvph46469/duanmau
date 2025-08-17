package com.example.duanmau.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.example.duanmau.DAO.LoaiTraiCayDAO;
import com.example.duanmau.DTO.LoaiTraiCayDTO;
import com.example.duanmau.R;

import java.util.ArrayList;

public class LoaiTraiCayAdapter extends  RecyclerView.Adapter<LoaiTraiCayAdapter.ViewHolder> {
    private Context mContext;
    ArrayList<LoaiTraiCayDTO> listTraiCay;

    public LoaiTraiCayAdapter(Context mContext, ArrayList<LoaiTraiCayDTO> listTraiCay) {
        this.mContext = mContext;
        this.listTraiCay = listTraiCay;
    }

    public void updateData(ArrayList<LoaiTraiCayDTO> newData) {
        listTraiCay.clear();
        listTraiCay.addAll(newData);
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loai_trai_cay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiTraiCayDTO loaiTraiCayDTO = listTraiCay.get(position);
        holder.txt_loaiTraiCay.setText("Loại trái cây: "+loaiTraiCayDTO.getTenLoai());

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Cảnh báo").setMessage("Bạn có muốn xóa ?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoaiTraiCayDAO dao = new LoaiTraiCayDAO(mContext);
                        int kq = dao.XoaLoaiTraiCay(loaiTraiCayDTO);
                        if( kq > 0){
                            listTraiCay.clear();
                            listTraiCay.addAll(dao.getList());
                            notifyDataSetChanged();
                            Toast.makeText(mContext,"Xóa thành công",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else
                            Toast.makeText(mContext,"Xóa thất bại",Toast.LENGTH_SHORT).show();
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

        holder.img_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_loai_trai_cay);
                TextInputEditText edtTraiCay = dialog.findViewById(R.id.edt_loaiTraiCay);
                Button btnOk = dialog.findViewById(R.id.btn_add_loaiTraiCay);
                Button btnHuy = dialog.findViewById(R.id.btn_huy_loaiTraiCay);

                edtTraiCay.setText(loaiTraiCayDTO.getTenLoai());
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenLoai = edtTraiCay.getText().toString();
                        if (tenLoai.isEmpty()){
                            Toast.makeText(mContext,"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loaiTraiCayDTO.setTenLoai(tenLoai);
                            LoaiTraiCayDAO dao = new LoaiTraiCayDAO(mContext);
                            int kq = dao.SuaLoaiTraiCay(loaiTraiCayDTO);
                            if( kq > 0){
                                listTraiCay.clear();
                                listTraiCay.addAll(dao.getList());
                                notifyDataSetChanged();
                                Toast.makeText(mContext,"Sửa thành công",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else
                                Toast.makeText(mContext,"Sửa thất bại",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listTraiCay.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_loaiTraiCay;
        ImageView img_update, img_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_loaiTraiCay = itemView.findViewById(R.id.txt_loaiTraiCay);
            img_update = itemView.findViewById(R.id.img_sua_loaiTraiCay);
            img_delete = itemView.findViewById(R.id.img_xoa_loaiTraiCay);
        }
    }
}
