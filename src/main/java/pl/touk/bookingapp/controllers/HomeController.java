package pl.touk.bookingapp.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import pl.touk.bookingapp.db.dao.SeatDao;
import pl.touk.bookingapp.db.entities.Movie;
import pl.touk.bookingapp.db.entities.Seat;
import pl.touk.bookingapp.db.repos.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@Controller
public class HomeController {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    SeatDao seatDao;

    @GetMapping("/")
    public String homeMoviesView() {
        return "index";
    }

    @PostMapping("/")
    public String filterMovies(Model model, HttpServletRequest request) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "uuuu-MM-dd" ) ;
        Date from=null;
        Date to=null;
        Time fromH=null;
        Time toH=null;

        if (request.getParameter("from")!=null && !request.getParameter("from").equals("")) {
            from=Date.valueOf(request.getParameter("from"));
            model.addAttribute("from", from);
        }
        if (request.getParameter("to")!=null && !request.getParameter("to").equals("")) {
            to=Date.valueOf(request.getParameter("to"));
            model.addAttribute("to", to);
        }
        if (request.getParameter("fromH")!=null && !request.getParameter("fromH").equals("")) {
            fromH=Time.valueOf(request.getParameter("fromH")+":00");
            model.addAttribute("fromH", fromH);
        }
        if (request.getParameter("toH")!=null && !request.getParameter("toH").equals("")) {
            toH=Time.valueOf(request.getParameter("toH")+":00");
            model.addAttribute("toH", toH);
        }
        model.addAttribute("movies", movieRepository.customFindWithinDatesAndTimes(from, to, fromH, toH));
        return "index";
    }

    @GetMapping("/details")
    public String movieDetails(@RequestParam("id") int id, Model model) {
        Movie movie = movieRepository.findById(id);
        model.addAttribute("movie", movie);
        model.addAttribute("availableSeats", movie.getAvailableSeats());

        return "movieDetails";
    }

    @GetMapping("/book")
    public String bookingView(Model model, @RequestParam("movieId") int movieId, @RequestParam("seatId") int seatId) {
        Movie movie = movieRepository.findById(movieId);
        model.addAttribute("movie", movie);
        Seat seat = movie.getSeatById(seatId);
        model.addAttribute("seat", seat);
        return "booking";
    }

    @PostMapping("/doBook")
    public String doBook(Model model, @RequestParam("movieId") int movieId, @RequestParam("seatId") int seatId,
                         @RequestParam("name") String name, @RequestParam("surname") String surname) {
        Movie movie = movieRepository.findById(movieId);
        Date date = movie.getDate();
        Time time = movie.getTime();
        Date dateNow = new Date(Calendar.getInstance().getTime().getTime());
        Time timeNow = new Time(Calendar.getInstance().getTime().getTime());
        long fifteenMinsInMillis = 60000*15;

        if (dateNow.getTime()<date.getTime() || (date.getTime()==dateNow.getTime() && timeNow.getTime() < time.getTime() &&
                time.getTime()-timeNow.getTime()>fifteenMinsInMillis)) {
            Seat seat = movie.getSeatById(seatId);
            seat.setAvailable(false);
            seat.setName(name);
            seat.setSurname(surname);
            seatDao.updateSeat(seat);
            model.addAttribute("ok", true);
        } else {
            model.addAttribute("ok", false);
        }

        return "index";
    }
}
