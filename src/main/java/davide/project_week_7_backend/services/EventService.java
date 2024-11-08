package davide.project_week_7_backend.services;

import davide.project_week_7_backend.entities.Event;
import davide.project_week_7_backend.exceptions.NotFoundException;
import davide.project_week_7_backend.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Event findById(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + id));
    }

    public Page<Event> getAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public void delete(UUID id) {
        Event event = this.findById(id);
        eventRepository.delete(event);
    }
}
