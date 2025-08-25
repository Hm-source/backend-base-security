package org.example.backendbasesecurity.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.backendbasesecurity.repository.entity.QUser;
import org.example.backendbasesecurity.repository.entity.User;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustom implements IUserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> findByName(String name) {
        return jpaQueryFactory.selectFrom(QUser.user)
            .where(QUser.user.name.trim().eq(name))
            .fetch();
    }
}