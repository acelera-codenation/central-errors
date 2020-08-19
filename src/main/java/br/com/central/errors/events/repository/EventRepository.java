package br.com.central.errors.events.repository;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.QEvent;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

public interface EventRepository extends PagingAndSortingRepository<Event, Long>, QuerydslPredicateExecutor<Event>,
        QuerydslBinderCustomizer<QEvent> {

    @Override
    default void customize(QuerydslBindings bindings, QEvent event) {
        bindings.excluding(event.id);
        bindings.bind(event.date).first((path, value) -> path.eq(value));

        bindings.bind(event.quantity).all((path, value) -> {
            Iterator<? extends Integer> it = value.iterator();
            if (value.size() == 2) {
                Integer from = it.next();
                Integer to = it.next();
                return Optional.of(path.between(from, to));
            } else {
                return Optional.of(path.in(value.stream().collect(Collectors.toList())));
            }
        });

        bindings.bind(String.class).first(
                (StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
