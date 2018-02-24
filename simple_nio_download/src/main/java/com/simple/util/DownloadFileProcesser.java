package com.simple.util;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件下载辅助类
 * @author 于继伟
 *
 */
public class DownloadFileProcesser implements Closeable {
	
	private ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);
	
	private FileChannel fileChannel;
	
	public DownloadFileProcesser(){
		try {
			FileInputStream fis = new FileInputStream("e:/1.jpg");
			fileChannel = fis.getChannel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int readFile2Buffer() throws IOException{
		int count = 0;
		buffer.clear();
		count = fileChannel.read(buffer);
		buffer.flip();
		return count;
	}
	
	public ByteBuffer getByteBuffer(){
		return buffer;
	}
	
	@Override
	public void close() throws IOException {
		fileChannel.close();
	}
}
