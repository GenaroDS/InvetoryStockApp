package com.example.demo.views;

import com.example.demo.backend.entity.Product;
import com.example.demo.backend.entity.UserInfo;
import com.example.demo.backend.security.SecurityService;
import com.example.demo.backend.service.ProductService;
import com.example.demo.backend.service.UserDetailsImpl;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import javax.annotation.security.PermitAll;

@Route("")
@PermitAll
@PageTitle("Products | StockApp")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {
    Grid<Product> grid = new Grid<>(Product.class);
    TextField filterText = new TextField();
    ProductForm form;
    UserDetailsImpl userDetails;
    ProductService productService;
    public UserInfo currentUser;


    public MainView(SecurityService securityService, ProductService productService) {
        addClassName("main-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        this.productService = productService;
        currentUser = userDetails.returnThisUser();

        Button logout = new Button("Logout", click -> securityService.logout());
        H2 logo = new H2("Welcome to StockApp: " + userDetails.getUsername());
        configureGrid(productService,userDetails);

        configureForm(userDetails,productService);
        FlexLayout content = new FlexLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setFlexShrink(0, form);
        content.addClassNames("content", "gap-m");
        content.setSizeFull();
        updateList(productService);
        HorizontalLayout h1 = getToolbar(productService);
        System.out.println(userDetails.returnThisUser());


        add(logo, h1, content ,logout);
        closeEditor();
    }

    private void configureForm(UserDetailsImpl userDetails, ProductService productService) {
        form = new ProductForm(userDetails.returnThisUser(),productService);
        form.setWidth("25em");
        form.addListener(ProductForm.SaveEvent.class, this::saveContact);
        form.addListener(ProductForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ProductForm.CloseEvent.class, closeEvent -> closeEditor());
    }

    private void deleteContact(ProductForm.DeleteEvent event){
        Product productToDelte = event.getProduct();
        this.productService.delete(productToDelte);
        updateList(productService);
        closeEditor();
    }

    private void saveContact(ProductForm.SaveEvent event){
        Product productToSave = event.getProduct();
        productToSave.setUser(currentUser);
        this.productService.add(productToSave);

        updateList(productService);
        closeEditor();
    }

    private void closeEditor() {
        form.setProduct(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    public void configureGrid(ProductService productService, UserDetailsImpl userDetails) {
        addClassName("grid");
        UserInfo user = userDetails.returnThisUser();
        this.grid.setItems(productService.find(user.getUserId()));
        this.grid.setColumns("id", "name", "price", "quantity");
        this.grid.addClassName("product-grid");
        this.grid.setSizeFull();

        grid.asSingleSelect().addValueChangeListener(e -> editProduct(e.getValue()));

    }

    private void editProduct(Product product) {
        if (product == null) {
            closeEditor();
        } else {
            form.setProduct(product);
            form.setVisible(true);
            addClassName("editing");
        }

    }

    private HorizontalLayout getToolbar(ProductService productService) {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList(productService));

        Button addContactButton = new Button("Add product");
        addContactButton.addClickListener(click -> addProduct());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addProduct() {
        grid.asSingleSelect().clear();
        editProduct(new Product());
    }

    private void updateList(ProductService productService) {
        grid.setItems(productService.find(currentUser.getUserId()));
    }

}