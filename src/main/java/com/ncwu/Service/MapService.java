package com.ncwu.Service;

import com.ncwu.Entity.Station;
import com.ncwu.Entity.Water;
import com.ncwu.Repository.StationRepository;
import com.ncwu.Repository.WaterRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MapService {

    @Resource
    private StationRepository stationRepository;

    @Resource
    private WaterRepository waterRepository;

    /**
     * 根据监测站编码 code 查找监测站水位数据集合
     */
    public List<Water> getLWaterByStaCode(String staCode) {
        return waterRepository.findByStationCode(staCode);
    }

    /**
     * 根据监测时间 date 查找监测站水位数据集合
     */
    public List<Water> getLWaterByDate(String date) {
//        return waterRepository.findByWaterDate(date);
        return waterRepository.findWatersByWaterDate(date);
    }

    /**
     * 根据数据量来获取监测站集合
     */
    public List<Station> getLStaByCount(int count) {
        return stationRepository.findByCount(count);
    }

}
