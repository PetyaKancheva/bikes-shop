package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.model.dto.*;
import bg.softuni.bikes_shop.model.events.UserUpdateProfileEvent;

import java.util.List;

public interface UserService {
    void register(UserRegisterDTO userRegisterDTO);

    void sendEmail(UserUpdateProfileEvent event);

    List<ShortUserProfileDTO> getAllByEmailFirsOrLastName(String searchWord);

    UserUpdateByAdminDTO getUserAdminUpdateDTO(String email);
    UserUpdateMainDetailsDTO getUserMainUpdateDTO(String email);

    void updateMainUserDetails(UserUpdateMainDetailsDTO userUpdateMainDetailsDTO);
    void updateEmail( UserUpdateEmailDTO userUpdateEmailDTO);
    void updatePassword(UserUpdatePasswordDTO userUpdatePasswordDTO);

    void updateByAdmin(UserUpdateByAdminDTO userUpdateByAdminDTO);

    boolean isUniqueEmail(String email);

    boolean isPasswordCorrect(String email, String password);


}
