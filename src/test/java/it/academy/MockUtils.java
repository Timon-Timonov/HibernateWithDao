package it.academy;

import it.academy.dto.Address;
import it.academy.dto.People;

import java.util.HashSet;
import java.util.Random;

public class MockUtils {

    private static int peopleCount = 0;
    private static int addressCount = 0;
    public static final Random random = new Random();

    private MockUtils() {
    }

    public static Address getAddress() {

        Address address = Address.builder()
                              .street(MockConstants.STREETS[addressCount])
                              .house(MockConstants.HOUSES[addressCount])
                              .people(new HashSet<>())
                              .build();
        if (++addressCount >= Math.min(
            MockConstants.HOUSES.length, MockConstants.STREETS.length)) {
            addressCount = 0;
        }
        return address;
    }

    public static People getPeople() {

        People people = People.builder()
                            .name(MockConstants.NAMES[peopleCount])
                            .surname(MockConstants.SURNAMES[peopleCount])
                            .age(MockConstants.AGE[peopleCount])
                            .addresses(new HashSet<>())
                            .build();
        if (++peopleCount >= Math.min(
            MockConstants.NAMES.length, MockConstants.SURNAMES.length)) {
            peopleCount = 0;
        }
        return people;
    }
}
