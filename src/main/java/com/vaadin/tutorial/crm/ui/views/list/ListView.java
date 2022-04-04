package com.vaadin.tutorial.crm.ui.views.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.tutorial.crm.backend.entity.JenisVaksin;
import com.vaadin.tutorial.crm.backend.entity.Vaksinasi;
import com.vaadin.tutorial.crm.backend.service.JenisVaksinService;
import com.vaadin.tutorial.crm.backend.service.VaksinasiService;
import com.vaadin.tutorial.crm.ui.MainLayout;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Sistem Informasi Vaksinasi")
public class ListView extends VerticalLayout {

    private final VaksinasiForm form;
    Grid<Vaksinasi> grid = new Grid<>(Vaksinasi.class);
    TextField filterText = new TextField();
    private VaksinasiService vaksinasiService;


    public ListView(VaksinasiService vaksinasiService,
                    JenisVaksinService jenisVaksinService) {
        this.vaksinasiService = vaksinasiService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();


        form = new VaksinasiForm(jenisVaksinService.findAll());
        form.addListener(VaksinasiForm.SaveEvent.class, this::saveContact);
        form.addListener(VaksinasiForm.DeleteEvent.class, this::deleteContact);
        form.addListener(VaksinasiForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void deleteContact(VaksinasiForm.DeleteEvent evt) {
        vaksinasiService.delete(evt.getContact());
        updateList();
        closeEditor();
    }

    private void saveContact(VaksinasiForm.SaveEvent evt) {
        vaksinasiService.save(evt.getContact());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter dengan nama...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        Registration registration = filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Tambah data", click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Vaksinasi());
    }

    private void updateList() {
        grid.setItems(vaksinasiService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("jenisVaksin");
        grid.setColumns("nama", "umur", "nik", "email", "waktu", "status");
        grid.addColumn(contact -> {
            JenisVaksin jenisVaksin = contact.getJenisVaksin();

            return jenisVaksin == null ? "-" : jenisVaksin.getName();
        }).setHeader("Jenis Vaksin");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));
    }

    private void editContact(Vaksinasi vaksinasi) {
        if(vaksinasi == null) {
            closeEditor();
        } else {
          form.setContact(vaksinasi);
          form.setVisible(true);
          addClassName("editing");
        }
    }

}
