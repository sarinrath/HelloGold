package com.hellogold;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.hellogold.adapter.SpotPriceAdapter;
import com.hellogold.model.SpotPrice;
import com.hellogold.service.ApiInterface;
import com.hellogold.service.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SARINRATH on 30/01/2019 AD.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEdtEmail;
    private EditText mEdtUuid;
    private EditText mEdtData;
    private CheckBox mCkTnc;
    private Button mBtnReg;
    private String tnc;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        mEdtUuid.setText(UUID.randomUUID().toString());

        byte[] r = new byte[256]; //Means 2048 bit
        new Random().nextBytes(r);
        String s = new String(r);
        mEdtData.setText(s);
    }

    private void initView() {
        mEdtEmail = (EditText) findViewById(R.id.edt_email);
        mEdtUuid = (EditText) findViewById(R.id.edt_uuid);
        mEdtData = (EditText) findViewById(R.id.edt_data);
        mCkTnc = (CheckBox) findViewById(R.id.ck_tnc);
        mBtnReg = (Button) findViewById(R.id.btn_reg);
        mBtnReg.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_reg:
                if (isValidEmail(mEdtEmail.getText().toString())) {
                    if (mCkTnc.isChecked()) {
                        tnc = "true";
                    } else {
                        tnc = "false";
                    }

                    ApiInterface apiInterface = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
                    Call<String> call = apiInterface.register(
                            mEdtEmail.getText().toString(),
                            mEdtUuid.getText().toString(),
                            mEdtData.getText().toString(),
                            tnc
                    );

                    progressDialog.show();

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            progressDialog.dismiss();
                            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                            intent.putExtra("email", mEdtEmail.getText().toString());
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                } else {
                    Toast.makeText(this, "Invalid email address", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
