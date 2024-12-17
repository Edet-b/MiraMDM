package ng.org.mirabilia.mdm.views;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import ng.org.mirabilia.mdm.repositories.UserRepository;
import ng.org.mirabilia.mdm.service.UserService;
import ng.org.mirabilia.mdm.views.forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "addNewUser")
@RolesAllowed({"ADMIN"})
public class UserFormView extends VerticalLayout {

    private UserService userService;
    private UserRepository userRepository;
    private UserForm userForm;
    Button backToUsersButton;

    @Autowired
    public UserFormView(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;

        userForm = new UserForm(userService, userRepository);

        backToUsersButton = new Button("Back To Users", e -> UI.getCurrent().navigate(UserView.class));

        backToUsersButton.setPrefixComponent(new Icon(VaadinIcon.ARROW_BACKWARD));
        add(backToUsersButton, userForm);
    }






}
