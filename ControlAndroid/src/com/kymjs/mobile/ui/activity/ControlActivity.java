package com.kymjs.mobile.ui.activity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ThreadPoolExecutor;

import org.kymjs.aframe.KJLoger;
import org.kymjs.aframe.core.KJThreadExecutors;
import org.kymjs.aframe.ui.ViewInject;
import org.kymjs.aframe.ui.activity.BaseActivity;
import org.kymjs.aframe.utils.StringUtils;

import android.view.KeyEvent;

import com.kymjs.mobile.AppContext;

public abstract class ControlActivity extends BaseActivity {
    public static int PORT = 8899;
    protected static final String LeftKeyDown = "leftButton:down";
    protected static final String LeftKeyUp = "leftButton:release";
    protected static final String RightKeyDown = "rightButton:down";
    protected static final String RightKeyUp = "rightButton:release";
    protected static final String MouseWheel = "mousewheel:";
    protected static final String MouseMove = "mouse:";
    protected static final String KeyBoard = "keyboard:";

    protected static final String Car_Release = "0";
    protected static final String Car_Up = "1";
    protected static final String Car_Down = "2";
    protected static final String Car_Left = "3";
    protected static final String Car_Right = "4";
    protected static final String Car_W = "5";
    protected static final String Car_S = "6";
    protected static final String Car_A = "7";
    protected static final String Car_D = "8";
    protected static final String Car_More = "9";
    protected static final String Car_LightOn = "a";
    protected static final String Car_LightOff = "b";
    protected static final String Car_Spaker = "c";

    protected AppContext application;
    private DatagramSocket socket;
    private ThreadPoolExecutor threadPool;

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
    protected abstract void handleKeyBoardEvent(int keyCode,
            KeyEvent event);

    /**
     * net request
     */
    protected void sendMessage(String datas) {
        if (!StringUtils.isEmpty(datas)) {
            KJLoger.debug("接收到信号：" + datas);
            threadPool
                    .submit(new SendCommandThread(datas.getBytes()));
        }
    }

    /**
     * 向电脑发送命令的线程
     */
    private class SendCommandThread implements Runnable {
        private byte[] datas;

        public SendCommandThread(byte[] datas) {
            this.datas = datas;
        }

        @Override
        public void run() {
            try {
                InetAddress pcAddress = InetAddress
                        .getByName(application.ip);
                DatagramPacket packet = new DatagramPacket(datas,
                        datas.length, pcAddress, PORT);
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
