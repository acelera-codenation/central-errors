package br.com.central.errors.events.repository;

import br.com.central.errors.events.entity.Event;
import br.com.central.errors.events.entity.QEvent;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event>,
        QuerydslBinderCustomizer<QEvent> {

    @Override
    default public void customize(QuerydslBindings bindings, QEvent root) {
        bindings.bind(String.class).first(
                (StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
