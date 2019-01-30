package com.hellogold.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SARINRATH on 30/01/2019 AD.
 */

public class SpotPrice implements Parcelable {

	@SerializedName("result")
	public String result;

	@SerializedName("data")
	public Data data;


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.result);
		dest.writeParcelable(this.data, flags);
	}

	public SpotPrice() {
	}

	protected SpotPrice(Parcel in) {
		this.result = in.readString();
		this.data = in.readParcelable(Data.class.getClassLoader());
	}

	public static final Creator<SpotPrice> CREATOR = new Creator<SpotPrice>() {
		@Override
		public SpotPrice createFromParcel(Parcel source) {
			return new SpotPrice(source);
		}

		@Override
		public SpotPrice[] newArray(int size) {
			return new SpotPrice[size];
		}
	};
}