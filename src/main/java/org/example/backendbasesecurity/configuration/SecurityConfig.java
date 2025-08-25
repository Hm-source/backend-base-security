package org.example.backendbasesecurity.configuration;


import lombok.RequiredArgsConstructor;
import org.example.backendbasesecurity.security.UsernamePasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsernamePasswordAuthenticationProvider authenticationProvider;
    private final CorsConfigurationSource reactConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//      (3) 인증 및 인가 외 모든 종류의 SecurityFilterChain 보안 설정 규칙 적용
//        http.csrf(AbstractHttpConfigurer::disable);
        http.csrf(csrf -> csrf
            .ignoringRequestMatchers("/api/**") // 특정 엔드포인트는 CSRF 미적용
        );
        http.cors((cors) -> cors.configurationSource(reactConfigurationSource)); // cors 설정

        http.formLogin(form -> form
            .loginPage("/login")
            .permitAll());
        http.logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login"));
        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/api/**").authenticated()
            .anyRequest().permitAll()
        ); // / 또는 /api 요청 시에 인증 처리하도록 제어

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
//      (2) 보안을 아예 적용하지 않으려는 WebSecurity 설정 (학습을 위해 설정했지만 공식적으론 HttpSecurity#authorizeHttpRequests 제안)
        return (web) -> web.ignoring()
            .requestMatchers("/health")
            .requestMatchers("/images/**")
            .requestMatchers("/favicon.ico");
    }
}