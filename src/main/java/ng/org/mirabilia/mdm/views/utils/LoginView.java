package ng.org.mirabilia.mdm.views.utils;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import ng.org.mirabilia.mdm.views.forms.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(" ")
@RouteAlias("login")
@AnonymousAllowed

public class LoginView extends VerticalLayout {

    private final LoginForm loginForm = new LoginForm();
    private final WelcomeTextScreen welcomeTextScreen = new WelcomeTextScreen();
    private final Image mdmImage = new Image("/image/logo.png", "LOGO");
    private final DaoAuthenticationProvider authenticationProvider;

    @Autowired
    public LoginView(DaoAuthenticationProvider authenticationProvider) {

        setSizeFull();
        setHeightFull();
        setWidthFull();
        addClassName("entireScreenLayout");

        this.authenticationProvider = authenticationProvider;

        //mdm login styling
        mdmImage.getStyle().setBackgroundColor("#0A73B7").setPadding("30px");

        if (authenticationProvider == null) {
            throw new IllegalStateException("AuthenticationManager is null");
        }

        // Handle login action
        loginForm.loginButton.addClickListener(event -> {
            String username = loginForm.username.getValue();
            String password = loginForm.password.getValue();

            try {
                // Create an authentication token with user input
                Authentication auth = new UsernamePasswordAuthenticationToken(username, password);

                // Authenticate the user using the authentication provider
                Authentication authenticatedUser = authenticationProvider.authenticate(auth);

                // Check if the user is authenticated and navigate to the UserView
                if (authenticatedUser.isAuthenticated()) {
                    // Set the authenticated user in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
                    UI.getCurrent().navigate("MainView");
                } else {
                    Notification.show("Authentication failed", 3000, Notification.Position.MIDDLE)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            } catch (AuthenticationException ex) {
                Notification.show("Invalid username or password", 3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        //Layout SetUp
        AppLayout appLayout = new AppLayout();
        appLayout.addToNavbar(mdmImage);
        appLayout.getStyle().setMargin("0").setPadding("0");
        appLayout.getStyle().set("height", "20px");


        HorizontalLayout loginFormScreenLayout = new HorizontalLayout();
        loginFormScreenLayout.setSizeFull();


        loginFormScreenLayout.add(welcomeTextScreen, loginForm);

        add(appLayout, loginFormScreenLayout);
    }
}






