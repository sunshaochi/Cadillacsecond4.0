package cadillac.example.com.cadillac;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.zhy.http.okhttp.OkHttpUtils;


import java.util.concurrent.TimeUnit;

import example.greendao.gen.DaoMaster;
import example.greendao.gen.DaoSession;
import okhttp3.OkHttpClient;

/**
 * Created by bitch-1 on 2017/3/27.
 */

public class MyApplication extends Application{

    public static MyApplication instance;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DaoMaster.DevOpenHelper mHelper;

    public static synchronized MyApplication getInstances() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initHttpUtils();

        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "cadillac-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * 初始化
     */
    private void initHttpUtils() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                    .readTimeout(100000L, TimeUnit.MILLISECONDS)
                    .build();
            OkHttpUtils.initClient(okHttpClient);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
