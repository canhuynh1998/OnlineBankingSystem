package SelfBankingSystem.SelfBankingSystem.security.config;

import SelfBankingSystem.SelfBankingSystem.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static SelfBankingSystem.SelfBankingSystem.security.config.UserRole.ADMIN;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomerService customerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*", "register").permitAll()  // white list URLs
                .antMatchers("/api/v1/registration/**").permitAll()
                .antMatchers("/customer").not().anonymous()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/customer",true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    // This will authenticate the DB
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(customerService);
        return provider;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user1 = User
                .builder()
                .username("user1")
                .password(passwordEncoder.encode("(ABCxyz98)"))
                .roles(ADMIN.name()) // this is not a good way to set roles/authorities
                .build();
//
//
//        UserDetails adminUser = User
//                .builder()
//                .username("adminUser")
//                .password(passwordEncoder.encode("(ABCxyz98)"))
////                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//
//        UserDetails adminUserTrainee = User
//                .builder()
//                .username("adminUserTrainee")
//                .password(passwordEncoder.encode("(ABCxyz98)"))
//                .authorities(ADMINTRAINEE.getGrantedAuthorities())
////                .roles(ADMINTRAINEE.name())
//                .build();
        return new InMemoryUserDetailsManager(
                user1
        );
    }

}
