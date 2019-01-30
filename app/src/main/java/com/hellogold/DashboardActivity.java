package com.hellogold;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hellogold.adapter.SpotPriceAdapter;
import com.hellogold.model.Data;
import com.hellogold.model.SpotPrice;
import com.hellogold.service.ApiInterface;
import com.hellogold.service.RetrofitClientInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SARINRATH on 30/01/2019 AD.
 */

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mPullToRefresh;
    private SpotPriceAdapter adapter;
    private ArrayList<Data> arrayList;
    private ProgressDialog progressDialog;
    private Button mBtnRe;
    private TextView mTextEmail;
    private String strEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                strEmail= null;
            } else {
                strEmail= extras.getString("email");
            }
        } else {
            strEmail= (String) savedInstanceState.getSerializable("email");
        }
        mTextEmail.setText("E-MAIL: "+strEmail);


        mPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                mPullToRefresh.setRefreshing(false);
            }
        });
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mPullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        mBtnRe = (Button) findViewById(R.id.btn_re);
        mTextEmail = (TextView) findViewById(R.id.text_email);

        mBtnRe.setOnClickListener(this);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        progressDialog = new ProgressDialog(this);
    }

    private void refreshData() {

        ApiInterface apiInterface = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<SpotPrice> call = apiInterface.spot_price();

        progressDialog.show();

        call.enqueue(new Callback<SpotPrice>() {
            @Override
            public void onResponse(Call<SpotPrice> call, Response<SpotPrice> response) {
                arrayList = new ArrayList<>();
                arrayList.add(response.body().data);
                adapter = new SpotPriceAdapter(arrayList, DashboardActivity.this);
                mRecyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SpotPrice> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_re:
                refreshData();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }
}
