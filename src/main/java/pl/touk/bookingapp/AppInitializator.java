package pl.touk.bookingapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.touk.bookingapp.db.entities.Movie;
import pl.touk.bookingapp.db.entities.MoviesSeats;
import pl.touk.bookingapp.db.entities.Room;
import pl.touk.bookingapp.db.entities.Seat;
import pl.touk.bookingapp.db.repos.MovieRepository;
import pl.touk.bookingapp.db.repos.MoviesSeatsRepository;
import pl.touk.bookingapp.db.repos.RoomRepository;
import pl.touk.bookingapp.db.repos.SeatRepository;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
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
    MoviesSeatsRepository moviesSeatsRepository;

    @PostConstruct
    private void init() {
        List<Seat> seats1 = seatRepository.findAllByIdNotNull();
        Room s1 = roomRepository.getById(1);

        List<Seat> seats2 = seatRepository.findByIdGreaterThan(3);
        Room s2 = roomRepository.getById(2);
        Movie panSamochodzik = new Movie("Pan samochodzik", Date.valueOf("2019-03-15"), Time.valueOf("12:00:00"),
                s1);

        MoviesSeats ms1 = new MoviesSeats();
        ms1.setMovie(panSamochodzik);
        ms1.setSeat(seats1.get(0));
        List<MoviesSeats> list = new ArrayList<>();
        list.add(ms1);
        panSamochodzik.setMoviesSeats(list);
        seats1.get(0).setMoviesSeats(list);
        moviesSeatsRepository.save(ms1);
        movieRepository.save(panSamochodzik);

        Movie imperiumKontratakuje = new Movie("Imperium kontratakuje", Date.valueOf("2021-12-15"), Time.valueOf("22:00:00"),
                s1);
        movieRepository.save(imperiumKontratakuje);
        Movie ogniemIMieczem = new Movie("Ogniem i mieczem", Date.valueOf("2019-12-15"), Time.valueOf("08:00:00"),
                s2);
        movieRepository.save(ogniemIMieczem);
        Movie najlepszy = new Movie("Najlepszy", Date.valueOf("2020-03-13"), Time.valueOf("12:00:00"),
                s2);
        movieRepository.save(najlepszy);
        Movie starTrek = new Movie("StarTrek", Date.valueOf("2020-03-12"), Time.valueOf("12:00:00"),
                s1);
        movieRepository.save(starTrek);
        Movie najlepszy2 = new Movie("Najlepszy", Date.valueOf("2021-03-13"), Time.valueOf("12:00:00"),
                s2);
        movieRepository.save(najlepszy2);
        Movie najlepszy3 = new Movie("Najlepszy", Date.valueOf("2021-03-13"), Time.valueOf("14:00:00"),
                s1);
        movieRepository.save(najlepszy3);
    }
}

