package com.org.obtech.user.core.events;

import com.org.obtech.user.core.models.User;
import lombok.Builder;
import lombok.Data;

//Events are raised in the command and handled in the query api
@Data
@Builder
public class UserRegisteredEvent {
    private String id;
    private User user;
}
