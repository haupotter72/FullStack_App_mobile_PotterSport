package com.example.meta.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.TextView;
import com.example.meta.utils.Utils;
import android.content.Intent;
import com.example.meta.R;

public class UserAccountActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnbackhomev1;
    TextView usertendangnhap, userFullName,useraccphone,useraccemail,useraccaddress;
    LinearLayout editpass,edittenuser,edittentaikhoan,editsodienthoai,editemail,edittaddressuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
            initView();
          ActionToolBar();
            getData();
        }

    private void getData() {
        userFullName.setText(Utils.user_current.getHo() + Utils.user_current.getTen());
        useraccaddress.setText(Utils.user_current.getDiaChi());
        usertendangnhap.setText(Utils.user_current.getTaiKhoan());
        useraccemail.setText(Utils.user_current.getEmail());
        useraccphone.setText(Utils.user_current.getSDT());
    }

    private void initView() {
        toolbar = findViewById(R.id.toobar);
        userFullName = findViewById(R.id.userFullName);
        usertendangnhap = findViewById(R.id.usertendangnhap);
        useraccaddress = findViewById(R.id.useraccaddress);
        useraccphone = findViewById(R.id.useraccphone);
        useraccemail = findViewById(R.id.useraccemail);
        editpass = findViewById(R.id.editpass);
        edittenuser = findViewById(R.id.edittenuser);
        edittentaikhoan = findViewById(R.id.edittentaikhoan);
        editsodienthoai = findViewById(R.id.editsodienthoai);
        editemail = findViewById(R.id.editemail);
        edittaddressuser = findViewById(R.id.edittaddressuser);
        btnbackhomev1 = findViewById(R.id.btnbackhomev1);

        btnbackhomev1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
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

}