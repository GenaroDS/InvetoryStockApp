package com.example.demo.views;

import com.example.demo.backend.entity.Product;
import com.example.demo.backend.security.SecurityService;
import com.example.demo.backend.service.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

import javax.annotation.security.PermitAll;

@Route("")
@PermitAll
public class MainView extends VerticalLayout {

    private SecurityService securityService;

    public MainView(SecurityService securityService, ProductService productService) {

        this.securityService = securityService;
        var crud = new GridCrud<>(Product.class,productService);
        crud.getGrid().setColumns("name","price","quantity","id");
        crud.getCrudFormFactory().setVisibleProperties("name","price","quantity");

        HorizontalLayout layout;

        if (securityService.getAuthenticatedUser() != null) {
            Button logout = new Button("Logout", click ->
                    securityService.logout());
            layout = new HorizontalLayout(logout);
        } else {
            layout = new HorizontalLayout();
        }

        H2 logo = new H2("StockApp");

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);


        add(logo, crud, layout);

    }
}