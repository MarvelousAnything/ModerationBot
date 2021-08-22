package com.yoursole1.command;

import com.yoursole1.command.annotation.Command;
import com.yoursole1.command.exception.UnableToInvokeCommandException;
import com.yoursole1.command.exception.NotACommandException;
import com.yoursole1.service.AdminService;
import com.yoursole1.service.ModeratorService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

@Slf4j
@Getter
public class CommandWrapper {
    private final Method method;
    private final Command command;

    public CommandWrapper(Method method) throws NotACommandException {
        if (!isCommand(method)) {
            throw new NotACommandException();
        }
        this.method = method;
        this.command = method.getAnnotation(Command.class);
    }

    public static boolean isCommand(AnnotatedElement element) {
        return element.isAnnotationPresent(Command.class);
    }

    public String[] getParameters(String input) {
        if (method.getParameterCount() > 0) {
            if (input.startsWith(getCommandString())) {
                input = input.replace(getCommandString() + " ", "");
            }
            return input.split(" ", method.getParameterCount());
        } else {
            return new String[]{};
        }
    }

    public String execute(Object[] parameters) throws UnableToInvokeCommandException {
        try {
            if (parameters.length == 0) {
                return (String) method.invoke(null);
            }
            if (parameters.length != method.getParameterCount()) {
                return "Wrong number of arguments, expected "
                        + method.getParameterCount()
                        + " but got " + parameters.length;
            } else {
                return (String) method.invoke(null, parameters);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error("Unable to invoke command", new UnableToInvokeCommandException(e));
            throw new UnableToInvokeCommandException(e);
        }
    }

    public String execute(String input) throws UnableToInvokeCommandException {
        return execute(getParameters(input));
    }

    public boolean checkPermissions(GuildMessageReceivedEvent event) {
        return switch (command.permissions().toLowerCase(Locale.ROOT)) {
            case "moderator" -> ModeratorService.getInstance()
                    .contains(event.getMember()) ||
            AdminService.getInstance()
                    .contains(event.getMember());
            case "none" -> true;
            default -> AdminService.getInstance()
                    .contains(event.getMember());
        };
    }

    public String getName() {
        return method.getName();
    }

    public String getPrefix() {
        return command.prefix();
    }

    public String getCommandString() {
        return getPrefix() + getName();
    }

    public String getHelpText() {
        return command.helpText();
    }

    public String getExampleText() {
        return command.exampleText();
    }

    public boolean commandStringMatches(String input) {
        return input.startsWith(getCommandString());
    }
}
