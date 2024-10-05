package lms.controller.admin;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class BarGraph2 {

    final static String MSJ = "Media Studies & Journalism";
    final static String BSSEDS = "Environment & Development Studies";
    final static String BAE = "English";
    final static String FIN = "Finance ";
    final static String IB = "International Business";

    public BarChart<String, Number> createBarChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bChart = new BarChart<>(xAxis, yAxis);
        bChart.setTitle("Undergraduate students of School of Humanities and Social Sciences");
        xAxis.setLabel("Department");
        yAxis.setLabel("Student Count");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Summer");
        series1.getData().add(new XYChart.Data<>(MSJ, 9));
        series1.getData().add(new XYChart.Data<>(BSSEDS, 4));
        series1.getData().add(new XYChart.Data<>(BAE, 9));
        series1.getData().add(new XYChart.Data<>(FIN, 11));
        series1.getData().add(new XYChart.Data<>(IB, 7));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Fall");
        series2.getData().add(new XYChart.Data<>(MSJ, 10));
        series2.getData().add(new XYChart.Data<>(BSSEDS, 4));
        series2.getData().add(new XYChart.Data<>(BAE, 7));
        series2.getData().add(new XYChart.Data<>(FIN, 12));
        series2.getData().add(new XYChart.Data<>(IB, 8));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("Spring");
        series3.getData().add(new XYChart.Data<>(MSJ, 10));
        series3.getData().add(new XYChart.Data<>(BSSEDS, 4));
        series3.getData().add(new XYChart.Data<>(BAE, 9));
        series3.getData().add(new XYChart.Data<>(FIN, 11));
        series3.getData().add(new XYChart.Data<>(IB, 7));

        bChart.getData().addAll(series1, series2, series3);
        return bChart;
    }
}
