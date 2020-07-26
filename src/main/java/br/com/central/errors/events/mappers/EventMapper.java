package br.com.central.errors.events.mappers;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.dto.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "level", target = "level"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "origin", target = "origin"),
            @Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd HH:mm"),
            @Mapping(source = "quantity", target = "quantity")
    })

    EventResponse map(Event event);
    List<EventResponse> map(List<Event> events);
}
