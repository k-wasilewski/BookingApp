package pl.touk.bookingapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.touk.bookingapp.db.dao.MovieDao;
import pl.touk.bookingapp.db.entities.Movie;
import pl.touk.bookingapp.db.entities.Room;
import pl.touk.bookingapp.db.entities.Seat;
import pl.touk.bookingapp.db.repos.MovieRepository;
import pl.touk.bookingapp.db.dao.RoomDao;
import pl.touk.bookingapp.db.repos.SeatRepository;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Component
class AppInitializator {
    @Autowired
    MovieDao movieDao;
    @Autowired
    RoomDao roomDao;
    @Autowired
    SeatRepository seatRepository;

    @PostConstruct
    private void init() {
        List<Seat> seats1 = seatRepository.findAllByIdNotNull();
        ArrayList<Seat> arrSeats1 = (ArrayList<Seat>) seats1;
        List<Seat> seats1a = (List<Seat>) arrSeats1.clone();
        List<Seat> seats1b = (List<Seat>) arrSeats1.clone();
        List<Seat> seats1c = (List<Seat>) arrSeats1.clone();
        List<Seat> seats1d = (List<Seat>) arrSeats1.clone();
        List<Seat> seats1e = (List<Seat>) arrSeats1.clone();
        List<Seat> seats1f = (List<Seat>) arrSeats1.clone();
        Room s1 = roomDao.getRoom(1);

        List<Seat> seats2 = seatRepository.findByIdGreaterThan(3);
        ArrayList<Seat> arrSeats2 = (ArrayList<Seat>) seats2;
        List<Seat> seats2a = (List<Seat>) arrSeats2.clone();
        List<Seat> seats2b = (List<Seat>) arrSeats2.clone();
        List<Seat> seats2c = (List<Seat>) arrSeats2.clone();
        List<Seat> seats2d = (List<Seat>) arrSeats2.clone();
        List<Seat> seats2e = (List<Seat>) arrSeats2.clone();
        List<Seat> seats2f = (List<Seat>) arrSeats2.clone();
        Room s2 = roomDao.getRoom(2);

        Movie panSamochodzik = new Movie("Pan samochodzik", Date.valueOf("2019-03-15"), Time.valueOf("12:00:00"),
                s1, seats1a);
        movieDao.saveMovie(panSamochodzik);
        Movie imperiumKontratakuje = new Movie("Imperium kontratakuje", Date.valueOf("2021-12-15"), Time.valueOf("22:00:00"),
                s1, seats1b);
        movieDao.saveMovie(imperiumKontratakuje);
        Movie ogniemIMieczem = new Movie("Ogniem i mieczem", Date.valueOf("2019-12-15"), Time.valueOf("08:00:00"),
                s2, seats2a);
        movieDao.saveMovie(ogniemIMieczem);
        Movie najlepszy = new Movie("Najlepszy", Date.valueOf("2020-03-13"), Time.valueOf("12:00:00"),
                s2, seats2b);
        movieDao.saveMovie(najlepszy);
        Movie starTrek = new Movie("StarTrek", Date.valueOf("2020-03-12"), Time.valueOf("12:00:00"),
                s1, seats1c);
        movieDao.saveMovie(starTrek);
        Movie najlepszy2 = new Movie("Najlepszy", Date.valueOf("2021-03-13"), Time.valueOf("12:00:00"),
                s2, seats2c);
        movieDao.saveMovie(najlepszy2);
        Movie najlepszy3 = new Movie("Najlepszy", Date.valueOf("2021-03-13"), Time.valueOf("14:00:00"),
                s1, seats1d);
        movieDao.saveMovie(najlepszy3);
    }
}

