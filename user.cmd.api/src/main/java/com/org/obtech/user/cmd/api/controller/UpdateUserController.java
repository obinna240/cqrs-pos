package com.org.obtech.user.cmd.api.controller;

import com.org.obtech.user.cmd.api.commands.UpdateUserCommand;
import com.org.obtech.user.core.dto.BaseResponse;
import com.org.obtech.user.cmd.api.dto.RegisterUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/updateuser")
public class UpdateUserController {

    private final CommandGateway commandGateway;

    @Autowired
    public UpdateUserController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE)")
    public ResponseEntity<BaseResponse> updateUser(@Valid @RequestBody UpdateUserCommand updateUserCommand, @PathVariable("id") String id) {
        try {
            //set the id in the command
            updateUserCommand.setId(id);
            commandGateway.sendAndWait(updateUserCommand);

            return new ResponseEntity<>(new BaseResponse("Successful update"), HttpStatus.OK);
        } catch (Exception exception) {
            var safeErrorMessage = "Error processing user update for "+ id;
            log.error(exception.getMessage());
            return new ResponseEntity<>(new RegisterUserResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
