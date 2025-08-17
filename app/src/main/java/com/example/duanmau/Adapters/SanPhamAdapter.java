package com.example.duanmau.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.example.duanmau.DAO.GioHangDAO;
import com.example.duanmau.DAO.HoaDonDAO;
import com.example.duanmau.DAO.LoaiTraiCayDAO;
import com.example.duanmau.DAO.SanPhamDAO;
import com.example.duanmau.DTO.GioHangDTO;
import com.example.duanmau.DTO.HoaDonDTO;
import com.example.duanmau.DTO.LoaiTraiCayDTO;
import com.example.duanmau.DTO.SanPhamDTO;
import com.example.duanmau.Fragments.HoaDon_Frag;
import com.example.duanmau.R;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<SanPhamDTO> listSP;
    private ArrayList<HoaDonDTO> listHoaDon = new ArrayList<>();
    HoaDonDAO hoaDonDAO;




    public SanPhamAdapter(Context mContext, ArrayList<SanPhamDTO> listSP) {
        this.mContext = mContext;
        this.listSP = listSP;
    }

    public void updateData(ArrayList<SanPhamDTO> newData) {
        listSP.clear();
        listSP.addAll(newData);
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_san_pham, parent, false);
        hoaDonDAO = new HoaDonDAO(mContext);
        listHoaDon= hoaDonDAO.getList();
        return new ViewHolder(view);
    }

    // ... (phần mã trước đó)

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPhamDTO sanPhamDTO = listSP.get(position);
        holder.txt_tenSp.setText("Tên sản phẩm: " + sanPhamDTO.getTenSP());
        holder.txt_SoLuong.setText("Số lượng: " + sanPhamDTO.getSoLuong());
        holder.txt_giaTien.setText("Giá tiền: " + sanPhamDTO.getGiaTien());
        holder.txt_moTa.setText("Mô tả: " + sanPhamDTO.getMoTa());

        if (holder.txt_maLoai != null) {
            holder.txt_maLoai.setText("Loại trái cây: " + sanPhamDTO.getTenLoai());
        } else {
            holder.txt_maLoai.setText("Loại trái cây: Nho");
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        if (isAdmin) {
            holder.img_update.setVisibility(View.VISIBLE);  // Hiển thị img_sua
            holder.img_delete.setVisibility(View.VISIBLE);  // Hiển thị img_xoa
            holder.img_them.setVisibility(View.GONE);
            holder.btn_mua.setVisibility(View.GONE);
        } else {
            holder.img_update.setVisibility(View.GONE);     // Ẩn img_sua
            holder.img_delete.setVisibility(View.GONE);
            holder.img_them.setVisibility(View.VISIBLE);
            holder.btn_mua.setVisibility(View.VISIBLE);
        }


        holder.img_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_san_pham);

                TextInputEditText edt_tenSP = dialog.findViewById(R.id.edt_ten_sanPham);
                TextInputEditText edt_soLuong = dialog.findViewById(R.id.edt_soLuong);
                TextInputEditText edt_giaTien = dialog.findViewById(R.id.edt_giaTien);
                TextInputEditText edt_moTa = dialog.findViewById(R.id.edt_moTa);
                Spinner sp_sp = dialog.findViewById(R.id.spn_sanPham);
                Button btn_add = dialog.findViewById(R.id.btn_add_sanPham);
                Button btn_huy = dialog.findViewById(R.id.btn_huy_sanPham);

                // Lấy danh sách loại sản phẩm và cập nhật Adapter cho Spinner
                ArrayList<LoaiTraiCayDTO> listLoaiSP = new LoaiTraiCayDAO(mContext).getList();
                ArrayAdapter<LoaiTraiCayDTO> loaiSpAdapter = new ArrayAdapter<LoaiTraiCayDTO>(mContext, android.R.layout.simple_spinner_item, listLoaiSP) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setText(listLoaiSP.get(position).getTenLoai());
                        return textView;
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                        textView.setText(listLoaiSP.get(position).getTenLoai());
                        return textView;
                    }
                };
                sp_sp.setAdapter(loaiSpAdapter);

                // Điền thông tin sản phẩm vào các trường
                edt_tenSP.setText(sanPhamDTO.getTenSP());
                edt_soLuong.setText(sanPhamDTO.getSoLuong());
                edt_giaTien.setText(sanPhamDTO.getGiaTien());
                edt_moTa.setText(sanPhamDTO.getMoTa());

                btn_add.setText("Cập nhật");

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenSP = edt_tenSP.getText().toString().trim();
                        String soLuong = edt_soLuong.getText().toString().trim();
                        String giaTien = edt_giaTien.getText().toString().trim();
                        String moTa = edt_moTa.getText().toString().trim();
                        LoaiTraiCayDTO selectedLoaiSP = (LoaiTraiCayDTO) sp_sp.getSelectedItem();

                        if (selectedLoaiSP == null) {
                            Toast.makeText(mContext, "Vui lòng chọn loại sản phẩm", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (tenSP.isEmpty() || soLuong.isEmpty() || giaTien.isEmpty()) {
                            Toast.makeText(mContext, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Cập nhật thông tin sản phẩm trong cơ sở dữ liệu
                        SanPhamDTO updatedSanPhamDTO = new SanPhamDTO(sanPhamDTO.getId(), tenSP, soLuong, giaTien, moTa, selectedLoaiSP.getTenLoai());
                        long isSuccess = new SanPhamDAO(mContext).SuaSanPham(updatedSanPhamDTO);

                        if (isSuccess != -1) {
                            Toast.makeText(mContext, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                            // Cập nhật danh sách sản phẩm
                            listSP.clear();
                            listSP.addAll(new SanPhamDAO(mContext).getList());
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(mContext, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
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
        });


        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Cảnh báo").setMessage("Bạn có muốn xóa ?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SanPhamDAO dao = new SanPhamDAO(mContext);
                        int kq = dao.XoaSanPham(sanPhamDTO);
                        if (kq > 0) {
                            listSP.clear();
                            listSP.addAll(dao.getList());
                            notifyDataSetChanged();
                            Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else
                            Toast.makeText(mContext, "Xóa thất bại", Toast.LENGTH_SHORT).show();
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


        holder.img_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_mua_sp);
                TextInputEditText edt_soLuong = dialog.findViewById(R.id.edt_soLuong_gioHang);
                Button btn_them = dialog.findViewById(R.id.btn_them_gio_hang);


                btn_them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String soLuong = edt_soLuong.getText().toString().trim();

                        // Kiểm tra soLuong có phải là số hay không
                        try {
                            int soLuongInt = Integer.parseInt(soLuong);

                            // Kiểm tra soLuongInt có lớn hơn 0 hay không
                            if (soLuongInt <= 0) {
                                Toast.makeText(mContext, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                                return; // Dừng lại nếu soLuongInt không lớn hơn 0
                            }

                        } catch (NumberFormatException e) {
                            Toast.makeText(mContext, "Số lượng phải là số", Toast.LENGTH_SHORT).show();
                            return; // Dừng lại nếu soLuong không phải là số
                        }




                        int position = holder.getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            int idSanPham = listSP.get(position).getId();

                            SanPhamDTO sanPhamDTO = new SanPhamDAO(mContext).getSanPhamById(idSanPham);

                            if (sanPhamDTO != null) {
                                int giaTien = Integer.parseInt(sanPhamDTO.getGiaTien());
                                int soLuongInt = Integer.parseInt(soLuong);
                                int giaTienMoi = giaTien * soLuongInt;

                                GioHangDTO gioHangDTO = new GioHangDTO();
                                gioHangDTO.setTenSP(sanPhamDTO.getTenSP());
                                gioHangDTO.setGiaTien(String.valueOf(giaTien));
                                gioHangDTO.setSoLuongGioHang(soLuong);
                                gioHangDTO.setGiaTienMoi(String.valueOf(giaTienMoi));

                                long result = new GioHangDAO(mContext).ThemGioHang(gioHangDTO);

                                if (result > 0) {
                                    Toast.makeText(mContext, "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, "Thêm sản phẩm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                            }
                        }

                        dialog.dismiss();
                    }
                });



                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });

        holder.btn_mua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_mua_sp);

                TextInputEditText edt_soLuong = dialog.findViewById(R.id.edt_soLuong_gioHang);
                Button btnThanhToan = dialog.findViewById(R.id.btn_them_gio_hang);
                btnThanhToan.setText("Thanh toán");

                btnThanhToan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String soLuong = edt_soLuong.getText().toString().trim();

                        // Kiểm tra soLuong có phải là số hay không
                        int soLuongInt;
                        try {
                            soLuongInt = Integer.parseInt(soLuong);
                        } catch (NumberFormatException e) {
                            Toast.makeText(mContext, "Số lượng phải là số", Toast.LENGTH_SHORT).show();
                            return; // Dừng lại nếu soLuong không phải là số
                        }

                        // Kiểm tra soLuongInt có lớn hơn 0 hay không
                        if (soLuongInt <= 0) {
                            Toast.makeText(mContext, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                            return; // Dừng lại nếu soLuongInt không lớn hơn 0
                        }

                        int position = holder.getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            int idSanPham = listSP.get(position).getId();

                            SanPhamDTO sanPhamDTO = new SanPhamDAO(mContext).getSanPhamById(idSanPham);

                            if (sanPhamDTO != null) {
                                int giaTien = Integer.parseInt(sanPhamDTO.getGiaTien());
                                int giaTienMoi = giaTien * soLuongInt;

                                GioHangDTO gioHangDTO = new GioHangDTO();
                                gioHangDTO.setTenSP(sanPhamDTO.getTenSP());
                                gioHangDTO.setGiaTien(String.valueOf(giaTien));
                                gioHangDTO.setSoLuongGioHang(soLuong);
                                gioHangDTO.setGiaTienMoi(String.valueOf(giaTienMoi));

                                HoaDonDTO hoaDonDTO = new HoaDonDTO();
                                hoaDonDTO.setTenDN(sharedPreferences.getString("tenDN", ""));
                                hoaDonDTO.setTenSP(sanPhamDTO.getTenSP());
                                hoaDonDTO.setSoLuong(soLuong);
                                hoaDonDTO.setGiaTienMoi(String.valueOf(giaTienMoi));

                                long kq = hoaDonDAO.themHoaDon(hoaDonDTO);

                                if (kq > 0) {
                                    Toast.makeText(mContext, "Thanh toán thành công", Toast.LENGTH_SHORT).show();

                                    FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
                                    HoaDon_Frag fragment_hoaDon = new HoaDon_Frag();
                                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                                    transaction.replace(R.id.frag_container001, fragment_hoaDon);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                } else {
                                    Toast.makeText(mContext, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                            }
                        }

                        dialog.dismiss();
                    }
                });


                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });





    }


    @Override
    public int getItemCount() {
        return listSP.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_tenSp, txt_SoLuong, txt_giaTien, txt_moTa, txt_maLoai;
        ImageView img_update, img_delete, img_them;
        Button btn_mua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_tenSp = itemView.findViewById(R.id.txt_tenSP);
            txt_SoLuong = itemView.findViewById(R.id.txt_soLuong);
            txt_giaTien = itemView.findViewById(R.id.txt_giaTien);
            txt_moTa = itemView.findViewById(R.id.txt_moTa);
            txt_maLoai = itemView.findViewById(R.id.txt_Loai);
            img_update = itemView.findViewById(R.id.img_sua_sanPham);
            img_delete = itemView.findViewById(R.id.img_xoa_sanPham);
            img_them = itemView.findViewById(R.id.img_them_gioHang);
            btn_mua = itemView.findViewById(R.id.btn_mua);
        }
    }
}
