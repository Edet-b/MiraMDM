package ng.org.mirabilia.mdm.views.components;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NavItemIconsOnly  extends HorizontalLayout {

    public NavItemIconsOnly(Icon icon){
        icon.setSize("24px");
        add(icon);
        setAlignItems(Alignment.CENTER);
        setSpacing(true);

    }
}
