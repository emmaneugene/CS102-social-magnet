# Object-Oriented Design Considerations

## Design patterns used

When considering the complexity of the project and the need to navigate back and forth between pages, our team decided to adopt the model-view-controller (MVC) pattern with a navigation stack.

We also load our credentials through system environment variables stored outside the git repositry to minimise exposure of sensitive data.

### Model-View-Controller

With MVC, we can separate the control flow between different pages with `Controller` objects, and render the necessary UIs with `View` objects. All data loaded from our database is represented by our models, placed under `src/.../main/model/`.

### Navigation

To store the state of our pages and the order in which they were initially called, we used a stack of `Controller` objects managed by the `Navigation` object.

Currently, because the depth of navigation is not too large, we do not have to handle any cases where the stack gets too large. However, `Navigation` can be adapted to better manage and cache its stored `Controller` objects if necessary.

#### Two-way binding of Navigation and Controller

When a controller is added to the navigation stack, we want to be able to access the `Navigation` object from within the controller such that we can add new `Controller` objects to the stack or pop the stack.

##### Arguments against creating and calling controllers from within other controllers

This method was taken as opposed to creating and calling new `Controller` objects from within other `Controller` objects. 

Instead of having to trace the navigation path from `Controller` to `Controller`, we can always inspect the `Navigation` object to determine the order in which `Controller` instances were created, and figure out the navigation path that way.

In addition, navigating up back to previous controllers is simple and controlled from a single access point under `Navigation`. 

### One-way data flow

Initially, `App` existed as a singleton to better manage the state of the application i.e. user session tracking, maintaining a single database connection pool. However upon testing, the high binding between `App` and other classes resulted in difficult-to-set-up tests.

Eventually, the application was refactored to pass only *necessary* information down into its children, with the exception of the `Navigation` and `Controller` relationship.

## Using stored procedures

By implementing all database view logic on the database itself, we can minimise the amount of hard-coded SQL queries within the application itself, and reduce binding between the application and the database design. 

## Saving tagged user information

We wanted to remember which tags were valid at the time of post creation. In addition, we need to remember where our tag was created within the content so that we could highlight the tag appropriately.

Given our requirements, we had two options of persisting that information:
1. Store a relationship table that associates users tagged to posts, and store the index of the tag within the post.
2. Mark valid user tags within the content using special format markers.

While option 2 seemed simpler, it came with multiple disadvantages
* Content on the database was littered with format markers, and the client application had to format it accordingly every time.
* There was no relational representation of the tagging relationship
* We would have to handle edge cases where content might contain our format markers

Instead, we adopted option 1, and represent the tagging relations with a table, while storing the original `@` tagging markers in the database.

When we load content in, we run through the string for all occurences of `@`, and validate the tag against the relational data before formatting accordingly.

> We assume that a user can only be tagged once in a post, and only the first valid tag is formatted.