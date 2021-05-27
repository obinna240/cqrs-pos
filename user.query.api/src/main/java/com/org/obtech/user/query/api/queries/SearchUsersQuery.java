package com.org.obtech.user.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SearchUsersQuery {
    private String searchFilter;
}
