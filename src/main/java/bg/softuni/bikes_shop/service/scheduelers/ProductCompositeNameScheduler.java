package bg.softuni.bikes_shop.service.scheduelers;

import bg.softuni.bikes_shop.model.entity.ProductEntity;
import bg.softuni.bikes_shop.repository.ProductRepository;
import bg.softuni.bikes_shop.service.ProductService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class ProductCompositeNameScheduler {
    private final ProductRepository productRepository;
    private final ProductService productService;

    public ProductCompositeNameScheduler(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }
//            @Scheduled(cron ="0 */1 */1 * * *")// every 1 min
    @Scheduled(cron ="0 0 * * * *")//every hour
    void checkForEmptyProductCompositeNames(){
            List<ProductEntity> toComplete=productRepository.findAllByCompositeNameNull();
            if(!toComplete.isEmpty()){
                productRepository.findAllByCompositeNameNull().forEach(p->productService.setCompositeName(p));
                System.out.println("*** Total "+toComplete.size() +"  composite names updated ***" + Instant.now() + " ****");
            }

    }
}
