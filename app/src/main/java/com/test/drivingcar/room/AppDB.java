package com.test.drivingcar.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.test.drivingcar.base.App;
import com.test.drivingcar.room.bean.CollectVo;
import com.test.drivingcar.room.bean.ErrorVo;
import com.test.drivingcar.room.bean.QuestionVo;

@Database(entities = {QuestionVo.class, CollectVo.class, ErrorVo.class}, version = 2, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    private static AppDB instance;

    private static AppDB init() {
        AppDB appDB = Room.databaseBuilder(App.getmContext(), AppDB.class, "question_k1.db")//创建本地数据库并命名
                .createFromAsset("questions_k1.db")//从资源文件导入本地数据库
                .allowMainThreadQueries()//是否允许主线程调用
                .fallbackToDestructiveMigration()
                .build();
        return appDB;
    }

    //单例模式
    public static AppDB getInstance() {
        if (instance == null) {
            synchronized (AppDB.class) {
                if (instance == null) {
                    instance = init();
                }
            }
        }
        return instance;
    }

    public abstract QuestionDao questionDao();

}
