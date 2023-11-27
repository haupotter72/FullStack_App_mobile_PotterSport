package com.example.meta.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meta.R;
import com.example.meta.adapter.SanPhamMoiAdapter;
import com.example.meta.model.GioHang;
import com.example.meta.model.SanPhamMoi;
import com.example.meta.retrofit.ApiBanHang;
import com.example.meta.retrofit.RetrofitClient;
import com.example.meta.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietActivity extends AppCompatActivity {
    TextView TenSP, DonGia, MoTa1;
    Button btnthem;
    ImageView HinhAnh, HinhAnh1,HinhAnh2;
    Spinner spinner,spinner1;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;
    List<SanPhamMoi> listSPMoi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    SanPhamMoiAdapter sanPhamMoiAdapter;
    RecyclerView listtuongduong;
    int MaDM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if (Utils.manggiohang.size() > 0){
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            int size = Integer.parseInt(spinner1.getSelectedItem().toString());
            for (int i = 0 ; i < Utils.manggiohang.size(); i++) {
                if (Utils.manggiohang.get(i).getMaSP() == sanPhamMoi.getMaSP()) {
                    Utils.manggiohang.get(i).setSoLuong(soluong + Utils.manggiohang.get(i).getSoLuong());
                    long gia = Long.parseLong(sanPhamMoi.getDonGia()) * Utils.manggiohang.get(i).getSoLuong();

                    flag =true;
                }
            }

            if (flag == false){
                long gia = Long.parseLong(sanPhamMoi.getDonGia()) ;
                GioHang gioHang = new GioHang();
                gioHang.setDonGia(gia);
                gioHang.setSoLuong(soluong);
                gioHang.setSize(size);
                gioHang.setMaSP(sanPhamMoi.getMaSP());
                gioHang.setTenSP(sanPhamMoi.getTenSP());
                gioHang.setHinhAnh(sanPhamMoi.getHinhAnh1());
                Utils.manggiohang.add(gioHang);
            }
        }else {
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            int size = Integer.parseInt(spinner1.getSelectedItem().toString());
            long gia = Long.parseLong(sanPhamMoi.getDonGia());
            GioHang gioHang = new GioHang();
            gioHang.setDonGia(gia);
            gioHang.setSoLuong(soluong);
            gioHang.setSize(size);
            gioHang.setMaSP(sanPhamMoi.getMaSP());
            gioHang.setTenSP(sanPhamMoi.getTenSP());
            gioHang.setHinhAnh(sanPhamMoi.getHinhAnh1());
            Utils.manggiohang.add(gioHang);
        }
        int tatolItem = 0;
        for (int i = 0; i < Utils.manggiohang.size() ; i++){
            tatolItem = tatolItem + Utils.manggiohang.get(i).getSoLuong();

        }
        badge.setText(String.valueOf(tatolItem));

    }

    private void initData() {
         sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
         MaDM = sanPhamMoi.getMaDM();
        TenSP.setText(sanPhamMoi.getTenSP());
        DonGia.setText(sanPhamMoi.getDonGia() + "đ");
        MoTa1.setText(
                "Tên sản phẩm: "+ sanPhamMoi.getTenSP() +"\n" +
                ""+ sanPhamMoi.getKieuDang() +"\n" +
                "Danh muc: "+ sanPhamMoi.getTenLSP() +"\n" +
                "Thương hiệu: "+ sanPhamMoi.getTenDM() +"\n"+
                 "Ngày phát hành: "+ sanPhamMoi.getNgayPH() +"\n"

        );

        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhAnh1()).into(HinhAnh);
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhAnh2()).into(HinhAnh1);
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhAnh3()).into(HinhAnh2);
        Integer[] sl = new Integer[]{1,2,3,4,5,6,7,8,9,10};
         Integer[] size = new Integer[]{36,37,38,39,40,41,42,43};
        ArrayAdapter<Integer> adaptersprinsl = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,sl);
        spinner.setAdapter(adaptersprinsl);
        ArrayAdapter<Integer> adaptersprinsize= new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,size);
        spinner1.setAdapter(adaptersprinsize);



        compositeDisposable.add(apiBanHang.getSanPham(MaDM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                listSPMoi = sanPhamMoiModel.getResult();
                                sanPhamMoiAdapter = new SanPhamMoiAdapter(getApplicationContext(),listSPMoi);
                                listtuongduong.setAdapter(sanPhamMoiAdapter);
                            }
                        }
                )
        );

    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        listSPMoi = new ArrayList<>();
        TenSP = findViewById(R.id.txttensp);
        DonGia = findViewById(R.id.txtgiasp);
        MoTa1 = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogiohang);
        spinner = findViewById(R.id.spinnerSL);
        spinner1 = findViewById(R.id.spinnerSize);
        HinhAnh = findViewById(R.id.imgchitiet);
        HinhAnh1 = findViewById(R.id.cthinhanh2);
        HinhAnh2 = findViewById(R.id.cthinhanh3);
        toolbar = findViewById(R.id.toobar);
        badge = findViewById(R.id.menu_sl);
        listtuongduong = findViewById(R.id.listtuongduong);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        listtuongduong.setLayoutManager(layoutManager);
        listtuongduong.setHasFixedSize(true);

        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);

        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });

        if (Utils.manggiohang != null){
            int tatolItem = 0;
            for (int i = 0; i < Utils.manggiohang.size() ; i++){
                tatolItem = tatolItem + Utils.manggiohang.get(i).getSoLuong();

            }

            badge.setText(String.valueOf(tatolItem));
        }
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null){
            int tatolItem = 0;
            for (int i = 0; i < Utils.manggiohang.size() ; i++){
                tatolItem = tatolItem + Utils.manggiohang.get(i).getSoLuong();

            }

            badge.setText(String.valueOf(tatolItem));
        }
    }

}