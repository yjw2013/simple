package com.simple.download;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Client4DownloadFile implements Runnable {

	// 标示
    private String name;
    private FileChannel fileChannel;
    public Client4DownloadFile(String name , RandomAccessFile randomAccessFile){
	    this.name = name;
	    this.fileChannel = randomAccessFile.getChannel(); 
    }
    
    private ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);

    @Override
    public void run() {
    	try {
		    SocketChannel sc = SocketChannel.open();
		    Selector selector = Selector.open();
		    sc.configureBlocking(false);
		    sc.register(selector, SelectionKey.OP_CONNECT);
		    sc.connect(new InetSocketAddress("127.0.0.1",8887));
   
		    while(true){
		        selector.select();
		        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
		       
		        while(iterator.hasNext()){
			        SelectionKey s = iterator.next();
			        if(s.isConnectable()){
				        System.out.println("客户端[" + name + "]已经连接上了服务器...");
				        SocketChannel sc2 = (SocketChannel)s.channel();
				        if(sc2.isConnectionPending() && sc2.finishConnect()){
					        sc2.configureBlocking(false);
					        String msg = "Thread-" + name + " send message!";
					        byte[] b = msg.getBytes("utf-8");
					        sc2.write(ByteBuffer.wrap(b));
					        System.out.println("客户端[" + name + "]给服务器端发送文本消息完毕...");
					        sc2.register(selector, SelectionKey.OP_READ);
				        }
			        }
    
				    if(s.isReadable()){
				        SocketChannel sc3 = (SocketChannel)s.channel();
				        buffer.clear();
				        int count = sc3.read(buffer);
				        if(count <= 0){
					        s.cancel();
					        System.out.println("Thread " + name + " 下载完毕...");
				        }
				        
					    while(count > 0){
					        buffer.flip();
					        fileChannel.write(buffer);
					        count = sc3.read(buffer);
					    }
				    }
			        iterator.remove();
			    }
		    }
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	
}
