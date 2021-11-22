package com.ncwu.Repository;

import com.ncwu.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);

}
