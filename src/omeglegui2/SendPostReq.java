package omeglegui2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendPostReq {
	String s=null;
	public String sendReq(String query)
	{
		try
		{
			URL u=new URL(query);
			HttpURLConnection conn=(HttpURLConnection) u.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			InputStream in=conn.getInputStream();
			BufferedReader eventReader=new BufferedReader(new InputStreamReader(in, "UTF-8"));
			s=eventReader.readLine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	public String sendReq(String query, String clientID)
	{
		try
		{
		URL u=new URL(query);
		HttpURLConnection conn=(HttpURLConnection) u.openConnection();
		clientID="id="+clientID;
		byte[] b=clientID.getBytes("UTF-8");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		conn.setRequestMethod("POST");
		conn.setFixedLengthStreamingMode(b.length);
		try(OutputStream o=conn.getOutputStream()){
			o.write(b);
		}
		BufferedReader buff=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		s="{ eventnotice:"+buff.readLine()+"}";
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}

}
