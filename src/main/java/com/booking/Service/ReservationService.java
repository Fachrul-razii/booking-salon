package com.booking.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.Models.Customer;
import com.booking.Models.Employee;
import com.booking.Models.Reservation;
import com.booking.Models.Service;
import com.booking.Models.Person;

public class ReservationService {
    private static final Scanner input = new Scanner(System.in);

    public static void createReservation(List<Person> personList, List<Service> serviceList,
            List<Reservation> reservationList) {
        System.out.println("Membuat reservation");
        System.out.println("=====================");
        System.out.print("Masukan id customer: ");
        String customerId = input.nextLine();
        Customer customer = personList.stream()
                .filter(person -> person instanceof Customer)
                .map(person -> (Customer) person)
                .filter(person -> person.getId().equals(customerId))
                .findFirst()
                .orElse(null);

        if (customer == null) {
            System.err.println("Id customer tidak ditemukan");
            return;
        }

        System.out.println("Service yang tersedia");
        for (int i = 0; i < serviceList.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + serviceList.get(i).getServiceName() + " - " + serviceList.get(i).getPrice());
        }

        System.out.println("Masukan nomor service (jika lebih dari satu, pisahkan dengan tanda koma (,))");
        String numberService = input.nextLine();
        String[] numbers = numberService.split(",");

        List<Service> selectedService = new ArrayList<>();
        double totalReservationPrice = 0.0;

        for (String number : numbers) {
            int idx = Integer.parseInt(number.trim()) - 1;
            if (idx >= 0 && idx < serviceList.size()) {
                selectedService.add(serviceList.get(idx));
                totalReservationPrice += serviceList.get(idx).getPrice();
            }
        }

        System.out.println("Masukkan id employee : ");
        String empolyeeId = input.nextLine();
        Employee employee = personList.stream()
                .filter(person -> person instanceof Employee)
                .map(person -> (Employee) person)
                .filter(person -> person.getId().equals(empolyeeId))
                .findFirst()
                .orElse(null);

        if (employee == null) {
            System.err.println("Id employee tidak ditemukan");
            return;
        }

        System.out.println("Masukkan tahap reservasi (In Process, Finish, Canceled)");
        String workstage = input.nextLine();
        if (!ValidationService.validateWorkstage(workstage)) {
            return;
        }

        Reservation reservation = Reservation.builder()
                .reservationId("Resv-" + reservationList.size() + 1)
                .customer(customer)
                .employee(employee)
                .services(selectedService)
                .workstage(workstage)
                .reservationPrice(totalReservationPrice)
                .build();

        reservationList.add(reservation);
        System.out.println("Reservasi berhasil dibuat");
    }

    public static Customer getCustomerByCustomerId(List<Person> personList, String id) {
        return personList.stream()
                .filter(person -> person instanceof Customer)
                .map(person -> (Customer) person)
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static void editReservationWorkstage(List<Reservation> reservationList) {
        System.out.println("Mengubah wrokstage");
        System.out.print("Masukan id reservasi: ");
        String reservationId = input.nextLine();
        Reservation reservation = reservationList.stream()
                .filter(resv -> resv.getReservationId().equals(reservationId))
                .findFirst()
                .orElse(null);

        if (reservation == null) {
            System.err.println("Id Reservasi tidak ditemukan");
        }

        System.out.println("Masukkan worstage baru (Finish, Canceled)");
        String workstage = input.nextLine();
        if (!ValidationService.validateWorkstage(workstage)) {
            return;
        }

        if ("Finish".equalsIgnoreCase(workstage)) {
            double price = reservation.getReservationPrice();
            Customer customer = reservation.getCustomer();

            if (price != 0.0 && customer != null) {
                double currentWallet = customer.getWallet();

                if (currentWallet < price) {
                    System.err.println("Saldo tidak mencukupi");
                    return;
                }

                double wallet = currentWallet - price;
                customer.setWallet(wallet);
            } else {
                System.err.println("Customer tidak ditemukan");
                return;
            }
        }

        reservation.setWorkstage(workstage);
        System.out.println("Workstage berhasil di perbaharui");
    }
}

// Silahkan tambahkan function lain, dan ubah function diatas sesuai kebutuhan