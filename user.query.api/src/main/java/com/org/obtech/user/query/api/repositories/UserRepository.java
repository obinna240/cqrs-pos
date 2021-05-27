package com.org.obtech.user.query.api.repositories;

import com.org.obtech.user.core.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{'$or' : [{'firstname': {$regex: ?0, $options: '1'}}, {'lastname': {$regex : ?0, $options: '1'}}, {'email': {$regex : ?0, $options: '1'}}, {'account.username': {$regex : ?0, $options: '1'}}]}")
    List<User> findByFilterRegex(String queryFilter);

}
