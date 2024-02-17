package com.upc.cargasinestres.CargaSinEstres.Business.model.dto.Reservation.request;


import com.upc.cargasinestres.CargaSinEstres.Business.model.entity.Servicio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * The BookingHistoryRequestDto class represents the data transfer object for creating a booking history record.
 * It contains fields related to the details of a booking history.
 * This class is used for receiving client requests to create booking history entries.
 * @author Grupo1
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDto {
    //private Company company;
    //private Client client;
    //private LocalDate bookingDate;
    private String origin_address;
    private String destination_address;
    private Date start_date;
    private String start_time;
    private List<Servicio> servicios;
}
