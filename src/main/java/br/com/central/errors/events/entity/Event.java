package br.com.central.errors.events.entity;

import br.com.central.errors.events.entity.enums.Level;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(example = "1")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(example = "INFO")
    @Column(name = "level_event")
    private Level level;

    @NotNull
    @ApiModelProperty(example = "Triggering deferred initialization of Spring Data repositories…")
    private String description;

    @NotNull
    @ApiModelProperty(example = "Retrograde clock change detected , soft-evicting connections from pool.")
    @Column(name = "log_event")
    private String log;

    @NotNull
    @ApiModelProperty(example = "o.s.web.servlet.DispatcherServlet")
    @Size(max = 100)
    private String origin;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(example = "2020-08-16")
    @Column(name = "date_event")
    private LocalDate date;

    @NotNull
    @ApiModelProperty(example = "1")
    private Integer quantity;

}
