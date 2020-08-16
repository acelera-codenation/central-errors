package br.com.central.errors.events.entity;

import br.com.central.errors.events.entity.enums.Level;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity(name = "event")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "level_error")
    @Enumerated(EnumType.STRING)
    private Level level;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "log_error")
    private String log;

    @NotNull
    private String origin;

    @NotNull
    @Column(name = "date_error")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    @NotNull
    private Integer quantity;

}
