package com.ncwu.Repository;

import com.ncwu.Entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Integer> {

    /**
     * 按检测站的数据量查询：30
     */
    List<Station> findByCount(int count);

    /**
     * 按监测站的编码查询：staCode->station
     */
    Station findByStationCode(String staCode);

}