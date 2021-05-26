package com.org.obtech.user.cmd.api.commands;

import com.org.obtech.user.core.models.User;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    private String id;
    @Valid
    @NotNull(message = "user cannot be null or blank")
    private User user;
}
