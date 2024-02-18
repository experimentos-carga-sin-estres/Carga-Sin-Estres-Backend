package com.upc.cargasinestres.CargaSinEstres.Business.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Represents an entity of booking history within the context of carga sin estres booking system. This class is a JPA entity
 * that maps to the "bookings" table in the database.
 * <p>
 *     The annotations used in this class are:
 *     <ul>
 *         <li>{@code @Data}: This Lombok annotation generates getter and setter methods for the class attributes.</li>
 *         <li>{@code @Builder}: This Lombok annotation generates a constructor with all the class parameters.</li>
 *         <li>{@code @NoArgsConstructor}: This Lombok annotation generates an empty constructor.</li>
 *         <li>{@code @AllArgsConstructor}: This Lombok annotation generates a constructor with all the class parameters.</li>
 *         <li>{@code @Entity}: This JPA annotation indicates that the class is an entity.</li>
 *         <li>{@code @Table}: This JPA annotation indicates the name of the database table that represents the entity.</li>
 *     </ul>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="reservations")
public class Reservation {
    /**
     * The id of the booking history.
     * <p>
     *     This attribute maps to the "id" column in the "bookings" table in the database.
     *     The value is generated automatically.
     *     The column is a primary key.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The company of the booking history.
     * <p>
     *     This attribute maps to the "idCompany" column in the "bookings" table in the database.
     *     The column is a foreign key.
     *     The attribute is mapped by the {@code company} attribute of the {@code Company} class.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name="idCompany", nullable = false, foreignKey = @ForeignKey(name="FK_reservation_company"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Company company;

    /**
     * The client of the booking history.
     * <p>
     *     This attribute maps to the "idClient" column in the "bookings" table in the database.
     *     The column is a foreign key.
     *     The attribute is mapped by the {@code client} attribute of the {@code Client} class.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name="idClient", nullable = false, foreignKey = @ForeignKey(name="FK_reservation_client"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Customer customer;

    /**
     * The moving date of the booking history.
     */
    @Column(name="start_date", nullable = false)
    private Date startDate; //movingDate

    /**
     * The moving time of the booking history.
     * <p>
     *     The attribute is mapped by the {@code movingTime} attribute of the {@code Time} class.
     * </p>
     */
    @Temporal(TemporalType.TIME)
    @Column(name="start_time", nullable = false)
    private Time startTime; //movingTime //cambiar a time

    /**
     * The pickup address of the booking history.
     */
    @JoinColumn(name = "origin_address", nullable = false)
    private String originAddress;

    /**
     * The destination address of the booking history.
     */
    @JoinColumn(name = "destination_address", nullable = false)
    private String destinationAddress;

    /**
     * The end date of the booking history.
     */
    @Column(name="end_date", nullable = false)
    private Date endDate;

    /**
     * The payment of the booking history.
     */
    @Column(name="price")
    private float price;

    /**
     * The status of the booking history.
     */
    @Column(name="status", nullable = false)
    private String status;

    /**
     * The services of the booking history.
     */
    @ManyToMany
    @JoinColumn(name="idServicio", nullable = false, foreignKey = @ForeignKey(name="FK_service_reservation"))
    private List<Servicio> servicios; //para carga rapida se llena solo la dirección inicial y final, la validación es lo mismo que la normal

    /**
     * The chats of the booking history.
     */
    @Column(name="chat")
    private Long chatId;
}