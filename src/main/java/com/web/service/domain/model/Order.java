package com.web.service.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends DefaultEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "consumer_name", nullable = false, length = 75)
    private String consumerName;
    @Column(name = "table_number", nullable = false)
    private Integer tableNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide")
    private User guide;
    private Boolean status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductOrder> productsOnOrder = new HashSet<>();
}
