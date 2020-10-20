package main;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		//directorye eklenen yeni dosyaları takip et
		//dosyanın uzantısı wav ise kuyruğa ekle
		//converter kuyruktaki wavlar için mp3 üretsin
		//wavı kuyruktan çıkar
		//Converter converter = new Converter();
		//converter.WAVToMP3("./source2.wav", "./target2.mp3");
		
		try {
			new Watcher("./").processEvents();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
