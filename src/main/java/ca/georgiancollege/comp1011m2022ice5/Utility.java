package ca.georgiancollege.comp1011m2022ice5;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;


public final class Utility
{
    // Create private static instance member variable
    private static Utility m_instance = null;

    // Make default constructor private
    private Utility()  {}

    // Create public static access method that returns an instance of the class
    public static Utility Instance()
    {
        if(m_instance == null)
        {
            m_instance = new Utility();
        }
        // return an instance  of the class
        return m_instance;
    }

    /*
     * method returns the distance from start to end
     *
     * @param start - start vector2D
     * @param end - ending vector2D
     * */
    public float Distance(Vector2D start, Vector2D end)
    {
        float diffXs = end.getX() - start.getX();
        float diffYs = end.getY() - start.getY();
        return (float) Math.sqrt(diffXs * diffXs + diffYs * diffYs);
    }


    public float Distance(float x1, float y1, float x2, float y2)
    {
        float diffXs = x2 - x1;
        float diffYs = y2 - y1;
        return (float) Math.sqrt(diffXs * diffXs + diffYs * diffYs);
    }

    /**
     * helper method
     * @param spinner
     * @param min
     * @param max
     * @param default_value
     * @param increment_value
     */
    public void ConfigureVector2DSpinner(Spinner<Double> spinner, double min, double max, double default_value, double increment_value)
    {
        SpinnerValueFactory<Double> spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, default_value, increment_value);
        spinner.setValueFactory(spinnerValueFactory);
        TextField spinnerTextField = spinner.getEditor();
        spinnerTextField.textProperty().addListener( (observable, oldValue, newValue) ->
        {
            try
            {
                Float.parseFloat(newValue);
            }
            catch (Exception e)
            {
                spinnerTextField.setText(oldValue);
            }
        });
    }
}