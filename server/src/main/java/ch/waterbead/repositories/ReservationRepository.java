package ch.waterbead.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.waterbead.models.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom{
}
