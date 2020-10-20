package main;

import java.io.File;
import java.util.LinkedList;

import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

public class Converter {
	public final LinkedList<String> q = new LinkedList<String>();

	public Boolean WAVToMP3(String sourcePath, String targetPath) {
		Boolean result = true;

		try {
			File source = new File(sourcePath);
			File target = new File(targetPath);

			AudioAttributes audio = new AudioAttributes();
			audio.setCodec("libmp3lame");
			audio.setBitRate(192000);
			audio.setChannels(2);
			audio.setSamplingRate(44100);

			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setOutputFormat("mp3");
			attrs.setAudioAttributes(audio);

			Encoder encoder = new Encoder();
			encoder.encode(new MultimediaObject(source), target, attrs);

		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}

		return result;
	}
}
