package com.example.meta.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.meta.R;
import com.example.meta.adapter.ChiTietDonHangAdapter;
import com.example.meta.model.SanPhamMoi;
import com.example.meta.retrofit.ApiBanHang;
import com.example.meta.retrofit.RetrofitClient;
import com.example.meta.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderCompleteActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnbackhome,btndeletedonhang;
    RecyclerView listdonhangdadat;
    ApiBanHang apiBanHang;
    ChiTietDonHangAdapter chiTietDonHangAdapter;
    List<SanPhamMoi> sanPhamMois;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    final int MaND = Utils.user_current.getMaND();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);

        initView();
        initData();
        ActionToolBar();
        deletedonHnag();
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
    private void initData() {
        compositeDisposable.add(apiBanHang.listdonhangdd(MaND)
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                sanPhamMois = sanPhamMoiModel.getResult();
                                chiTietDonHangAdapter = new ChiTietDonHangAdapter(getApplicationContext(),sanPhamMois);
                                listdonhangdadat.setAdapter(chiTietDonHangAdapter);
                            }
                        },throwable -> {
                            Toast.makeText(this, "Ok!", Toast.LENGTH_SHORT).show();
                        }
                )

        );

        btnbackhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        sanPhamMois = new ArrayList<>();
        toolbar = findViewById(R.id.toobar);
        btnbackhome = findViewById(R.id.btnbackhome);
        btndeletedonhang = findViewById(R.id.btndeletedonhang);
        listdonhangdadat = findViewById(R.id.listdonhangdadat);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listdonhangdadat.setLayoutManager(layoutManager);
    }

    private void deletedonHnag(){
        btndeletedonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickbtndeletehd();
                Intent intent = new Intent(getApplicationContext(), OrderCompleteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void clickbtndeletehd(){
        compositeDisposable.add(apiBanHang.deletedonhangs(MaND)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                Toast.makeText(OrderCompleteActivity.this, "Thành Công", Toast.LENGTH_SHORT).show();
                            }
                        },throwable -> {
                            Toast.makeText(OrderCompleteActivity.this, "Thành Công", Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

}