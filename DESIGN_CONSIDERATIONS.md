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