package com.tbs.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_measurements")
public class CustomerMeasurements {


    @Id
    @Column(name = "m_id")
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "c_id", nullable = false)
    private Customer customer;

    @Column(name = "bust")
    private double bust;

    @Column(name = "waist")
    private double waist;

    @Column(name = "hips")
    private double hips;

    @Column(name = "inseam")
    private double inseam;

    @Column(name = "height")
    private double height;

    @Column(name = "shoulder_width")
    private double shoulderWidth;

    @Column(name = "arm_length")
    private double armLength;

    @Column(name = "thigh")
    private double thigh;

    @Column(name = "calf")
    private double calf;

    @Column(name = "neck")
    private double neck;


}
