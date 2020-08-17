package br.com.central.errors.events.entity.dto;

import br.com.central.errors.events.entity.enums.Level;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel(value = "EventDTO")
@NoArgsConstructor
public class EventDTO {

    @ApiModelProperty(example = "1", hidden = true)
    private Long id;

    @NotNull(message = "{NotNull.eventLogDTO.level}")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(example = "INFO", required = true)
    private Level level;

    @NotBlank(message = "{NotBlank.eventLogDTO.description}")
    @Size(max = 255, message = "{Max.eventLogDTO.description}")
    @ApiModelProperty(example = "Triggering deferred initialization of Spring Data repositories…", required = true)
    private String description;

    @NotBlank(message = "{NotBlank.eventLogDTO.origin}")
    @Size(min = 0, max = 100, message = "{Max.eventLogDTO.origin}")
    @ApiModelProperty(example = "o.s.web.servlet.DispatcherServlet", required = true)
    private String origin;

    @NotNull(message = "{NotNull.eventLogDTO.date}")
    @ApiModelProperty(example = "2020-08-16", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "{NotNull.eventLogDTO.quantity}")
    @Positive
    @Min(value = 1, message = "{Min.eventLogDTO.quantity}")
    @ApiModelProperty(example = "1", required = true)
    private Integer quantity;
}
