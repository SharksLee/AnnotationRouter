package com.example.loginlib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.example.commonlib.ActionConstant;
import com.example.commonlib.RouterByAnnotationManager;
import com.example.routerlib.BaseActivity;
import com.example.routerlib.Router;

/**
 * Created by lishaojie on 2018/2/7.
 */

public class LoginActivity extends BaseActivity {
    EditText etUserName, etPassWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserName = findViewById(R.id.user_name);
        etPassWord = findViewById(R.id.user_password);
    }

    public void login(View v) {
        new Router.Builder(this, RouterByAnnotationManager.getInstance().getRouter(ActionConstant.ACTION_USER_INFO)).
                addParams(ActionConstant.KEY_USER_NAME, etUserName.getText().toString())
                .addParams(ActionConstant.KEY_PASS_WORD, etPassWord.getText().toString()).go();
    }
}
