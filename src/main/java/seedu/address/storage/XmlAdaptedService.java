package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Service;

/**
 * JAXB-friendly adapted version of the Service.
 */
public class XmlAdaptedService {

    @XmlValue
    private String service;

    /**
     * Constructs an XmlAdaptedService.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedService() {}

    /**
     * Constructs a {@code XmlAdaptedService} with the given {@code service}.
     */
    public XmlAdaptedService(String service) {
        this.service = service;
    }

    /**
     * Converts a given Service into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedService(Service source) {
        service = source.toString();
    }

    /**
     * Converts this jaxb-friendly adapted service object into the model's Service object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted client
     */
    public Service toModelType() throws IllegalValueException {
        String[] splitString = service.split("\\$");
        String serviceName = splitString[0].trim();
        String serviceCost = splitString[1].trim();

        if (!Service.isValidServiceName(serviceName)) {
            throw new IllegalValueException(Service.MESSAGE_SERVICE_NAME_CONSTRAINTS);
        }

        if (!Service.isValidServiceCost(serviceCost)) {
            throw new IllegalValueException(Service.MESSAGE_SERVICE_COST_CONSTRAINTS);
        }
        return new Service(serviceName, serviceCost);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedService)) {
            return false;
        }

        return service.equals(((XmlAdaptedService) other).service);
    }
}
