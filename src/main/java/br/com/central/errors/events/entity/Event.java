package br.com.central.errors.events.entity;

import br.com.central.errors.events.entity.enums.Level;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;


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
    @Column(name = "level_event")
    @ApiModelProperty(example = "INFO")
    private Level level;

    @NotNull
    @ApiModelProperty(example = "Spring Data repositories…")
    private String description;

    @NotNull
    @Column(name = "log_event")
    @ApiModelProperty(example = "Retrograde clock change detected , soft-evicting connections from pool.")
    private String log;

    @NotNull
    @Size(max = 100)
    @ApiModelProperty(example = "DispatcherServlet")
    private String origin;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_event")
    @ApiModelProperty(example = "2020-08-16")
    private LocalDate date;

    @NotNull
    @ApiModelProperty(example = "1")
    private Integer quantity;

}
