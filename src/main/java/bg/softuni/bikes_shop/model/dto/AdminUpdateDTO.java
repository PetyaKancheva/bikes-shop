package bg.softuni.bikes_shop.model.dto;

import jakarta.validation.Valid;

public record AdminUpdateDTO(
        @Valid UserMainUpdateDTO userMainUpdateDTO,
        @Valid UserAdminUpdateDTO userAdminUpdateDTO) {
    public static AdminUpdateDTO empty() {
        return new AdminUpdateDTO(UserMainUpdateDTO.empty(), UserAdminUpdateDTO.empty());
    }

}
