package com.vaadin.tutorial.crm.ui.views.list;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.tutorial.crm.backend.entity.JenisVaksin;
import com.vaadin.tutorial.crm.backend.entity.Vaksinasi;

import java.util.List;

public class VaksinasiForm extends FormLayout {

     TextField nama = new TextField("Nama");
     TextField umur = new TextField("Umur");
     TextField nik = new TextField("Nik");
     EmailField email = new EmailField("Email");
     TextField waktu = new TextField("Waktu");
     ComboBox<Vaksinasi.Status> status = new ComboBox<>("Status");
     ComboBox<JenisVaksin> jenisVaksin = new ComboBox<>("Jenis Vaksin");


     Button save = new Button("Save");
     Button delete = new Button("Delete");
     Button close = new Button("Cancel");

     Binder<Vaksinasi> binder = new BeanValidationBinder<>(Vaksinasi.class);



     public VaksinasiForm(List<JenisVaksin> companies){
          addClassName("contact-form");

          binder.bindInstanceFields(this);
          status.setItems(Vaksinasi.Status.values());
          jenisVaksin.setItems(companies);
          jenisVaksin.setItemLabelGenerator(JenisVaksin::getName);

          add(
                  nama,
                  umur,
                  nik,
                  waktu,
                  email,
                  status,
                  jenisVaksin,
                  createButtonsLayout());
     }

     public void setContact(Vaksinasi vaksinasi){
          binder.setBean(vaksinasi);
     }
     private HorizontalLayout createButtonsLayout() {
          save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
          delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
          close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

          save.addClickShortcut(Key.ENTER);
          close.addClickShortcut(Key.ESCAPE);

          save.addClickListener(click -> validateandSave());
          delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
          close.addClickListener(click -> fireEvent(new CloseEvent(this)));

          binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));
          return new HorizontalLayout(save, delete, close);
     }

     private void validateandSave() {
          if(binder.isValid()) {
               fireEvent(new SaveEvent(this, binder.getBean()));
          }
     }

     public static abstract class VaksinasiFormEvent extends ComponentEvent<VaksinasiForm> {
          private Vaksinasi vaksinasi;
          protected VaksinasiFormEvent(VaksinasiForm source, Vaksinasi vaksinasi) {
               super(source, false);
               this.vaksinasi = vaksinasi;
          }
          public Vaksinasi getContact() {
               return vaksinasi;
          }
     }
     public static class SaveEvent extends VaksinasiFormEvent {
          SaveEvent(VaksinasiForm source, Vaksinasi vaksinasi) {
               super(source, vaksinasi);
          }
     }
     public static class DeleteEvent extends VaksinasiFormEvent {
          DeleteEvent(VaksinasiForm source, Vaksinasi vaksinasi) {
               super(source, vaksinasi);
          }
     }
     public static class CloseEvent extends VaksinasiFormEvent {
          CloseEvent(VaksinasiForm source) {
               super(source, null);
          }
     }
     public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                   ComponentEventListener<T> listener) {
          return getEventBus().addListener(eventType, listener);
     }

}