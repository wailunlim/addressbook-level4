package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event indicating a successful login.
 */
public class LoginSuccessEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
