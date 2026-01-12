package com.hotelreview.hotel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hotelId;
    @Column(nullable = false)
    private String hotelName;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String about;
}
