Set up
=======


1. create commands
2. create events - events are created after something has happened. The user event objects are 
created in the core API.

Aggregates
Create a user aggregate where user commands will be handled and user events generated.
We do this in the UserCommandApi's aggregates package.

1. Annotate the aggregate with @Aggregate annotation to tell axon framework that it is an aggregate
2. We annotate the field with the aggregateIdenifier annotation which helps axon know which aggregate from the command is being targeted
