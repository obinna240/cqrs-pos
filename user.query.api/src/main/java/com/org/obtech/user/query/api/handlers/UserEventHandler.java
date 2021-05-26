package com.org.obtech.user.query.api.handlers;

import com.org.obtech.user.core.events.UserRegisteredEvent;
import com.org.obtech.user.core.events.UserRemovedEvent;
import com.org.obtech.user.core.events.UserUpdatedEvent;

/**
 * This interface
 */
public interface UserEventHandler {

    void on(UserRegisteredEvent userRegisteredEvent);
    void on(UserUpdatedEvent userUpdatedEvent);
    void on(UserRemovedEvent userRemovedEvent);
}
