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
import java.util.ArrayList;
import java.util.List;

@Controller
public class DetailsController {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    SeatRepository seatRepository;

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
