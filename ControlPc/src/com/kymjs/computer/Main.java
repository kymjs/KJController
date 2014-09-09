package com.kymjs.computer;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 电脑端代码
 */
public class Main extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int port = 8899;

    private JTextField messagebox;
    private int menux = 0; // menux信号标志 0表示未开启 1表示开启 2表示暂停
    private boolean btnIsStart = false;
    private ServerThread serverthread; // 阻塞态线程

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super();
        initLayout();
        initIpAddress();
        initButton();
        initExplain();
        this.setVisible(true);
    }

    /**
     * 初始化窗口布局
     */
    private void initLayout() {
        setTitle("kymjs手机远程控制");
        setSize(230, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = getToolkit().getScreenSize(); // 获得Dimension对象
        int screenHeight = dimension.height; // 获得屏幕的宽高度
        int screenWidth = dimension.width;
        int frm_Height = this.getHeight(); // 获取窗体宽高
        int frm_width = this.getWidth();
        setLocation((screenWidth - frm_width) / 2,
                (screenHeight - frm_Height) / 2); // 居中显示窗口
        getContentPane().setLayout(null);
    }

    /**
     * 初始化IP信息
     */
    private void initIpAddress() {
        String localIp = null;
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
        }
        final JLabel label = new JLabel();
        label.setFont(new Font("SimSun", Font.PLAIN, 16));
        if (localIp != null && localIp.startsWith("192.168.1.")) {
            label.setText("本机IP为：" + localIp);
            label.setBounds(10, 20, 300, 25);
        } else {
            label.setText("获取IP失败，请手动检测");
            label.setBounds(30, 20, 300, 25);
        }
        getContentPane().add(label);
    }

    /**
     * 开/关按钮
     */
    private void initButton() {
        final JButton mBtn = new JButton("开启");
        mBtn.setBounds(10, 60, 190, 25);
        getContentPane().add(mBtn);
        mBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (!btnIsStart) {
                    start();
                    mBtn.setText("关闭");
                } else {
                    stop();
                    mBtn.setText("开启");
                }
            }
        });
    }

    /**
     * 初始化说明Text
     */
    private void initExplain() {
        final JLabel label3 = new JLabel();
        label3.setText("请在手机端输入本机IP");
        label3.setBounds(40, 100, 280, 25);
        getContentPane().add(label3);

        messagebox = new JTextField();
        messagebox.setBounds(10, 130, 190, 25);
        messagebox.setEnabled(false);
        getContentPane().add(messagebox);
    }

    public void start() {
        btnIsStart = true;
        if (menux == 0) {
            serverthread = new ServerThread();
            serverthread.start();
            menux = 1;
            messagebox.setText("控制服务已开启");
        }
        if (menux == 2) {
            serverthread.resume();
            menux = 1;
            messagebox.setText("控制服务已恢复");
        }
    }

    public void stop() {
        btnIsStart = false;
        if (menux == 1) {
            serverthread.suspend();
            menux = 2;
            messagebox.setText("控制服务已暂停");
        }
    }

    public class ServerThread extends Thread {
        public void run() {
            connection();
        }

        private void connection() {
            DatagramSocket socket;
            try {
                socket = new DatagramSocket(port);
                messagebox.setText("控制服务启动成功");
            } catch (Exception e) {
                messagebox.setText("8899端口被占用");
                menux = 0;
                return;
            }
            byte data[] = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data,
                    data.length);
            try {
                while (true) {
                    socket.receive(packet);
                    String message = new String(packet.getData(),
                            packet.getOffset(), packet.getLength());
                    messagebox.setText(message);
                    String[] messages = message.split(":");
                    if (messages.length >= 2) {
                        String type = messages[0];
                        String info = messages[1];
                        switch (type) {
                        case "mouse":
                            mouseMove(info);
                            break;
                        case "leftButton":
                            leftButton(info);
                            break;
                        case "rightButton":
                            rightButton(info);
                            break;
                        case "mousewheel":
                            mouseWheel(info);
                            break;
                        case "keyboard":
                            keyBoard(info);
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket.close();
        }

        /**
         * 处理鼠标移动
         */
        public void mouseMove(String info) {
            String args[] = info.split(",");
            String x = args[0];
            String y = args[1];
            float px = Float.valueOf(x);
            float py = Float.valueOf(y);

            PointerInfo pinfo = MouseInfo.getPointerInfo(); // 得到鼠标的坐标
            java.awt.Point p = pinfo.getLocation();
            double mx = p.getX(); // 得到当前电脑鼠标的坐标
            double my = p.getY();
            try {
                java.awt.Robot robot = new Robot();
                robot.mouseMove((int) (mx + px), (int) (my + py));
            } catch (AWTException e) {
            }
        }

        /**
         * 左键事件处理
         */
        public void leftButton(String info) {
            java.awt.Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                return;
            }
            if (info.equals("down")) {
                robot.mousePress(InputEvent.BUTTON1_MASK);
            } else if (info.equals("release")) {
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            } else if (info.equals("up")) {
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            } else if (info.equals("click")) {
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
        }

        /**
         * 右键事件处理
         */
        public void rightButton(String info) {
            java.awt.Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                return;
            }
            if (info.equals("down")) {
                robot.mousePress(InputEvent.BUTTON3_MASK);
            } else if (info.equals("release")) {
                robot.mouseRelease(InputEvent.BUTTON3_MASK);
            } else if (info.equals("up")) {
                robot.mouseRelease(InputEvent.BUTTON3_MASK);
            } else if (info.equals("click")) {
                robot.mousePress(InputEvent.BUTTON3_MASK);
                robot.mouseRelease(InputEvent.BUTTON3_MASK);
            }
        }

        /**
         * 鼠标滚轮事件处理
         */
        public void mouseWheel(String info) {
            java.awt.Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                return;
            }
            if (info.startsWith("-")) {
                robot.mouseWheel(-1);
            } else {
                robot.mouseWheel(1);
            }
        }

        /**
         * 按键事件处理
         */
        public void keyBoard(String info) {
            if ("Up".equals(info)) {
                keyBoard(KeyEvent.VK_UP);
            } else if ("Down".equals(info)) {
                keyBoard(KeyEvent.VK_DOWN);
            } else if ("Left".equals(info)) {
                keyBoard(KeyEvent.VK_LEFT);
            } else if ("Right".equals(info)) {
                keyBoard(KeyEvent.VK_RIGHT);
            } else if ("Ctrl+Z".equals(info)) {
                keyBoard(KeyEvent.VK_CONTROL, KeyEvent.VK_Z);
            } else if ("Ctrl+C".equals(info)) {
                keyBoard(KeyEvent.VK_CONTROL, KeyEvent.VK_C);
            } else if ("Ctrl+V".equals(info)) {
                keyBoard(KeyEvent.VK_CONTROL, KeyEvent.VK_V);
            } else if ("Ctrl+S".equals(info)) {
                keyBoard(KeyEvent.VK_CONTROL, KeyEvent.VK_S);
            }
        }

        /**
         * 单击
         */
        private void keyBoard(int key) {
            java.awt.Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                return;
            }
            robot.keyPress(key);
            robot.keyRelease(key);
        }

        /**
         * 组合键
         */
        private void keyBoard(int keyPress, int key) {
            java.awt.Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                return;
            }
            robot.keyPress(keyPress);
            robot.keyPress(key);
            robot.keyRelease(key);
            robot.keyRelease(keyPress);
        }
    }
}
