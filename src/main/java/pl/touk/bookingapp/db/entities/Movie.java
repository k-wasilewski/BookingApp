package pl.touk.bookingapp.db.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Seat> seats;

    public Movie() {}

    public Movie(String name, Date date, Time time, Room room) {
        this.name=name;
        this.date=date;
        this.time=time;
        this.room=room;
        this.seats=this.room.getSeats();
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

    public List<Seat> getSeats() {
        return seats;
    }

    public List<Seat> getAvailableSeats() {
        List<Seat> availableSeats = new ArrayList<>();
        for (Seat s: this.seats) {
            if (s.isAvailable()) {
                availableSeats.add(s);
            }
        }
        return availableSeats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
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
