package pl.touk.bookingapp.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.touk.bookingapp.db.entities.Screening;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    @Query("SELECT m FROM Screening m WHERE m.date > :fromm AND m.date < :too AND " +
            "m.time > :frommH AND m.time < :tooH ORDER BY m.movie.name DESC, m.date DESC, m.time DESC")
    List<Screening> customFindWithinDatesAndTimes(@Param("fromm") Date from, @Param("too") Date to,
                                              @Param("frommH") Time fromH, @Param("tooH") Time toH);
    Screening findById(int id);
}
