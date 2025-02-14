package com.web.service.domain.model;

import com.web.service.presentation.viewModel.OrderSearchResult;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SqlResultSetMapping(
        name = "OrderSearchResultMapping",
        classes = @ConstructorResult(
                targetClass = OrderSearchResult.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "consumerName", type = String.class),
                        @ColumnResult(name = "tableNumber", type = Integer.class),
                        @ColumnResult(name = "guideName", type = String.class),
                        @ColumnResult(name = "status", type = Boolean.class),
                        @ColumnResult(name = "createdAt", type = LocalDateTime.class),
                        @ColumnResult(name = "updatedAt", type = LocalDateTime.class)
                }
        )
)
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
