import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PingJiaSupermarketItemPage {
    private static JLabel[] itemLabels;
    private static JLabel[] priceLabels;
    private static JButton[] detailsButtons;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createItemPage();
            }
        });
    }

    private static void createItemPage() {
        JFrame itemFrame = new JFrame();
        itemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        itemFrame.setTitle("»Ìπ§222∞‡ ª∆∫Í—Û 2218140214");

        JPanel itemPanel = new JPanel(new GridLayout(3, 4));
        itemPanel.setBackground(Color.YELLOW);

        itemLabels = new JLabel[12];
        priceLabels = new JLabel[12];
        detailsButtons = new JButton[12];

        for (int i = 0; i < 12; i++) {
            itemLabels[i] = new JLabel("", SwingConstants.CENTER);
            itemLabels[i].setFont(new Font("ÀŒÃÂ", Font.BOLD, 24));
            itemPanel.add(itemLabels[i]);

            priceLabels[i] = new JLabel("", SwingConstants.CENTER);
            priceLabels[i].setFont(new Font("ÀŒÃÂ", Font.PLAIN, 24));
            itemPanel.add(priceLabels[i]);

            detailsButtons[i] = new JButton("œÍ«È");
            detailsButtons[i].setFont(new Font("ÀŒÃÂ", Font.BOLD, 24));
            int index = i; // Need to create a separate final variable for ActionListener
            detailsButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showItemDetails(index);
                }
            });
            itemPanel.add(detailsButtons[i]);
        }

        itemFrame.getContentPane().add(BorderLayout.CENTER, itemPanel);
        itemFrame.pack();
        itemFrame.setLocationRelativeTo(null); // Center the frame on the screen
        itemFrame.setVisible(true);
    }

    private static void showItemDetails(int index) {
        // Code for displaying item details goes here
    }
}
