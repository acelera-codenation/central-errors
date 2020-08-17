package br.com.central.errors.events.mappers;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.dto.EventDTO;
import br.com.central.errors.events.entity.dto.EventLogDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface Mappers {
    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd")
    EventDTO toDTO(Event event);

    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd")
    EventLogDTO toLogDTO(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd")
    Event toEvent(EventLogDTO event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd")
    Event updateEvent(EventLogDTO request, @MappingTarget Event event);
}
