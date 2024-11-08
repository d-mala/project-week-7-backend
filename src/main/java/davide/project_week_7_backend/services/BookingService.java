package davide.project_week_7_backend.services;

import davide.project_week_7_backend.entities.Booking;
import davide.project_week_7_backend.entities.Event;
import davide.project_week_7_backend.exceptions.BadRequestException;
import davide.project_week_7_backend.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private EventService eventService;

    public Booking save(Booking booking) {
        Event event = booking.getEvent();
        if (event.getAvailableSeats() <= 0) {
            throw new BadRequestException("No available seats for this event");
        }
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventService.save(event);
        return bookingRepository.save(booking);
    }
}
