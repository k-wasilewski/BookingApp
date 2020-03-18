package pl.touk.bookingapp.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.touk.bookingapp.db.entities.Movie;
import pl.touk.bookingapp.db.entities.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findByIdGreaterThan(Integer no);
    Seat findByNoAndMovie(String no, Movie movie);
    List<Seat> findAllByMovie(Movie movie);
    List<Seat> findByIdLessThan(Integer no);
    List<Seat> findAllByRow(char row);
    Seat findByMovieAndRowAndPos(Movie movie, char row, int pos);
}
