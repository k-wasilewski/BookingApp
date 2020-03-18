package pl.touk.bookingapp.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.touk.bookingapp.db.repos.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.touk.bookingapp.db.repos.SeatRepository;
import java.sql.Date;
import java.sql.Time;

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
    public String filterMovies(Model model, @RequestParam(name = "from", required = false) Date from,
                               @RequestParam(name = "to", required = false) Date to,
                               @RequestParam(name = "fromH", required = false) String fromHstr,
                               @RequestParam(name = "toH", required = false) String toHstr) {
        Time fromH=null;
        if (fromHstr!=null && !fromHstr.equals("")) {
            fromH = Time.valueOf(fromHstr+":00");
        }
        Time toH=null;
        if (fromHstr!=null && !toHstr.equals("")) {
            toH = Time.valueOf(toHstr+":00");
        }

        if (from!=null && !from.equals("")) {
            model.addAttribute("from", from);
        }
        if (to!=null && !to.equals("")) {
            model.addAttribute("to", to);
        }
        if (fromH!=null && !fromH.equals("")) {
            model.addAttribute("fromH", fromH);
        }
        if (toH!=null && !toH.equals("")) {
            model.addAttribute("toH", toH);
        }
        model.addAttribute("movies", movieRepository.customFindWithinDatesAndTimes(from, to, fromH, toH));
        model.addAttribute("redir", true);

        return "index";
    }
}
