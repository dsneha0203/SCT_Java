package com.simpsoft.salesCommission.app.api.httpReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpReader {
	private volatile long diff;
	private static long[] timeTaken;

	public static void main(String[] args) throws IOException, InterruptedException {

		HttpReader h = new HttpReader();
	}

	public HttpReader() throws IOException, InterruptedException {
		timeTaken = new long[50];

		for (int i = 0; i < 50; i++) {

			Thread t = new ThreadTest(i);
			t.start();
			
			Thread.currentThread().sleep(1000);

		}
		
		long totalTimeTaken = 0;
		for (int i = 0; i < 50; i++) {
			totalTimeTaken += timeTaken[i];
		}
		System.out.println(String.format("Time taken total = %d ms, avg = %d ms", totalTimeTaken, totalTimeTaken/50));
	}
	
	public class ThreadTest extends Thread {
		private int threadSrl;

		public ThreadTest(int srl) throws IOException {
			threadSrl = srl;

		}

		public void run() {

			long before = System.currentTimeMillis();
			URL oracle = null;
			try {
				oracle = new URL("https://www.google.com");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String str;

			// String inputLine;
			try {
				while ((str = in.readLine()) != null) {
					// System.out.println(str);
				}
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			long timeTakenHere = System.currentTimeMillis() - before;

			
			System.out.println("time taken for thread " + threadSrl + " " + timeTakenHere + "ms");
			timeTaken[threadSrl] = timeTakenHere;
		}

	}
}
