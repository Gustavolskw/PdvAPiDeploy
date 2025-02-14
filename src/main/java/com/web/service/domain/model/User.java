package com.web.service.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends DefaultEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   private String username;
   private String password;
   @Email
   private String email;
   @ManyToOne
   @JoinColumn(name = "role_id")
   private Role role;
   @Column(nullable = true, updatable = true, insertable = true)
   private Boolean status;

   @JsonIgnore
   @OneToMany(mappedBy = "guide" , cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
   private List<Order> ordersList = new ArrayList<>();

   public User( String username, String password, String email, Role role, boolean status) {
      this.username = username;
      this.password = password;
      this.email = email;
      this.role = role;
      this.status = status;
   }
}
