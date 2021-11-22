package com.ncwu.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_station")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private int stationId;

    // 检测站编码
    @Column(name = "station_code")
    private String stationCode;

    // 监测站名
    @Column(name = "station_name")
    private String stationName;

    // 监测站地址
    @Column(name = "station_location")
    private String stationLocation;

    // 监测站经纬度
    @Column(name = "station_lon")
    private double stationLon;

    @Column(name = "station_lat")
    private double stationLat;

    @Column(name = "count")
    private int count;

}
