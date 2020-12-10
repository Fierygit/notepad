package basic.IO.Stream;

import java.io.*;


public class TestStream {

	public static void main(String[] args) throws IOException {


		/**
		 * @author Firefly
		 * class loader 的文件夹，class 文件存在的地方
		 */
		String path = ClassLoader.getSystemResource("").getPath();
		System.out.println(path);


		/**
		 * @author Firefly
		 * java 当前目录是在工程文件夹下面，   JavaStudy 文件夹下面，
		 *
		 */
		File file = new File("hello.txt");
		// Test outoutstream

		OutputStream os = new FileOutputStream(file);
		String temp = "FileOutputStream1\n";
		byte[] tmp =temp.getBytes();
		os.write(tmp);
		// os.close();

		// Test BufferedWriter

		BufferedOutputStream bos = new BufferedOutputStream(os);
		bos.write("BufferedOutputStream\n".getBytes());
		bos.flush();
		// bos.close();

		System.out.println("have print");
		OutputStreamWriter osw = new OutputStreamWriter(os);
		osw.append("OutputStreamWriter");
		osw.flush();

		/*

		File image = new File("C:\\Users\\Firefly\\Desktop\\tt.jpg");

		FileInputStream is = new FileInputStream(image);

		OutputStream os1 = new FileOutputStream("C:\\Users\\Firefly\\Desktop\\yy.jpg");
		byte[] bt = new byte[1024];
		while (is.read(bt) != -1) {
			os1.write(bt);
			os1.flush();
		}

		 */
		
		

	}
}
