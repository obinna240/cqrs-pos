package com.org.obtech.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@Document(collection = "users")
public final class User {

   @Id
   private String id;
   private String firstname;
   private String lastname;
   private Account account;

}
