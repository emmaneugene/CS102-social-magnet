# Object-Oriented Design Considerations

## Model-View-Controller

`Models` supply `Views` with the data necessary for the UI.

`Views` organise data to be presented on the UI.

`Controllers` handle state, load data into `Models` and inject those into `Views`, and handle user input to decide which `Controller` to navigate to next.

### Navigation and Controller

The `Navigation` object is used to implement the [state pattern](https://en.wikipedia.org/wiki/State_pattern), where the behaviour of our application depends on its current state/controller.

The first controller on the base of the navigation stack represents our application's starting state, and the last controller on the top of the stack always represent the current state of our application.

As such, we can control the execution of our application with just one `while` loop, and depend on the state of our navigation stack to control behaviour.

* This behaviour was chosen over managing multiple loops within multiple `Controllers`, and having to trace a navigation path from `Controller` to `Controller`.

#### Two-way binding of Navigation and Controller

All `Controllers` are initialised with a `Navigation` object, and `Navigation` pushes and pops `Controller` objects onto its stack.

```java
public class Controller {
    public Controller(Navigation nav) {
        this.nav = nav;
    }
}

public class Navigation {
    public void push(Controller next) {
        navigationStack.add(next);
    }
}
```

This creates a two-way binding between `Controllers` and `Navigation`, which is necessary for `Navigation` to maintain its stack of `Controllers`, and `Controllers` to decide the next `Controller` to push onto `Navigation`.

#### Navigating to other controllers

To navigate to other controllers, instantiate the controller, then push it onto the stack.

```java
// current controller: WelcomeController
nav.push(new RegisterController(nav));
// current controller: RegisterController
nav.pop();
// current controller: WelcomeController
```

#### Passing data between Controllers

Data **must be** passed between controllers through their constructors.

### Views

There are two types of views:
1. `PageView`, responsible for representing entire pages.
2. `Component`, responsible for rendering a provided data model.

`PageViews` **belong to** `Controllers`, and `PageViews` can contain zero or many `Components`.

#### Principles

`PageView` holds a reference to the model(s) that it should display, but **not model(s) that should be displayed by its `Components`**.

```java
public class ThreadPageView {
    // Directly rendered by ThreadPageView.
    private Thread thread;

    // Comments are rendered by its Component, therefore ThreadPageView should not hold
    // a reference to List<Comment>.
    private List<CommentComponent> commentComps = ...
    ...
```

##### Passing data into PageView

For data that is **not expected to update** during the lifecycle of the `PageView`, data can be passed once through the `PageView` constructor and left as it is.
* A `Controller` lifecycle is the period from its instantiation, to being popped off the navigation stack.

If the data **is expected to change**, then it should not be passed through the constructor. Instead, the `Controller` must inject data into its `PageView` through supplied methods before it calls `PageView::display()`.

When data is injected, the `PageView` will subsequently pass data into its `Components` as necessary.

```java
public class WallPageView {
    // Not expected to update while WallPageController is on the navigation stack.
    public WallPageView(String currentUsername) {}

    // Will be updated when navigating back from ThreadController.
    public void updateThreads(List<Thread> threads)
    ...
```

##### Passing data into Component

Similarly, data that is **not expected to change** for a given instance of `Component` should be passed in through its constructor, and data that is **dynamic** should be set through setter methods before `Component::render()`.

## Database

### Credentials

We also load our credentials through system environment variables stored outside the git repositry to minimise exposure of sensitive data.

### Using stored procedures

By implementing all database-related logic on the database itself, we can minimise the amount of hard-coded SQL queries within the application, and reduce binding between the application and the database design. 

### Saving tagged user information

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
* We assume that a user can only be tagged once in a post, and only the first valid tag is formatted.