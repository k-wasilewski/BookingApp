package pl.touk.bookingapp.db.dao;

import org.springframework.stereotype.Repository;
import pl.touk.bookingapp.db.entities.Room;
import pl.touk.bookingapp.db.entities.Seat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class SeatDao {
    @PersistenceContext
    EntityManager entityManager;

    public void saveSeat(Seat seat) {
        entityManager.persist(seat);
    }

    public void updateSeat(Seat seat) {
        entityManager.merge(seat);
    }

    public Seat getSeat(Integer id) {
        return entityManager.find(Seat.class, id);
    }
}

