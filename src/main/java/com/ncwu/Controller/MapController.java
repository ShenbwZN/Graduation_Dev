package com.ncwu.Controller;

import com.ncwu.Entity.Station;
import com.ncwu.Entity.Water;
import com.ncwu.Service.MapService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MapController {

    @Resource
    private MapService mapService;

    /**
     * 映射地图
     */
    @GetMapping("/map")
    public String showMap() {
        return "map";
    }

    /**
     * 获取 站的 信息
     */
    @PostMapping("/stations")
    @ResponseBody
    public List<Station> getStaByCount() {
        return mapService.getLStaByCount(30);
    }


    @GetMapping("/details")
    public String getStationDetail(String staCode, Model model) {
        model.addAttribute("staCode", staCode);
        return "staDetails";
    }

    @GetMapping("/byStation")
    public String searchByStation(Model model) {
        model.addAttribute("stations", mapService.getLStaByCount(30));
        return "byStation";
    }

    /**
     * 获取一个检测站的水位信息
     */
    @GetMapping("/waterValue")
    @ResponseBody
    public HashMap<String, Object> getWaterByStaCode(String way) {
        List<Water> waterList = mapService.getLWaterByStaCode(way);
        if (waterList.size() > 0) {
            ArrayList<String> waterDate = new ArrayList<>();
            ArrayList<Double> waterValue = new ArrayList<>();
            for (Water water : waterList) {
                waterValue.add(water.getWaterValue());
                waterDate.add(water.getWaterDate());
            }
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("xValue", waterDate);
            hashMap.put("yValue", waterValue);
            return hashMap;
        } else return null;
    }


    @GetMapping("/byDate")
    public String searchByDate(Model model) {
        String[] strDate = new String[]{"2018/1/1", "2018/2/1", "2018/3/1", "2018/4/1", "2018/5/1", "2018/6/1",
                "2018/7/1", "2018/8/1", "2018/9/1", "2018/10/1", "2018/11/1", "2018/12/1",
                "2019/1/1", "2019/2/1", "2019/3/1", "2019/4/1", "2019/5/1", "2019/6/1",
                "2019/7/1", "2019/8/1", "2019/9/1", "2019/10/1", "2019/11/1", "2019/12/1",
                "2020/1/1", "2020/2/1", "2020/3/1", "2020/4/1", "2020/5/1", "2020/6/1"};
        model.addAttribute("dates", strDate);
        return "byDate";
    }

    @GetMapping("/waterDate")
    @ResponseBody
    public HashMap<String, Object> getWaterByDate(String way) {
        List<Water> waterList = mapService.getLWaterByDate(way);
        if (waterList.size() > 0) {
            ArrayList<String> waterSta = new ArrayList<>();
            ArrayList<Double> waterValue = new ArrayList<>();
            for (Water water : waterList) {
                waterSta.add(water.getStationCode());
                waterValue.add(water.getWaterValue());
            }
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("xValue", waterSta);
            hashMap.put("yValue", waterValue);
            return hashMap;
        } else return null;
    }

}
