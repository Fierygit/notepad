package basic.IO.Stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

public class URLStream {
	public static void main(String[] args) throws IOException {

		URL url = new URL("http://www.baidu.com");
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		BufferedReader br = new BufferedReader(isr);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		String msg = "";
		while ((msg = br.readLine()) != null) {
			bw.write(msg);
			bw.flush();
		}

	}
}
