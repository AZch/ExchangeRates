package com.wcreators.databasestarter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rate_action")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateActionEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "major")
    private String major;

    @Column(name = "minor")
    private String minor;

    @Column(name = "date")
    private Date date;

    @Column(name = "action")
    private String action;

    @Column(name = "strategy")
    private String strategy;

    @Column(name = "rate")
    private Double rate;
}
