package com.biblioteca.repository;

import com.biblioteca.model.Loan;
import com.biblioteca.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationRepository extends JpaRepository <Reservation, Long> {
}
