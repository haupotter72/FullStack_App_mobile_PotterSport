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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKyActivity extends AppCompatActivity {
    EditText hoten,username,email,pass,repass,sdt,diachi;
    TextView btndangnhap;
    AppCompatButton button;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        initView();
        initControl();

    }

    private void initControl() {
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKi();
            }
        });

    }

    private void dangKi() {
        String str_hoten = hoten.getText().toString().trim();
        String str_username = username.getText().toString().trim();
        String str_sdt = sdt.getText().toString().trim();
        String str_diachi = diachi.getText().toString().trim();
        String str_email = email.getText().toString().trim();
        String str_pass = pass.getText().toString().trim();
        String str_repass = repass.getText().toString().trim();


        if(TextUtils.isEmpty(str_hoten)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập Họ và Tên", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_email)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(str_sdt)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập Số Điện Thoại", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_diachi)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập Địa Chỉ", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_username)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập tên Tài Khoản", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_pass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập Mật Khẩu", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(str_repass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập lại Mật Khẩu", Toast.LENGTH_SHORT).show();
        }else {
            if(str_pass.equals((str_repass))){
                //post data
                compositeDisposable.add(apiBanHang.dangKi(str_hoten,str_email,str_sdt,str_diachi,str_username,str_pass)
                    .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if(userModel.isSuccess()){
                                        Utils.user_current.setTaiKhoan(str_username);
                                        Utils.user_current.setMatKhau(str_pass);
                                        Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(),userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },throwable -> {
                                    Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        )
                );
            }else {
                Toast.makeText(getApplicationContext(), "Mật Khẩu chưa giống nhau", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        hoten = findViewById(R.id.hoten);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        repass = findViewById(R.id.repass);
        sdt = findViewById(R.id.sdt);
        diachi = findViewById(R.id.diachi);
        button = findViewById(R.id.btndangki);
        btndangnhap = findViewById(R.id.txtdangnhap);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}