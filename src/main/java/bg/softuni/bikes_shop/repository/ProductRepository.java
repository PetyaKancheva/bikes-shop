package bg.softuni.bikes_shop.repository;

import bg.softuni.bikes_shop.model.entity.ProductEntity;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Override
    Optional<ProductEntity> findById(Long aLong);

    @Query(value = "SELECT DISTINCT category FROM products "
            , nativeQuery = true)
    List<String> getDistinctCategories();

    @Query(value = "SELECT * FROM products WHERE  composite_name IS NOT NULL"
            , nativeQuery = true)
    Page<ProductEntity> findAllProductsWithCompositeNameNotNull(Pageable pageable);

    List<ProductEntity> findAllByCompositeNameNull();

    Page<ProductEntity> findByCategory(Pageable pageable, String name);

    Optional<ProductEntity> findByCompositeName(String compositeName);


//    @Query(value="SELECT *  FROM products WHERE MATCH (category, name, description) AGAINST( ? ) AND composite_name IS NOT NULL ",
//            nativeQuery = true)
//

//    @Query(value="SELECT *  FROM products WHERE name = ? AND composite_name IS NOT NULL ",
//            nativeQuery = true)
    @Query(value="SELECT *  FROM products WHERE MATCH (category, name, description) AGAINST( ? ) AND composite_name IS NOT NULL ",
            nativeQuery = true)
    Page<ProductEntity> findAllByKeyword(String keyword, Pageable pageable);
//
//    @Query(value="SELECT *  FROM products WHERE MATCH (category, name, description) AGAINST( ? ) AND composite_name IS NOT NULL ",
//            nativeQuery = true)
    Page<ProductEntity> searchAllByDescriptionContainingOrCategoryOrNameContaining(String description,String category,String name,Pageable pageable);
}
