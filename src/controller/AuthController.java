package controller;

import container.ComponentContainer;
import dto.Profile;
import service.AuthService;
import util.ScannerUtil;

import java.util.Scanner;

public class AuthController {

    public void start() {
        boolean game = true;
        while (game) {
            menu();
            int action = ScannerUtil.getAction();
            switch (action) {
                case 1:
                    login();
                    break;
                case 2:
                    registration();
                    break;
                case 0:
                    game = false;
                default:
                    System.out.println("Mazgi nima bu");
            }
        }
    }
    public void menu() {
        System.out.println("\t\tMENU\t\t");
        System.out.println("1. LOGIN");
        System.out.println("2. REGISTRATION");
        System.out.println("0. EXIT ");
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter phone:");
        String phone = scanner.nextLine();

        System.out.print("Enter password:");
        String password = scanner.nextLine();

        AuthService profileService = new AuthService();
        profileService.login(phone, password);
    }

    private void registration() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name:");
        String name = scanner.nextLine();

        System.out.print("Enter surname:");
        String surname = scanner.nextLine();

        System.out.print("Enter phone:");
        String phone = scanner.nextLine();

        System.out.print("Enter password:");
        String password = scanner.nextLine();

        Profile profile = new Profile();
        profile.setName(name);
        profile.setSurname(surname);
        profile.setPhone(phone);
        profile.setPassword(password);


        AuthService authService = new AuthService();
        authService.registration(profile);
    }


}
