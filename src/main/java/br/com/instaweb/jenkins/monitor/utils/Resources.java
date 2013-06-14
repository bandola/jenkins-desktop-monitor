package br.com.instaweb.jenkins.monitor.utils;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.common.io.CharStreams;

public class Resources {

	public String text(String file) {
		checkArgument(file != null, "File name cannot be null.");
		InputStream stream = this.getClass().getResourceAsStream(file);
		checkArgument(stream != null, "File %s not found", file);
		return readSilently(stream);
	}
	
	private static String readSilently(InputStream stream){
		 try {
			return CharStreams.toString(new InputStreamReader(stream));
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not read " + stream, e);
		}
	}
	

}
