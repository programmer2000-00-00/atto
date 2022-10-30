package service;

import container.ComponentContainer;
import controller.AdminController;
import controller.ProfileController;
import dto.Profile;
import enums.GeneralStatus;
import enums.ProfileRole;
import repository.ProfileRepository;
import util.MD5Util;

import java.time.LocalDateTime;

public class AuthService {
    private ProfileRepository profileRepository;

    public AuthService() {
        profileRepository = new ProfileRepository();
    }

    public void login(String phone, String password) {
        Profile profile = profileRepository.getProfileByPhoneAndPassword(phone, MD5Util.encode(password));

        if (profile == null) {
            System.out.println("Phone or Password incorrect");
            return;
        }

        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            System.out.println("You not allowed.MF");
            return;
        }

        ComponentContainer.currentProfile = profile;

        if (profile.getRole().equals(ProfileRole.ADMIN)) {
            AdminController adminController = new AdminController();
            adminController.start(profile);
        } else if (profile.getRole().equals(ProfileRole.USER)) {
            ProfileController profileController = new ProfileController();
            profileController.start(profile);
        } else {
            System.out.println("You don't have any role.");
        }

    }

    public void registration(Profile profile) {
        String check = check(profile);
        if (check != null){
            System.out.println(check);
            return;
        }


        Boolean exist = profileRepository.isPhoneExist(profile.getPhone()); // unique
        if (exist) {
            System.out.println(" Phone already exist.");
            return;
        }

        profile.setStatus(GeneralStatus.ACTIVE);
        profile.setCreatedDate(LocalDateTime.now());
        profile.setRole(ProfileRole.USER);
        profile.setPassword(MD5Util.encode(profile.getPassword()));
        int result = profileRepository.saveProfile(profile);

        if (result != 0) {
            System.out.println("Profile created.");
        }else {
            System.out.println("ERROR");
        }

    }


    private  String check(Profile profile) {
        if (profile.getName() == null || profile.getName().isBlank()){
            return "Name is required";
        }
        if (profile.getSurname() == null || profile.getSurname().isBlank()){
            return "Surname is required";
        }
        if (profile.getPhone() == null || profile.getPhone().isBlank()){
            return "Phone is required";
        }
        if (profile.getPassword() == null || profile.getPassword().isBlank()){
            return "Password is required";
        }
        return null;
    }
}
