package com.ist.cadillacpaltform.SDK.util;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.ist.cadillacpaltform.SDK.bean.HighStockAge.City;
import com.ist.cadillacpaltform.SDK.bean.HighStockAge.Province;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dearlhd on 2016/12/21.
 */
public class RegionParser {
    final String PROVINCES_CITIES_FILE = "Province_City_District.xml";

    List<Province> mProvinces;

    public RegionParser(Context context) throws Exception {
        InputStream is = context.getAssets().open(PROVINCES_CITIES_FILE);

        parse(is);

    }

    private void parse (InputStream is) throws XmlPullParserException, IOException {
        Province province = null;
        List<City> cities = null;
        City city = null;
        boolean flag = true;

        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(is, "UTF-8");

        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    mProvinces = new ArrayList<>();
                    break;

                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("province")) {
                        province = new Province();
                    } else if (xpp.getName().equals("name")) {
                        eventType = xpp.next();
                        province.setName(xpp.getText());
                    } else if (xpp.getName().equals("cities")) {
                        eventType = xpp.next();
                        flag = true;
                        cities = new ArrayList<>();

                        // 解析城市
                        while (flag) {
                            switch (eventType) {
                                case XmlPullParser.START_TAG:
                                    if (xpp.getName().equals("city")) {
                                        city = new City();
                                    } else if (xpp.getName().equals("name")) {
                                        eventType = xpp.next();
                                        city.setName(xpp.getText());
                                    }
                                    break;

                                case XmlPullParser.END_TAG:
                                    if (xpp.getName().equals("city")) {
                                        cities.add(city);
                                    } else if (xpp.getName().equals("cities")) {
                                        flag = false;
                                        province.setCities(cities);
                                    }
                                    break;
                            }

                            eventType = xpp.next();
                        }
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("province")) {
                        mProvinces.add(province);
                    }
                    break;
            }
            eventType = xpp.next();
        }
    }


    public List<Province> getProvinces () {
        return mProvinces;
    }

    public List<City> getCitiesByProvince (String province) {
        for (int i = 0; i < mProvinces.size(); i++) {
            if (mProvinces.get(i).getName().equals(province)) {
                return mProvinces.get(i).getCities();
            }
        }
        return null;
    }

    private void printAll () {
        for (int i = 0; i < mProvinces.size(); i++) {
            Log.i("dong", mProvinces.get(i).getName());
            String cities = "  ";
            for (int j = 0; j < mProvinces.get(i).getCities().size(); j++) {
                cities += mProvinces.get(i).getCities().get(j).getName() + " ";
            }
            Log.i("dong", cities);
        }
    }
}
