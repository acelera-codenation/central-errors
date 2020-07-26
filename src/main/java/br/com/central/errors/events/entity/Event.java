package br.com.central.errors.events.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer level;

    @NotNull
    private String description;

    @NotNull
    private String log;

    @NotNull
    private String origin;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Integer quantity;
}
