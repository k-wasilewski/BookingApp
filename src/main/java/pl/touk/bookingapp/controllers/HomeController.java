package pl.touk.bookingapp.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import pl.touk.bookingapp.db.entities.Movie;
import pl.touk.bookingapp.db.entities.Seat;
import pl.touk.bookingapp.db.repos.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.touk.bookingapp.db.repos.SeatRepository;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class HomeController {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    SeatRepository seatRepository;

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
        model.addAttribute("redir", true);

        return "index";
    }

    @PostMapping("/details")
    public String movieDetails(@RequestParam("id") int id, @RequestParam("seatsNo") String seatsNoStr, Model model) {
        int numberOfSeats=0;
        if (!seatsNoStr.equals("")) numberOfSeats = Integer.parseInt(seatsNoStr);
        else {
            model.addAttribute("numberError", true);
            return "index";
        }

        Movie movie = movieRepository.findById(id);
        model.addAttribute("movie", movie);
        model.addAttribute("seatsNo", numberOfSeats);
        List<Seat> availableSeats1 = getAvailableSeats(movie, numberOfSeats).get(0);
        if (!availableSeats1.isEmpty()) model.addAttribute("availableSeats1", availableSeats1);
        List<Seat> availableSeats2 = getAvailableSeats(movie, numberOfSeats).get(1);
        if (!availableSeats2.isEmpty()) model.addAttribute("availableSeats2", availableSeats2);

        return "movieDetails";
    }

    @PostMapping("/book")
    public String bookingView(Model model, @RequestParam("movieId") int movieId, HttpServletRequest request,
                              @RequestParam("seatsNo") int seatsNo) {
        List<Seat> availableSeats1 = convertSeatsStringToList(request.getParameter("availableSeats1"), movieId);
        List<Seat> availableSeats2 = convertSeatsStringToList(request.getParameter("availableSeats2"), movieId);
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
            };
            if (availableSeats1.contains(s) && (list==0||list==1)) list = 1;
            else if (availableSeats2.contains(s) && (list==0||list==2)) list = 2;
            else listsError=true;
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
                    request.getParameter("availableSeats"), movieId));
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
        if (surname.contains("-") && !Character.isUpperCase(surname.charAt(surname.indexOf("-")+1))) {
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

    /**
     * find two sets of available number of seats right before and after already booked seats
     */
    private List<List<Seat>> getAvailableSeats(Movie movie, int numberOfSeats) {
        List<Seat> allAvailableSeats = movie.getAvailableSeats();
        List<Seat> allUnavailableSeats = movie.getUnvailableSeats();
        List<List<Seat>> availableSeatsLists = new ArrayList<>();
        List<Seat> availableSeats1 = new ArrayList<>();
        List<Seat> availableSeats2 = new ArrayList<>();

        int positionsInRow=1;
        for (Seat s : seatRepository.findAllByIdNotNull()) {
            if (s.getPos()>positionsInRow) {
                positionsInRow=s.getPos();
            }
        }

        char highestRow='A';
        for (Seat s : seatRepository.findAllByIdNotNull()) {
            if (s.getRow()>highestRow) highestRow=s.getRow();
        }

        /*
         * find the list of seats (size=numberOfSeats) right before already booked seats
         */
        if (allUnavailableSeats.isEmpty()) {
            availableSeats1=allAvailableSeats;
        } else if (allAvailableSeats.size()>=numberOfSeats) {
            int firstPos = allUnavailableSeats.get(0).getPos();
            char firstRow = allUnavailableSeats.get(0).getRow();
            if (firstPos>numberOfSeats) {
                firstPos-=(numberOfSeats);
            } else if (firstRow!='A') {
                firstRow = (char) (firstRow-1);
                firstPos = seatRepository.findAllByRow(firstRow).get(seatRepository.
                        findAllByRow(firstRow).size()-1-numberOfSeats).getPos();
            } else {
                firstPos=0;
            }

            if (firstPos!=0) {
                for (int i=0; i<numberOfSeats; i++) {
                    char row = firstRow;
                    int pos = firstPos+i;
                    Seat toAdd = seatRepository.findByMovieAndRowAndPos(movie, row, pos);
                    if (toAdd!=null) availableSeats1.add(toAdd);
                }
            }

            /*
             * find the list of seats (size=numberOfSeats) right after already booked seats
             */
            int lastPos = allUnavailableSeats.get(allUnavailableSeats.size()-1).getPos();
            char lastRow = allUnavailableSeats.get(allUnavailableSeats.size()-1).getRow();

            //are there free numberOfSeats in this row
            if (lastPos<seatRepository.findAllByRow(lastRow).get(seatRepository.
                    findAllByRow(lastRow).size()-numberOfSeats).getPos()) {
                lastPos+=1;
            } else if (lastRow!=highestRow) {
                lastRow += 1;
                lastPos = 1;
            } else {
                lastRow='.';
            }

            if (lastRow!='.') {
                for (int i=0; i<numberOfSeats; i++) {
                    char row = lastRow;
                    int pos = lastPos+i;
                    Seat toAdd = seatRepository.findByMovieAndRowAndPos(movie, row, pos);
                    if (toAdd!=null) availableSeats2.add(toAdd);
                }
            }

            if (availableSeats1.size()<numberOfSeats) availableSeats1.clear();
            if (availableSeats2.size()<numberOfSeats) availableSeats2.clear();
        }
        availableSeatsLists.add(availableSeats1);
        availableSeatsLists.add(availableSeats2);

        return availableSeatsLists;
    }
}
