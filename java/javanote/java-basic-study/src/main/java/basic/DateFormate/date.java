package basic.DateFormate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class date {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		System.out.println("date to formatDate!!!");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d = new Date();
		String str = df.format(d);
		System.out.println(str);
		System.out.println("***********************");
		System.out.println("formatDate to date");
		String str2 = "1977,6,6";
		DateFormat df2 = new SimpleDateFormat("yyyy,MM,dd");
		Date d2 = df2.parse(str2);
		System.out.println(d2);
	}

}
