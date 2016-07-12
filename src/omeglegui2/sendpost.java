package omeglegui2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class sendpost {
	public String sendPostReq(String clientID)
	{
		try
		{
		URL u=new URL("http://front9.omegle.com/events");
		String sj="id="+clientID;
		byte[] out = sj.getBytes(StandardCharsets.UTF_8);
		int length = out.length;
		HttpURLConnection connection=(HttpURLConnection)u.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		connection.setFixedLengthStreamingMode(length);
		try(OutputStream os = connection.getOutputStream()) {
		    os.write(out);
		}
		InputStream eventResponse=connection.getInputStream();
		BufferedReader eventReader=new BufferedReader(new InputStreamReader(eventResponse, "UTF-8"));
		String event=eventReader.readLine();
		event="{ eventnotice:"+event+"}";
		return event;
		}catch(Exception e)
		{
			return null;
		}
	}

}
