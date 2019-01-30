package com.hellogold.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SARINRATH on 30/01/2019 AD.
 */

public class Data implements Parcelable {

	@SerializedName("spot_price")
	public double spotPrice;

	@SerializedName("buy")
	public double buy;

	@SerializedName("sell")
	public double sell;

	@SerializedName("timestamp")
	public String timestamp;


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(this.spotPrice);
		dest.writeDouble(this.buy);
		dest.writeDouble(this.sell);
		dest.writeString(this.timestamp);
	}

	public Data() {
	}

	protected Data(Parcel in) {
		this.spotPrice = in.readDouble();
		this.buy = in.readDouble();
		this.sell = in.readDouble();
		this.timestamp = in.readString();
	}

	public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
		@Override
		public Data createFromParcel(Parcel source) {
			return new Data(source);
		}

		@Override
		public Data[] newArray(int size) {
			return new Data[size];
		}
	};
}