package com.hodumaru.newsmaru.repository;

import com.hodumaru.newsmaru.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
