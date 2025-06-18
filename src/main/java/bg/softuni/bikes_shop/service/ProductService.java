package bg.softuni.bikes_shop.service;

import bg.softuni.bikes_shop.model.dto.ProductAddDTO;
import bg.softuni.bikes_shop.model.dto.ProductDTO;
import bg.softuni.bikes_shop.model.entity.ProductEntity;
import bg.softuni.bikes_shop.model.events.ProductAdditionEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<ProductDTO> getProducts(Integer size, Integer page, String sort);

    Optional<ProductDTO> getSingleProduct(String compositeName);

    void addProduct(ProductAddDTO productAddDTO);

    List<String> getDistinctCategories();

    Page<ProductDTO> getProductsFromCategoryPageable(Pageable pageable, String category);

    void setCompositeName(ProductEntity productEntity);

    void addCompositeName(ProductAdditionEvent event);

    Page<ProductDTO> searchForProducts(String productToSearch);
}
