package pl.touk.bookingapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.touk.bookingapp.db.entities.Movie;
import pl.touk.bookingapp.db.entities.Room;
import pl.touk.bookingapp.db.entities.Screening;
import pl.touk.bookingapp.db.entities.Seat;
import pl.touk.bookingapp.db.repos.MovieRepository;
import pl.touk.bookingapp.db.repos.RoomRepository;
import pl.touk.bookingapp.db.repos.SeatRepository;
import pl.touk.bookingapp.db.repos.ScreeningRepository;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Component
class AppInitializator {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    EntityManager entityManager;
    @Autowired
    ScreeningRepository screeningRepository;

    private void setMovieSeats(Screening screening, List<Seat> seats) {
        for (Seat s : seats) {
            Seat s2 = new Seat();
            s2.setScreening(screening);
            s2.setNo(s.getNo());
            s2.setRow(s.getRow());
            s2.setPos(s.getPos());
            s2.setAvailable(true);
            seatRepository.save(s2);
        }
        screening.setSeats(seats);
        screeningRepository.save(screening);
    }

    @PostConstruct
    private void init() {
        /*Movie mock = new Movie();
        mock.setName("mock");
        movieRepository.save(mock);*/

        for (int i=1; i<4; i++) {
            Seat s = new Seat();
            s.setRow('A');
            s.setPos(i);
            s.setNo("A"+i);
            //s.setMovie(mock);
            seatRepository.save(s);
        }
        for (int i=1; i<4; i++) {
            Seat s = new Seat();
            s.setRow('B');
            s.setPos(i);
            s.setNo("B"+i);
            //s.setMovie(mock);
            seatRepository.save(s);
        }

        for (int i = 1; i < 4; i++) {
            Room r = new Room();
            r.setName("sala"+i);
            roomRepository.save(r);
        }

        List<Seat> seats1 = seatRepository.findAll();
        Room s1 = roomRepository.getById(1);

        List<Seat> seats2 = seatRepository.findByIdGreaterThan(3);
        Room s2 = roomRepository.getById(2);

        List<Seat> seats3 = seatRepository.findByIdLessThan(4);
        Room s3 = roomRepository.getById(3);

        Movie panSamochodzik = new Movie("Pan samochodzik");
        movieRepository.save(panSamochodzik);
        Screening screening1 = new Screening(s1, panSamochodzik, Date.valueOf("2019-03-15"), Time.valueOf("12:00:00"));
        screeningRepository.save(screening1);
        setMovieSeats(screening1, seats1);

        Movie imperiumKontratakuje = new Movie("Imperium kontratakuje");
        movieRepository.save(imperiumKontratakuje);
        Screening screening2 = new Screening(s1, imperiumKontratakuje, Date.valueOf("2021-12-15"), Time.valueOf("22:00:00"));
        screeningRepository.save(screening2);
        setMovieSeats(screening2, seats1);

        Movie imperiumKontratakuje2 = new Movie("Imperium kontratakuje");
        movieRepository.save(imperiumKontratakuje2);
        Screening screening3 = new Screening(s3, imperiumKontratakuje2, Date.valueOf("2021-06-15"), Time.valueOf("22:00:00"));
        screeningRepository.save(screening3);
        setMovieSeats(screening3, seats3);

        Movie imperiumKontratakuje3 = new Movie("Imperium kontratakuje");
        movieRepository.save(imperiumKontratakuje3);
        Screening screening4 = new Screening(s3, imperiumKontratakuje3, Date.valueOf("2021-07-15"), Time.valueOf("22:00:00"));
        screeningRepository.save(screening4);
        setMovieSeats(screening4, seats3);

        Movie ogniemIMieczem = new Movie("Ogniem i mieczem");
        movieRepository.save(ogniemIMieczem);
        Screening screening5 = new Screening(s3, ogniemIMieczem, Date.valueOf("2019-12-15"), Time.valueOf("08:00:00"));
        screeningRepository.save(screening5);
        setMovieSeats(screening5, seats2);

        Movie najlepszy = new Movie("Najlepszy");
        movieRepository.save(najlepszy);
        Screening screening6 = new Screening(s3, najlepszy, Date.valueOf("2020-03-13"), Time.valueOf("12:00:00"));
        screeningRepository.save(screening6);
        setMovieSeats(screening6, seats2);

        Movie starTrek = new Movie("StarTrek");
        movieRepository.save(starTrek);
        Screening screening7 = new Screening(s3, starTrek, Date.valueOf("2020-03-12"), Time.valueOf("12:00:00"));
        screeningRepository.save(screening7);
        setMovieSeats(screening7, seats1);

        Movie najlepszy2 = new Movie("Najlepszy");
        movieRepository.save(najlepszy2);
        Screening screening8 = new Screening(s3, najlepszy2, Date.valueOf("2021-03-13"), Time.valueOf("12:00:00"));
        screeningRepository.save(screening8);
        setMovieSeats(screening8, seats2);

        Movie najlepszy3 = new Movie("Najlepszy");
        movieRepository.save(najlepszy3);
        Screening screening9 = new Screening(s3, najlepszy3, Date.valueOf("2021-03-13"), Time.valueOf("14:00:00"));
        screeningRepository.save(screening9);
        setMovieSeats(screening9, seats1);
    }
}

