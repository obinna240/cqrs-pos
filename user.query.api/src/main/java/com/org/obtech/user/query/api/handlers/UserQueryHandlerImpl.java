package com.org.obtech.user.query.api.handlers;

import com.org.obtech.user.query.api.dto.UserLookupResponse;
import com.org.obtech.user.query.api.queries.FindAllUsersQuery;
import com.org.obtech.user.query.api.queries.FindUserByIdQuery;
import com.org.obtech.user.query.api.queries.SearchUsersQuery;
import com.org.obtech.user.query.api.repositories.UserRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserQueryHandlerImpl implements UserQueryHandler {

    private final UserRepository userRepository;

    @Autowired
    public UserQueryHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryHandler
    @Override
    public UserLookupResponse getUserById(FindUserByIdQuery findUserByIdQuery) {
        var user = userRepository.findById(findUserByIdQuery.getId());
        return user.isPresent() ? new UserLookupResponse(user.get()) : null;
    }

    @QueryHandler
    @Override
    public UserLookupResponse searchUser(SearchUsersQuery searchUsersQuery) {
        var userlist = userRepository.findByFilterRegex(searchUsersQuery.getSearchFilter());
        return new UserLookupResponse(userlist);
    }

    @QueryHandler
    @Override
    public UserLookupResponse getAllUsers(FindAllUsersQuery findAllUsersQuery) {
       var users = new ArrayList<>(userRepository.findAll());
       return new UserLookupResponse(users);
    }
}
