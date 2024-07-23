package org.example.settlement.dbentity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tpp_ref_account_type")
public class TppRefAccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,unique = true)
    private String value;
}
