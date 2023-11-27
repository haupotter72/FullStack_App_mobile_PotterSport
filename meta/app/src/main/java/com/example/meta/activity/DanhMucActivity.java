package com.example.meta.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.meta.R;
import com.example.meta.adapter.DanhMucAdapter;
import com.example.meta.model.SanPhamMoi;

import com.example.meta.model.SanPhamMoiModel;
import com.example.meta.retrofit.ApiBanHang;
import com.example.meta.retrofit.RetrofitClient;
import com.example.meta.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DanhMucActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int MaDM;
    String title= "";
    DanhMucAdapter adapterDM;
    List<SanPhamMoi> sanPhamMoiList;
    LinearLayoutManager linearLayoutManager;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_giay);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        MaDM = getIntent().getIntExtra("MaDM", 1);
        title = getIntent().getStringExtra("title");
        AnhXa();
       ActionToolBar();
        getData();
    }

    private void getData() {
        compositeDisposable.add(apiBanHang.getSanPham(MaDM)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                   sanPhamMoiModel -> {
                           if(sanPhamMoiModel.isSuccess()){
                               sanPhamMoiList = sanPhamMoiModel.getResult();
                               adapterDM = new DanhMucAdapter(getApplicationContext(),sanPhamMoiList);
                               recyclerView.setAdapter(adapterDM);
                           }
                       },
                    throwable -> {
                           Toast.makeText(getApplicationContext(),"Không kết nối sever",Toast.LENGTH_LONG).show();
                   }
        ));
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


        getSupportActionBar().setTitle(title);

    }


    private void AnhXa() {
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recycleview_dmg);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}