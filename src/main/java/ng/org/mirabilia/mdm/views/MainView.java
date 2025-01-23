package ng.org.mirabilia.mdm.views;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import ng.org.mirabilia.mdm.views.components.NavItem;
import ng.org.mirabilia.mdm.views.components.NavItemIconsOnly;
import ng.org.mirabilia.mdm.views.user.UserView;

import java.util.ArrayList;
import java.util.List;


@Route(value = "MainView")
@PermitAll
@CssImport("./themes/style.css")
public class MainView extends AppLayout implements AfterNavigationObserver {

    private VerticalLayout sidebar;
    private VerticalLayout sidebarWithOnlyIcons;
    private final List<RouterLink> routerLinks = new ArrayList<>();

    public MainView() {
        Button toggle = new Button(new Icon(VaadinIcon.LINES));
        addToNavbar(toggle);

        // Sidebar menu
        sidebar = new VerticalLayout();
        sidebar.addClassName("sidebar");

        // Add menu items with both icons and labels (default when expanded)
        sidebar.add(createMenuItemWithText(VaadinIcon.HOME_O, "Home", UserView.class));
        sidebar.add(createMenuItemWithText(VaadinIcon.BROWSER, "Device Management",  UserView.class));
        sidebar.add(createMenuItemWithText(VaadinIcon.GRID_BIG_O, "Group Management",  UserView.class));
        sidebar.add(createMenuItemWithText(VaadinIcon.USERS, "User Management",  UserView.class));
        sidebar.add(createMenuItemWithText(VaadinIcon.LAPTOP, "Device type Management",  UserView.class));
        sidebar.add(createMenuItemWithText(VaadinIcon.STOP_COG, "Policy Management",  UserView.class));
        sidebar.add(createMenuItemWithText(VaadinIcon.COG_O, "Configuration Setting",  UserView.class));
        sidebar.add(createMenuItemWithText(VaadinIcon.CLIPBOARD, "Device Location",  UserView.class));

        sidebarWithOnlyIcons = new VerticalLayout();
        sidebarWithOnlyIcons.addClassName("sidebarWithOnlyIcons");

        // Add menu items with both icons and labels (default when expanded)
        sidebarWithOnlyIcons.add(createMenuItemWithIcons(VaadinIcon.HOME_O,  UserView.class));
        sidebarWithOnlyIcons.add(createMenuItemWithIcons(VaadinIcon.BROWSER,  UserView.class));
        sidebarWithOnlyIcons.add(createMenuItemWithIcons(VaadinIcon.GRID_BIG_O,  UserView.class));
        sidebarWithOnlyIcons.add(createMenuItemWithIcons(VaadinIcon.USERS,  UserView.class));
        sidebarWithOnlyIcons.add(createMenuItemWithIcons(VaadinIcon.LAPTOP,  UserView.class));
        sidebarWithOnlyIcons.add(createMenuItemWithIcons(VaadinIcon.STOP_COG,  UserView.class));
        sidebarWithOnlyIcons.add(createMenuItemWithIcons(VaadinIcon.COG_O,  UserView.class));
        sidebarWithOnlyIcons.add(createMenuItemWithIcons(VaadinIcon.CLIPBOARD,  UserView.class));

        // Add sidebar to the drawer
        addToDrawer(sidebar);

        toggle.addClickListener(
                clickEvent -> {
                    removeClassName("sidebar");
                    remove(sidebar);

                    VaadinSession.getCurrent().setAttribute("savedData", "sidebarWithOnlyIcons");
                    addToDrawer(sidebarWithOnlyIcons);
                });

        toggle.addSingleClickListener(
                clickEvent -> {
                    removeClassName("sidebarWithOnlyIcons");
                    remove(sidebarWithOnlyIcons);

                    VaadinSession.getCurrent().setAttribute("savedData", "sidebar");
                    addToDrawer(sidebar);
                });
    }

    // Method to create a menu item with both icons and text
    private RouterLink createMenuItemWithText(VaadinIcon icon, String text, Class<? extends com.vaadin.flow.component.Component> navigationTarget) {
        Icon vaadinIcon = icon.create();
        vaadinIcon.getStyle().setColor("#F47415");

        RouterLink link = new RouterLink();
        link.addClassName("drawer-link");

        NavItem menuItem = new NavItem(vaadinIcon, text);
        menuItem.setAlignItems(FlexComponent.Alignment.CENTER);

        link.add(menuItem);
        link.setRoute(navigationTarget);
        routerLinks.add(link);

        return link;
    }

    //Method to create a menu item with only icons
    private RouterLink createMenuItemWithIcons(VaadinIcon icon,  Class<? extends com.vaadin.flow.component.Component> navigationTarget) {
        Icon vaadinIcon = icon.create();
        vaadinIcon.getStyle().setColor("#F47415");

        RouterLink link = new RouterLink();
        link.addClassName("drawer-link-icons");

        NavItemIconsOnly menuItem = new NavItemIconsOnly(vaadinIcon);
        menuItem.setAlignItems(FlexComponent.Alignment.CENTER);

        link.add(menuItem);
        link.setRoute(navigationTarget);
        routerLinks.add(link);

        return link;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        String activeUrl = event.getLocation().getPath();

        System.out.println("Nav: " + event.getLocation().toString() + ":::"+event.getLocation().getPath());
        routerLinks.forEach(link -> {
            if (link.getHref().equals(activeUrl)) {
                link.addClassName("active-link");
            } else {
                link.removeClassName("active-link");
            }
        });
    }
}