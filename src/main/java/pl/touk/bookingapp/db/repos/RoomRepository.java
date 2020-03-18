package pl.touk.bookingapp.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.touk.bookingapp.db.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Room getById(Integer id);
}
