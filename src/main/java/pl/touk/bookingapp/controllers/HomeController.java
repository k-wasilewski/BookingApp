package pl.touk.bookingapp.controllers;

import pl.touk.bookingapp.db.entities.Movie;
import pl.touk.bookingapp.db.repos.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    MovieRepository movieRepository;

    @RequestMapping("/")
    public String homeView(Model model) {
        model.addAttribute("movies", movieRepository.findAllByIdNotNull());
        return "index";
    }

    @GetMapping("/get")
    public String delDonation(Model model, @RequestParam("id") int id) {
        Movie movie = movieRepository.findById(id);
        return "";
    }
}
