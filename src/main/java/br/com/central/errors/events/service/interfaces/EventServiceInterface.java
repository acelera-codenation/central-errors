package br.com.central.errors.events.service.interfaces;

import br.com.central.errors.events.entity.Event;

import java.util.List;

public interface EventServiceInterface {
    Event findById(Long id);
    List<Event> findAll();
    <S extends Event> S save(S event);
}
