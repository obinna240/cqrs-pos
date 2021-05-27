package com.org.obtech.user.query.api.dto;

import com.org.obtech.user.core.dto.BaseResponse;
import com.org.obtech.user.core.models.User;
import java.util.ArrayList;
import java.util.List;

public class UserLookupResponse extends BaseResponse {

    //make this immutable later
    private List<User> userList;

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return this.userList;
    }

    public UserLookupResponse(List<User> userList) {
        super(null);
        this.userList = userList;
    }

    public UserLookupResponse(String message, User user) {
        super(message);
        this.userList = new ArrayList<>();
        this.userList.add(user);
    }

    public UserLookupResponse(User user) {
        super(null);
        this.userList = new ArrayList<>();
        this.userList.add(user);
    }
}
