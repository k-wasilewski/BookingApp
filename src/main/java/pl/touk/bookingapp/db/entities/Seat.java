package pl.touk.bookingapp.db.entities;

import javax.persistence.*;

@Entity
@Table(name="seats")
public class Seat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String no;
    @Column(name = "available", nullable = false, columnDefinition = "boolean default true")
    private boolean available;

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
