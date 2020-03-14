package pl.touk.bookingapp.db.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MoviesSeatsId implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer movieId;
    private Integer seatId;
    public MoviesSeatsId() {
    }
    public MoviesSeatsId(Integer movieId, Integer seatId) {
        super();
        this.movieId = movieId;
        this.seatId = seatId;
    }
    public Integer getMovieId() {
        return movieId;
    }
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
    public Integer getSeatId() {
        return seatId;
    }
    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((movieId == null) ? 0 : movieId.hashCode());
        result = prime * result
                + ((seatId == null) ? 0 : seatId.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MoviesSeatsId other = (MoviesSeatsId) obj;
        return Objects.equals(getMovieId(), other.getMovieId()) && Objects.equals(getSeatId(), other.getSeatId());
    }
}