package com.barebone.app;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.barebone.app.binders.UiManager;
import com.barebone.app.core.BaseActivity;
import com.barebone.app.databinding.ActivityLoginBinding;
import com.barebone.app.viewmodels.LoginViewModel;

public class LoginActivity extends BaseActivity {

    private UiManager uiManager;
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Initializing data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        uiManager = new UiManager();
        binding.setUimanager(uiManager);

        //Initializing viewmodel
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        initAction();

    }

    private void initAction(){
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NavBaseActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
