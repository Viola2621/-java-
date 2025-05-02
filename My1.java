import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class My {
    public static void main(String args[]) {
        ZWindow win = new ZWindow();
    }
}

class ZWindow extends JFrame {
    TWindow p;

    public ZWindow() {
        setBounds(100, 100, 800, 400);
        setVisible(true);
        p = new TWindow();
        add(p, BorderLayout.CENTER);
        validate();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class TWindow extends JPanel {
    JTabbedPane p;
    ByName byName;
    ByBirth byBirth;

    public TWindow() {
        setLayout(new BorderLayout());
        p = new JTabbedPane();
        byName = new ByName();
        byBirth = new ByBirth();
        p.add("按姓名查询", byName);
        p.add("按出生查询", byBirth);
        add(p, BorderLayout.CENTER);
    }
}

class Query { // Query查询通用类
    String databaseName = ""; // 数据库名
    String SQL; // SQL语句
    String[] columnName; // 全部字段（列）名
    String[][] record; // 查询到的记录

    public Query() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载JDBC-MySQL驱动
        } catch (Exception e) {
        }
    }

    public void setDatabaseName(String s) {
        databaseName = s.trim();
    }

    public void setSQL(String SQL) {
        this.SQL = SQL.trim(); // 传要执行的sql语句
    }

    public String[] getColumnName() {
        if (columnName == null) {
            System.out.println("先查询记录");
            return null;
        }
        return columnName;
    }

    public String[][] getRecord() {
        startQuery();
        return record;
    }

    void startQuery() {
        Connection con;
        Statement sql;
        ResultSet rs;
        String uri = "jdbc:mysql://localhost:3306/" + databaseName
                + "?useSSL=true&serverTimezone=GMT&characterEncoding=utf-8";// 连接数据库
        try {
            con = DriverManager.getConnection(uri, "root", "123");// 在navicat里设置的用户名账号密码
            sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = sql.executeQuery(SQL);// 执行查询，一次性从数据库中导出
            ResultSetMetaData metaData = rs.getMetaData();// 一次性把查询结果全部读取到结果集里
            int columnCount = metaData.getColumnCount();// 字段数目
            columnName = new String[columnCount];// 查询到了几条几列
            for (int i = 1; i <= columnCount; i++) {
                columnName[i - 1] = metaData.getColumnName(i);
            }
            rs.last();// ***跳转到后面***************
            int recordAmount = rs.getRow(); // 结果集中的记录数目
            record = new String[recordAmount][columnCount];// 创建一个二维数组
            int i = 0;
            rs.beforeFirst();// 跳转到前面
            while (rs.next()) {
                for (int j = 1; j <= columnCount; j++) {
                    record[i][j - 1.
] = rs.getString(j);
                }
                i++;
            }
            rs.close();
            sql.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class ByName extends JPanel implements ActionListener {
    JLabel name;
    JTextField nameField;
    JButton queryButton;
    JTable table;

    public ByName() {
        setLayout(new BorderLayout());
        JPanel p1 = new JPanel();
        name = new JLabel("姓名:");
        nameField = new JTextField(10);
        queryButton = new JButton("查询");
        queryButton.addActionListener(this);
        p1.add(name);
        p1.add(nameField);
        p1.add(queryButton);
        add(p1, BorderLayout.NORTH);
    }

    public void actionPerformed(ActionEvent e) {
        String inputName = nameField.getText();
        String sql = "SELECT * FROM student WHERE name='" + inputName + "'";
        Query query = new Query();
        query.setDatabaseName("数据库名");
        query.setSQL(sql);
        String[] columnName = query.getColumnName();
        String[][] record = query.getRecord();
        table = new JTable(record, columnName);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);
        validate();
    }
}

class ByBirth extends JPanel implements ActionListener {
    JLabel startLabel;
    JTextField startField;
    JLabel endLabel;
    JTextField endField;
    JButton queryButton;
    JTable table;

    public ByBirth() {
        setLayout(new BorderLayout());
        JPanel p1 = new JPanel();
        startLabel = new JLabel("开始日期:");
        startField = new JTextField(10);
        endLabel = new JLabel("结束日期:");
        endField = new JTextField(10);
        queryButton = new JButton("查询");
        queryButton.addActionListener(this);
        p1.add(startLabel);
        p1.add(startField);
        p1.add(endLabel);
        p1.add(endField);
        p1.add(queryButton);
        add(p1, BorderLayout.NORTH);
    }

    public void actionPerformed(ActionEvent e) {
        String startDate = startField.getText();
        String endDate = endField.getText();
        String sql = "SELECT * FROM student WHERE birth >= '" + startDate + "' AND birth <= '" + endDate + "'";
        Query query = new Query();
        query.setDatabaseName("数据库名");
        query.setSQL(sql);
        String[] columnName = query.getColumnName();
        String[][] record = query.getRecord();
        table = new JTable(record, columnName);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);
        validate();
    }
}
