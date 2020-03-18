package pl.touk.bookingapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.touk.bookingapp.db.entities.Movie;
import pl.touk.bookingapp.db.entities.Room;
import pl.touk.bookingapp.db.entities.Seat;
import pl.touk.bookingapp.db.repos.MovieRepository;
import pl.touk.bookingapp.db.repos.RoomRepository;
import pl.touk.bookingapp.db.repos.SeatRepository;

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

    private void setMovieSeats(Movie movie, List<Seat> seats) {
        for (Seat s : seats) {
            Seat s2 = new Seat();
            s2.setMovie(movie);
            s2.setNo(s.getNo());
            s2.setRow(s.getRow());
            s2.setPos(s.getPos());
            s2.setAvailable(true);
            seatRepository.save(s2);
        }
        movie.setSeats(seats);
        movieRepository.save(movie);
    }

    @PostConstruct
    private void init() {
        List<Seat> seats1 = seatRepository.findAll();
        Room s1 = roomRepository.getById(1);

        List<Seat> seats2 = seatRepository.findByIdGreaterThan(3);
        Room s2 = roomRepository.getById(2);

        List<Seat> seats3 = seatRepository.findByIdLessThan(4);
        Room s3 = roomRepository.getById(3);

        Movie panSamochodzik = new Movie("Pan samochodzik", Date.valueOf("2019-03-15"), Time.valueOf("12:00:00"),
                s1);
        movieRepository.save(panSamochodzik);
        setMovieSeats(panSamochodzik, seats1);

        Movie imperiumKontratakuje = new Movie("Imperium kontratakuje", Date.valueOf("2021-12-15"), Time.valueOf("22:00:00"),
                s1);
        movieRepository.save(imperiumKontratakuje);
        setMovieSeats(imperiumKontratakuje, seats1);

        Movie imperiumKontratakuje2 = new Movie("Imperium kontratakuje", Date.valueOf("2021-06-15"), Time.valueOf("22:00:00"),
                s3);
        movieRepository.save(imperiumKontratakuje2);
        setMovieSeats(imperiumKontratakuje2, seats3);

        Movie imperiumKontratakuje3 = new Movie("Imperium kontratakuje", Date.valueOf("2021-07-15"), Time.valueOf("22:00:00"),
                s3);
        movieRepository.save(imperiumKontratakuje3);
        setMovieSeats(imperiumKontratakuje3, seats3);

        Movie ogniemIMieczem = new Movie("Ogniem i mieczem", Date.valueOf("2019-12-15"), Time.valueOf("08:00:00"),
                s2);
        movieRepository.save(ogniemIMieczem);
        setMovieSeats(ogniemIMieczem, seats2);

        Movie najlepszy = new Movie("Najlepszy", Date.valueOf("2020-03-13"), Time.valueOf("12:00:00"),
                s2);
        movieRepository.save(najlepszy);
        setMovieSeats(najlepszy, seats2);

        Movie starTrek = new Movie("StarTrek", Date.valueOf("2020-03-12"), Time.valueOf("12:00:00"),
                s1);
        movieRepository.save(starTrek);
        setMovieSeats(starTrek, seats1);

        Movie najlepszy2 = new Movie("Najlepszy", Date.valueOf("2021-03-13"), Time.valueOf("12:00:00"),
                s2);
        movieRepository.save(najlepszy2);
        setMovieSeats(najlepszy2, seats2);

        Movie najlepszy3 = new Movie("Najlepszy", Date.valueOf("2021-03-13"), Time.valueOf("14:00:00"),
                s1);
        movieRepository.save(najlepszy3);
        setMovieSeats(najlepszy3, seats1);
    }
}

