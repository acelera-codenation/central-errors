package br.com.central.errors.events.service;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.repository.EventRepository;
import br.com.central.errors.events.service.interfaces.EventServiceInterface;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService implements EventServiceInterface {

    private EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    @Override
    public List<Event> findAll() {
        return repository.findAll();
    }

    @Override
    public <S extends Event> S save(S event) {
        return repository.save(event);
    }
    
}
