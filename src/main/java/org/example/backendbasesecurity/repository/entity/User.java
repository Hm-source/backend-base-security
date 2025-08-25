package org.example.backendbasesecurity.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.backendbasesecurity.repository.entity.vo.Source;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User implements UserDetails {

    public final static SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority(
        "ROLE_USER");
    public final static SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority(
        "ROLE_ADMIN");
    public final static List<SimpleGrantedAuthority> SIMPLE_ROLES = List.of(ROLE_USER);
    public final static List<SimpleGrantedAuthority> ADMIN_ROLES = List.of(ROLE_USER, ROLE_ADMIN);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Integer id;
    private String username;
    private String password;
    private Source source;
    private String name;
    private Integer age;
    private String job;
    private String specialty;
    private LocalDateTime createdAt;
    @Transient // 직렬화 대상에서 제외하고 싶은 필드에 사용하는 애너테이션이다.
    private List<SimpleGrantedAuthority> authorities = ADMIN_ROLES;

    public static User create(String username, String password, String name, Integer age,
        String job, String specialty) {

        return new User(
            null,
            username,
            password,
            Source.HOMEPAGE,
            name,
            age,
            job,
            specialty,
            LocalDateTime.now(),
            ADMIN_ROLES
        );
    }


}
