package bg.softuni.bikes_shop.model.dto;

public record ProductDTO(
        String compositeName,
        String name,
        String description,
        String category,
        Double price,
        String pictureURL
) {

}
