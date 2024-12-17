package ng.org.mirabilia.mdm.utills;


import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.dependency.NpmPackage.Container;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginI18n;

@Tag("vaadin-login-form")
@Container({@NpmPackage(
        value = "@vaadin/polymer-legacy-adapter",
        version = "24.4.10"
), @NpmPackage(
        value = "@vaadin/login",
        version = "24.4.10"
)})
@com.vaadin.flow.component.dependency.JsModule.Container(
        {@JsModule("@vaadin/polymer-legacy-adapter/style-modules.js"),
                @JsModule("@vaadin/login/src/vaadin-login-form.js")})
public class LoginFormx extends AbstractLogin implements HasStyle {
    public LoginFormx() {
    }

    public LoginFormx(LoginI18n i18n) {
        super(i18n);
    }
}
