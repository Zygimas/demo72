package com.example.demo72.repository;

import com.example.demo72.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findById(Integer userID);
    void deleteById(Integer userId);
}
