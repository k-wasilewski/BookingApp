package pl.touk.bookingapp.db.entities;

import javax.persistence.*;

@Entity
@Table(name="mv_st")
public class MoviesSeats {
    @EmbeddedId
    private MoviesSeatsId id = new MoviesSeatsId();
    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("movieId")
    private Movie movie;
    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("seatId")
    private Seat seat;

    public MoviesSeatsId getId() {
        return id;
    }
    public void setId(MoviesSeatsId id) {
        this.id = id;
    }
    public Movie getMovie() {
        return movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public Seat getSeat() {
        return seat;
    }
    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
