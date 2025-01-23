package ng.org.mirabilia.mdm.views.forms;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style;;

@CssImport("./themes/style.css")
public class LoginForm extends HorizontalLayout  {

    private final H3 loginText;
    public final TextField username;
    public final PasswordField password;
    private final Html loginNotificationText;
    public final Button loginButton;
    private final FormLayout loginFormLayout;
    private final HorizontalLayout secondHalfHorizontalLayout;

    public LoginForm(){
        addClassName("LoginFormLayout");
        setSizeFull();
        setHeightFull();
        setWidthFull();

        //second Half of the login screen
        loginText = new H3("Login");
        username = new TextField("USERNAME");
        password = new PasswordField("PASSWORD");
        loginNotificationText = new Html("<div>This site uses cookies. By logging in to the site, you are " +
                "agreeing to the usage of cookies. For more information, "+
                "refer to the cookie policy and privacy policy.</div>");
        secondHalfHorizontalLayout = new HorizontalLayout();
        secondHalfHorizontalLayout.add(loginNotificationText);
        loginButton = new Button("LOGIN");
        loginButton.addClassName("LoginButton");


        //loginFormLayout Assignment and initialization
        loginFormLayout = new FormLayout();
        loginFormLayout.add(loginText, username, password, secondHalfHorizontalLayout, loginButton);
        loginFormLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        VerticalLayout loginVerticalLayout = new VerticalLayout();
        loginVerticalLayout.add(loginFormLayout);
        loginVerticalLayout.setHeightFull();


        //inline styling
        loginNotificationText.getStyle().setFontSize("14px").setFontWeight(400);
        loginText.getStyle().setTextAlign(Style.TextAlign.CENTER).setFontWeight(700).setFontSize("30px");
        secondHalfHorizontalLayout.getStyle().setMaxWidth("700px").setBorderRadius("7px").setPadding("15px").setBackgroundColor("#FCD4B6")
                .setTextAlign(Style.TextAlign.CENTER).setMarginTop("20px");
        loginButton.getStyle().setMaxWidth("305px").setMarginTop("30px");
        loginFormLayout.getStyle().setMaxWidth("500px");
        loginVerticalLayout.getStyle().setJustifyContent(Style.JustifyContent.CENTER).setAlignItems(Style.AlignItems.CENTER).setPadding("20%").setMinWidth("500px");

        add(loginVerticalLayout);

    }

}
