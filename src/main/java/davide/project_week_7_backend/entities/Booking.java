package davide.project_week_7_backend.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"organizedEvents", "password", "authorities"})
    private User user;
    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnoreProperties({"bookings", "organizer"})
    private Event event;
    private LocalDateTime bookingTime;
}
