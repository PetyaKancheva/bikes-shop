package bg.softuni.bikes_shop.configuration;

import bg.softuni.bikes_shop.model.CustomUserDetails;
import bg.softuni.bikes_shop.model.UserRoleEnum;
import bg.softuni.bikes_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final ProductService productService;
    private final String keyRemember;

    public SecurityConfig(@Value("${bikes.rememberMeKey}") String keyRemember, ProductService productService) {
        this.productService = productService;
        this.keyRemember = keyRemember;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
//                        .anyRequest().permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/login", "/register").permitAll()
                        .requestMatchers("/internationalisation").permitAll()
                        .requestMatchers("/services", "/contacts", "/about").permitAll()
                        .requestMatchers( "/comments/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/currency").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/search-result").permitAll()
                        .requestMatchers("/user/activate/*").permitAll()
                        .requestMatchers("/admin/**").hasRole(UserRoleEnum.ADMIN.name())
                        .requestMatchers("/product/add").hasRole(UserRoleEnum.EMPLOYEE.name())
                        .requestMatchers("/user","/user/main","/user/password","/user/email").hasAnyRole(UserRoleEnum.USER.name(),UserRoleEnum.EMPLOYEE.name(),UserRoleEnum.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                        .requestMatchers("/{categories}", String.valueOf(productService.getDistinctCategories())).permitAll()
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> formLogin
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureForwardUrl("/login-error")
        ).logout(
                logout -> logout//
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
        ).rememberMe(
                rememberMe -> rememberMe // TODO if time is there to add token option instead
                        .rememberMeParameter("rememberme")
                        .key(keyRemember)
                        .rememberMeCookieName("remembermecookie")
                        .tokenValiditySeconds(604800000)// one week in ms

        );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

}
