package br.com.central.errors.events.mappers;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.dto.EventRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventRequestMapper {

    Event map(EventRequest eventRequest);
    Event updateEvent(EventRequest request, @MappingTarget Event event);

}
