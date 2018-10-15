package seedu.address.logic.parser;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.RegisterAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Role;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parse input arguments.
 */
public class RegisterAccountCommandParser implements Parser<RegisterAccountCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns a LoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RegisterAccountCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput,
                RegisterAccountCommand.PREFIX_USERNNAME, RegisterAccountCommand.PREFIX_PASSWORD,
                RegisterAccountCommand.PREFIX_ROLE);

        if (!arePrefixesPresent(argMultimap, RegisterAccountCommand.PREFIX_USERNNAME,
                RegisterAccountCommand.PREFIX_PASSWORD, RegisterAccountCommand.PREFIX_ROLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterAccountCommand.MESSAGE_USAGE));
        }

        Optional<String> username = argMultimap.getValue(RegisterAccountCommand.PREFIX_USERNNAME);
        Optional<String> password = argMultimap.getValue(RegisterAccountCommand.PREFIX_PASSWORD);
        Optional<String> role = argMultimap.getValue(RegisterAccountCommand.PREFIX_ROLE);

        if (username.isPresent() && password.isPresent() && role.isPresent()) {
            String roleName = role.get();

            if (roleName.equalsIgnoreCase("superuser")) {
                Account account = new Account(username.get(), password.get(), Role.SUPER_USER);
                return new RegisterAccountCommand(account);
            } else if (roleName.equalsIgnoreCase("readonlyuser")) {
                Account account = new Account(username.get(), password.get(), Role.READ_ONLY_USER);
                return new RegisterAccountCommand(account);
            } else {
                throw new ParseException("Role should contains only \"superuser\" or \"readonlyuser\".");
            }
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterAccountCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
