package com.vaadin.tutorial.crm.ui.views.dashboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.backend.service.JenisVaksinService;
import com.vaadin.tutorial.crm.backend.service.VaksinasiService;
import com.vaadin.tutorial.crm.ui.MainLayout;

import java.util.Map;

@PageTitle("Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    private final VaksinasiService vaksinasiService;
    private final JenisVaksinService jenisVaksinService;

    public DashboardView(VaksinasiService vaksinasiService,
                         JenisVaksinService jenisVaksinService) {
        this.vaksinasiService = vaksinasiService;
        this.jenisVaksinService = jenisVaksinService;

        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(
                getContactStats(),
                getCompaniesChart()
        );

    }

    private Component getCompaniesChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> stats = jenisVaksinService.getStats();
        stats.forEach((name, number) ->
                dataSeries.add(new DataSeriesItem(name, number)));

        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private Span getContactStats() {
        Span stats = new Span(vaksinasiService.count() + " Vaksin");
        stats.addClassName("contact-stats");

        return stats;

    }
}
