package br.com.central.errors.events.service.interfaces;

import br.com.central.errors.events.entity.Event;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventServiceInterface {
    Event findById(Long id);

    <S extends Event> S save(S event);

    void delete(Long id);

    Page<Event> findAll(Predicate predicate, Pageable pageable);
}
