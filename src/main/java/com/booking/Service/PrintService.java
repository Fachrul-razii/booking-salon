package com.booking.Service;

import java.util.List;

import com.booking.Models.Customer;
import com.booking.Models.Employee;
import com.booking.Models.Reservation;
import com.booking.Models.Service;
import com.booking.Models.Person;

public class PrintService {
    public static void printMenu(String title, String[] menuArr) {
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);
            num++;
        }
    }

    public static String printServices(List<Service> serviceList) {
        String result = "";
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public static void showRecentReservation(List<Reservation> reservationList) {
        int num = 1;
        System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out
                .println("+========================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Waiting")
                    || reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(),
                        printServices(reservation.getServices()), reservation.getReservationPrice(),
                        reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
    }

    public static void showAllCustomer(List<Person> personList) {
        personList.stream()
                .filter(person -> person instanceof Customer)
                .map(person -> (Customer) person)
                .forEach(customer -> {
                    System.out.println("id : " + customer.getId());
                    System.out.println("name : " + customer.getName());
                    System.out.println("member : " + customer.getMember());
                    System.out.println("wallet : " + customer.getWallet());
                });
    }

    public static void showAllEmployee(List<Person> employees) {
        employees.stream()
                .filter(person -> person instanceof Employee)
                .forEach(employee -> {
                    System.out.println("id : " + employee.getId());
                    System.out.println("name : " + employee.getName());
                });
    }

    public static void showHistoryReservation(List<Reservation> reservationList) {
        double total = reservationList.stream()
                .filter(reservation -> "Finish".equals(reservation.getWorkstage()))
                .mapToDouble(Reservation::getReservationPrice)
                .sum();
        reservationList.stream()
                .filter(reservation -> "Finish".equals(reservation.getWorkstage()))
                .forEach(reservation -> {
                    System.out.println("Reservation id : " + reservation.getReservationId());
                    System.out.println("Customer name : " + reservation.getCustomer().getName());
                    System.out.println("Employee name : " + reservation.getEmployee().getName());
                    System.out.println("Reservation price : " + reservation.getReservationPrice());
                    System.out.println("====================");
                });
        System.out.println("Total keungtungan: " + total);
    }

    public static void showHistoryReservationCancel(List<Reservation> reservationList) {
        reservationList.stream()
                .filter(reservation -> "Canceled".equals(reservation.getWorkstage()))
                .forEach(reservation -> {
                    System.out.println("Reservation id : " + reservation.getReservationId());
                    System.out.println("Customer name : " + reservation.getCustomer().getName());
                    System.out.println("====================");
                });
    }

}