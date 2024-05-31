package com.booking.Service;

import java.util.List;

import com.booking.Models.Person;

public class ValidationService {
    // Buatlah function sesuai dengan kebutuhan
    public static boolean validateInput(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static Person findPersonById(List<Person> personList, String id) {
        return personList.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static boolean validateCustomerId(String id, List<Person> personList) {
        if (!validateInput(id)) {
            System.err.println("id tidak boleh kosong");
            return false;
        }
        if (findPersonById(personList, id) == null) {
            System.err.println("id " + id + " tidak ditemukan");
            return false;
        }

        return true;

    }

    public static boolean validateWorkstage(String workstage) {
        if (!("In Process".equalsIgnoreCase(workstage) || "Finish".equalsIgnoreCase(workstage)
                || "Canceled".equalsIgnoreCase(workstage))) {
            System.err.println("Workstage tidak ditemukan");
            return false;
        }
        return true;
    }
}