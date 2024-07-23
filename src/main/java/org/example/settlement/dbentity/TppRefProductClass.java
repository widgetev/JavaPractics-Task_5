package org.example.settlement.dbentity;

import jakarta.persistence.*;

@Entity
@Table(name = "tpp_ref_product_class")
public class TppRefProductClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String value;

    @Column(length = 50)
    private String gbi_code;

    @Column(length = 100)
    private String gbi_name;

    @Column(length = 50)
    private String product_row_code;

    @Column(length = 100)
    private String product_row_name;

    @Column(length = 50)
    private String subclass_code;

    @Column(length = 100)
    private String subclass_name;

}
