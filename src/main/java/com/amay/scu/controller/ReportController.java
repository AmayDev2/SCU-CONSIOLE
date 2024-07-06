package com.amay.scu.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;

public class ReportController {



    @FXML
    private PieChart summaryPieChart;

    @FXML
    public void initialize() {


        // Initialize the pie chart
        initializePieChart();
    }

    private void initializePieChart() {
        // Create pie chart data
        PieChart.Data operational = new PieChart.Data("Operational", 30);
        PieChart.Data underMaintenance = new PieChart.Data("Under Maintenance", 15);
        PieChart.Data outOfService = new PieChart.Data("Out of Service", 5);

        // Add data to the pie chart
        summaryPieChart.getData().add(operational);
        summaryPieChart.getData().add(underMaintenance);
        summaryPieChart.getData().add(outOfService);

        // Add tooltips and data labels
        for (PieChart.Data data : summaryPieChart.getData()) {
            Tooltip tooltip = new Tooltip(data.getName() + ": " + data.getPieValue());
            Tooltip.install(data.getNode(), tooltip);

            // Data labels
            data.getNode().setOnMouseEntered(event -> {
                data.getNode().setStyle("-fx-opacity: 0.7;");
                Tooltip.install(data.getNode(), tooltip);
            });
            data.getNode().setOnMouseExited(event -> data.getNode().setStyle("-fx-opacity: 1.0;"));
        }
    }
}
