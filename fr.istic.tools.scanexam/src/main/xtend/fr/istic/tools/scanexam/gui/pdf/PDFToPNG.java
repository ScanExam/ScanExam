package fr.istic.tools.scanexam.gui.pdf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class PDFToPNG {
	private static class StreamGobbler implements Runnable {
	    private InputStream inputStream;
	    private Consumer<String> consumer;
	 
	    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
	        this.inputStream = inputStream;
	        this.consumer = consumer;
	    }
	 
	    @Override
	    public void run() {
	        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			new BufferedReader(inputStreamReader).lines().forEach(consumer);
	    }
	    
	}
	static void convert() throws IOException, InterruptedException {
		String homeDirectory = System.getProperty("user.home");
		Process process;
		boolean isWindows = System.getProperty("os.name")
				  .toLowerCase().startsWith("windows");
		if (isWindows) {
		    process = Runtime.getRuntime()
		      .exec(String.format("cmd.exe /c dir %s", homeDirectory));
		} else {
		    process = Runtime.getRuntime()
		      .exec(String.format("sh -c ls %s", homeDirectory));
		}
		StreamGobbler streamGobbler = 
		  new StreamGobbler(process.getInputStream(), System.out::println);
		Executors.newSingleThreadExecutor().submit(streamGobbler);
		int exitCode = process.waitFor();
		assert exitCode == 0;
	}
}
