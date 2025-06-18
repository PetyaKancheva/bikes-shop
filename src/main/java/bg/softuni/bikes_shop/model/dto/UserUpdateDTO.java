package bg.softuni.bikes_shop.model.dto;

import jakarta.validation.Valid;

public record UserUpdateDTO(
        @Valid UserMainUpdateDTO userMainUpdateDTO,
        @Valid UserSelfUpdateDTO userSelfUpdateDTO)
{
    public static UserUpdateDTO empty() {
        return new UserUpdateDTO(UserMainUpdateDTO.empty(), UserSelfUpdateDTO.empty());
    }
}
