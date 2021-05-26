package com.org.obtech.user.cmd.api.aggregates;

import com.org.obtech.user.cmd.api.commands.RegisterUserCommand;
import com.org.obtech.user.cmd.api.commands.RemoveUserCommand;
import com.org.obtech.user.cmd.api.commands.UpdateUserCommand;
import com.org.obtech.user.cmd.api.security.PasswordEncoder;
import com.org.obtech.user.cmd.api.security.PasswordEncoderImpl;
import com.org.obtech.user.core.events.UserRegisteredEvent;
import com.org.obtech.user.core.events.UserRemovedEvent;
import com.org.obtech.user.core.events.UserUpdatedEvent;
import com.org.obtech.user.core.models.User;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

//axon requires two construcors taking one command and taking none
//the first contructror takes the command handler annotation signifying that
//it will handle the command and generate an event
//we can have additional command handlers to handle other commands
@Aggregate
public class UserAggregates {
    @AggregateIdentifier
    private String id;
    private User user;
    private final PasswordEncoder passwordEncoder;

    //axon requires two construcors taking one command and taking none
    //the first contructror takes the command handler annotation signifying that
    //it will handle the command and generate an event
    //we can have additional command handlers to handle other commands
    public UserAggregates(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @CommandHandler
    public UserAggregates(RegisterUserCommand command) {

        var newUser = command.getUser();
        var password = newUser.getAccount().getPassword();
        passwordEncoder = new PasswordEncoderImpl(); //make this a bean
        var hashPassword = passwordEncoder.hashPassword(password);

        newUser.setId(command.getId());
        newUser.getAccount().setPassword(hashPassword);

        //create a new event from the command
        var event = UserRegisteredEvent.builder()
                .id(command.getId())
                .user(newUser)
                .build();

        //this stores the event in the event store and publishes it in the event bus
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateUserCommand command) {
        //handle the command
        var updatedUser = command.getUser();
        updatedUser.setId(command.getId());
        var password = updatedUser.getAccount().getPassword();
        var hashPassword = passwordEncoder.hashPassword(password);

        //generate the event using a new uuid, this is the event we want to generate
        //remember that we use this event to rebuild the request in the event of issues
        //so we generate an event with an id, a unique id and the user
        var event = UserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updatedUser)
                .build();

        //store the user updated event in the event store and then push it to the event bus
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemoveUserCommand command) {
        //create a new event
        var event = new UserRemovedEvent();

        //set the event id for the command
        event.setId(command.getId());

        //store the event in the event store and push to the bus
        AggregateLifecycle.apply(event);
    }

    //once commands are handled, events will be raised so we need Event sourcing handlers as well
    @EventSourcingHandler
    public void on(UserUpdatedEvent userUpdatedEvent) {
        //update the user field of the aggregate
        //we only update the user because the id was selected in the
        //update user command

        //in a sense, what we are doing here is modifying the state of the aggregate
        this.user = userUpdatedEvent.getUser();
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent userRegisteredEvent) {
        this.id = userRegisteredEvent.getId();
        this.user = userRegisteredEvent.getUser();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent userRemovedEvent) {

        //delete the aggregate
        AggregateLifecycle.markDeleted();
    }
}
