package davide.project_week_7_backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime date;
    private String location;
    private int totalSeats;
    private int availableSeats;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "organizer_id")
    private User organizer;
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("event")
    private List<Booking> bookings;
}
