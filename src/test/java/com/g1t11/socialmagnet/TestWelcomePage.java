// package com.g1t11.socialmagnet;

// import com.g1t11.socialmagnet.util.TestSocialMagnet;
// import com.g1t11.socialmagnet.controller.WelcomePageController;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.BeforeAll;

// // @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// public class TestWelcomePage extends TestSocialMagnet {
//     @BeforeAll
//     public static void initializeWelcome() {
//         App.shared().setAppName("Social Magnet");
//         App.shared().getNavigation().setFirstController(new WelcomePageController());
//     }

//     @Test
//     @Order(1)
//     public void displayMenu() {
//         App.shared().getNavigation().getCurrentController().updateView();

//         String expected = "\033[H\033[2J";
//         expected += String.join("\n",
//             "== Social Magnet :: Welcome ==",
//             "Good afternoon, anonymous!",
//             "1. Register",
//             "2. Login",
//             "3. Exit",
//             "Enter your choice > "
//         );
//         Assertions.assertEquals(expected, getOutput());
//     }

//     @Test
//     @Order(2)
//     public void simulateInvalidInput() {
//         provideInput("5");
//         App.shared().getNavigation().getCurrentController().handleInput();
//         App.shared().getNavigation().getCurrentController().updateView();

//         String expectedError = "Please enter a choice between 1 & 3!";
//         String errorMessage = getOutput().split("\\r?\\n")[1];
//         Assertions.assertEquals(expectedError, errorMessage);
//     }

//     @Test
//     @Order(3)
//     public void simulateExit() {
//         provideInput("3");
//         App.shared().getNavigation().getCurrentController().handleInput();

//         String expectedMessage = "Goodbye!\n";
//         String message = getOutput();
//         Assertions.assertEquals(expectedMessage, message);
//     }
// }
