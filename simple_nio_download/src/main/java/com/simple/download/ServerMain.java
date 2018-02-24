package com.simple.download;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.simple.util.DownloadFileProcesser;

/**
 * 服务器端代码
 * @author 于继伟
 *
 */
public class ServerMain {
	
	public static void main(String[] args) throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
	    serverSocketChannel.configureBlocking(false);
	    serverSocketChannel.socket().bind(new InetSocketAddress(8887));
	    Selector selector = Selector.open();
	    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

	    while (true) {
	        selector.select();
	        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
	        while (iterator.hasNext()) {
		        SelectionKey s = iterator.next();
		        // 如果客户端有连接请求
		        if (s.isAcceptable()) {
			        System.out.println("客户端连接请求..");
			        ServerSocketChannel ssc = (ServerSocketChannel) s.channel();
			        SocketChannel sc = ssc.accept();
			        sc.configureBlocking(false);
			        sc.register(selector, SelectionKey.OP_READ);
		        }
		        // 如果客户端有发送数据请求
		        if (s.isReadable()) {
			        System.out.println("接受客户端发送过来的文本消息...");
			        //这里拿出的通道就是ACCEPT上注册的SocketChannel通道
			        SocketChannel sc = (SocketChannel) s.channel();
			        //要读取数据先要准备好BUFFER缓冲区
			        ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);
			        //准备BYTE数组,形成输出
			        sc.read(buffer);
			        byte[] clientByteInfo = new byte[buffer.position()];
			        buffer.flip();
			        buffer.get(clientByteInfo);
			        System.out.println("服务器端收到消息：" + new String(clientByteInfo,"utf-8"));
			        //CLIENT下一步的动作就是读取服务器端的文件,因此需要注册写事件
			        SelectionKey selectionKey = sc.register(selector, SelectionKey.OP_WRITE);
			        //在这个selectionKey上绑定一个对象,以供写操作时取出进行处理
			        DownloadFileProcesser downloadFileProcesser = new DownloadFileProcesser();
			        selectionKey.attach(downloadFileProcesser);
		        }
		    
		        // 如果客户端有下载文件数据请求
			    if (s.isWritable()) {
				    // 这里把attachment取出进行写入操作
				    DownloadFileProcesser downloadFileProcesser = (DownloadFileProcesser)s.attachment();
				    int count = downloadFileProcesser.readFile2Buffer();
				   
				    if(count <= 0){
				        System.out.println("客户端下载完毕...");
				        // 关闭通道
				        s.channel().close();
				        downloadFileProcesser.close();
				    }else{
				        // 需要注意的是我们这里并没有出现常见的while写的结构,这是为何?
				        // 因为client其实不断的在read操作，从而触发了SELECTOR的不断写事件!
				        SocketChannel sc = (SocketChannel)s.channel();
				        sc.write(downloadFileProcesser.getByteBuffer());
				    }
			    }
		        iterator.remove();
		    }
	    }
	 }
}
