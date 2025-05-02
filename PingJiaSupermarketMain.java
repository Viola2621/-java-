import javax.swing.*;
import java.awt.*;

public class PingJiaSupermarketMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createMainFrame();
            }
        });
    }

    private static void createMainFrame() {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("软工222班 黄宏洋 2218140214");

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(176, 224, 230)); // 浅蓝色

        JLabel titleLabel = new JLabel("平价超市", SwingConstants.CENTER);
        titleLabel.setFont(new Font("楷体", Font.BOLD, 72)); // 更大字体
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel welcomeLabel = new JLabel("欢迎光临", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("楷体", Font.PLAIN, 48)); // 更大字体
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);

        JButton enterButton = new JButton("进入");
        enterButton.setFont(new Font("楷体", Font.PLAIN, 48)); // 设置与"欢迎光临"标签相同的字体大小
        enterButton.addActionListener(e -> createItemPage());
        mainPanel.add(enterButton, BorderLayout.SOUTH);

        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);
    }

    private static void createItemPage() {
        JFrame itemFrame = new JFrame();
        itemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        itemFrame.setTitle("软工222班 黄宏洋 2218140214");

        JPanel itemPanel = new JPanel(new GridLayout(3, 4));
        itemPanel.setBackground(Color.YELLOW);

        // Item labels, price labels, and details buttons creation code goes here

        itemFrame.getContentPane().add(BorderLayout.CENTER, itemPanel);
        itemFrame.pack();
        itemFrame.setLocationRelativeTo(null); // Center the frame on the screen
        itemFrame.setVisible(true);
    }
}

