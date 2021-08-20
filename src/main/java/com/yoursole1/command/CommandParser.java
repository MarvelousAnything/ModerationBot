package com.yoursole1.command;

import com.yoursole1.command.annotation.AnnotationProcessor;
import com.yoursole1.command.exception.CommandException;
import com.yoursole1.command.exception.CommandNotFoundException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Owen Hayes
 */
public class CommandParser {

    private static List<CommandWrapper> commands;

    static {
        try {
            commands = AnnotationProcessor.loadCommands(CommandRegistry.class);
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    public static List<CommandWrapper> getCommands() {
        return List.copyOf(commands);
    }

    public static CommandWrapper findCommand(String message) throws CommandNotFoundException {
        for (CommandWrapper command : commands) {
            if (command.commandStringMatches(message)) {
                return command;
            }
        }
        throw new CommandNotFoundException();
    }

    public static CommandWrapper findCommand(GuildMessageReceivedEvent event) throws CommandNotFoundException {
        return findCommand(event.getMessage().getContentRaw());
    }

    public static String parse(String input) throws
            InvocationTargetException,
            IllegalAccessException {
        CommandWrapper command = findCommand(input);
        return command.execute(input);
    }

    public static String parse(GuildMessageReceivedEvent event) throws
            InvocationTargetException,
            IllegalAccessException {
        return parse(event.getMessage().getContentRaw());
    }


}
