package ng.org.mirabilia.mdm.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "oldLogin")
public class newestLoginView extends VerticalLayout implements BeforeEnterObserver {

    private final Text loginText;
    private final TextField username;
    private final PasswordField password;
    private final Text loginNotificationText;
    private final Button loginButton;
    private final FormLayout loginFormLayout;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public newestLoginView(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        loginText = new Text("Login");
        username = new TextField("USERNAME");
        password = new PasswordField("PASSWORD");
        loginNotificationText = new Text("This site uses cookies. By logging in to the site, you are agreeing to the usage of cookies. For more information, refer to the cookie policy and privacy policy.");
        loginButton = new Button("LOGIN");

        loginFormLayout = new FormLayout();
        loginFormLayout.add(loginText, username, password, loginNotificationText, loginButton);
        loginFormLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        setSizeFull();
        loginFormLayout.setMaxWidth("500px");
        loginFormLayout.getStyle().setMargin("auto");

        add(loginFormLayout);

        loginButton.addClickListener(e -> authenticateUser(username.getValue(), password.getValue()));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            Notification.show("Authentication error. Please try again.", 3000, Notification.Position.MIDDLE);
        }
    }

    private void authenticateUser(String enteredUsername, String enteredPassword) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(enteredUsername, enteredPassword);
            authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authToken);
            Notification.show("Login successful!", 3000, Notification.Position.MIDDLE);
            getUI().ifPresent(ui -> ui.navigate("userView")); // Redirect to the user view after successful login
        } catch (AuthenticationException e) {
            Notification.show("Invalid credentials. Please try again.", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
