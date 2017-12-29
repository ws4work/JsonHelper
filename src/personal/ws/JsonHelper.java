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
        jFrame = new JFrame("Json��װ����");
        //����JFrame
        frameConfig(jFrame);
        //��������ͼ��
        try {
            trayConfig(jFrame);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Container contentPane = jFrame.getContentPane();
        contentPane.setSize(500, 300);
        contentPane.setVisible(true);
        contentPane.setLayout(new GridLayout(rowNum + 1, 1));
        //������
        JPanel upPanel = new JPanel();
        upPanel.setVisible(true);
        upPanel.setLayout(new GridLayout(1, 2));
        //���������
        JPanel paramPanel = new JPanel();
        constructParam(paramPanel);
        upPanel.add(paramPanel);

        //�����
        JPanel downPanel = new JPanel();
        downPanel.setVisible(true);
        JTextArea resultArea = new JTextArea();
        resultArea.setVisible(true);
        downPanel.add(resultArea);


        //����
        JButton changeButton = new JButton("����");
        changeButton.setSize(50, 30);
        changeOper(paramPanel, resultArea, changeButton);
        upPanel.add(changeButton);
//        JButton addButton = new JButton("���");
//        addButton.setSize(50,30);
//        addButton.addActionListener(e->{
//            rowNum++;
//            contentPane.setLayout();
//        });
//        upPanel.add(addButton);
        //�������
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
        // ���ϵͳ����
        SystemTray tray = SystemTray.getSystemTray();
        // ͼ��
        Image image = Toolkit.getDefaultToolkit().getImage(
                "src/personal/ws/img/cursor-wight-up.png");
        // ��������ʽ�˵�
        PopupMenu popupMenu = new PopupMenu();
        // �����˵���
        MenuItem openItem = new MenuItem("��");
        openItem.addActionListener(e -> {
            jFrame.setVisible(true);
        });
        MenuItem hideItem = new MenuItem("����");
        hideItem.addActionListener(e -> {
            jFrame.setVisible(false);
        });
        MenuItem exitItem = new MenuItem("�ر�");
        exitItem.addActionListener(e -> System.exit(0));

        popupMenu.add(openItem);
        popupMenu.add(hideItem);
        popupMenu.add(exitItem);
        // ��������ͼ��
        TrayIcon trayIcon = new TrayIcon(image, "Json��װ����", popupMenu);
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
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize(); // �����ʾ����С����
        Dimension frameSize = jFrame.getSize();             // ��ô��ڴ�С����
        if (frameSize.width > displaySize.width)
            frameSize.width = displaySize.width;           // ���ڵĿ�Ȳ��ܴ�����ʾ���Ŀ��
        if (frameSize.height > displaySize.height)
            frameSize.height = displaySize.height;          // ���ڵĸ߶Ȳ��ܴ�����ʾ���ĸ߶�
        jFrame.setLocation((displaySize.width - frameSize.width) / 2,
                (displaySize.height - frameSize.height) / 2); // ���ô��ھ�����ʾ����ʾ
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رմ����˳�����
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
