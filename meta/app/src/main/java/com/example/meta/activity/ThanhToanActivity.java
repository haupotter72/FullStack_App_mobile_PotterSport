package com.example.meta.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meta.R;
import com.example.meta.retrofit.ApiBanHang;
import com.example.meta.retrofit.RetrofitClient;
import com.example.meta.utils.Utils;
import com.google.gson.Gson;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    EditText username,userPhone,userAddress, userNote;
    AppCompatButton button;
    ApiBanHang apiBanHang;
    TextView userTongTien;

    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        initView();

        initControl();

    }


    private void initControl() {
        userTongTien.setText(getIntent().getLongExtra("tongtien",0)+"" +"đ ");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xacnhanthanhtoan();
                Intent intent = new Intent(getApplicationContext(), OrderCompleteActivity.class);
                startActivity(intent);
                Utils.manggiohang.removeAll(Utils.manggiohang);
            }
        });

    }

    private void xacnhanthanhtoan() {
        int MaND = Utils.user_current.getMaND();
        int str_TongTien = Integer.parseInt(getIntent().getLongExtra("tongtien",0)+"");
        String str_username = username.getText().toString().trim();
        String str_userPhone = userPhone.getText().toString().trim();
        String str_userAddress = userAddress.getText().toString().trim();
        String str_userNote = userNote.getText().toString().trim();

        if(TextUtils.isEmpty(str_username)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập tên người nhận", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_userPhone)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_userAddress)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
        }else {

            //post data
            compositeDisposable.add(apiBanHang.checkout(MaND,str_username,str_userPhone,str_userAddress,str_userNote,str_TongTien, new Gson().toJson(Utils.manggiohang))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if(userModel.isSuccess()){

                                    Toast.makeText(getApplicationContext(),"Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },throwable -> {
                                Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    )
            );
        }
    }


    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        username = findViewById(R.id.txttennguoinhan);
        userPhone = findViewById(R.id.userPhone);
        userAddress = findViewById(R.id.userAddress);
        userNote = findViewById(R.id.userNote);
        button = findViewById(R.id.btncheckout);
        userTongTien = findViewById(R.id.userTongTien);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}