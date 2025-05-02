import java.sql.*;

public class DatabaseUtils {
    private Connection connection;

    public DatabaseUtils() {
        connectToDatabase();
        loadItemData();
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/mybase?user=root&password=123";
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadItemData() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM objects");

            int index = 0;
            while (resultSet.next() && index < 12) {
                String itemName = resultSet.getString("物品");
                int price = resultSet.getInt("价格");

                System.out.println("物品：" + itemName + "，价格：" + price);
                index++;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DatabaseUtils();
    }
}
