package davide.project_week_7_backend.controllers;

import davide.project_week_7_backend.entities.Event;
import davide.project_week_7_backend.entities.User;
import davide.project_week_7_backend.payloads.EventDTO;
import davide.project_week_7_backend.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public Page<Event> getAll(
            @PageableDefault(page = 0, size = 10, sort = "date", direction = Sort.Direction.DESC) 
            Pageable pageable) {
        return eventService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable UUID id) {
        return eventService.findById(id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('EVENT_ORGANIZER', 'ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Event create(@RequestBody @Validated EventDTO body, @AuthenticationPrincipal User currentUser) {
        Event event = new Event();
        event.setTitle(body.getTitle());
        event.setDescription(body.getDescription());
        event.setDate(body.getDate());
        event.setLocation(body.getLocation());
        event.setTotalSeats(body.getTotalSeats());
        event.setAvailableSeats(body.getTotalSeats());
        event.setOrganizer(currentUser);
        return eventService.save(event);
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyAuthority('EVENT_ORGANIZER', 'ADMIN')")
    public Event update(@PathVariable UUID id, @RequestBody @Validated EventDTO body) {
        Event event = eventService.findById(id);
        event.setTitle(body.getTitle());
        event.setDescription(body.getDescription());
        event.setDate(body.getDate());
        event.setLocation(body.getLocation());
        event.setTotalSeats(body.getTotalSeats());
        return eventService.save(event);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyAuthority('EVENT_ORGANIZER', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        eventService.delete(id);
    }
}
