package pl.touk.bookingapp.db.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="movies")
public class Movie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @NotBlank
    private String name;
    private Date date;
    private Time time;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "room")
    private Room room;
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<Seat> seats;

    public Movie() {}

    public Movie(String name, Date date, Time time, Room room) {
        this.name=name;
        this.date=date;
        this.time=time;
        this.room=room;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Seat getSeatById(int id) {
        for(Seat s: this.seats) {
            if (s.getId()==id) {
                return s;
            }
        }
        return null;
    }

    public List<Seat> getAvailableSeats() {
        List<Seat> availableSeats = this.seats.stream()
                .filter((seat -> seat.isAvailable()))
                .collect(Collectors.toList());
        return availableSeats;
    }

    public List<Seat> getUnvailableSeats() {
        List<Seat> unavailableSeats = this.seats.stream()
                .filter((seat -> !seat.isAvailable()))
                .collect(Collectors.toList());
        return unavailableSeats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "'"+this.name+"', ("+this.date+", "+this.time+")";
    }
}
