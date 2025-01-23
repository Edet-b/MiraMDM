package ng.org.mirabilia.mdm.views.user;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import ng.org.mirabilia.mdm.domain.entities.User;
import ng.org.mirabilia.mdm.repositories.UserRepository;
import ng.org.mirabilia.mdm.services.UserService;
import ng.org.mirabilia.mdm.views.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Route(value = "UserView", layout = MainView.class)
@PageTitle("User | Mobile Device Management")

@PermitAll
public class UserView extends VerticalLayout {

    Button addNewUser;
    TextField search = new TextField();

    @Autowired
    UserRepository userRepository;
    UserService userService;

    @Autowired
    public UserView(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;

        //styling
        setSizeFull();
        setWidthFull();
        setHeightFull();

        //filtering text
        search.setPlaceholder("search");
        search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        search.setClearButtonVisible(true);
        search.setValueChangeMode(ValueChangeMode.LAZY);
        search.addValueChangeListener(e -> userService.findByUsername(search.getValue()));

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.getStyle().set("background-color", "#f5f5f5");

        HorizontalLayout actionBar = new HorizontalLayout();
        Button bulkEditButton = new Button("Bulk Edit");
        bulkEditButton.getStyle()
                .set("background-color", "#f39c12")
                .set("color", "white");

        HorizontalLayout actionBarTwo = new HorizontalLayout();
        actionBarTwo.add(search);

        addNewUser = new Button("ADD USER", e -> UI.getCurrent().navigate(UserFormView.class));
        addNewUser.setPrefixComponent(new Icon(VaadinIcon.PLUS));

        actionBar.add(addNewUser, bulkEditButton);
        actionBar.setSpacing(true);


        //inline styling
        FlexLayout gridLayout = new FlexLayout();
        gridLayout.setWidthFull();
        gridLayout.getStyle()
                .set("display", "grid")
                .set("gap", "15px 15px")
                .setAlignSelf(Style.AlignSelf.CENTER)
                .setAlignItems(Style.AlignItems.CENTER);

        addNewUser.getStyle()
                .set("background-color", "#0d6efd")
                .set("color", "#ffffff")
                .set("border-radius", "4px")
                .set("padding", "10px 15px");

        String text = (String) VaadinSession.getCurrent().getAttribute("savedData");

        if(text == null) {
            text = "sidebar";

            String finalText = text;
            UI.getCurrent().access(() -> {
            if("sidebar".equals(finalText)){
                gridLayout.getStyle()
                        .set("grid-template-columns", "repeat(3, auto)");
            } else if("sidebarWithOnlyIcons".equals(finalText)){
                gridLayout.getStyle()
                        .set("grid-template-columns", "repeat(4, auto)");
            }
        });
        }


        List<User> users = userRepository.findAll();
        UserCard card;

        // Add user cards to the layout
        for (User user : users) {
            card = new UserCard(user);

            //inline styling for the cards
            card.setWidth("350px");
            card.setHeight("150px");
            card.getStyle()
                    .set("border", "1px solid #ddd")
                    .set("border-radius", "8px")
                    .set("box-shadow", "0px 4px 6px rgba(0,0,0,0.1)")
                    .set("background-color", "#E7F1F8")
                    .set("padding", "15px");

            card.setAlignItems(Alignment.CENTER);
            card.setJustifyContentMode(JustifyContentMode.CENTER);
            gridLayout.add(card);
        }

        mainLayout.add(actionBar,actionBarTwo, gridLayout);
        add(mainLayout);
    }

}


