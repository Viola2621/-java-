import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PingJiaSupermarket {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel[] itemLabels;
    private JLabel[] priceLabels;
    private JButton[] detailsButtons;
    private JPanel cartPanel;
    private JLabel cartLabel;
    private JLabel totalLabel;
    private JButton checkoutButton;

    private Connection connection;

private void showItemPage() {
    mainFrame.getContentPane().removeAll();
    mainFrame.getContentPane().setLayout(new BorderLayout());

    mainPanel = new JPanel(new GridLayout(3, 4));
    mainPanel.setBackground(Color.YELLOW);

    itemLabels = new JLabel[12];
    priceLabels = new JLabel[12];
    detailsButtons = new JButton[12];

    for (int i = 0; i < 12; i++) {
        itemLabels[i] = new JLabel("", SwingConstants.CENTER);
        itemLabels[i].setFont(new Font("宋体", Font.BOLD, 24));
        mainPanel.add(itemLabels[i]);

        priceLabels[i] = new JLabel("", SwingConstants.CENTER);
        priceLabels[i].setFont(new Font("宋体", Font.PLAIN, 24));
        mainPanel.add(priceLabels[i]);

        detailsButtons[i] = new JButton("详情");
        detailsButtons[i].setFont(new Font("宋体", Font.BOLD, 24));
        int index = i; // Need to create a separate final variable for ActionListener
        detailsButtons[i].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               itemPage.showItemDetails(index);
            }
        });
        mainPanel.add(detailsButtons[i]);
    }

    cartPanel = new JPanel();
    cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
    cartPanel.setPreferredSize(new Dimension(200, 0));
    cartPanel.setBackground(Color.YELLOW);

    cartLabel = new JLabel("已选商品：");
    cartLabel.setFont(new Font("宋体", Font.BOLD, 24));
    cartPanel.add(cartLabel);

    totalLabel = new JLabel("总价：0");
    totalLabel.setFont(new Font("宋体", Font.BOLD, 24));
    cartPanel.add(totalLabel);

    checkoutButton = new JButton("结算");
    checkoutButton.setFont(new Font("宋体", Font.BOLD, 24));
    checkoutButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showCheckoutDialog();
        }
    });
    cartPanel.add(checkoutButton);

    mainFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
    mainFrame.getContentPane().add(BorderLayout.EAST, cartPanel);

    mainFrame.revalidate();
    mainFrame.repaint();
}

    public void initializeGUI() {
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("软工222班 黄宏洋 2218140214");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(176, 224, 230)); // 浅蓝色

        titleLabel = new JLabel("平价超市", SwingConstants.CENTER);
        titleLabel.setFont(new Font("楷体", Font.BOLD, 72)); // 更大字体
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel welcomeLabel = new JLabel("欢迎光临", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("楷体", Font.PLAIN, 48)); // 更大字体
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);

        JButton enterButton = new JButton("进入");
        enterButton.setFont(new Font("楷体", Font.PLAIN, 48)); // 设置与"欢迎光临"标签相同的字体大小
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showItemPage();
            }
        });
        mainPanel.add(enterButton, BorderLayout.SOUTH);

        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
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
private ItemPage itemPage;

public PingJiaSupermarket() {
    initializeGUI();
    connectToDatabase();
    itemPage = new ItemPage(mainFrame, connection);
}

// 调用showCheckoutDialog()方法
private void showCheckoutDialog() {
    itemPage.showCheckoutDialog();
}

}
