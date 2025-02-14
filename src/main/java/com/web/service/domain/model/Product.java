package com.web.service.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends DefaultEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private ProductType productType;
    private String name;
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    private BigDecimal value;
    @Column(name = "status", nullable = false)
    private Boolean status;

}
