package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns a LoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public LoginCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput,
                LoginCommand.PREFIX_USERNNAME, LoginCommand.PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, LoginCommand.PREFIX_USERNNAME,
                LoginCommand.PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        Optional<String> username = argMultimap.getValue(LoginCommand.PREFIX_USERNNAME);
        Optional<String> password = argMultimap.getValue(LoginCommand.PREFIX_PASSWORD);

        if (username.isPresent() && password.isPresent()) {
            return new LoginCommand(username.get(), password.get());
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
