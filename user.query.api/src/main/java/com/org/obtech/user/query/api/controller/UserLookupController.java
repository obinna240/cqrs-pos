package com.org.obtech.user.query.api.controller;

import com.org.obtech.user.query.api.dto.UserLookupResponse;
import com.org.obtech.user.query.api.queries.FindAllUsersQuery;
import com.org.obtech.user.query.api.queries.FindUserByIdQuery;
import com.org.obtech.user.query.api.queries.SearchUsersQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/lookup")
public class UserLookupController {

    //the query gateway is a query dispatching mechanism that allows us to dispatch a query
    //and wait synchronously or asynchronously for a response
    private final QueryGateway queryGateway;

    @Autowired
    public UserLookupController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping(path = "/all")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE)")
    public ResponseEntity<UserLookupResponse> getAllUsers(){
        try {
            var query = new FindAllUsersQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();

            if(response == null || response.getUserList() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
//            var resReturned = Optional.ofNullable(response)
//                    .filter(res -> res == null || res.getUserList() == null)
//                    .map(res -> new ResponseEntity<>(null, HttpStatus.NO_CONTENT))
//                    .orElse(new ResponseEntity<>(response, HttpStatus.OK));
//            return resReturned;
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch(Exception exception) {
            var errormsg = "Unable to get users requests";
            return new ResponseEntity<>(new UserLookupResponse(errormsg, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/id/{id}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE)")
    public ResponseEntity<UserLookupResponse> getUserById(@PathVariable("id") String id){
        try {
            var query = new FindUserByIdQuery(id);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();

            if(response == null || response.getUserList() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch(Exception exception) {
            var errormsg = "Unable to get user by id request";
            return new ResponseEntity<>(new UserLookupResponse(errormsg, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/filter/{filter}")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE)")
    public ResponseEntity<UserLookupResponse> searchUserByFilter(@PathVariable("filter") String filter){
        try {
            var query = new SearchUsersQuery(filter);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(UserLookupResponse.class)).join();

            if(response == null || response.getUserList() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch(Exception exception) {
            var errormsg = "Unable to get search request";
            return new ResponseEntity<>(new UserLookupResponse(errormsg, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
