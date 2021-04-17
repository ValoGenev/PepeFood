package com.gegessen.security.config;

import com.gegessen.repository.IUserRepository;
import com.gegessen.security.filters.CustomLogoutFilter;
import com.gegessen.security.filters.JwtTokenAuthenticationFilter;
import com.gegessen.security.filters.SocialAuthenticationFilter;
import com.gegessen.security.filters.UnauthorizedExceptionFilter;
import com.gegessen.security.jwt.JwtTokenUtil;
import com.gegessen.security.provider.EmailAuthenticationProvider;
import com.gegessen.security.provider.FacebookAuthenticationProvider;
import com.gegessen.security.provider.GoogleAuthenticationProvider;
import com.gegessen.security.provider.JwtAuthenticationProvider;
import com.gegessen.security.userdetails.EmailUserDetailsServiceImpl;
import com.gegessen.security.userdetails.FacebookUserDetailsServiceImpl;
import com.gegessen.security.userdetails.GoogleUserDetailsServiceImpl;
import com.gegessen.security.userdetails.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

import static com.gegessen.model.UserRole.ADMIN;
import static com.gegessen.model.UserRole.USER;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    IUserRepository userRepository;

    @Autowired
    GoogleUserDetailsServiceImpl googleUserDetailsService;

    @Autowired
    FacebookUserDetailsServiceImpl facebookUserDetailsService;

    @Autowired
    EmailUserDetailsServiceImpl emailUserDetailsService;

    @Autowired
    JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Bean
    public GoogleAuthenticationProvider socialAuthenticationProvider() {
        return new GoogleAuthenticationProvider(googleUserDetailsService);
    }

    @Bean
    public FacebookAuthenticationProvider facebookAuthenticationProvider() {
        return new FacebookAuthenticationProvider(facebookUserDetailsService);
    }

    @Bean
    public EmailAuthenticationProvider emailAuthenticationProvider() {
        return new EmailAuthenticationProvider(emailUserDetailsService);
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(){
        return new JwtAuthenticationProvider(jwtUserDetailsService,jwtTokenUtil());
    }


    @Bean
    public SocialAuthenticationFilter authenticationFilter() throws Exception {
        return new SocialAuthenticationFilter(authenticationManagerBean());
    }

    @Bean
    public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter() throws Exception {
        return new JwtTokenAuthenticationFilter(authenticationManagerBean(),jwtTokenUtil());
    }

    @Bean
    public UnauthorizedExceptionFilter unauthorizedExceptionFilter() {
        return new UnauthorizedExceptionFilter();
    }

    @Bean
    CustomLogoutFilter customLogoutFilter() {
        return new CustomLogoutFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index", "/css/*", "/js/*").permitAll()
                .antMatchers("/config/api/v1/users/login", "/config/api/v1/users/logout").permitAll()

//                //users
//                .antMatchers(HttpMethod.DELETE,"/config/api/v1/users/**").hasAnyRole(ADMIN.name())
//                .antMatchers(HttpMethod.POST,"/config/api/v1/users").hasRole(ADMIN.name())
//                .antMatchers(HttpMethod.PUT,"/config/api/v1/users/**").hasAnyRole(ADMIN.name(),USER.name())
//
//
//                //restaurants
//                .antMatchers(HttpMethod.DELETE,"/config/api/v1/restaurants/**").hasAnyRole(ADMIN.name())
//                .antMatchers(HttpMethod.POST,"/config/api/v1/restaurants").hasAnyRole(ADMIN.name(),USER.name())
//                .antMatchers(HttpMethod.PUT,"/config/api/v1/restaurants/**").hasAnyRole(ADMIN.name(),USER.name())
//
//
//                //products
//                .antMatchers(HttpMethod.DELETE,"/config/api/v1/products/**").hasAnyRole(ADMIN.name(),USER.name())
//                .antMatchers(HttpMethod.POST,"/config/api/v1/products").hasAnyRole(ADMIN.name(),USER.name())
//                .antMatchers(HttpMethod.PUT,"/config/api/v1/products/**").hasAnyRole(ADMIN.name(),USER.name())
//
//                //orders
//                .antMatchers(HttpMethod.DELETE,"/config/api/v1/orders/**").hasAnyRole(ADMIN.name())
//                .antMatchers(HttpMethod.POST,"/config/api/v1/orders").hasAnyRole(ADMIN.name(),USER.name())
//                .antMatchers(HttpMethod.PUT,"/config/api/v1/orders/**").hasAnyRole(ADMIN.name(),USER.name())


                .antMatchers("**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterAt(authenticationFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(jwtTokenAuthenticationFilter(), SocialAuthenticationFilter.class);
        http.addFilterBefore(unauthorizedExceptionFilter(), LogoutFilter.class);
        http.addFilterAt(customLogoutFilter(),LogoutFilter.class);

    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(googleUserDetailsService)
                .and()
                .userDetailsService(facebookUserDetailsService)
                .and()
                .userDetailsService(emailUserDetailsService)
                .and()
                .userDetailsService(jwtUserDetailsService)
                .and()
                .authenticationProvider(socialAuthenticationProvider())
                .authenticationProvider(facebookAuthenticationProvider())
                .authenticationProvider(emailAuthenticationProvider())
                .authenticationProvider(jwtAuthenticationProvider());
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }


}
