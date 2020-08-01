package br.com.central.errors.events.mappers;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.dto.EventLogResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapperWithLog {
    EventLogResponse map(Event event);
}
