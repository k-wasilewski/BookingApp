package pl.touk.bookingapp.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.touk.bookingapp.db.entities.MovieSeat;
import pl.touk.bookingapp.db.entities.MoviesSeatsId;

public interface MoviesSeatsRepository extends JpaRepository<MovieSeat, MoviesSeatsId> {
}
