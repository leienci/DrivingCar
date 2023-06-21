package com.test.drivingcar.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.test.drivingcar.room.bean.CollectVo;
import com.test.drivingcar.room.bean.ErrorVo;
import com.test.drivingcar.room.bean.QuestionVo;

import java.util.List;

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM questions WHERE subject = :subject AND type =:type")
    List<QuestionVo> queryAll(int subject, int type);



    /***************************收藏题目操作********************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrReplace(CollectVo... collectVos);

    @Delete
    void delete(CollectVo... collectVoss);

    @Update
    void update(CollectVo... collectVos);

    //根据科目车型查询题库
    @Query("SELECT * FROM collect_questions WHERE subject = :mSubject AND type = :type")
    List<CollectVo> queryCollectAll(int mSubject, int type);

    /***************************错误题目操作********************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrReplace(ErrorVo... errorVos);

    @Delete
    void delete(ErrorVo... errorVos);

    @Update
    void update(ErrorVo... errorVos);

    //根据科目车型查询题库
    @Query("SELECT * FROM error_questions WHERE subject = :mSubject AND type = :type")
    List<ErrorVo> queryErrorAll(int mSubject, int type);
}
