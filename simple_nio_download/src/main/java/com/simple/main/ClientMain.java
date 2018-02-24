package com.simple.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import com.simple.download.Client4DownloadFile;

public class ClientMain {
	public static void main(String[] args) throws FileNotFoundException{
        for(int i = 0 ; i < 10 ; i++){
            File file = new File("e:/" + i + "jpg" + ".jpg");
            RandomAccessFile raf = new RandomAccessFile(file,"rw");
            Client4DownloadFile client4DownloadFile = new Client4DownloadFile("" + i, raf);
            new Thread(client4DownloadFile).start();
        }
    }
}
