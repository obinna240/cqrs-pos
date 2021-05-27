package com.org.obtech.user.query.api.handlers;

import com.org.obtech.user.query.api.dto.UserLookupResponse;
import com.org.obtech.user.query.api.queries.FindAllUsersQuery;
import com.org.obtech.user.query.api.queries.FindUserByIdQuery;
import com.org.obtech.user.query.api.queries.SearchUsersQuery;

public interface UserQueryHandler {

    UserLookupResponse getUserById(FindUserByIdQuery findUserByIdQuery);
    UserLookupResponse searchUser(SearchUsersQuery searchUsersQuery);
    UserLookupResponse getAllUsers(FindAllUsersQuery findAllUsersQuery);
}
