package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.model.dto.*;
import bg.softuni.bikes_shop.model.events.UserUpdateProfileEvent;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void register(UserRegisterDTO userRegisterDTO);

    void notify(UserUpdateProfileEvent event);

    List<ShortUserDTO> getAllByEmailFirsOrLastName(String searchWord);

    AdminUpdateDTO getAdminDTO(String email);

    UserMainUpdateDTO getUserMainUpdateDTO(String email);

    void updateByUser(UserUpdateDTO userUpdateDTO, String email);

    void updateByAdmin(AdminUpdateDTO adminUpdateDTO, String email);

    boolean isUniqueEmail(String email);

    boolean isPasswordCorrect(String email, String password);


}
