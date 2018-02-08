package com.example.userlib;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.annatationlib.RouterTarget;
import com.example.commonlib.ActionConstant;
import com.example.routerlib.BaseActivity;

/**
 * Created by lishaojie on 2018/2/7.
 */
@RouterTarget(ActionConstant.ACTION_USER_INFO)
public class UserInfoActivity  extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        String userName = getIntent().getStringExtra(ActionConstant.KEY_USER_NAME);
        String userPassword = getIntent().getStringExtra(ActionConstant.KEY_PASS_WORD);
        TextView tvUserName = findViewById(R.id.user_name);
        TextView tvPassword = findViewById(R.id.user_password);
        tvUserName.setText(userName);
        tvPassword.setText(userPassword);
    }
}
