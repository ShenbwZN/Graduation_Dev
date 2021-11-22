package com.ncwu.Repository;

import com.ncwu.Entity.Water;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WaterRepository extends JpaRepository<Water, Integer> {

    /**
     * 根据监测站的编码 查找监测站的数据： staCode->WaterValue集合
     */
    List<Water> findByStationCode(String staCode);

    /**
     * 根据 水位 的时间查询
     */
    List<Water> findByWaterDate(String date);

    @Query("select w from Water w, Station s where w.stationCode=s.stationCode " +
            "and s.count = 30 and w.waterDate=?1")
    List<Water> findWatersByWaterDate(String date);

}
