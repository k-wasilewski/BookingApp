package pl.touk.bookingapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;
import pl.touk.bookingapp.db.entities.Room;
import pl.touk.bookingapp.db.entities.Seat;
import pl.touk.bookingapp.db.repos.MovieRepository;
import pl.touk.bookingapp.db.repos.RoomDao;
import pl.touk.bookingapp.db.repos.SeatRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
class AppInitializator {
    @Autowired
    MovieRepository movieRepository;
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
    }
}

