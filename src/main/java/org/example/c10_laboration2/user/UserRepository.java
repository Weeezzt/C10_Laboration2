package org.example.c10_laboration2.user;

import org.example.c10_laboration2.user.entity.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Integer> {
}
