package com.yoursole1.command.annotation;

import com.yoursole1.command.CommandWrapper;
import com.yoursole1.command.exception.CommandException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public final class AnnotationProcessor {

    @Contract(pure = true)
    private AnnotationProcessor() {}

    /**
     *
     * @param clazz The class of the command registry.
     * @return {@link List<Method>} of methods annotated with {@link Command} from class annotated with {@link Commands}
     * @throws CommandException Exception thrown if clazz is not annotated with {@link Commands}
     * to indicate that it is a command registry.
     */
    @Contract(value = "_ -> new", pure = true)
    public static List<CommandWrapper> loadCommands(@NotNull Class<?> clazz) throws CommandException {
        if (!clazz.isAnnotationPresent(Commands.class)) {
            throw new CommandException("The class " + clazz.getSimpleName() + " is not annotated with Commands");
        } else {
            return Arrays
                    .stream(clazz.getMethods())
                    .filter(method -> method
                            .isAnnotationPresent(Command.class))
                    .filter(method -> method
                            .getReturnType()
                            .equals(String.class))
                    .filter(method ->
                            Arrays.stream(method.getParameterTypes())
                            .allMatch(parameter ->
                                    parameter.equals(String.class)))
                    .map(CommandWrapper::new)
                    .collect(Collectors.toList());
        }
    }
}
