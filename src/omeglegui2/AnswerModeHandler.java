package omeglegui2;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import org.w3c.dom.Document;

import com.google.gson.Gson;

public class AnswerModeHandler {
	private JTextPane textPane; 
	private JLabel label; 
	private JTextField textField; 
	private JButton button;
	private String query;
	private String response;
	private boolean start;
	private JButton btnStartConvo;
	
	public void setDetails(JTextPane textPane, JLabel label, JTextField textField, JButton button, JButton btnStartConvo)
	{
		this.textField=textField;
		this.textPane=textPane;
		this.label=label;
		this.button=button;
		this.btnStartConvo=btnStartConvo;
	}
	
	public void handler(boolean done)
	{
	Runnable r=new Runnable()
	{
		SendPostReq req=new SendPostReq();
		@Override
		public void run() {
			label.setText("Connecting...");
			label.setForeground(Color.GREEN);
			query="http://front1.omegle.com/start?rcs=1&firstevents=1&spid=&randid=52A2U2Z6&wantsspy=1&lang=en";
			String response=req.sendReq(query);
			Gson g=new Gson();
			details d=g.fromJson(response, details.class);
			String id=d.getID();
			label.setText(d.getEvents().get(0)[0]);
			StyledDocument doc=textPane.getStyledDocument();
			int i=0;
			boolean typing=false;
			do{
			query="http://front1.omegle.com/events";
			response=req.sendReq(query,id);
			if(response.contains("statusInfo")) response=response.substring(0, 39)+"]}";
			System.out.println(response);
			eventHandler e=g.fromJson(response, eventHandler.class);
			response=e.getEvents().get(0)[0];
			if(response.equals("connected")){
				label.setForeground(Color.BLACK);
				label.setText(e.getEvents().get(1)[1]);
				button.setEnabled(true);
				start=true;
			}else if(response.equals("strangerDisconnected"))
			{
				label.setForeground(Color.RED);
				label.setText("Stranger has disconnected");
				button.setEnabled(false);
				btnStartConvo.setText("Start Conversation");
				try {
					doc.remove(0, doc.getLength());
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				start=false;
			}else if(response.equals("typing"))
			{
				try {
					doc.insertString(i, "Stranger is typing\n", null);
					typing=true;
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}else if(response.equals("gotMessage"))
			{
				try {
					if(typing)
					{
					doc.remove(i, 20);
					doc.insertString(i, "Stranger:"+ e.getEvents().get(0)[1]+"\n", null);
					i=doc.getLength();
					typing=false;
					}
					else
					{
					doc.insertString(i, "Stranger:"+e.getEvents().get(0)[1]+"\n", null);
					}
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
			}while(start);
		}
		
	};
	Thread t1=new Thread(r);
	if(!done)
	t1.start();
	else
	{
		button.setEnabled(false);
		t1.stop();
	}
	}

}
