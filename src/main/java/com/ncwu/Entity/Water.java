package com.ncwu.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_water")
public class Water {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "water_id")
    private int waterId;

    // 监测站编码
    @Column(name = "station_code")
    private String stationCode;

    // 数据时间
    @Column(name = "water_date")
    private String waterDate;

    // 校验后的值
    @Column(name = "water_value")
    private double waterValue;

}