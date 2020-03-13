package pl.touk.bookingapp.db.repos;

import pl.touk.bookingapp.db.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Movie findById(int id);
    List<Movie> findAllByIdNotNull();
}
