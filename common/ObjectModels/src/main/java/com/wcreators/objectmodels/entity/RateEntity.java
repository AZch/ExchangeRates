package com.wcreators.objectmodels.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "major")
    private String major;

    @Column(name = "minor")
    private String minor;

    @Column(name = "sell")
    private Double sell;

    @Column(name = "buy")
    private Double buy;

    @Column(name = "created")
    private Date createdDate;

    @Column(name = "resource")
    private String resource;
}
