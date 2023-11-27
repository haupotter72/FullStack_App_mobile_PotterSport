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

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtdangki,txtresetpass;
    EditText username, password;
    AppCompatButton btndangnhap;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControl();

    }

    private void initControl() {
        txtdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DangKyActivity.class);
                startActivity(intent);
            }
        });

        txtresetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ResetPassActivity.class);
                startActivity(intent);
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_username = username.getText().toString().trim();
                String str_password = password.getText().toString().trim();

                if(TextUtils.isEmpty(str_username)){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập Username", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(str_password)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập Password", Toast.LENGTH_SHORT).show();
                }else{
                    //save
                    Paper.book().write("username",str_username);
                    Paper.book().write("password",str_password);
                    compositeDisposable.add(apiBanHang.dangNhap(str_username,str_password)
                        .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if(userModel.isSuccess()){
                                            Utils.user_current = userModel.getResult().get(0);
                                            // Luu thong tin nguoi dung
                                            Paper.book().write("user", userModel.getResult().get(0));
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    },throwable -> {

                                        Toast.makeText(getApplicationContext(),"Đăng nhập thất bại! Vui lòng kiểm tra lại.", Toast.LENGTH_SHORT).show();
                                    }
                            )
                    );
                }
            }
        });




    }

    private void initView() {
        Paper.init(this);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btndangnhap = findViewById(R.id.btndangnhap);
        txtdangki = findViewById(R.id.txtdangki);
        txtresetpass = findViewById(R.id.txtresetpass);

        //read data
        if(Paper.book().read("username") != null && Paper.book().read("password") != null){
            username.setText(Paper.book().read("username"));
            password.setText(Paper.book().read("password"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.user_current.getTaiKhoan() != null && Utils.user_current.getMatKhau() != null ){
            username.setText(Utils.user_current.getTaiKhoan());
            password.setText(Utils.user_current.getMatKhau());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();

        super.onDestroy();

    }
}