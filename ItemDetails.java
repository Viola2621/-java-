import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ItemDetails {
    private JFrame mainFrame;
    private Connection connection;
    private JPanel cartPanel;
    private JLabel cartLabel;
    private JLabel totalLabel;
    private JButton checkoutButton;
    private JLabel[] itemLabels;

    public ItemDetails(JFrame frame, Connection conn,JLabel[] labels) {
        mainFrame = frame;
        connection = conn;
        itemLabels = labels;
        initializeCartPanel();
    }

    private void initializeCartPanel() {
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

        mainFrame.getContentPane().add(BorderLayout.EAST, cartPanel);
    }

    public void showCheckoutDialog() {
        JOptionPane.showMessageDialog(mainFrame,
                "购买成功，平价超市欢迎您下次光临！",
                "结算",
                JOptionPane.INFORMATION_MESSAGE);

        resetCart();
    }

    private void resetCart() {
        cartPanel.removeAll();

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

        mainFrame.revalidate();
        mainFrame.repaint();
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

    // 调用selectItem()方法
   private void selectItem(int index) {
    String itemName = itemLabels[index].getText();

    // 更新已选商品的显示
    JLabel selectedItemLabel = new JLabel(itemName);
    selectedItemLabel.setFont(new Font("宋体", Font.BOLD, 24));
    cartPanel.add(selectedItemLabel);
    mainFrame.revalidate();
    mainFrame.repaint();

    // 计算总价并更新显示
    int price = getPrice(itemName);
    int total = getTotalPrice() + price;
    totalLabel.setText("总价：" + total);

    // 在这里可以执行其他选择物品后的逻辑操作
}

private int getPrice(String itemName) {
    // 查询数据库获取物品的价格并返回
    String query = "SELECT 价格 FROM objects WHERE 物品 = ?";
    int price = 0;
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
    String totalText = totalLabel.getText();
    String priceText = totalText.substring(4); // 去掉 "总价：" 前缀
    return Integer.parseInt(priceText);
}

}

