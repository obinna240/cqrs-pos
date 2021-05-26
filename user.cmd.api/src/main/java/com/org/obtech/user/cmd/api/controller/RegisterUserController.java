package com.org.obtech.user.cmd.api.controller;

import com.org.obtech.user.cmd.api.commands.RegisterUserCommand;
import com.org.obtech.user.cmd.api.dto.RegisterUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/register")
public class RegisterUserController {

    /**
     * The commandgateway is a command dispatching mechanism that will dispatch
     * the registeruser command and once dispatched will invoke the command handling constructor
     * for the register user command handler.
     */
    private final CommandGateway commandGateway;

    @Autowired
    public RegisterUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserCommand registerUserCommand) {
        //generate a random uuid for the command
        registerUserCommand.setId(UUID.randomUUID().toString());
        try {
            //using send again here is kinda similar to Kafka acknowledgemet where
            //when it is 0, the producer does not wait for an acknowledgement, when it is 1, it does
            //sendAndWait, sends the request and waits for a response
            //send simply sends and assumes that the command has been handled
            commandGateway.sendAndWait(registerUserCommand); //we ca also include a timeout value here
            return new ResponseEntity<>(new RegisterUserResponse("Successfully registered"), HttpStatus.CREATED);
        } catch (Exception exception) {
            var safeErrorMessage = "Error processing user request for id = "+ registerUserCommand.getId();
            log.error(exception.getMessage());
            return new ResponseEntity<>(new RegisterUserResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
