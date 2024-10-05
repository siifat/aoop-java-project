package lms.controller.admin;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class BarGraph1 {

    final static String HRM = "Human Resource Management";
    final static String MGT = "Management ";
    final static String BBA = "Business Administration";
    final static String FIN = "Finance ";
    final static String IB = "International Business";

    public BarChart<String, Number> createBarChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bChart = new BarChart<>(xAxis, yAxis);
        bChart.setTitle("Undergraduate students of School of Business and Economics");
        xAxis.setLabel("Department");
        yAxis.setLabel("Student Count");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Summer");
        series1.getData().add(new XYChart.Data<>(HRM, 10));
        series1.getData().add(new XYChart.Data<>(MGT, 13));
        series1.getData().add(new XYChart.Data<>(BBA, 9));
        series1.getData().add(new XYChart.Data<>(FIN, 17));
        series1.getData().add(new XYChart.Data<>(IB, 5));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Fall");
        series2.getData().add(new XYChart.Data<>(HRM, 15));
        series2.getData().add(new XYChart.Data<>(MGT, 14));
        series2.getData().add(new XYChart.Data<>(BBA, 7));
        series2.getData().add(new XYChart.Data<>(FIN, 16));
        series2.getData().add(new XYChart.Data<>(IB, 8));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("Spring");
        series3.getData().add(new XYChart.Data<>(HRM, 14));
        series3.getData().add(new XYChart.Data<>(MGT, 15));
        series3.getData().add(new XYChart.Data<>(BBA, 9));
        series3.getData().add(new XYChart.Data<>(FIN, 17));
        series3.getData().add(new XYChart.Data<>(IB, 7));

        bChart.getData().addAll(series1, series2, series3);
        return bChart;
    }
}
