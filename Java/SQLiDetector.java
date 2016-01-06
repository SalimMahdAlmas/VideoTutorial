import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class SQLiDetector {

    /**
     * Hello Guys today i will show how to make program which will detect
     * sites is it vulnerable by sql injection in short form we call SQLiDetector
     *
     * We will make in Java so first of create class where main method is declared
     * so as you can see i have already
     *
     * So next, print the the program name and print 'Enter your link :'
     * next, using scanner get the input and check is it valid link with the help of
     * URL class
     */
    public static void main(String[] args) {
        System.out.println("SQLiDetector \n");
        System.out.println("Enter your link : ");
        String link = new Scanner(System.in).next();

        HttpURLConnection httpURLConnection = null;
        // So now we will append ' to the link

        try {
            new URL(link);

            link += "%27"; // this means '

            if (!link.contains("?")) {
                System.out.println("Site is not vulnerable");
                // because generally if link does not contains ? that means that line is not database
            }
            else {
                URL url = new URL(link);
                // we have init url with new link
                // after we will init httpurlconnection by
                httpURLConnection = (HttpURLConnection) url.openConnection();
                // Using buffered reader we can get the contents
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                // then we will write the contents to stringbuilder then we will transfer to string

                String input;
                // in this case don't use string buffer because it is friendly with buffered reader but it will work both
                StringBuilder stringBuilder = new StringBuilder();

                while ( (input = bufferedReader.readLine()) != null) {
                    stringBuilder.append(input);
                }

                String content = stringBuilder.toString();

                // if the site contains any error or missing then site is vulnerable
                if (content.contains("mysql_fetch_array()") || content.contains("Exception") || content.contains("missing") || content.length() <=0) {
                    System.out.println("Site is vulnerable");
                }else {
                    System.out.println("Site is not vulnerable");
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("Try to enter valid link");
        } catch (IOException e) {
            // again we will check here if site is vulnerable
            assert httpURLConnection != null;

            try {
                int res_code =httpURLConnection.getResponseCode();

                if (res_code == 500) {
                    System.out.println("Site is vulnerable");
                }else {
                    System.out.println("Site is not vulnerable");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        // okay let's test

    }

}
