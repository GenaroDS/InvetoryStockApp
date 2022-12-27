package com.example.demo.views;

import com.example.demo.backend.entity.Product;
import com.example.demo.backend.entity.UserInfo;
import com.example.demo.backend.service.ProductService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;


public class ProductForm extends FormLayout {
    TextField name = new TextField("Product name");
    IntegerField price = new IntegerField("Product price");
    IntegerField quantity = new IntegerField("Product quantity");
    ProductService productService;

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button cancel = new Button("cancel");
    Binder<Product> productBinder = new BeanValidationBinder<>(Product.class);
    Binder<UserInfo> userInfoBinder = new BeanValidationBinder<>(UserInfo.class);
    private Product product;

    public ProductForm(UserInfo user, ProductService productService){
        addClassName("product-form");
        this.productService = productService;
        productBinder.bindInstanceFields(this);
        add(name,price,quantity, createButtonsLayout());

    }

    public void setProduct(Product product) {
        this.product = product;
        productBinder.setBean(product);
    }



    private Component createButtonsLayout() {
        productBinder.addStatusChangeListener(e -> save.setEnabled(productBinder.isValid()));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, product)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent((this))));

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            productBinder.writeBean(product);
            fireEvent(new SaveEvent(this, product));
        } catch (ValidationException e){

            e.printStackTrace();
        }
    }

    public void setFormName(String name){
        this.name.setValue(name);
    }
    public void setFormPrice(Integer price){
        this.price.setValue(price);
    }
    public void setFormQuantity(Integer quantity){
        this.quantity.setValue(quantity);
    }


    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<ProductForm> {
        private Product product;

        protected ContactFormEvent(ProductForm source, Product product) {
            super(source, false);
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ProductForm source, Product product) {
            super(source, product);

        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ProductForm source, Product product) {
            super(source, product);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ProductForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
