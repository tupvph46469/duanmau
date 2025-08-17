package com.example.duanmau.Adapters;

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
import com.example.duanmau.DAO.DangKyDAO;
import com.example.duanmau.DTO.DangKyDTO;
import com.example.duanmau.R;

import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder> {

    private Context mContext;
    private List<DangKyDTO> mList;

    public KhachHangAdapter(Context context, List<DangKyDTO> list) {
        mContext = context;
        mList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_khach_hang, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DangKyDTO dangKyDTO = mList.get(position);
        holder.txtTenDN.setText("Tên đăng nhập: " + dangKyDTO.getTenDN());
        holder.txtGmail.setText("Gmail: " + dangKyDTO.getGmail());

        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Cảnh báo").setMessage("Bạn có muốn xóa?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DangKyDAO dao = new DangKyDAO(mContext);
                        int kq = dao.XoaKhachHang(dangKyDTO);
                        if (kq > 0) {
                            mList.clear();
                            mList.addAll(dao.getList());
                            notifyDataSetChanged();
                            Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
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

        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_khach_hang);
                TextInputEditText edtTenTK = dialog.findViewById(R.id.edt_khachhang);
                TextInputEditText edtGmail = dialog.findViewById(R.id.edt_gmail);
                Button btnOk = dialog.findViewById(R.id.btn_add_khachhang);
                Button btnHuy = dialog.findViewById(R.id.btn_huy_khachhang);

                edtTenTK.setText(dangKyDTO.getTenDN());
                edtGmail.setText(dangKyDTO.getGmail());
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String TenDN = edtTenTK.getText().toString();
                        String Gmail = edtGmail.getText().toString();
                        if (TenDN.isEmpty() || Gmail.isEmpty()){
                            Toast.makeText(mContext,"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dangKyDTO.setTenDN(TenDN);
                            dangKyDTO.setGmail(Gmail);
                            DangKyDAO dao = new DangKyDAO(mContext);
                            int kq = dao.update(dangKyDTO);
                            if( kq > 0){
                                mList.clear();
                                mList.addAll(dao.getList());
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
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenDN, txtGmail;
        ImageView imgSua, imgXoa;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenDN = itemView.findViewById(R.id.txt_ten_dn);
            txtGmail = itemView.findViewById(R.id.txt_Gmail); // Ánh xạ TextView cho Gmail
            imgSua = itemView.findViewById(R.id.img_sua_khachHang);
            imgXoa = itemView.findViewById(R.id.img_xoa_khachHang);
        }
    }


//trl line
}
