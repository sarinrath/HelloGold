package com.hellogold.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hellogold.R;
import com.hellogold.model.Data;

import java.util.ArrayList;

/**
 * Created by SARINRATH on 30/01/2019 AD.
 */

public class SpotPriceAdapter extends RecyclerView.Adapter<SpotPriceAdapter.SpotPriceHolder> {

    ArrayList<Data> arrayList;
    Context context;

    public SpotPriceAdapter(ArrayList<Data> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    /**
     * View holder class
     */
    public class SpotPriceHolder extends RecyclerView.ViewHolder {

        TextView textView_buy, textView_sell, textView_spotprice, textview_timestamp;

        public SpotPriceHolder(View itemView) {
            super(itemView);
            textView_buy = (TextView) itemView.findViewById(R.id.text_buy);
            textView_sell = (TextView) itemView.findViewById(R.id.text_sell);
            textView_spotprice = (TextView) itemView.findViewById(R.id.text_spotprice);
            textview_timestamp = (TextView) itemView.findViewById(R.id.text_Timestamp);

        }
    }

    @Override
    public SpotPriceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spotprice, parent, false);
        return new SpotPriceHolder(v);
    }

    @Override
    public void onBindViewHolder(SpotPriceHolder holder, int position) {
        Data data = arrayList.get(position);

        holder.textView_sell.setText("Sell: " + data.sell);
        holder.textView_buy.setText("Buy: " + data.buy);
        holder.textView_spotprice.setText("Spot Price: " + data.spotPrice);
        holder.textview_timestamp.setText("TimeStamp: " + data.timestamp);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
