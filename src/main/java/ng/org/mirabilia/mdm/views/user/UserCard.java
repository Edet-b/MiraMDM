package ng.org.mirabilia.mdm.views.user;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import ng.org.mirabilia.mdm.domain.entities.User;

public class UserCard extends HorizontalLayout {

    public UserCard(User user) {
        // Card container styling
        setWidth("300px"); // Fixed width per card
        setHeight("120px");
        setPadding(false);
        setSpacing(false);
        getStyle()
                .set("border", "1px solid #e0e0e0")
                .set("border-radius", "8px")
                .set("box-shadow", "0px 4px 6px rgba(0,0,0,0.1)")
                .set("background-color", "#f9fcff");

        // Horizontal layout for icon + details
        HorizontalLayout container = new HorizontalLayout();
        container.setPadding(true);
        container.setSpacing(true);
        container.setAlignItems(Alignment.CENTER);
        container.setWidthFull();
        container.getStyle().setMaxWidth("339px").setMaxHeight("160px");

        // User Icon
        HorizontalLayout userProfileLayout = new HorizontalLayout();
        userProfileLayout.getStyle().setWidth("100px").setHeight("100px")
                .setBorder("1px").setBorderRadius("8px").setBackgroundColor("white");
        Icon userIcon = VaadinIcon.USER.create();
        userIcon.getStyle().setAlignSelf(Style.AlignSelf.CENTER).setMargin("auto");
        userIcon.setSize("50px");
        userIcon.getStyle()
                .set("color", "#0d6efd");
        userProfileLayout.add(userIcon);

        // User Details
        VerticalLayout detailsLayout = new VerticalLayout();
        detailsLayout.setPadding(false);
        detailsLayout.setSpacing(false);

        H4 name = new H4(user.getFirstName() + " " + user.getLastName());
        name.getStyle()
                .set("margin", "5px 0")
                .set("font-size", "16px")
                .set("color", "#000");

        HorizontalLayout usernameLayout = new HorizontalLayout();
        usernameLayout.setSpacing(true);
        usernameLayout.setAlignItems(Alignment.CENTER);
        Icon usersIcon = VaadinIcon.USER.create();
        usersIcon.setSize("14px");
        usersIcon.getStyle().set("color","#0d6efd");

        Span username = new Span(user.getUsername());
        username.getStyle()
                .set("color", "black")
                .set("font-size", "12px");
        usernameLayout.add(usersIcon, username);

        // Email Row
        HorizontalLayout emailLayout = new HorizontalLayout();
        emailLayout.setSpacing(true);
        emailLayout.setAlignItems(Alignment.CENTER);
        Icon emailIcon = VaadinIcon.ENVELOPE.create();
        emailIcon.setSize("14px");
        emailIcon.getStyle().set("color", "#0d6efd");

        Span emailText = new Span(user.getEmail());
        emailText.getStyle()
                .set("font-size", "12px")
                .set("color", "#black");
        emailLayout.add(emailIcon, emailText);

        // Action icons
        HorizontalLayout actions = new HorizontalLayout();
        actions.setSpacing(true);
        actions.setMargin(false);
        actions.setAlignItems(Alignment.CENTER);

        Icon keyIcon = VaadinIcon.EDIT.create();
        keyIcon.getStyle().set("color", "#F47415").setWidth("15px");

        Icon mailIcon = VaadinIcon.LOCK.create();
        mailIcon.getStyle().set("color", "#F47415").setWidth("15px");

        Icon deleteIcon = VaadinIcon.TRASH.create();
        deleteIcon.getStyle().set("color", "#F47415").setWidth("15px");

        actions.add(keyIcon, mailIcon, deleteIcon);
        actions.getStyle().setAlignSelf(Style.AlignSelf.END);
        actions.getStyle().set("margin-top", "8px");

        // Add components to layouts
        detailsLayout.add(name, usernameLayout, emailLayout, actions);
        container.add(userProfileLayout, detailsLayout);

        add(container);
    }
}