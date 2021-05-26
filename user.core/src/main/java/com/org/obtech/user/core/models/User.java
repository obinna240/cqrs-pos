package com.org.obtech.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@Document(collection = "users")
public final class User {

   @Id
   private String id;
   @NotEmpty(message = "First name cannot be null")
   private String firstname;
   @NotEmpty(message = "Last name cannot be null")
   private String lastname;
   @NotNull(message = "Account cannot be null")
   @Valid
   private Account account;
   @Email(message = "Email MUST BE VALID")
   private String email;

}
