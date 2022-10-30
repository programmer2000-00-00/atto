package container;

import dto.Profile;
import service.CardService;
import service.ProfileService;

public class ComponentContainer {
    public static CardService cardService = new CardService();

    public static ProfileService profileService = new ProfileService();

    public static Profile currentProfile = new Profile();
}
