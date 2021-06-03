package com.org.obtech.user.cmd.api.controller;

import com.org.obtech.user.cmd.api.commands.RemoveUserCommand;
import com.org.obtech.user.core.dto.BaseResponse;
import com.org.obtech.user.cmd.api.dto.RegisterUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/removeuser")
public class RemoveUserController {

    private final CommandGateway commandGateway;

    @Autowired
    public RemoveUserController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE)")
    public ResponseEntity<BaseResponse> removeUser(@PathVariable("id") String id) {
        try {
            var newRemoveUserCommand = new RemoveUserCommand(id);
            commandGateway.sendAndWait(newRemoveUserCommand);

            return new ResponseEntity<>(new BaseResponse("user was removed"), HttpStatus.OK);
        } catch (Exception exception) {
            var safeErrorMessage = "Error processing remove user "+ id;
            log.error(exception.getMessage());
            return new ResponseEntity<>(new RegisterUserResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
