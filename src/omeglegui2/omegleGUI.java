package omeglegui2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.google.gson.Gson;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextPane;

public class omegleGUI {

	private JFrame frmOmegle;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					omegleGUI window = new omegleGUI();
					window.frmOmegle.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	public omegleGUI() {
		initialize();
	}
	
	ThreadHolder holder;
	Runnable runner;
	private JTextField answerField;

	/**
	 * Initialize the contents of the frame.
	 */
	private void updateTextArea(final String text, JTextArea textArea) {
		  SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		      textArea.append(text);
		    }
		  });
		}
	private void redirectSystemStreams(JTextArea textArea)
	{
		OutputStream out=new OutputStream(){

			@Override
		    public void write(int b) throws IOException {
		      updateTextArea(String.valueOf((char) b), textArea);
		    }
		 
		    @Override
		    public void write(byte[] b, int off, int len) throws IOException {
		      updateTextArea(new String(b, off, len), textArea);
		    }
		 
		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }
		};
		System.setOut(new PrintStream(out, true));
	    System.setErr(new PrintStream(out, true));
	}
	
	private void initialize() {
		frmOmegle = new JFrame();
		frmOmegle.setBackground(Color.BLUE);
		frmOmegle.setForeground(Color.RED);
		frmOmegle.setResizable(false);
		frmOmegle.setTitle("Omegle ");
		frmOmegle.setBounds(100, 100, 639, 485);
		frmOmegle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmOmegle.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Spy Mode", null, panel, null);
		panel.setLayout(null);
		
		JLabel label_1 = new JLabel("Question:");
		label_1.setForeground(Color.BLUE);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(24, 308, 72, 20);
		panel.add(label_1);
		
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(24, 87, 565, 196);
		panel.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(24, 340, 563, 31);
		panel.add(textField);
		
		JButton btnSend = new JButton("Send");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "STATUS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel_2.setBounds(24, 15, 174, 55);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel statusLabel = new JLabel("");
		statusLabel.setBounds(14, 18, 146, 23);
		panel_2.add(statusLabel);
		statusLabel.setOpaque(true);
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Stranger 1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel_3.setBounds(211, 15, 185, 55);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel stranger1 = new JLabel("");
		stranger1.setHorizontalAlignment(SwingConstants.CENTER);
		stranger1.setBounds(12, 23, 161, 20);
		panel_3.add(stranger1);
		stranger1.setOpaque(true);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Stranger 2", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel_4.setBounds(404, 15, 185, 54);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel stranger2 = new JLabel("");
		stranger2.setHorizontalAlignment(SwingConstants.CENTER);
		stranger2.setBounds(12, 22, 161, 20);
		panel_4.add(stranger2);
		stranger2.setOpaque(true);
		
		startConversation start=new startConversation();
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Answer Mode", null, panel_5, null);
		panel_5.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Question", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel_6.setBounds(29, 7, 543, 51);
		panel_5.add(panel_6);
		panel_6.setLayout(null);
		
		JLabel questionLabel = new JLabel("");
		questionLabel.setBounds(5, 18, 532, 27);
		panel_6.add(questionLabel);
		
		JScrollPane scrollPane_3 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_3.setBounds(32, 337, 540, 44);
		panel_5.add(scrollPane_3);
		
		answerField = new JTextField();
		answerField.setEditable(false);
		scrollPane_3.setViewportView(answerField);
		answerField.setColumns(10);
		
		AnswerModeHandler a=new AnswerModeHandler();
		
		JButton btnStartConvo = new JButton("Start Conversation");
		btnStartConvo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(btnStartConvo.getText().equals("Start Conversation"))
				{
					btnStartConvo.setText("Stop Conversation");
					answerField.setEditable(true);
					
					
				}
				
			}
		});
		btnStartConvo.setBounds(213, 390, 160, 26);
		panel_5.add(btnStartConvo);
		
		JScrollPane scrollPane_2 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(29, 70, 543, 255);
		panel_5.add(scrollPane_2);
		
		JTextPane convoPane = new JTextPane();
		convoPane.setEditable(false);
		scrollPane_2.setViewportView(convoPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Logs", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(25, 26, 558, 360);
		panel_1.add(scrollPane_1);
		
		JTextArea textAreaLogs = new JTextArea();
		textAreaLogs.setEditable(false);
		scrollPane_1.setViewportView(textAreaLogs);
		
		runner=new Runnable(){
			@Override
			public void run(){
				String q=textField.getText();
				textArea.setText("");
				textArea.repaint();
				q=q.replace(" ", "%20");
				if(q.length()>10)
				{
					String result="";
					Gson g=new Gson();
					sendpost sender=new sendpost();
					String req="http://front9.omegle.com/start?rcs=1&firstevents=1&spid=&randid=88ARYSC2&ask="+q+"&lang=en";
					try {
						InputStream input = new URL(req).openStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
						result=reader.readLine();
						System.out.println(result);
						details d=g.fromJson(result, details.class);
						System.out.println(d.clientID);
						String event=sender.sendPostReq(d.clientID);
						eventHandler eventn=g.fromJson(event, eventHandler.class);
						String[] str=eventn.eventnotice.get(0);
						statusLabel.setForeground(Color.GREEN);
						statusLabel.setText("Connected");
						statusLabel.update(statusLabel.getGraphics());
						statusLabel.repaint();
						System.out.println("Connected");
						String s=start.startConvo(str[0], d.clientID, textArea, stranger1, stranger2);
						textArea.append(s +" has disconnected");
						stranger1.setText(""); stranger1.update(stranger1.getGraphics()); stranger1.repaint();
						stranger2.setText(""); stranger2.update(stranger1.getGraphics());stranger2.repaint();
						statusLabel.setForeground(Color.RED);
						statusLabel.setText("Disconnected");
						btnSend.setText("Send");

					} catch (Exception e) {
						System.out.println("Exception occured:"+e.getCause());
						btnSend.setText("Send");
						stranger1.setText(""); stranger1.update(stranger1.getGraphics()); stranger1.repaint();
						stranger2.setText(""); stranger2.update(stranger1.getGraphics());stranger2.repaint();
						statusLabel.setForeground(Color.RED);
						statusLabel.setText("Disconnected");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Question is too short");
				}
			}
		};
		
		
		
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textAreaLogs.setText("");
				redirectSystemStreams(textAreaLogs);
				if(btnSend.getText().equals("Send")){
					btnSend.setText("Disconnect");
					Thread t1=new Thread(runner);
					t1.start();
					holder=new ThreadHolder(t1);
				}
				else if(btnSend.getText().equals("Disconnect"))
				{
				    Thread t1= (Thread) holder.getObject();
				    t1.stop();
					statusLabel.setForeground(Color.RED);statusLabel.setText("Disconnected"); statusLabel.update(statusLabel.getGraphics());
					stranger1.setText(""); stranger1.update(stranger1.getGraphics()); stranger1.repaint();
					stranger2.setText(""); stranger2.update(stranger1.getGraphics());stranger2.repaint();
					textArea.setText("");textArea.update(textArea.getGraphics());textArea.repaint();
					btnSend.setText("Send");
					try {
						this.finalize();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnSend.setForeground(new Color(165, 42, 42));
		btnSend.setBounds(24, 383, 89, 23);
		panel.add(btnSend);
		
		
	}
}
