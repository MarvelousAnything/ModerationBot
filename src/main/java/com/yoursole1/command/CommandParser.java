package com.yoursole1.command;

import com.yoursole1.command.annotation.AnnotationProcessor;
import com.yoursole1.command.exception.CommandException;
import com.yoursole1.command.exception.CommandNotFoundException;
import com.yoursole1.command.exception.UnableToInvokeCommandException;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.Objects;

/**
 * @author Owen Hayes
 */
@Slf4j
public final class CommandParser {

    private static List<CommandWrapper> commands;

    static {
        try {
            commands = AnnotationProcessor.loadCommands(CommandRegistry.class);
        } catch (CommandException e) {
            log.error("Commands could not be loaded", e);
        }
    }

    private CommandParser() {}

    public static List<CommandWrapper> getCommands() {
        return List.copyOf(commands);
    }

    public static CommandWrapper findCommand(String message) {
        for (CommandWrapper command : commands) {
            if (command.commandStringMatches(message)) {
                return command;
            }
        }
        return null;
    }

    public static CommandWrapper findCommand(GuildMessageReceivedEvent event) throws CommandNotFoundException {
        return findCommand(event.getMessage().getContentRaw());
    }

    public static String parse(String input) throws UnableToInvokeCommandException {
        CommandWrapper command = findCommand(input);
        if (Objects.isNull(command)) {
            return null;
        } else {
            return command.execute(input);
        }
    }

    public static String parse(GuildMessageReceivedEvent event) throws UnableToInvokeCommandException {
        String input = event.getMessage().getContentRaw();
        CommandWrapper command = findCommand(input);
        if (Objects.isNull(command)) {
            return null;
        } else if (!command.checkPermissions(event)) {
            return "Insufficient permissions";
        } else {
            return command.execute(input);
        }
    }


}
