package pl.touk.bookingapp.db.dao;

import org.springframework.stereotype.Repository;
import pl.touk.bookingapp.db.entities.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class RoomDao {
    @PersistenceContext
    EntityManager entityManager;

    public void saveRoom(Room room) {
        entityManager.persist(room);
    }

    public void updateRoom(Room room) {
        entityManager.merge(room);
    }

    public Room getRoom(Integer id) {
        return entityManager.find(Room.class, id);
    }
}

