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
    @Column(name = "available", nullable = false, columnDefinition = "boolean default true")
    private boolean available;
    private String name;
    private String surname;
    @OneToMany(mappedBy = "seat", cascade = CascadeType.MERGE)
    private List<MoviesSeats> moviesSeats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MoviesSeats> getMoviesSeats() {
        return moviesSeats;
    }

    public void setMoviesSeats(List<MoviesSeats> moviesSeats) {
        this.moviesSeats = moviesSeats;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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
