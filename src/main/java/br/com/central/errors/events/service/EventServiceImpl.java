package br.com.central.errors.events.service;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.repository.EventRepository;
import br.com.central.errors.events.service.interfaces.EventServiceInterface;
import br.com.central.errors.infrastructure.exception.CustomNotFoundException;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventServiceImpl implements EventServiceInterface {

    private EventRepository repository;

    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    @Cacheable(value = "event", key = "#id", unless = "#result == null")
    public Event findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CustomNotFoundException(Event.class));
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
