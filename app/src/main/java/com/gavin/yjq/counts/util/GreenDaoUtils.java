package com.gavin.yjq.counts.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.gavin.yjq.counts.gen.DaoMaster;
import com.gavin.yjq.counts.gen.DaoSession;

public class GreenDaoUtils {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private static GreenDaoUtils greenDaoUtils;

    private GreenDaoUtils(){}

    public static GreenDaoUtils getSingleTon(){
        if (greenDaoUtils==null){
            greenDaoUtils=new GreenDaoUtils();
        }
        return greenDaoUtils;
    }

    private void initGreenDao(Context context){
        mHelper=new DaoMaster.DevOpenHelper(context,"an-db",null);
        db=mHelper.getWritableDatabase();
        mDaoMaster=new DaoMaster(db);
        mDaoSession=mDaoMaster.newSession();
    }

    public DaoSession getmDaoSession(Context context) {
        if (mDaoMaster==null){
            initGreenDao(context);
        }
        return mDaoSession;
    }

    public SQLiteDatabase getDb(Context context) {
        if (db==null){
            initGreenDao(context);
        }
        return db;
    }
}
