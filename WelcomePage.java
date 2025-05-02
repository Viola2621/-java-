  private void initializeGUI() {
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
private void showWelcomePage() {
    mainFrame.getContentPane().removeAll();
    mainFrame.getContentPane().setLayout(new BorderLayout());

    JPanel welcomePanel = new JPanel();
    welcomePanel.setBackground(new Color(176, 224, 230)); // 浅蓝色
    welcomePanel.setLayout(new GridLayout(5, 3));

    JLabel welcomeLabel = new JLabel("欢迎光临", SwingConstants.CENTER);
    welcomeLabel.setFont(new Font("楷体", Font.PLAIN, 72)); // 更大字体
    welcomePanel.add(welcomeLabel);

    mainFrame.getContentPane().add(BorderLayout.CENTER, welcomePanel);
    mainFrame.revalidate();
    mainFrame.repaint();

    showItemPage(); // 显示黄色窗口
}