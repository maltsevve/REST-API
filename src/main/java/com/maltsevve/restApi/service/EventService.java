package com.maltsevve.restApi.service;

import com.maltsevve.restApi.model.Event;
import com.maltsevve.restApi.repository.EventRepository;
import com.maltsevve.restApi.repository.JavaIOEventRepositoryImpl;

import java.util.List;

public class EventService {
    private final EventRepository EventRepository = new JavaIOEventRepositoryImpl();

    public Event save(Event event) {
        return EventRepository.save(event);
    }

    public Event update(Event event) {
        return EventRepository.update(event);
    }

    public Event getById(Long aLong) {
        return EventRepository.getById(aLong);
    }

    public List<Event> getAll() {
        return EventRepository.getAll();
    }

    public void deleteById(Long aLong) {
        EventRepository.deleteById(aLong);
    }
}
