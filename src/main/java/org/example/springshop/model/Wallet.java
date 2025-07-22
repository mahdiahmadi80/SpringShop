package org.example.springshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_WALLET")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User userId;
    @Column(name = "BALANCE")
    private Long balance;
    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
    @Builder(builderClassName = "UserWalletClass", builderMethodName = "userWalletClass")
    public Wallet(User user) {
        this.balance = 0L;
        this.userId = user;
    }
}