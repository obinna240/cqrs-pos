package com.org.obtech.user.cmd.api.commands;

import com.org.obtech.user.core.models.User;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * The Register command handles commands to register a user
 * It is important that the id of the command is annotated with
 * @TargetAggregateIdentifier, for Axon to know which instance of an aggregate type
 * will handle the command message, so this field carrying the id must be annotated
 * Additionally the command should carry enough information to enable the aggregate handle the command
 * or carry out the action intended.
 * To this end, we need the User that needs to be registered which is added in the RegisterUserCommand
 */
@Data
@Builder
public class RegisterUserCommand {
    @TargetAggregateIdentifier
    private String id;
    private User user;
}
