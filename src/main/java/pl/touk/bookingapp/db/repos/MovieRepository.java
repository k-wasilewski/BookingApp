package pl.touk.bookingapp.db.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.touk.bookingapp.db.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Movie findById(int id);
    List<Movie> findAllByIdNotNull();
    @Query("SELECT m FROM Movie m WHERE m.date > :fromm AND m.date < :too AND " +
            "m.time > :frommH AND m.time < :tooH")
    List<Movie> customFindWithinDatesAndTimes(@Param("fromm") Date from, @Param("too") Date to,
                                              @Param("frommH")Time fromH, @Param("tooH") Time toH);

}
