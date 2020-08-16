package br.com.central.errors.events.mappers;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.dto.EventDTO;
import br.com.central.errors.events.entity.dto.EventLogDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface Mappers {
    EventDTO toDTO(Event event);

    EventLogDTO toLogDTO(Event event);

    @Mapping(target = "id", ignore = true)
    Event toEvent(EventLogDTO event);

    @Mapping(target = "id", ignore = true)
    Event updateEvent(EventLogDTO request, @MappingTarget Event event);
}
