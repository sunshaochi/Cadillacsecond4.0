package com.ist.cadillacpaltform.SDK.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by czh on 2017/3/31.
 */

public class FilePathHelper {
    public static boolean isStringEmptyOrNull(String str) {
        return str == null || str.isEmpty();
    }

    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static String createUploadPicName() {
        Calendar a = Calendar.getInstance();
        String imageName =
                String.valueOf(a.get(Calendar.YEAR)) + '_' +
                        String.valueOf(a.get(Calendar.MONTH) + 1) + '_' +
                        String.valueOf(a.get(Calendar.DAY_OF_MONTH)) + '_' +
                        String.valueOf(a.get(Calendar.HOUR)) + '_' +
                        String.valueOf(a.get(Calendar.MINUTE)) + '_' +
                        String.valueOf(a.get(Calendar.SECOND)) + '_' +
                        String.valueOf((int) Math.random() * 1000) + ".jpg";
        return imageName;
    }
}
