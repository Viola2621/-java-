import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckoutDialog {
    private JFrame mainFrame;

    public CheckoutDialog(JFrame frame) {
        mainFrame = frame;
    }

    public void selectItem(int index) {
        // 在这里执行选择物品后的逻辑操作
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
}

