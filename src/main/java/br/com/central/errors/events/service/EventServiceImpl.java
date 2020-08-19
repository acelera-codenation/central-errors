package br.com.central.errors.events.service;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.repository.EventRepository;
import br.com.central.errors.events.service.interfaces.EventServiceInterface;
import com.querydsl.core.types.Predicate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventServiceInterface {

    private EventRepository repository;

    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    @Cacheable(value = "event", key = "#id", unless = "#result == null")
    public Event findById(Long id) {
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public <S extends Event> S save(S event) {
        return repository.save(event);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Cacheable(value = "event", key = "#predicate", unless = "#result == null")
    public Page<Event> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

}
