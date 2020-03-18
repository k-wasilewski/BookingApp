package pl.touk.bookingapp.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import pl.touk.bookingapp.db.repos.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.touk.bookingapp.db.repos.SeatRepository;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
import java.time.format.DateTimeFormatter;

@Controller
public class IndexController {
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
}
