package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.model.dto.*;
import bg.softuni.bikes_shop.model.events.UserUpdateProfileEvent;
import org.apache.catalina.UserDatabase;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void register(UserRegisterDTO userRegisterDTO);

    void notify(UserUpdateProfileEvent event);

    List<ShortUserDTO> getAllByEmailFirsOrLastName(String searchWord);

    UserAdminUpdateDTO getUserAdminUpdateDTO(String email);
    UserMainUpdateDTO getUserMainUpdateDTO(String email);

    void updateByUser(UserUpdateDTO userUpdateDTO, String email);

    void updateByAdmin(UserAdminUpdateDTO userAdminUpdateDTO,  String email);

    boolean isUniqueEmail(String email);

    boolean isPasswordCorrect(String email, String password);


}
