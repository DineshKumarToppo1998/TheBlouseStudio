package com.bts.tailor.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Measurements {

    private Double bust;
    private Double waist;
    private Double hips;
    private Double height;

}
