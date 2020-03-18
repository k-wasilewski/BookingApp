package pl.touk.bookingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.touk.bookingapp.db.entities.Movie;
import pl.touk.bookingapp.db.entities.Seat;
import pl.touk.bookingapp.db.repos.MovieRepository;
import pl.touk.bookingapp.db.repos.SeatRepository;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class BookingController {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    SeatRepository seatRepository;

    @PostMapping("/book")
    public String bookingView(Model model, @RequestParam("movieId") int movieId, HttpServletRequest request,
                              @RequestParam("seatsNo") int seatsNo,
                              @RequestParam("availableSeats1") String availableSeats1str,
                              @RequestParam("availableSeats2") String availableSeats2str,
                              @RequestParam("availableSeats") String availableSeatsStr) {
        List<Seat> availableSeats1 = convertSeatsStringToList(availableSeats1str, movieId);
        List<Seat> availableSeats2 = convertSeatsStringToList(availableSeats2str, movieId);
        int list = 0;
        boolean listsError=false;

        /*
         * get checked seats
         */
        List<Seat> seats = new ArrayList<>();
        for (Seat s : seatRepository.findAllByMovie(movieRepository.findById(movieId))) {
            String no = s.getNo();
            boolean checked=false;

            if (request.getParameter(s.getNo())!=null &&
                    request.getParameter(s.getNo()).equals("checked")) {
                seats.add(s);

                if (availableSeats1.contains(s) && (list==0||list==1)) {
                    list = 1;
                } else if (availableSeats2.contains(s) && (list==0||list==2)) {
                    list = 2;
                } else if (availableSeats1.contains(s) || availableSeats2.contains(s)) {
                    listsError=true;
                }
            }
        }

        //error handling
        if (listsError) {
            Movie movie = movieRepository.findById(movieId);
            model.addAttribute("movie", movie);
            model.addAttribute("availableSeats1", availableSeats1);
            model.addAttribute("availableSeats2", availableSeats2);
            model.addAttribute("listsError", true);
            model.addAttribute("seatsNo", seatsNo);

            return "movieDetails";
        }

        //error handling
        if (seats.size()!=seatsNo) {
            Movie movie = movieRepository.findById(movieId);
            model.addAttribute("movie", movie);
            model.addAttribute("availableSeats", convertSeatsStringToList(
                    availableSeatsStr, movieId));
            model.addAttribute("noChecked", true);
            model.addAttribute("seatsNo", seatsNo);

            return "movieDetails";
        }

        Movie movie = movieRepository.findById(movieId);
        model.addAttribute("movie", movie);
        model.addAttribute("seats", seats);
        return "booking";
    }

    @PostMapping("/doBook")
    public String doBook(Model model, @RequestParam("movieId") int movieId, @RequestParam("seats") String seatsString,
                         @RequestParam("name") String name, @RequestParam("surname") String surname,
                         @RequestParam("age") String age) {
        List<Seat> seats = convertSeatsStringToList(seatsString, movieId);

        Movie movie = movieRepository.findById(movieId);
        Date date = movie.getDate();
        Time time = movie.getTime();
        Date dateNow = new Date(Calendar.getInstance().getTime().getTime());
        Time timeNow = new Time(Calendar.getInstance().getTime().getTime());
        long fifteenMinsInMillis = 60000*15;
        String price="";

        //error handling
        if (name.length()<3 || surname.length()<3 || !Character.isUpperCase(name.charAt(0))
                || !Character.isUpperCase(surname.charAt(0))) {
            model.addAttribute("lengthError", true);

            model.addAttribute("movie", movie);
            model.addAttribute("seats", seats);
            return "booking";
        }

        //error handling
        if (surname.contains("-") && (!Character.isUpperCase(surname.charAt(surname.indexOf("-")+1))
                        && surname.charAt(surname.indexOf("-")+1)!='\0')) {
            model.addAttribute("surnameError", true);

            model.addAttribute("movie", movie);
            model.addAttribute("seats", seats);
            return "booking";
        }

        switch (age) {
            case "adult":
                price="25,00";
                break;
            case "student":
                price="18,00";
                break;
            case "child":
                price="12,50";
                break;
        }

        Time expirationTime = Time.valueOf(time.toLocalTime().minusMinutes(15));

        if (dateNow.getTime()<date.getTime() || (date.getTime()==dateNow.getTime() && timeNow.getTime() < time.getTime() &&
                time.getTime()-timeNow.getTime()>fifteenMinsInMillis)) {
            for (Seat s : seats) {
                s.setAvailable(false);
                s.setName(name);
                s.setSurname(surname);
                seatRepository.save(s);
            }
            model.addAttribute("ok", true);
            model.addAttribute("date", date);
            model.addAttribute("expiration", expirationTime);
            model.addAttribute("price", price);
        } else {
            model.addAttribute("ok", false);
        }

        model.addAttribute("from", new ArrayList<>());
        model.addAttribute("to", new ArrayList<>());
        model.addAttribute("fromH", new ArrayList<>());
        model.addAttribute("toH", new ArrayList<>());
        model.addAttribute("movies", null);
        return "index";
    }

    private List<Seat> convertSeatsStringToList(String seats, Integer movieId) {
        Movie movie=null;
        if (movieRepository.findById(movieId)!=null) {
            movie = movieRepository.findById(movieId).get();
        }
        List<Seat> seatsList = new ArrayList<>();
        String seatsCommas="";

        String regex = "\\[(.*?)\\]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(seats);

        if (m.find()) {
            seatsCommas=m.group(1);
        }
        String[] seatsArr=seatsCommas.split(", ");
        for (String s : seatsArr) {
            Seat seat = seatRepository.findByNoAndMovie(s, movie);
            seatsList.add(seat);
        }
        return seatsList;
    }
}
