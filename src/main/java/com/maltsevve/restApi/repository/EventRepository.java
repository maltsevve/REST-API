package com.maltsevve.restApi.repository;

import com.maltsevve.restApi.model.Event;

import java.util.List;

public interface EventRepository extends GenericRepository<Event, Long> {
    @Override
    Event save(Event event);

    @Override
    Event update(Event event);

    @Override
    Event getById(Long aLong);

    @Override
    List<Event> getAll();

    @Override
    void deleteById(Long aLong);
}
