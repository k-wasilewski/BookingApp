package pl.touk.bookingapp.db.repos;

import pl.touk.bookingapp.db.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Movie findById(int id);
}
