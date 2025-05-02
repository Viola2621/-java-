import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemPage {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JLabel[] itemLabels;
    private JLabel[] priceLabels;
    private JButton[] detailsButtons;
    private JPanel cartPanel;
    private JLabel cartLabel;
    private JLabel totalLabel;
    private JButton checkoutButton;

    private Connection connection;
    private List<String> selectedItems; // 存储已选商品的列表

    public ItemPage(JFrame frame, Connection conn) {
        mainFrame = frame;
        connection = conn;
        selectedItems = new ArrayList<>(); // 初始化已选商品列表
             // itemLabels = new JLabel[12]; // 实例化itemLabels数组
        showItemPage();
    }

    public void showItemPage() {
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
                    showItemDetails(index);
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

    private void selectItem(int index) {
        String itemName = itemLabels[index].getText();

        // 更新已选商品的显示
        JLabel selectedItemLabel = new JLabel(itemName);
        selectedItemLabel.setFont(new Font("宋体", Font.BOLD, 24));
        cartPanel.add(selectedItemLabel);
        mainFrame.revalidate();
        mainFrame.repaint();

        // 添加已选商品到列表
        selectedItems.add(itemName);

        // 计算总价并更新显示
        int price = getPrice(itemName);
        int total = getTotalPrice() + price;
        totalLabel.setText("总价：" + total);

        // 在这里可以执行其他选择物品后的逻辑操作
    }


       // 在 ItemPage 类中添加重载的 showItemDetails 方法
    public void showItemDetails(int index) {
        String itemName = itemLabels[index].getText();
        showItemDetails(index, itemName);
         }

    public void showItemDetails(int index, String itemName) {
        String query = "SELECT * FROM objects WHERE 物品 = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, itemName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String productionDate = resultSet.getString("生产日期");
                String expirationDate = resultSet.getString("到期日期");
                int stock = resultSet.getInt("库存");

 // 在这里可以将获取的数据显示在界面上，如弹出对话框或更新其他组件
                String message = "物品名：" + itemName + "\n生产日期：" + productionDate + "\n到期日期：" + expirationDate + "\n库存：" + stock;
                JOptionPane.showMessageDialog(mainFrame, message, "详情", JOptionPane.INFORMATION_MESSAGE);

                JOptionPane.showMessageDialog(mainFrame,
                        "物品: " + itemName + "\n" +
                                "生产日期: " + productionDate + "\n" +
                                "到期日期: " + expirationDate + "\n" +
                                "库存: " + stock,
                        "物品详情",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

  private int getPrice(String itemName) {
    int price = 0;
    String query = "SELECT 价格 FROM objects WHERE 物品 = ?";
    try {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, itemName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            price = resultSet.getInt("价格");
        }
        resultSet.close();
        statement.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return price;
}

private int getTotalPrice() {
    int total = 0;
    for (String itemName : selectedItems) {
        total += getPrice(itemName);
    }
    return total;
}


    public void showCheckoutDialog() {
        JOptionPane.showMessageDialog(mainFrame,
                "购买成功，平价超市欢迎您下次光临！",
                "结算",
                JOptionPane.INFORMATION_MESSAGE);

        resetCart();
    }

    private void resetCart() {
        JPanel cartPanel = (JPanel) mainFrame.getContentPane().getComponent(1);

        cartPanel.removeAll();

        JLabel cartLabel = new JLabel("已选商品：");
        cartLabel.setFont(new Font("宋体", Font.BOLD, 24));
        cartPanel.add(cartLabel);

        JLabel totalLabel = new JLabel("总价：0");
        totalLabel.setFont(new Font("宋体", Font.BOLD, 24));
        cartPanel.add(totalLabel);

        JButton checkoutButton = new JButton("结算");
        checkoutButton.setFont(new Font("宋体", Font.BOLD, 24));
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCheckoutDialog();
            }
        });
        cartPanel.add(checkoutButton);

        mainFrame.revalidate();
        mainFrame.repaint();
    }
private int getIndex(JButton button) {
    for (int i = 0; i < detailsButtons.length; i++) {
        if (detailsButtons[i] == button) {
            return i;
        }
    }
    return -1;
}

}

