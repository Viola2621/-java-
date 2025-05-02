import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoadItemData {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JLabel[] itemLabels;
    private JLabel[] priceLabels;

    private Connection connection;

    public LoadItemData(JFrame frame, Connection conn) {
        mainFrame = frame;
        connection = conn;
    }

    private void loadItemData() {
        showItemPage(); // 初始化itemLabels数组
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM objects");

            int index = 0;
            while (resultSet.next() && index < 12) {
                String itemName = resultSet.getString("物品");
                int price = resultSet.getInt("价格");

                itemLabels[index].setText(itemName);
                priceLabels[index].setText("价格：" + price);

                index++;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showItemPage() {
    mainFrame.getContentPane().removeAll();
    mainFrame.getContentPane().setLayout(new BorderLayout());

    mainPanel = new JPanel(new GridLayout(3, 4));
    mainPanel.setBackground(Color.YELLOW);

    itemLabels = new JLabel[12];
    priceLabels = new JLabel[12];

    for (int i = 0; i < 12; i++) {
        itemLabels[i] = new JLabel("", SwingConstants.CENTER);
        itemLabels[i].setFont(new Font("宋体", Font.BOLD, 24));
        mainPanel.add(itemLabels[i]);

        priceLabels[i] = new JLabel("", SwingConstants.CENTER);
        priceLabels[i].setFont(new Font("宋体", Font.PLAIN, 24));
        mainPanel.add(priceLabels[i]);
    }

    mainFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);

    mainFrame.revalidate();
    mainFrame.repaint();
}

}
