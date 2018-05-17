package cadillac.example.com.cadillac.adapter;

import android.content.Context;

import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cadillac.example.com.cadillac.R;

import cadillac.example.com.cadillac.bean.Reportbean;
import cadillac.example.com.cadillac.bean.StateBean;
import cadillac.example.com.cadillac.utils.CadillacUtils;
import cadillac.example.com.cadillac.utils.MyLogUtils;
import cadillac.example.com.cadillac.view.caldroid.CaldroidGridAdapter;
import hirondelle.date4j.DateTime;

public class CaldroidSampleCustomAdapter extends CaldroidGridAdapter {
	private List<StateBean>list;

	public CaldroidSampleCustomAdapter(Context context, int month, int year,
									   Map<String, Object> caldroidData,
									   Map<String, Object> extraData) {
		super(context, month, year, caldroidData, extraData);
		this.extraData=extraData;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View cellView = convertView;

		// For reuse
		if (convertView == null) {
			cellView = inflater.inflate(R.layout.custom_cell, null);
		}

		TextView tv1 = (TextView) cellView.findViewById(R.id.tv1);
		TextView tv2 = (TextView) cellView.findViewById(R.id.tv2);
		TextView tv3=(TextView) cellView.findViewById(R.id.tv3);


		DateTime dateTime = this.datetimeList.get(position);
//		Map<String, Object> extraData=this.extraData;
		tv1.setText("" + dateTime.getDay());
		list= (List<StateBean>) extraData.get("statbean");

		if (dateTime.getMonth() != month) {//文字全变成白色，背景变成白色
			tv1.setTextColor(Color.parseColor("#333333"));//变黑色
			tv1.setBackgroundColor(Color.parseColor("#cec7c8"));
			tv2.setTextColor(Color.parseColor("#333333"));//变灰色
			tv3.setTextColor(Color.parseColor("#333333"));//变灰色
			cellView.setBackgroundColor(Color.parseColor("#cec7c8"));//变灰色

			cellView.setTag("no");
			tv1.setText("");
			tv2.setText("");
			tv3.setText("");
		}else {
			tv1.setTextColor(resources
					.getColor(R.color.white));//白色
			tv1.setBackgroundColor(Color.parseColor("#8a152b"));
			tv2.setTextColor(Color.parseColor("#666666"));//变灰色
			tv3.setTextColor(Color.parseColor("#4674a1"));
			cellView.setBackgroundColor(Color.parseColor("#ffffff"));//白色
			tv2.setText("-");
			tv3.setText("-");
			cellView.setTag("no");
			if (list != null && list.size() > 0) {
					for (int k = 0; k < list.size(); k++) {
						if (tv1.getText().equals(CadillacUtils.numFormart(list.get(k).getDateKey()))) {
							update(k, tv2, tv3);
							cellView.setTag("yes");
						}
					}

			} else {
				cellView.setTag("yes");
				tv2.setText("-");
				tv3.setText("-");
			}

		}





		// Set color of the dates in previous / next month
//		if (dateTime.getMonth() != month) {//文字全变成白色，背景变成白色
//			tv1.setTextColor(resources
//					.getColor(R.color.caldroid_gray));//变灰色
//			tv2.setTextColor(resources
//					.getColor(R.color.caldroid_gray));//变灰色
//			tv3.setTextColor(resources
//					.getColor(R.color.caldroid_gray));//变灰色
//			cellView.setBackgroundResource(R.drawable.bg_write);//变灰色
//
//			cellView.setTag("no");
//			tv1.setText("-");
//			tv2.setText("-");
//			tv3.setText("-");
//		}else {
//			tv1.setTextColor(Color.WHITE);//白色
//			tv2.setTextColor(Color.BLACK);//黑色
//			tv3.setTextColor(Color.BLUE);//蓝色
//
//			cellView.setBackgroundResource(R.mipmap.rilino);//没点击下的背景
//
//			cellView.setTag("yes");
//			if(extraData!=null&&extraData.size()!=0){
//				if(list!=null&&list.size()!=0){
//				if(list.get(0).getInRooms().size()!=0&&list.get(0).getInRooms()!=null) {
//
//					if(list.get(0).getInRooms().size()==1){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//
//					if(list.get(0).getInRooms().size()==2){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//
//					if(list.get(0).getInRooms().size()==3){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//
//					if(list.get(0).getInRooms().size()==4){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//
//					if(list.get(0).getInRooms().size()==5){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//
//					if(list.get(0).getInRooms().size()==6){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==7){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//
//					if(list.get(0).getInRooms().size()==9){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==8){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//
//					if(list.get(0).getInRooms().size()==10){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==11){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==12){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==13){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==14){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==15){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==16){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==17){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==18){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==19){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}else if(tv1.getText().equals("19")){
//							update(18,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==20){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}else if(tv1.getText().equals("19")){
//							update(18,tv2,tv3);
//						}else if(tv1.getText().equals("20")){
//							update(19,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==21){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}else if(tv1.getText().equals("19")){
//							update(18,tv2,tv3);
//						}else if(tv1.getText().equals("20")){
//							update(19,tv2,tv3);
//						}else if(tv1.getText().equals("21")){
//							update(20,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==22){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}else if(tv1.getText().equals("19")){
//							update(18,tv2,tv3);
//						}else if(tv1.getText().equals("20")){
//							update(19,tv2,tv3);
//						}else if(tv1.getText().equals("21")){
//							update(20,tv2,tv3);
//						}else if(tv1.getText().equals("22")){
//							update(21,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==23){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}else if(tv1.getText().equals("19")){
//							update(18,tv2,tv3);
//						}else if(tv1.getText().equals("20")){
//							update(19,tv2,tv3);
//						}else if(tv1.getText().equals("21")){
//							update(20,tv2,tv3);
//						}else if(tv1.getText().equals("22")){
//							update(21,tv2,tv3);
//						}else if(tv1.getText().equals("23")){
//							update(22,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==24){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}else if(tv1.getText().equals("19")){
//							update(18,tv2,tv3);
//						}else if(tv1.getText().equals("20")){
//							update(19,tv2,tv3);
//						}else if(tv1.getText().equals("21")){
//							update(20,tv2,tv3);
//						}else if(tv1.getText().equals("22")){
//							update(21,tv2,tv3);
//						}else if(tv1.getText().equals("23")){
//							update(22,tv2,tv3);
//						}else if(tv1.getText().equals("24")){
//							update(23,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==25){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}else if(tv1.getText().equals("19")){
//							update(18,tv2,tv3);
//						}else if(tv1.getText().equals("20")){
//							update(19,tv2,tv3);
//						}else if(tv1.getText().equals("21")){
//							update(20,tv2,tv3);
//						}else if(tv1.getText().equals("22")){
//							update(21,tv2,tv3);
//						}else if(tv1.getText().equals("23")){
//							update(22,tv2,tv3);
//						}else if(tv1.getText().equals("24")){
//							update(23,tv2,tv3);
//						}else if(tv1.getText().equals("25")){
//							update(24,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==26){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}else if(tv1.getText().equals("19")){
//							update(18,tv2,tv3);
//						}else if(tv1.getText().equals("20")){
//							update(19,tv2,tv3);
//						}else if(tv1.getText().equals("21")){
//							update(20,tv2,tv3);
//						}else if(tv1.getText().equals("22")){
//							update(21,tv2,tv3);
//						}else if(tv1.getText().equals("23")){
//							update(22,tv2,tv3);
//						}else if(tv1.getText().equals("24")){
//							update(23,tv2,tv3);
//						}else if(tv1.getText().equals("25")){
//							update(24,tv2,tv3);
//						}else if(tv1.getText().equals("26")){
//							update(25,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==27){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}else if(tv1.getText().equals("19")){
//							update(18,tv2,tv3);
//						}else if(tv1.getText().equals("20")){
//							update(19,tv2,tv3);
//						}else if(tv1.getText().equals("21")){
//							update(20,tv2,tv3);
//						}else if(tv1.getText().equals("22")){
//							update(21,tv2,tv3);
//						}else if(tv1.getText().equals("23")){
//							update(22,tv2,tv3);
//						}else if(tv1.getText().equals("24")){
//							update(23,tv2,tv3);
//						}else if(tv1.getText().equals("25")){
//							update(24,tv2,tv3);
//						}else if(tv1.getText().equals("26")){
//							update(25,tv2,tv3);
//						}else if(tv1.getText().equals("27")){
//							update(26,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//
//					if(list.get(0).getInRooms().size()==28){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}else if(tv1.getText().equals("19")){
//							update(18,tv2,tv3);
//						}else if(tv1.getText().equals("20")){
//							update(19,tv2,tv3);
//						}else if(tv1.getText().equals("21")){
//							update(20,tv2,tv3);
//						}else if(tv1.getText().equals("22")){
//							update(21,tv2,tv3);
//						}else if(tv1.getText().equals("23")){
//							update(22,tv2,tv3);
//						}else if(tv1.getText().equals("24")){
//							update(23,tv2,tv3);
//						}else if(tv1.getText().equals("25")){
//							update(24,tv2,tv3);
//						}else if(tv1.getText().equals("26")){
//							update(25,tv2,tv3);
//						}else if(tv1.getText().equals("27")){
//							update(26,tv2,tv3);
//						}else if(tv1.getText().equals("28")){
//							update(27,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//					if(list.get(0).getInRooms().size()==29){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}else if(tv1.getText().equals("19")){
//							update(18,tv2,tv3);
//						}else if(tv1.getText().equals("20")){
//							update(19,tv2,tv3);
//						}else if(tv1.getText().equals("21")){
//							update(20,tv2,tv3);
//						}else if(tv1.getText().equals("22")){
//							update(21,tv2,tv3);
//						}else if(tv1.getText().equals("23")){
//							update(22,tv2,tv3);
//						}else if(tv1.getText().equals("24")){
//							update(23,tv2,tv3);
//						}else if(tv1.getText().equals("25")){
//							update(24,tv2,tv3);
//						}else if(tv1.getText().equals("26")){
//							update(25,tv2,tv3);
//						}else if(tv1.getText().equals("27")){
//							update(26,tv2,tv3);
//						}else if(tv1.getText().equals("28")){
//							update(27,tv2,tv3);
//						}else if(tv1.getText().equals("29")){
//							update(28,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//
//					if(list.get(0).getInRooms().size()==30){
//						if(tv1.getText().equals("1")){
//							update(0,tv2,tv3);
//						}else if(tv1.getText().equals("2")){
//							update(1,tv2,tv3);
//						} else if(tv1.getText().equals("3")){
//							update(2,tv2,tv3);
//						}else if(tv1.getText().equals("4")){
//							update(3,tv2,tv3);
//						}else if(tv1.getText().equals("5")){
//							update(4,tv2,tv3);
//						}else if(tv1.getText().equals("6")){
//							update(5,tv2,tv3);
//						}else if(tv1.getText().equals("7")){
//							update(6,tv2,tv3);
//						}else if(tv1.getText().equals("8")){
//							update(7,tv2,tv3);
//						}else if(tv1.getText().equals("9")){
//							update(8,tv2,tv3);
//						}else if(tv1.getText().equals("10")){
//							update(9,tv2,tv3);
//						}else if(tv1.getText().equals("11")){
//							update(10,tv2,tv3);
//						}else if(tv1.getText().equals("12")){
//							update(11,tv2,tv3);
//						}else if(tv1.getText().equals("13")){
//							update(12,tv2,tv3);
//						}else if(tv1.getText().equals("14")){
//							update(13,tv2,tv3);
//						}else if(tv1.getText().equals("15")){
//							update(14,tv2,tv3);
//						}else if(tv1.getText().equals("16")){
//							update(15,tv2,tv3);
//						}else if(tv1.getText().equals("17")){
//							update(16,tv2,tv3);
//						}else if(tv1.getText().equals("18")){
//							update(17,tv2,tv3);
//						}else if(tv1.getText().equals("19")){
//							update(18,tv2,tv3);
//						}else if(tv1.getText().equals("20")){
//							update(19,tv2,tv3);
//						}else if(tv1.getText().equals("21")){
//							update(20,tv2,tv3);
//						}else if(tv1.getText().equals("22")){
//							update(21,tv2,tv3);
//						}else if(tv1.getText().equals("23")){
//							update(22,tv2,tv3);
//						}else if(tv1.getText().equals("24")){
//							update(23,tv2,tv3);
//						}else if(tv1.getText().equals("25")){
//							update(24,tv2,tv3);
//						}else if(tv1.getText().equals("26")){
//							update(25,tv2,tv3);
//						}else if(tv1.getText().equals("27")){
//							update(26,tv2,tv3);
//						}else if(tv1.getText().equals("28")){
//							update(27,tv2,tv3);
//						}else if(tv1.getText().equals("29")){
//							update(28,tv2,tv3);
//						}else if(tv1.getText().equals("30")){
//							update(29,tv2,tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//
//					if(list.get(0).getInRooms().size()==31) {
//						if (tv1.getText().equals("1")) {
//							update(0, tv2, tv3);
//						} else if (tv1.getText().equals("2")) {
//							update(1, tv2, tv3);
//						} else if (tv1.getText().equals("3")) {
//							update(2, tv2, tv3);
//						} else if (tv1.getText().equals("4")) {
//							update(3, tv2, tv3);
//						} else if (tv1.getText().equals("5")) {
//							update(4, tv2, tv3);
//						} else if (tv1.getText().equals("6")) {
//							update(5, tv2, tv3);
//						} else if (tv1.getText().equals("7")) {
//							update(6, tv2, tv3);
//						} else if (tv1.getText().equals("8")) {
//							update(7, tv2, tv3);
//						} else if (tv1.getText().equals("9")) {
//							update(8, tv2, tv3);
//						} else if (tv1.getText().equals("10")) {
//							update(9, tv2, tv3);
//						} else if (tv1.getText().equals("11")) {
//							update(10, tv2, tv3);
//						} else if (tv1.getText().equals("12")) {
//							update(11, tv2, tv3);
//						} else if (tv1.getText().equals("13")) {
//							update(12, tv2, tv3);
//						} else if (tv1.getText().equals("14")) {
//							update(13, tv2, tv3);
//						} else if (tv1.getText().equals("15")) {
//							update(14, tv2, tv3);
//						} else if (tv1.getText().equals("16")) {
//							update(15, tv2, tv3);
//						} else if (tv1.getText().equals("17")) {
//							update(16, tv2, tv3);
//						} else if (tv1.getText().equals("18")) {
//							update(17, tv2, tv3);
//						} else if (tv1.getText().equals("19")) {
//							update(18, tv2, tv3);
//						} else if (tv1.getText().equals("20")) {
//							update(19, tv2, tv3);
//						} else if (tv1.getText().equals("21")) {
//							update(20, tv2, tv3);
//						} else if (tv1.getText().equals("22")) {
//							update(21, tv2, tv3);
//						} else if (tv1.getText().equals("23")) {
//							update(22, tv2, tv3);
//						} else if (tv1.getText().equals("24")) {
//							update(23, tv2, tv3);
//						} else if (tv1.getText().equals("25")) {
//							update(24, tv2, tv3);
//						} else if (tv1.getText().equals("26")) {
//							update(25, tv2, tv3);
//						} else if (tv1.getText().equals("27")) {
//							update(26, tv2, tv3);
//						} else if (tv1.getText().equals("28")) {
//							update(27, tv2, tv3);
//						} else if (tv1.getText().equals("29")) {
//							update(28, tv2, tv3);
//						} else if (tv1.getText().equals("30")) {
//							update(29, tv2, tv3);
//						} else if (tv1.getText().equals("31")) {
//							update(30, tv2, tv3);
//						}
//						else {
//							tv2.setText("-");
//							tv3.setText("-");
//							cellView.setTag("no");
//						}
//					}
//
//				}else {
//					tv2.setText("-");
//					tv3.setText("-");
//					cellView.setTag("no");
//				}
//
//				}
//
//			}
//		}






		return cellView;
	}

	/**
	 * 改变数据
	 */
	private void update(int i,TextView tv2,TextView tv3) {

		tv2.setText(list.get(i).getTopShow());
		tv3.setText(list.get(i).getButtonShow());
	}
}
