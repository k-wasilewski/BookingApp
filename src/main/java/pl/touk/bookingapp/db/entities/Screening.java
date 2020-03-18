package pl.touk.bookingapp.db.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="screenings")
public class Screening {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "room")
    private Room room;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "movie")
    private Movie movie;
    private Date date;
    private Time time;
    @OneToMany(mappedBy = "screening", fetch = FetchType.LAZY)
    private List<Seat> seats;

    public Screening() {}

    public Screening(Room room, Movie movie, Date date, Time time) {
        this.room=room;
        this.movie=movie;
        this.date=date;
        this.time=time;
    }

    public Integer getId() {return this.id;}
    public void setId(Integer id) {this.id=id;}
    public Room getRoom() {return this.room;}
    public void setRoom(Room room) {this.room=room;}
    public Movie getMovie() {return this.movie;}
    public void setMovie(Movie movie) {this.movie=movie;}
    public Date getDate() {return this.date;}
    public void setDate(Date date) {this.date=date;}
    public Time getTime() {return this.time;}
    public void setTime(Time time) {this.time=time;}
    public void setSeats(List<Seat> seats) {this.seats=seats;}
    public List<Seat> getSeats() {return this.seats;}

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

    @Override
    public String toString() {return this.movie+", ("+this.date+", "+this.time+"), "+this.room;}
}
