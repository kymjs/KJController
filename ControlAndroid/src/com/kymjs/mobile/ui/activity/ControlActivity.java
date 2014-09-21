package com.kymjs.mobile.ui.activity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadPoolExecutor;

import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.core.KJThreadExecutors;
import org.kymjs.aframe.ui.ViewInject;
import org.kymjs.aframe.ui.activity.BaseActivity;
import org.kymjs.aframe.utils.StringUtils;

import android.view.KeyEvent;

import com.kymjs.mobile.AppContext;

public abstract class ControlActivity extends BaseActivity {
    protected static final String LeftKeyDown = "leftButton:down";
    protected static final String LeftKeyUp = "leftButton:release";
    protected static final String RightKeyDown = "rightButton:down";
    protected static final String RightKeyUp = "rightButton:release";
    protected static final String MouseWheel = "mousewheel:";
    protected static final String MouseMove = "mouse:";
    protected static final String KeyBoard = "keyboard:";

    protected static final String Car_Release = "e";
    protected static final String Car_Up = "a";
    protected static final String Car_Down = "b";
    protected static final String Car_Left = "d";
    protected static final String Car_Right = "c";
    protected static final String Car_W = "e";
    protected static final String Car_S = "e";
    protected static final String Car_A = "e";
    protected static final String Car_D = "e";
    protected static final String Car_More = "e";
    protected static final String Car_LightOn = "e";
    protected static final String Car_LightOff = "e";
    protected static final String Car_Spaker = "e";

    protected AppContext application;
    private DatagramSocket socket;
    private ThreadPoolExecutor threadPool;

    private Socket clientSocket = null;

    @Override
    protected void initData() {
        super.initData();
        threadPool = (ThreadPoolExecutor) KJThreadExecutors
                .newCachedThreadPool();
        application = (AppContext) getApplication();
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            ViewInject.toast("网络连接失败");
            skipActivity(aty, Main.class);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        handleKeyBoardEvent(keyCode, event);
        return true;
    }

    /**
     * 处理按键事件
     */
    protected abstract void handleKeyBoardEvent(int keyCode, KeyEvent event);

    /**
     * net request
     */
    protected void sendPcMessage(String datas) {
        if (!StringUtils.isEmpty(datas)) {
            KJLoger.debug("准备发送信号：" + datas);
            threadPool.submit(new PcCommandThread(datas.getBytes()));
        }
    }

    /**
     * net request
     */
    protected void sendCarMessage(String datas) {
        if (!StringUtils.isEmpty(datas)) {
            KJLoger.debug("准备发送小车信号：" + datas);
            StringBuffer request = new StringBuffer();
            request.append("GET /?action=" + datas + " HTTP/1.0\r\n");
            request.append("Host: 192.168.8.1\r\n\r\n");
            threadPool.submit(new CarCommandThread(request.toString()
                    .getBytes()));
        }
    }

    /**
     * 向小车发送命令的线程
     */
    private class CarCommandThread implements Runnable {
        private byte[] datas;

        public CarCommandThread(byte[] datas) {
            this.datas = datas;
        }

        @Override
        public void run() {
            try {
                if (clientSocket == null) {
                    clientSocket = new Socket("192.168.8.1", 2001);
                }
                clientSocket.getOutputStream().write(datas, 0, datas.length);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 向电脑发送命令的线程
     */
    private class PcCommandThread implements Runnable {
        private byte[] datas;

        public PcCommandThread(byte[] datas) {
            this.datas = datas;
        }

        @Override
        public void run() {
            try {
                InetAddress pcAddress = InetAddress.getByName(application.ip);
                DatagramPacket packet = new DatagramPacket(datas, datas.length,
                        pcAddress, application.port);
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
