package com.upc.cargasinestres.CargaSinEstres.Business.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * This class represents the Subscription entity for CSE. The table name is subscriptions. And the columns are:
 * <ul>
 *     <li>id - The id of the subscription</li>
 *     <li>firma - The signature of the subscription</li>
 *     <li>idCompany - The id of the company that is subscribed</li>
 *     <li>subscriptionDate - The date of the subscription</li>
 *     <li>subscriptionType - The type of the subscription</li>
 *     <li>paymentMethod - The payment method of the subscription</li>
 * </ul>
 * @author Grupo1
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "memberships")
public class Membership {
    /**
     * The id of the subscription.
     * This is a primary key.
     * This id is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The company that is subscribed.
     * This is a foreign key.
     * This is a one to one relationship.
     */
    @OneToOne
    @JoinColumn(name="idCompany", nullable = false, foreignKey = @ForeignKey(name="FK_membership_company"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Company company;

    /**
     * The start date of the subscription.
     */
    @Column(name="start_date", nullable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;
    /**
     * The description of the subscription.
     */
    @Column(name="description", nullable = false)
    private String description;

    /**
     * The payment method of the subscription.
     */
    @Column(name="price", nullable = false)
    private float price;

    /*
    * id: any;
    firma: any;
    idCompany: any;
    subscriptionDate: any;
    subscriptionType: any;
    payment: {
      paymentMethod: any;
    };
    hiredCompany: {
      name: any;
      logo: any;
    };
    * */

    //la membresia resalta a la compañia y la hace aparecer entre las principales en al tabla,
    // tambien hace que se busque primero entre estas para carga rapida

}