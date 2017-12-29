package personal.ws;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;

public class JsonHelper {

    public static int rowNum = 1;
    public static JFrame jFrame;
    public static JTable table;

    public static void main(String[] args) {
        jFrame = new JFrame("Json组装工具");
        //配置JFrame
        frameConfig(jFrame);
        //配置托盘图标
        try {
            trayConfig(jFrame);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Container contentPane = jFrame.getContentPane();
        contentPane.setSize(500, 300);
        contentPane.setVisible(true);
        contentPane.setLayout(new GridLayout(rowNum + 1, 1));
        //参数框
        JPanel upPanel = new JPanel();
        upPanel.setVisible(true);
        upPanel.setLayout(new GridLayout(1, 2));
        //输出参数框
        JPanel paramPanel = new JPanel();
        constructParam(paramPanel);
        upPanel.add(paramPanel);

        //结果框
        JPanel downPanel = new JPanel();
        downPanel.setVisible(true);
        JTextArea resultArea = new JTextArea();
        resultArea.setVisible(true);
        downPanel.add(resultArea);


        //操作
        JButton changeButton = new JButton("生成");
        changeButton.setSize(50, 30);
        changeOper(paramPanel, resultArea, changeButton);
        upPanel.add(changeButton);
//        JButton addButton = new JButton("添加");
//        addButton.setSize(50,30);
//        addButton.addActionListener(e->{
//            rowNum++;
//            contentPane.setLayout();
//        });
//        upPanel.add(addButton);
        //最终添加
        contentPane.add(upPanel);
        contentPane.add(downPanel);
        jFrame.setEnabled(true);
        jFrame.setVisible(true);
    }

    private static void changeOper(JPanel paramPanel, JTextArea resultArea, JButton changeButton) {
        changeButton.addActionListener(e -> {
            StringBuffer stringBuffer = new StringBuffer("{");
            int rowCount = table.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                Object key = table.getValueAt(i, 0);
                if (null == key || "".equals(String.valueOf(key).trim())) {
                    continue;
                }
                stringBuffer.append("\"");
                stringBuffer.append(key);
                stringBuffer.append("\"");
                stringBuffer.append(":");
                stringBuffer.append("\"");
                stringBuffer.append(table.getValueAt(i, 1));
                stringBuffer.append("\"");
                stringBuffer.append(",");
            }
            int length = stringBuffer.length();
            if (length > 1) {
                stringBuffer.deleteCharAt(length - 1);
            }
            stringBuffer.append("}");
            resultArea.setText(stringBuffer.toString());
        });
    }

    private static void trayConfig(JFrame jFrame) throws UnsupportedEncodingException {
        // 获得系统托盘
        SystemTray tray = SystemTray.getSystemTray();
        // 图标
        Image image = Toolkit.getDefaultToolkit().getImage(
                "src/personal/ws/img/cursor-wight-up.png");
        // 创建弹出式菜单
        PopupMenu popupMenu = new PopupMenu();
        // 创建菜单项
        MenuItem openItem = new MenuItem("打开");
        openItem.addActionListener(e -> {
            jFrame.setVisible(true);
        });
        MenuItem hideItem = new MenuItem("隐藏");
        hideItem.addActionListener(e -> {
            jFrame.setVisible(false);
        });
        MenuItem exitItem = new MenuItem("关闭");
        exitItem.addActionListener(e -> System.exit(0));

        popupMenu.add(openItem);
        popupMenu.add(hideItem);
        popupMenu.add(exitItem);
        // 创建托盘图标
        TrayIcon trayIcon = new TrayIcon(image, "Json组装工具", popupMenu);
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // setVisible(true);
                    jFrame.setVisible(true);
                }
            }
        });
        try {
            tray.add(trayIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void frameConfig(JFrame jFrame) {
        jFrame.setSize(500, 300);
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize(); // 获得显示器大小对象
        Dimension frameSize = jFrame.getSize();             // 获得窗口大小对象
        if (frameSize.width > displaySize.width)
            frameSize.width = displaySize.width;           // 窗口的宽度不能大于显示器的宽度
        if (frameSize.height > displaySize.height)
            frameSize.height = displaySize.height;          // 窗口的高度不能大于显示器的高度
        jFrame.setLocation((displaySize.width - frameSize.width) / 2,
                (displaySize.height - frameSize.height) / 2); // 设置窗口居中显示器显示
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗体退出程序
    }

    private static void constructParam(JPanel paramPanel) {
        paramPanel.setVisible(true);
        paramPanel.setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Key", "Value"});
        model.setColumnCount(2);
        model.setNumRows(50);
//        model.setValueAt("Key", 0, 0);
//        model.setValueAt("Value", 0, 1);
        table = new JTable(model);
        table.setRowHeight(20);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setVisible(true);
        paramPanel.add(table, BorderLayout.CENTER);
    }
}
