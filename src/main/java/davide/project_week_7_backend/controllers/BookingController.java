package davide.project_week_7_backend.controllers;

import davide.project_week_7_backend.entities.Booking;
import davide.project_week_7_backend.entities.Event;
import davide.project_week_7_backend.entities.User;
import davide.project_week_7_backend.services.BookingService;
import davide.project_week_7_backend.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private EventService eventService;

    @PostMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('NORMAL_USER', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> bookEvent(@PathVariable UUID eventId, @AuthenticationPrincipal User currentUser) {
        Event event = eventService.findById(eventId);
        Booking booking = new Booking();
        booking.setUser(currentUser);
        booking.setEvent(event);
        booking.setBookingTime(LocalDateTime.now());
        Booking savedBooking = bookingService.save(booking);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Booking successful");
        response.put("bookingId", savedBooking.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
