package ng.org.mirabilia.mdm.views.forms;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import ng.org.mirabilia.mdm.domain.entities.User;
import ng.org.mirabilia.mdm.domain.enums.Role;
import ng.org.mirabilia.mdm.domain.enums.UserStoreDomain;
import ng.org.mirabilia.mdm.repositories.UserRepository;
import ng.org.mirabilia.mdm.services.UserService;
import ng.org.mirabilia.mdm.views.user.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

public class UserForm extends VerticalLayout {

    private UserService userService;
    private Text userNotificationText;
    private ComboBox<UserStoreDomain> userStoreDomainField;
    private TextField usernameField;
    private TextField firstNameField;
    private TextField lastNameField;
    private EmailField emailField;
    private ComboBox<Role> userRolesField;
    private Button saveUserButton;
    private FormLayout userFormLayout;
    UserRepository userRepository;
    private final Consumer<Void> onSuccess;

    @Autowired
    public UserForm(UserService userService, UserRepository userRepository, Consumer<Void> onSuccess){

        this.userService = userService;
        this.userRepository = userRepository;
        this.onSuccess = onSuccess;

        H2 addUser = new H2("Add User");
        userNotificationText = new Text("Please note that * represents required field of data");
        userStoreDomainField = new ComboBox<>("User Store Domain");
        userStoreDomainField.setItems(UserStoreDomain.PRIMARY);

        usernameField = new TextField("User Name");
        firstNameField = new TextField("First Name");
        lastNameField = new TextField("Last Name");
        emailField = new EmailField("Email");
        saveUserButton = new Button("Add User", e -> saveUser()); // Call the method to save the user)

        userRolesField = new ComboBox<>("User Role");
        userRolesField.setItems(Role.values());


        userFormLayout = new FormLayout();
        userFormLayout.add(addUser, userNotificationText, userStoreDomainField, usernameField, firstNameField, lastNameField,
                emailField, userRolesField, saveUserButton);
        userFormLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));


        //Inline Styling
        setSizeFull();
        userFormLayout.setMaxWidth("500px");
        add(userFormLayout);

    }

    //getting the users details and saving it
    private void saveUser() {
        String firstName = firstNameField.getValue();
        String lastName = lastNameField.getValue();
        String email = emailField.getValue();
        String userName = usernameField.getValue();

        var userRoles = userRolesField.getValue();
        var userStoreDomain = userStoreDomainField.getValue();

        // Check if email or username already exists
        Optional<User> userEmailRepo = userRepository.findByEmail(email);
        Optional<User> userNameRepo = userRepository.findByUsername(userName);

        boolean isEmailTaken = userEmailRepo.isPresent();
        boolean isUsernameTaken = userNameRepo.isPresent();

        if (isEmailTaken) {
            Notification.show("Email already exists", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return; // Stop execution if any error condition is met
        }

        if (isUsernameTaken) {
            Notification.show("Username already exists", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return; // Stop execution if any error condition is met
        }

        // Validate that all fields are filled and that email/username are not taken
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || userName.isEmpty() ||
                userRoles.isEmpty() || userStoreDomain.isEmpty()) {
            Notification.show("All fields are required", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return; // Stop execution if any error condition is met
        }

        // If all checks pass, create and save the new user
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setUsername(userName);
        newUser.setPassword(generatedUserPassword());
        newUser.setDateAndTimeCreated(LocalDateTime.now());
        newUser.setUserrole(userRoles);
        newUser.setUserStoreDomain(userStoreDomain);

        userService.addUser(newUser);


        Notification.show("Username: " + userName + ",  Password: " + generatedUserPassword(),
                       5000, Notification.Position.MIDDLE)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        saveUserButton.addClickListener(e -> UI.getCurrent().navigate(UserView.class));
        onSuccess.accept(null);
    }


    private String generatedUserPassword(){
        return generatePassword(8);
    }

    public static String generatePassword(int length) {
        // Ensure the password length is at least 8 characters
        if (length < 8) {
            throw new IllegalArgumentException("Password length must be at least 8 characters.");
        }

        // Characters to use in password
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        String specialCharacters = "!@#$%^&*()-_+=<>?/[]{}|";

        // Combine all characters
        String allCharacters = lowerCaseLetters + upperCaseLetters + digits + specialCharacters;
        Random random = new Random();

        // Generate password
        StringBuilder password = new StringBuilder();

        // Ensure at least one character from each set is included
        password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        // Fill the rest of the password length with random characters
        for (int i = 4; i < length; i++) {
            password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }

        // Shuffle the password for randomness
        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }

        return new String(passwordArray);
    }
}







