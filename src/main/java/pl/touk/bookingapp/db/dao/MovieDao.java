package pl.touk.bookingapp.db.dao;

import org.springframework.stereotype.Repository;
import pl.touk.bookingapp.db.entities.Movie;
import pl.touk.bookingapp.db.entities.Room;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class MovieDao {
    @PersistenceContext
    EntityManager entityManager;

    public void saveMovie(Movie movie) {
        entityManager.persist(movie);
    }

    public void updateMovie(Movie movie) {
        entityManager.merge(movie);
    }

    public Movie getMovie(Integer id) {
        return entityManager.find(Movie.class, id);
    }
}

