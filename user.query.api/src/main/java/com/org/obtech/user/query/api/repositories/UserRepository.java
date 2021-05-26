package com.org.obtech.user.query.api.repositories;

import com.org.obtech.user.core.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
