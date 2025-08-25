package org.example.backendbasesecurity.repository;

import java.util.List;
import org.example.backendbasesecurity.repository.entity.User;

public interface IUserRepositoryCustom {

    List<User> findByName(String name);
}
