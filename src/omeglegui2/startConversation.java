package omeglegui2;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.google.gson.Gson;

public class startConversation {
	sendpost sender=new sendpost();
	Gson g=new Gson();
	spyHandler handler=new spyHandler();

	public String startConvo(String str, String clientID, JTextArea textArea, JLabel strang1, JLabel strang2)
	{
		String[] str1={};
		while(!(str.equals("disconnected")))
		{
			String s=sender.sendPostReq(clientID);
			if(s.contains("statusInfo")) s=s.substring(0, 47)+"]}";
			System.out.println(s);
			eventHandler e=g.fromJson(s, eventHandler.class);
			str1=e.eventnotice.get(0);
			if(str1.length==2)
			{
				if(str1[0].equals("spyTyping")) 
				{
					handler.handle(str1[1], strang1, strang2);
					str="connected";
					continue;
				}
				else if(str1[0].equals("spyDisconnected"))
				{
					str="disconnected";
					return str1[1];
				}
				else if(str1[0].equals("spyStoppedTyping"))
				{
					continue;
				}
			}
			else if(str1.length==3)
			{
				if(str1[0].equals("spyMessage"))
				{
					textArea.append(str1[1]+": "+str1[2]+"\n");
					textArea.update(textArea.getGraphics());
					if(str1[1].equals("Stranger 1")) { strang1.setText(""); strang1.update(strang1.getGraphics());strang1.repaint();}
					else if(str1[1].equals("Stranger 2")) { strang2.setText(""); strang2.update(strang2.getGraphics());strang2.repaint(); }
				}
				continue;
			}
		}
		return str1[1];
	}

}
