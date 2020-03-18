package pl.touk.bookingapp.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.touk.bookingapp.db.entities.Screening;
import pl.touk.bookingapp.db.entities.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findByIdGreaterThan(Integer no);
    Seat findByNoAndScreening(String no, Screening screening);
    List<Seat> findAllByScreening(Screening screening);
    List<Seat> findByIdLessThan(Integer no);
    List<Seat> findAllByRow(char row);
    Seat findByScreeningAndRowAndPos(Screening screening, char row, int pos);
}
