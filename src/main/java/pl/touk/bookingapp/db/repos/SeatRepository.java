package pl.touk.bookingapp.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.touk.bookingapp.db.entities.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findAllByIdNotNull();
    List<Seat> findByIdGreaterThan(Integer no);
    Seat findByNo(String no);
}
