package com.upc.cargasinestres.CargaSinEstres.Business.model.dto.Reservation.response;

import com.upc.cargasinestres.CargaSinEstres.Business.model.entity.Customer;
import com.upc.cargasinestres.CargaSinEstres.Business.model.entity.Company;
import com.upc.cargasinestres.CargaSinEstres.Business.model.entity.Servicio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * This class represents the response of the booking history
 * @author Grupo1
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDto {
    private Long id;
    private Company company;
    private Customer customer;
    private LocalDate bookingDate;
    private String origin_address;
    private String destination_address;
    private Date movingDate;
    private String movingTime;
    private String status;
    private List<Servicio> servicios;
    private int price;
    private Long chat_id;

    public void setChat_id(Long chat_id) {
            this.chat_id = chat_id;
    }
}
