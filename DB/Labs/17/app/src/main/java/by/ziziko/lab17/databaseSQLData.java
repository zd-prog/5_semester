package by.ziziko.lab17;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseSQLData extends AsyncTask<String, String, String> {

    Connection connection;
    String LOG = "Сообщения тут!";

    @Override
    protected String doInBackground(String... strings) {
        String rc = "";
        connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Log.e(LOG, "Driver is registered");

            String url = "jdbc:jtds:sqlserver://80.94.168.145;databaseName=17db;user=student;password=Pa$$w0rd";

            connection = DriverManager.getConnection(url);
            Log.e(LOG, "You are connected");

        } catch (ClassNotFoundException e) {
            Log.e(LOG, e.getMessage());
        } catch (SQLException throwables) {
            Log.e(LOG, throwables.getMessage());
        }

        return null;
    }

    String Query()
    {
        String result = "";
        String query = "select top(5) * from Profession ";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                result += resultSet.getString(2).trim() + " " + resultSet.getString(3) + "\n";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return result;
    }

    String Procedure()
    {
        String result = "";
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall("dbo.LabProcedure");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next())
            {
                result += resultSet.getString(2) + " " + resultSet.getString(3) + "\n";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    String Function()
    {
        String result = "";
        Statement Statement = null;
        String query = "select dbo.LabFunction('ИТ') as num";
        try {
            Statement = connection.createStatement();
            ResultSet resultSet = Statement.executeQuery(query);
            while (resultSet.next())
            {
                result += "Количество оценок: " + resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    String Batch()
    {
        Statement Statement = null;
        String result = "Первая группа:\n";
        String query = "select name from student where IDGROUP = 1";
        try {
            Statement = connection.createStatement();
            ResultSet resultSet = Statement.executeQuery(query);
            while (resultSet.next())
            {
                result += resultSet.getString(1) + "\n";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        result += "\n";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("insert into Student(name, idgroup) values(?,?)");
            preparedStatement.setString(1, "Первый студент");
            preparedStatement.setInt(2,1);
            preparedStatement.addBatch();

            preparedStatement.setString(1, "Второй студент");
            preparedStatement.setInt(2,1);
            preparedStatement.addBatch();

            preparedStatement.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            Statement = connection.createStatement();
            ResultSet resultSet = Statement.executeQuery(query);
            while (resultSet.next())
            {
                result += resultSet.getString(1) + "\n";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    String Update()
    {
        String result = "";
        String query1 = "select name from student where IDGROUP = 1";

        String query = "update student set name = '1' where name = 'Первый студент'";
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            ResultSet resultSet = statement.executeQuery(query1);
            while (resultSet.next())
            {
                result += resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }

    String Delete()
    {
        String result = "";
        String query = "delete student where name = '1'";
        String query1 = "select name from student where IDGROUP = 1";
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            ResultSet resultSet = statement.executeQuery(query1);
            while (resultSet.next())
            {
                result += "Добавлен пользователь: " + resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }
}
