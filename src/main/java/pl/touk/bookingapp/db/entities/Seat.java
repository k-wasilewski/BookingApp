package pl.touk.bookingapp.db.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="seats")
public class Seat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String no;
    private String name;
    private String surname;
    @OneToMany(mappedBy = "seat", fetch = FetchType.LAZY)
    private List<MovieSeat> movieSeats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieSeat> getMovieSeats() {
        return movieSeats;
    }

    public void setMovieSeats(List<MovieSeat> movieSeats) {
        this.movieSeats = movieSeats;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return this.no;
    }
}
