package ca.georgiancollege.comp1011m2022ice5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;

public class Vector2DChartViewController implements Initializable
{

    @FXML
    private BarChart<String, Float> barChart;

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    private void LoadTableView(ActionEvent event) throws IOException
    {
        SceneManager.Instance().changeScene(event, "vector2d-tableview.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        barChart.getData().addAll(DBManager.Instance().getMagnitude());
        numberAxis.setLabel("Magnitude");
        categoryAxis.setLabel("Vector2D");
        barChart.setLegendVisible(false);
    }
}
