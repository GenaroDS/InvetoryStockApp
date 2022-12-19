package com.example.demo.views;

import com.example.demo.backend.entity.UserInfo;
import com.example.demo.backend.repository.UserInfoRepository;
import com.example.demo.backend.security.SecurityConfig;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route("register")
@AnonymousAllowed
public class RegisterView extends Composite {

    private final SecurityConfig securityConfig;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterView(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    @Override
    protected Component initContent() {
        TextField username = new TextField("Username");
        PasswordField password1 = new PasswordField("Password");
        PasswordField password2 = new PasswordField("Confirm password");

        VerticalLayout layout = new VerticalLayout(
        new H2("Register"),
                username,
                password1,
                password2,
                new Button("Send", event -> register(
                        username.getValue(),
                        password1.getValue(),
                        password2.getValue()
                )
                )

        );
        layout.setSizeFull();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        return layout;

    }


    private void register(String username, String password1, String password2) {
        if (username.trim().isEmpty()) {
            Notification.show("Enter a username");
        } else if (password1.isEmpty()) {
            Notification.show("Enter a password");
        } else if (!password1.equals(password2)) {
            Notification.show("Passwords don't match");
        } else {
            userInfoRepository.save(new UserInfo(username, passwordEncoder.encode(password1),"ADMIN"));
            Notification.show("Account created successfully!");
            UI.getCurrent().getPage().setLocation("/login");
        }
    }

}