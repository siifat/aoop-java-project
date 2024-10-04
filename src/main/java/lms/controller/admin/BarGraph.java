package lms.controller.admin;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class BarGraph {

    final static String CSE = "Computer Science & Engineering";
    final static String EEE = "Electrical and Electronics Engineering";
    final static String CE = "Civil Engineering";
    final static String DS = "Data Science";
    final static String ME = "Mechanical Engineering";

    public BarChart<String, Number> createBarChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bChart = new BarChart<>(xAxis, yAxis);
        bChart.setTitle("Undergraduate students of School of Science and Engineering");
        xAxis.setLabel("Department");
        yAxis.setLabel("Student Count");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Summer");
        series1.getData().add(new XYChart.Data<>(CSE, 17));
        series1.getData().add(new XYChart.Data<>(EEE, 12));
        series1.getData().add(new XYChart.Data<>(CE, 6));
        series1.getData().add(new XYChart.Data<>(DS, 10));
        series1.getData().add(new XYChart.Data<>(ME, 6));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Fall");
        series2.getData().add(new XYChart.Data<>(CSE, 16));
        series2.getData().add(new XYChart.Data<>(EEE, 11));
        series2.getData().add(new XYChart.Data<>(CE, 7));
        series2.getData().add(new XYChart.Data<>(DS, 11));
        series2.getData().add(new XYChart.Data<>(ME, 6));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("Spring");
        series3.getData().add(new XYChart.Data<>(CSE, 17));
        series3.getData().add(new XYChart.Data<>(EEE, 13));
        series3.getData().add(new XYChart.Data<>(CE, 6));
        series3.getData().add(new XYChart.Data<>(DS, 8));
        series3.getData().add(new XYChart.Data<>(ME, 9));

        bChart.getData().addAll(series1, series2, series3);
        return bChart;
    }
}
