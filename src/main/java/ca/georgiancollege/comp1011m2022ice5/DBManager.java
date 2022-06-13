package ca.georgiancollege.comp1011m2022ice5;

import javafx.scene.chart.XYChart;
import java.sql.*;
import java.util.ArrayList;

/* Singleton  Class*/
public class DBManager
{

    //private static instance member variable
    private static DBManager m_instance = null;

    // make default constructor private
    private DBManager() {}
    //create a public static entry point
    public static DBManager Instance()
    {
        //Check if private instance member variable is null or not
        if(m_instance == null)
        {
            //Instantiate a new DBManager instance
            m_instance = new DBManager();
        }
        return m_instance;
    }

    // private instance member variables, MySQL
    private String m_user = "root";
    private String m_password = "Mairo.or21";
    private String m_connectURL = "jdbc:mysql://localhost:3306/comp1011m2022";

    /**
     * Method inserts a Vector2D object to our MySQL Database
     *
     * @param vector2D
     * @return
     * @throws SQLException
     */
    public int insertVector2D(Vector2D vector2D) throws SQLException {
        int vectorID = -1; // if this method returns -1 something went wrong

        // initializing the resultSet object
        ResultSet resultSet = null;

        String sql = "INSERT INTO vectors(X, Y, Magnitude) VALUES(?, ?, ?);";

        try
        (
            Connection connection = DriverManager.getConnection(m_connectURL, m_user, m_password);
            PreparedStatement statement = connection.prepareStatement(sql, new String[] {"vectorID"});
        )
        {
            // configure prepared statement
            statement.setFloat(1, vector2D.getX());
            statement.setFloat(2, vector2D.getY());
            statement.setFloat(3, vector2D.getMagnitude());

            // running on our MySQL Database
            statement.executeUpdate();

            // getting VectorID
            resultSet = statement.getGeneratedKeys();
            while(resultSet.next())
            {
                // getting VectorID from Database
                vectorID = resultSet.getInt(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(resultSet != null)
            {
                resultSet.close();
            }
        }

        return vectorID;
    }


     /** Method reads vectors table from the MySQL database
     * and returns an ArrayList of Vector2D type
     * @return
     */
    public ArrayList<Vector2D> readVectorTable()
    {
        // Instantiates an ArrayList collection
        ArrayList<Vector2D> vectors = new ArrayList<Vector2D>();

        String sql = "SELECT vectors.vectorID, X, Y FROM vectors GROUP BY vectors.vectorID";

        try
        (
        Connection connection = DriverManager.getConnection(m_connectURL, m_user, m_password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        )
        {
            // looping through resultset
            while(resultSet.next())
            {
                // deserialize data from database table
                int vectorID = resultSet.getInt("vectorID");
                float X = resultSet.getFloat("X");
                float Y = resultSet.getFloat("Y");

                vectors.add( new Vector2D(vectorID, X, Y));
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return vectors;
    }

    /**
     * Method returns Bar Chart Data from the Database
     * @return
     */
    public XYChart.Series<String, Float> getMagnitude()
    {
        XYChart.Series<String, Float> magnitudes = new XYChart.Series<>();
        magnitudes.setName("2022");

        //Get Data from the Database
        ArrayList<Vector2D> vectors = readVectorTable();

        for (var vector : vectors)
        {
            var chartData = new XYChart.Data<>(vector.toOneDecimalString(), vector.getMagnitude());
            magnitudes.getData().add(chartData);
        }

        return magnitudes;
    }
}
