package bg.softuni.bikes_shop.model.dto;

public record ShortUserDTO(
        String email,
        String firstName,
        String lastName
) {
    public static ShortUserDTO empty(){
        return new ShortUserDTO(null,null,null);
    }
    public static String stringMessage(){
            return "%s %s with email: %s";
    }
}
