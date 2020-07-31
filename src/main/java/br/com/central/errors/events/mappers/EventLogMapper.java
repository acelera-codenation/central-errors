package br.com.central.errors.events.mappers;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.dto.EventLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EventLogMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "level", target = "level"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "log", target = "log"),
            @Mapping(source = "origin", target = "origin"),
            @Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd HH:mm"),
            @Mapping(source = "quantity", target = "quantity")
    })

    EventLogResponse map(Event event);
}
