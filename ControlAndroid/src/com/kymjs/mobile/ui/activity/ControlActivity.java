package com.kymjs.mobile.ui.activity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ThreadPoolExecutor;

import org.kymjs.aframe.core.KJThreadExecutors;
import org.kymjs.aframe.ui.ViewInject;
import org.kymjs.aframe.ui.activity.BaseActivity;

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
    protected abstract void handleKeyBoardEvent(int keyCode, KeyEvent event);
    
    /**
     * net request
     */
    protected void sendMessage(String datas) {
        threadPool.submit(new SendCommandThread(datas.getBytes()));
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
                InetAddress pcAddress = InetAddress.getByName(application.ip);
                DatagramPacket packet = new DatagramPacket(datas, datas.length,
                        pcAddress, PORT);
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
