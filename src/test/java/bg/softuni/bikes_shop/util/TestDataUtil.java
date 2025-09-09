package bg.softuni.bikes_shop.util;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.UserRoleEnum;
import bg.softuni.bikes_shop.model.dto.ItemDTO;
import bg.softuni.bikes_shop.model.dto.ProductAddDTO;
import bg.softuni.bikes_shop.model.entity.*;
import bg.softuni.bikes_shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Component
public class TestDataUtil {

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ActivationCodeRepository activationCodeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;


    public void createExchangeRate(String currency, BigDecimal rate) {
        currencyRepository.save(
                new CurrencyEntity().setName(currency).setRate(rate));

    }

    public OrderEntity createOrderEntity(String email) {

        OrderEntity order = new OrderEntity()
                .setBuyer(userRepository.findUserByEmail(email).orElseThrow())
                .setStatus("open")
                .setDateCreated(LocalDate.now())
                .setTotalSum(BigDecimal.valueOf(1000d));

        return orderRepository.save(order);
    }

    public UserEntity createUserEntity() {

        var roleEntities = userRoleRepository.findAllByNameIn(List.of(UserRoleEnum.USER));
        UserEntity newUser = new UserEntity()
                .setEnabled(false)
                .setFirstName("First name test")
                .setLastName("Last name test")
                .setEmail("test@mail.com")
                .setAddress("Address Test")
                .setCountry("Country Test")
                .setRoles(roleEntities)
                .setPassword("test1234");

        return userRepository.save(newUser);
    }

    public UserActivationCodeEntity createActivationCode() {
        UserActivationCodeEntity newActivationCode = new UserActivationCodeEntity()
                .setActivationCode("jmCY4WoeBarWrdb")
                .setCreated(Instant.now())
                .setUser(createUserEntity());

        return activationCodeRepository.save(newActivationCode);
    }

    public ProductEntity createTestProduct() {
        ProductEntity newProduct = new ProductEntity()
                .setName("test product")
                .setCompositeName("test_product")
                .setDescription("test description")
                .setPrice(BigDecimal.valueOf(1000))
                .setCategory("testCategory")
                .setPictureURL("urlTest");


        return productRepository.save(newProduct);
    }

    public void cleanUp() {
        currencyRepository.deleteAll();
        productRepository.deleteAll();
        orderRepository.deleteAll();
        itemRepository.deleteAll();
        activationCodeRepository.deleteAll();
        userRepository.deleteAll();

    }
}
