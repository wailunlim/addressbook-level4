package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for editing user password.
 */
public class EditPasswordCommandParser implements Parser<EditPasswordCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns a LoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditPasswordCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput,
                EditPasswordCommand.PREFIX_OLDPASSWORD, EditPasswordCommand.PREFIX_NEWPASSWORD);

        if (!arePrefixesPresent(argMultimap, EditPasswordCommand.PREFIX_OLDPASSWORD,
                EditPasswordCommand.PREFIX_NEWPASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPasswordCommand.MESSAGE_USAGE));
        }

        Optional<String> oldPassword = argMultimap.getValue(EditPasswordCommand.PREFIX_OLDPASSWORD);
        Optional<String> newPassword = argMultimap.getValue(EditPasswordCommand.PREFIX_NEWPASSWORD);

        if (oldPassword.isPresent() && newPassword.isPresent()) {
            return new EditPasswordCommand(oldPassword.get(), newPassword.get());
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPasswordCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

