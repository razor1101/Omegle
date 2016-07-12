package omeglegui2;

import javax.swing.JLabel;

public class spyHandler {
	public void handle(String s, JLabel strang1, JLabel strang2)
	{
	if(s.equals("Stranger 1"))
	{
			strang1.setText("Typing");
			strang1.update(strang1.getGraphics());
			strang1.repaint();
	}
	else if(s.equals("Stranger 2"))
	{
			strang2.setText("Typing"); 
			strang2.update(strang2.getGraphics());
			strang2.repaint();
	}
	}

}
