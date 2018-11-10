package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.SERVICE_COST_DESC_MID;
import static seedu.address.logic.commands.CommandTestUtil.SERVICE_DESC_HOTEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERVICE_COST_MID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERVICE_HOTEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddServiceCommand;
import seedu.address.model.ContactType;
import seedu.address.model.contact.Service;

public class AddServiceCommandParserTest {
    private AddServiceCommandParser clientParser = new AddServiceCommandParser(ContactType.CLIENT);
    private AddServiceCommandParser vendorParser = new AddServiceCommandParser(ContactType.VENDOR);

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(clientParser, VALID_SERVICE_HOTEL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));
        assertParseFailure(vendorParser, VALID_SERVICE_HOTEL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.VENDOR, "#<ID>")));

        // Invalid index specified
        assertParseFailure(clientParser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));
        assertParseFailure(vendorParser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.VENDOR, "#<ID>")));

        // no service type specified
        assertParseFailure(clientParser, "1" + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));
        assertParseFailure(vendorParser, "1" + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.VENDOR, "#<ID>")));

        // no service cost specified
        assertParseFailure(clientParser, "1" + SERVICE_DESC_HOTEL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));
        assertParseFailure(vendorParser, "1" + SERVICE_DESC_HOTEL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.VENDOR, "#<ID>")));

        // no service type and cost specified
        assertParseFailure(clientParser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));
        assertParseFailure(vendorParser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.VENDOR, "#<ID>")));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(clientParser, "-5" + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));
        assertParseFailure(vendorParser, "-5" + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.VENDOR, "#<ID>")));

        // zero index
        assertParseFailure(clientParser, "0" + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));
        assertParseFailure(vendorParser, "0" + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.VENDOR, "#<ID>")));

        // invalid arguments being parsed as preamble
        assertParseFailure(clientParser, "1 some random string" + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));
        assertParseFailure(vendorParser, "1 some random string" + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.VENDOR, "#<ID>")));

        // invalid prefix being parsed as preamble
        assertParseFailure(clientParser, "1 i/ string" + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.CLIENT, "#<ID>")));
        assertParseFailure(vendorParser, "1 i/ string" + SERVICE_DESC_HOTEL + SERVICE_COST_DESC_MID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(AddServiceCommand.MESSAGE_USAGE, ContactType.VENDOR, "#<ID>")));
    }

    @Test
    public void parse_invalidServiceType_failure() {
        // Service type not in available list
        assertParseFailure(clientParser, "1 " + PREFIX_SERVICE + "florist " + SERVICE_COST_DESC_MID,
                Service.MESSAGE_SERVICE_NAME_CONSTRAINTS);
        assertParseFailure(vendorParser, "1 " + PREFIX_SERVICE + "florist " + SERVICE_COST_DESC_MID,
                Service.MESSAGE_SERVICE_NAME_CONSTRAINTS);

        // Service type with additional symbols
        assertParseFailure(clientParser, "1" + SERVICE_DESC_HOTEL + "&" + SERVICE_COST_DESC_MID,
                Service.MESSAGE_SERVICE_NAME_CONSTRAINTS);
        assertParseFailure(vendorParser, "1" + SERVICE_DESC_HOTEL + "&" + SERVICE_COST_DESC_MID,
                Service.MESSAGE_SERVICE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_invalidServiceCost_failure() {
        // Cost is negative
        assertParseFailure(clientParser, "1" + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "-10.00",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);
        assertParseFailure(vendorParser, "1" + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "-10.00",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);

        // Cost is 0
        assertParseFailure(clientParser, "1" + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "0.00",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);
        assertParseFailure(vendorParser, "1" + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "0.00",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);

        // Cost has 1 decimal place
        assertParseFailure(clientParser, "1" + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "88.0",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);
        assertParseFailure(vendorParser, "1" + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "88.0",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);

        // Cost has more than 2 decimal places
        assertParseFailure(clientParser, "1" + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "880.000",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);
        assertParseFailure(vendorParser, "1" + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "880.000",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);

        // Cost has additional symbols
        assertParseFailure(clientParser, "1" + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "880.0.9",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);
        assertParseFailure(clientParser, "1" + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "$880.00",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);
        assertParseFailure(vendorParser, "1" + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "880.0.9",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);
        assertParseFailure(vendorParser, "1" + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "$880.00",
                Service.MESSAGE_SERVICE_COST_CONSTRAINTS);
    }

    @Test
    public void parse_validServiceTypes_success() {
        // All lower case
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_SERVICE + "hotel" + SERVICE_COST_DESC_MID;
        Service inputService = new Service("hotel", VALID_SERVICE_COST_MID);
        AddServiceCommand expectedClientCommand = new AddServiceCommand(targetIndex, inputService, ContactType.CLIENT);
        assertParseSuccess(clientParser, userInput, expectedClientCommand);
        AddServiceCommand expectedVendorCommand = new AddServiceCommand(targetIndex, inputService, ContactType.VENDOR);
        assertParseSuccess(vendorParser, userInput, expectedVendorCommand);

        // Some upper case
        userInput = targetIndex.getOneBased() + " " + PREFIX_SERVICE + "HoTel" + SERVICE_COST_DESC_MID;
        inputService = new Service("HoTel", VALID_SERVICE_COST_MID);
        expectedClientCommand = new AddServiceCommand(targetIndex, inputService, ContactType.CLIENT);
        assertParseSuccess(clientParser, userInput, expectedClientCommand);
        expectedVendorCommand = new AddServiceCommand(targetIndex, inputService, ContactType.VENDOR);
        assertParseSuccess(vendorParser, userInput, expectedVendorCommand);
    }

    @Test
    public void parse_validServiceCosts_success() {
        // Lowest cost
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "0.01";
        Service inputService = new Service(VALID_SERVICE_HOTEL, "0.01");
        AddServiceCommand expectedClientCommand = new AddServiceCommand(targetIndex, inputService, ContactType.CLIENT);
        assertParseSuccess(clientParser, userInput, expectedClientCommand);
        AddServiceCommand expectedVendorCommand = new AddServiceCommand(targetIndex, inputService, ContactType.VENDOR);
        assertParseSuccess(vendorParser, userInput, expectedVendorCommand);

        // High cost
        userInput = targetIndex.getOneBased() + SERVICE_DESC_HOTEL + " " + PREFIX_COST + "8000000.00";
        inputService = new Service(VALID_SERVICE_HOTEL, "8000000.00");
        expectedClientCommand = new AddServiceCommand(targetIndex, inputService, ContactType.CLIENT);
        assertParseSuccess(clientParser, userInput, expectedClientCommand);
        expectedVendorCommand = new AddServiceCommand(targetIndex, inputService, ContactType.VENDOR);
        assertParseSuccess(vendorParser, userInput, expectedVendorCommand);
    }
}
