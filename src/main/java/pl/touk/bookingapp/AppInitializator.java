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
        List<Seat> allSeats = seatRepository.findAllByIdNotNull();
        Room s1 = roomDao.getRoom(1);
        s1.setSeats(allSeats);
        roomDao.updateRoom(s1);

        List<Seat> bSeats = seatRepository.findByIdGreaterThan(3);
        Room s2 = roomDao.getRoom(2);
        s2.setSeats(bSeats);
        roomDao.updateRoom(s2);

        Movie panSamochodzik = new Movie("Pan samochodzik", Date.valueOf("2019-06-15"), Time.valueOf("12:00:00"),
                s1);
        movieDao.saveMovie(panSamochodzik);
        Movie imperiumKontratakuje = new Movie("Imperium kontratakuje", Date.valueOf("2019-12-15"), Time.valueOf("22:00:00"),
                s1);
        movieDao.saveMovie(imperiumKontratakuje);
        Movie ogniemIMieczem = new Movie("Ogniem i mieczem", Date.valueOf("2019-12-15"), Time.valueOf("08:00:00"),
                s2);
        movieDao.saveMovie(ogniemIMieczem);
        Movie najlepszy = new Movie("Najlepszy", Date.valueOf("2020-03-13"), Time.valueOf("12:00:00"),
                s2);
        movieDao.saveMovie(najlepszy);
        Movie starTrek = new Movie("StarTrek", Date.valueOf("2020-03-12"), Time.valueOf("12:00:00"),
                s1);
        movieDao.saveMovie(starTrek);
    }
}

