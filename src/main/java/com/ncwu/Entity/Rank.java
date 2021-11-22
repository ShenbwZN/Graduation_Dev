package com.ncwu.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_rank")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private int rankId;

    @Column(name = "rank_code")
    private String rankCode;

    @Column(name = "rank_date")
    private String rankDate;

    @Column(name = "rank")
    private char rank;

}
