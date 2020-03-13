package pl.touk.bookingapp.db.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="rooms")
public class Room {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Seat> seats;

    public List<Seat> getSeats() {
        return seats;
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

    @Override
    public String toString() {
        return "room: "+this.name;
    }
}
