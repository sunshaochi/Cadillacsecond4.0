package com.ist.cadillacpaltform.SDK.util.oss;

/**
 * Created by czh on 2017/3/6.
 */

public class ImageHelper {
    static public String resize(String url, int width, int height) {
        if (url == null) {
            return null;
        }
        String queryString = url + "?x-oss-process=image/resize,m_fixed,h_" + width + ",w_" + height;
        return queryString;
    }
}
