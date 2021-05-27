Set up
=======
CQRS entails segregating Commands and queries.
Separating them so that one service handles all Commands to do something and another service
handles events generated when something has happened.


1. create commands
2. create events - events are created after something has happened. The user event objects are 
created in the core API.

Aggregates
Create a user aggregate where user commands will be handled and user events generated.
We do this in the UserCommandApi's aggregates package.

1. Annotate the aggregate with @Aggregate annotation to tell axon framework that it is an aggregate
2. We annotate the field with the aggregateIdenifier annotation which helps axon know which aggregate from the command is being targeted

Queries
-
Queries are request from the user to get something in other words we intend to query the
read database.
This can be accomplished without using Axon, but it's probably better to implement this with Axon since we can use
Axon's features such as interceptors and message monitoring.
Our queries will include:
1. A query to find a user by id
2. A query to search for users

Annotations
-
@QueryHandler - Axon annotation, used for annotating methods to handle incoming query requests,
which usually involves querying the read database.

Security
-
1. Spring security & OAuth2 - JWTS
2. For authentication and authorization
- Generate OAUTH2.0 Authorization Server
- Create user service repository so that OAUTH can use the user data to authenticate
- Implement web and auth server configuration classes.
- Configure services to use OAUTH 2, so that the services can verify the JWTs