package bg.softuni.bikes_shop.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentDTO(
        Long id,
        String user_name,
        String title,
        String body

) {public static CommentDTO empty(){
    return  new CommentDTO(null, null,null,null);
}


}
