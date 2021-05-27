package com.org.obtech.user.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FindUserByIdQuery {
    private String id;
}
