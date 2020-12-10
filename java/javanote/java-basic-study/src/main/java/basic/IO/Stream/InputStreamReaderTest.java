package basic.IO.Stream;

import java.io.*;

public class InputStreamReaderTest {

    public static void main(String[] args) throws IOException {

        InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        // BufferedReader bufferedReader = new BufferedReader(new

        OutputStream os = System.out;
        OutputStreamWriter osr = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osr);
        String msg = "";
        while (!msg.equals("exit")) {

            msg = br.readLine();
            bw.write(msg);
            bw.newLine();
            bw.flush();
        }


    }
}