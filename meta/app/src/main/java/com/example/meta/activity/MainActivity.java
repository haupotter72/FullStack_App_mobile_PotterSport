package com.example.meta.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.meta.R;
import com.example.meta.adapter.LoaiSpAdapter;
import com.example.meta.adapter.SanPhamMoiAdapter;
import com.example.meta.model.LoaiSp;
import com.example.meta.model.SanPhamMoi;
import com.example.meta.model.SanPhamMoiModel;
import com.example.meta.model.User;
import com.example.meta.retrofit.ApiBanHang;
import com.example.meta.retrofit.RetrofitClient;
import com.example.meta.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listviewmanhinhchinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SanPhamMoi> listSPMoi;
    SanPhamMoiAdapter spAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout,frameaccount;
    ImageView imgsearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        if(Paper.book().read("user") != null ){
            User user = Paper.book().read("user");
            Utils.user_current = user;

        }
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Anhxa();
        ActionBar();
//        ActionViewFlipper();
        if (isConnected(this)) {
            ActionViewFlipper();
            getLoaiSanPham();
            getSpMoi();
            getEventClick();
        } else {
            Toast.makeText(getApplicationContext(), "Không có internet", Toast.LENGTH_LONG).show();
        }
    }

    private void getEventClick() {
           listviewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   switch (i) {
                        case 0:
                            Intent nike = new Intent(getApplicationContext(),DanhMucActivity.class);
                            nike.putExtra("MaDM",1);
                            nike.putExtra("title", "NIKE");
                            startActivity(nike);
                            break;

                        case 1:
                            Intent adidas = new Intent(getApplicationContext(),DanhMucActivity.class);
                            adidas.putExtra("MaDM",2);
                            adidas.putExtra("title", "ADIDAS");
                            startActivity(adidas);
                            break;
                        case 2:
                            Intent vans = new Intent(getApplicationContext(),DanhMucActivity.class);
                            vans.putExtra("MaDM",3);
                            vans.putExtra("title", "VANS");
                            startActivity(vans);
                            break;
                        case 3:
                           Intent puma = new Intent(getApplicationContext(),DanhMucActivity.class);
                            puma.putExtra("MaDM",4);
                            puma.putExtra("title", "PUMA");
                           startActivity(puma);
                           break;
                       case 4:
                           Intent bitis = new Intent(getApplicationContext(),DanhMucActivity.class);
                           bitis.putExtra("MaDM",5);
                           bitis.putExtra("title", "BITI'S");
                           startActivity(bitis);
                           break;
                       case 5:
                           Intent dep = new Intent(getApplicationContext(),DanhMucActivity.class);
                           dep.putExtra("MaDM",6);
                           dep.putExtra("title", "DÉP");
                           startActivity(dep);
                           break;
                       case 6:
                           Intent order = new Intent(getApplicationContext(),OrderCompleteActivity.class);
                           order.putExtra("MaDM",7);
                           order.putExtra("title", "TTDH");
                           startActivity(order);
                           break;
                       case 7:
                           Intent logout = new Intent(getApplicationContext(),DangNhapActivity.class);
                           logout.putExtra("MaDM",8);
                           logout.putExtra("title", "TTDH");
                           startActivity(logout);
                           break;
                        default:
                            // xoa cay user
                            Paper.book().delete("user");
                            Intent dangnhap = new Intent(getApplicationContext(), SplashActivity.class);
                            startActivity(dangnhap);
                            break;
                   }
               }
           });

    }
//
    private void getSpMoi() {
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()){
                                listSPMoi = sanPhamMoiModel.getResult();
                                spAdapter = new SanPhamMoiAdapter(getApplicationContext(),listSPMoi);
                                recyclerViewManHinhChinh.setAdapter(spAdapter);
                            }
                        },throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được với sever"+throwable.getMessage(),Toast.LENGTH_LONG).show();
                        }
                )
        );
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess()) {
                                mangloaisp = loaiSpModel.getResult();
                                //khoitao adapter
                                loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), mangloaisp);
                                listviewmanhinhchinh.setAdapter(loaiSpAdapter);
                            }
                        },
                        throwable -> {
                            Log.d("test", throwable.getMessage());
                        }
                ));

    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("http://192.168.1.7/n8.com/public/images/sliders/adidas1.jpg");
        mangquangcao.add("http://192.168.1.7/n8.com/public/images/sliders/adidas2.jpg");
        mangquangcao.add("http://192.168.1.7/n8.com/public/images/sliders/nike1.png");
        mangquangcao.add("http://192.168.1.7/n8.com/public/images/sliders/newbalance.png");
        mangquangcao.add("http://192.168.1.7/n8.com/public/images/sliders/nike3.png");
        mangquangcao.add("http://192.168.1.7/n8.com/public/images/sliders/puma3.jpg");
        mangquangcao.add("http://192.168.1.7/n8.com/public/images/sliders/puma2.png");
        mangquangcao.add("http://192.168.1.7/n8.com/public/images/sliders/puma4.png");
        mangquangcao.add("http://192.168.1.7/n8.com/public/images/sliders/vans1.jpg");
        mangquangcao.add("http://192.168.1.7/n8.com/public/images/sliders/vans2.jpg");
        mangquangcao.add("http://192.168.1.7/n8.com/public/images/sliders/vans3.png");
        mangquangcao.add("http://192.168.1.7/n8.com/public/images/sliders/all.png");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }


    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    private void Anhxa() {
        imgsearch = findViewById(R.id.imgsearch);
        toolbar = findViewById(R.id.toobarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        listviewmanhinhchinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        frameaccount = findViewById(R.id.frameaccount);
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);
        //khoi tao list
        mangloaisp = new ArrayList<>();
        listSPMoi = new ArrayList<>();
        if(Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else{
            int tatolItem = 0;
            for (int i = 0; i < Utils.manggiohang.size() ; i++){
                tatolItem = tatolItem + Utils.manggiohang.get(i).getSoLuong();

            }
            badge.setText(String.valueOf(tatolItem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        });
        frameaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent auser = new Intent(getApplicationContext(),UserAccountActivity.class);
                startActivity(auser);
            }
        });

        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        int tatolItem = 0;
        for (int i = 0; i < Utils.manggiohang.size() ; i++){
            tatolItem = tatolItem + Utils.manggiohang.get(i).getSoLuong();

        }
        badge.setText(String.valueOf(tatolItem));
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;

        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}