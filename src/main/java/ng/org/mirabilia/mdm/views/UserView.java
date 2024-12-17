package ng.org.mirabilia.mdm.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.RolesAllowed;
import ng.org.mirabilia.mdm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = " ")
@RouteAlias("userView")
@RolesAllowed({"ADMIN"})
public class UserView extends VerticalLayout {

    Button addNewUser;

    @Autowired
    UserRepository userRepository;


    @Autowired
    public UserView(UserRepository userRepository){
        this.userRepository = userRepository;

        addNewUser = new Button("ADD USER", e ->  UI.getCurrent().navigate(UserFormView.class));
        addNewUser.setPrefixComponent(new Icon(VaadinIcon.PLUS));

        add(addNewUser);
    }



}
