package com.org.obtech.user.query.api.handlers;

import com.org.obtech.user.core.events.UserRegisteredEvent;
import com.org.obtech.user.core.events.UserRemovedEvent;
import com.org.obtech.user.core.events.UserUpdatedEvent;
import com.org.obtech.user.query.api.repositories.UserRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

/**
 * processing group is similar to a consumer group in kafka, which means
 * that a cosumer or event handler in this case consumes an event, Axon will track the offset to ensure that
 * within a given processing group that you will always consume the latest event.
 * Axon manages the consumed offset for each processing group separately
 */
@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

    private final UserRepository userRepository;

    public UserEventHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //add the event handler to perform an action when the event is
    //consumed from the event bus
    @EventHandler
    @Override
    public void on(UserRegisteredEvent userRegisteredEvent) {
        userRepository.save(userRegisteredEvent.getUser());
    }

    @EventHandler
    @Override
    public void on(UserUpdatedEvent userUpdatedEvent) {
        userRepository.save(userUpdatedEvent.getUser());
    }

    @EventHandler
    @Override
    public void on(UserRemovedEvent userRemovedEvent) {
        userRepository.deleteById(userRemovedEvent.getId());
    }
}
