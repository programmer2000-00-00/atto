package db;

import dto.Card;
import dto.Profile;
import enums.GeneralStatus;
import enums.ProfileRole;
import repository.CardRepository;
import repository.ProfileRepository;
import util.MD5Util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InitDataBase {

    public static void adminInit() {

        Profile profile = new Profile();
        profile.setName("Muhammadsodiq");
        profile.setSurname("Nabijonov");
        profile.setPhone("905555555");
        profile.setPassword(MD5Util.encode("5555"));
        profile.setCreatedDate(LocalDateTime.now());
        profile.setStatus(GeneralStatus.ACTIVE);
        profile.setRole(ProfileRole.ADMIN);


        ProfileRepository profileRepository = new ProfileRepository();

        Profile profile1 = profileRepository.getProfileByPhone(profile.getPhone());
        if (profile1 != null) {
            return;
        }
        profileRepository.saveProfile(profile);
    }

    public static void addCompanyCard() {
        Card card = new Card();
        card.setCardNumber("5555");
        card.setExpDate(LocalDate.of(2025, 12, 01));

        card.setPhone("123");
        card.setBalance(0d);
        card.setCreatedDate(LocalDateTime.now());
        card.setStatus(GeneralStatus.ACTIVE);

        CardRepository cardRepository = new CardRepository();
        Card exists = cardRepository.getCardByNumber(card.getCardNumber());

        if (exists != null) {
            return;
        }
        cardRepository.save(card);
    }
}
