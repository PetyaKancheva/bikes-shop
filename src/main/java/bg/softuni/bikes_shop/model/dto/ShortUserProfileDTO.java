package bg.softuni.bikes_shop.model.dto;

public record ShortUserProfileDTO(
        String email,
        String firstName,
        String lastName
) {
    public static ShortUserProfileDTO empty(){
        return new ShortUserProfileDTO(null,null,null);
    }
    public static String stringMessage(){
            return "%s %s with email: %s";
    }
}
