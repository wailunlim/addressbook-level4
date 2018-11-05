package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.RegisterAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Role;

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RegisterAccountCommand.MESSAGE_USAGE));
        }

        Optional<String> username = argMultimap.getValue(RegisterAccountCommand.PREFIX_USERNNAME);
        Optional<String> password = argMultimap.getValue(RegisterAccountCommand.PREFIX_PASSWORD);
        Optional<String> role = argMultimap.getValue(RegisterAccountCommand.PREFIX_ROLE);

        if (username.isPresent() && password.isPresent() && role.isPresent()) {
            String roleName = role.get();

            ensureFieldNotEmptyString(username, RegisterAccountCommand.MESSAGE_FAILURE_EMPTYUSERNAME);
            ensureFieldNotEmptyString(password, RegisterAccountCommand.MESSAGE_FAILURE_EMPTYPASSWORD);
            ensureFieldDoesNotContainSpace(username, RegisterAccountCommand.MESSAGE_FAILURE_USERNAMEWITHSPACE);
            ensureFieldDoesNotContainSpace(password, RegisterAccountCommand.MESSAGE_FAILURE_PASSWORDWITHSPACE);

            if (roleName.equalsIgnoreCase("superuser")) {
                Account account = new Account(username.get(), password.get(), Role.SUPER_USER);
                return new RegisterAccountCommand(account);
            } else if (roleName.equalsIgnoreCase("readonlyuser")) {
                Account account = new Account(username.get(), password.get(), Role.READ_ONLY_USER);
                return new RegisterAccountCommand(account);
            } else {
                throw new ParseException(RegisterAccountCommand.MESSAGE_INVALIDROLE);
            }
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterAccountCommand.MESSAGE_USAGE));
    }

    private void ensureFieldNotEmptyString(Optional<String> field, String commandFailureMessage) throws ParseException {
        if (field.get().equals("")) {
            throw new ParseException(commandFailureMessage);
        }
    }

    private void ensureFieldDoesNotContainSpace(Optional<String> field, String commandFailureMessage)
            throws ParseException {
        if (field.get().contains(" ")) {
            throw new ParseException(commandFailureMessage);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
