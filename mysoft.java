//Import Packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
class mysoft
{	int x=0,y=0;
	JFrame fob;
	JPanel panel,btnpanel;
	String strto=null,strsub=null,strmsg=null,strattach=null;;
	Object cmbobj=null;
	JLabel l1,l2,l3,l4,l5;
	JComboBox cmb;
	JTextField t1,t2,t3;
	JTextArea ta1;
	JButton b1,b2,b3,b4,b5,b6;
	Dimension shortfield,longfield,hugefield;
	Connection con1,con2;
	Statement stmt1,stmt2;
	ResultSet res1,res2;
	mysoft()
	{	
		fob=new JFrame();
		fob.setTitle("Compose New Messgae");
		fob.setSize(600,400);
		fob.setLocation(300,100);
		
		fob.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				System.exit(0);
			}
		});
		
		panel=new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c=new GridBagConstraints();
		
		shortfield=new Dimension(200,20);
		longfield=new Dimension(240,20);
		hugefield=new Dimension(240,120);
		
		c.insets=new Insets(2,2,2,2);
		c.anchor=GridBagConstraints.WEST;
		
		l1=new JLabel("To:");
		panel.add(l1,c);
		
		cmb=new JComboBox();
		cmb.setPreferredSize(shortfield);
		cmb.addItem("");
		// Load Email IDs from database
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con1=DriverManager.getConnection("jdbc:odbc:		");								// Enter name of DSN
			stmt1=con1.createStatement();
			res1=stmt1.executeQuery("select * from emailid");
			while (res1.next())
			{
				cmb.addItem(res1.getString(1));
			}
			con1.close();
			res1.close();
		}
		catch (Exception e)
		{}
		cmb.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				cmbobj=cmb.getSelectedItem();
			}
		});
		c.gridx=1;
		c.gridwidth=2;
		c.fill=GridBagConstraints.HORIZONTAL;
		panel.add(cmb,c);
		
		t1=new JTextField();
		t1.setPreferredSize(shortfield);
		t1.setEnabled(false);
		c.gridx=1;
		panel.add(t1,c);
		
		l2=new JLabel("Attachment:");
		c.gridx=0;
		c.gridy=2;
		c.gridwidth=1;
		c.weightx=0.0;
		panel.add(l2,c);
		
		t3=new JTextField("Enter address of the attachment");
		t3.setPreferredSize(shortfield);
		t3.setEnabled(false);
		c.gridx=1;
		c.gridwidth=2;
		c.fill=GridBagConstraints.HORIZONTAL;
		panel.add(t3,c);
		
		l3=new JLabel("NOTE : Please use 'Double Backslash' in the address");
		c.gridy=3;
		panel.add(l3,c);
		
		b1=new JButton("Add Attachment");
		b1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				t3.setEnabled(true);
				t3.setText("");
				t3.requestFocus();
				y=1;
				b1.setVisible(false);
				b2.setVisible(true);
			}
		});
		c.gridx=3;
		c.gridy=2;
		c.gridwidth=1;
		c.fill=GridBagConstraints.NONE;
		panel.add(b1,c);
		
		b2=new JButton("Remove Attachment");
		b2.setVisible(false);
		b2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				b1.setVisible(true);
				t3.setText("Enter address of the attachment");
				t3.setEnabled(false);
				b2.setVisible(false);
			}
		});
		c.gridy=3;
		panel.add(b2,c);
		
		l4=new JLabel("Subject:");
		c.gridx=0;
		c.gridy=4;
		c.gridwidth=1;
		c.weightx=0.0;
		panel.add(l4,c);
		
		t2=new JTextField();
		t2.setPreferredSize(longfield);
		c.gridx=1;
		c.weightx=1.0;
		c.gridwidth=3;
		c.fill=GridBagConstraints.HORIZONTAL;
		panel.add(t2,c);
		
		l5=new JLabel("Message:");
		c.gridwidth=1;
		c.weightx=0.0;
		c.gridx=0;
		c.gridy=5;
		panel.add(l5,c);
		
		ta1=new JTextArea();
		JScrollPane j=new JScrollPane(ta1);
		j.setPreferredSize(hugefield);
		c.gridx=1;
		c.weightx=1.0;
		c.weighty=1.0;
		c.gridwidth=3;
		c.gridheight=5;
		c.fill=GridBagConstraints.BOTH;
		panel.add(j,c);
		
		btnpanel=new JPanel();
		btnpanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		
		b3=new JButton("New ID");
		b3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				t1.setEnabled(true);
				t1.requestFocus();
				cmb.setEnabled(false);
				x=1;
			}
		});
		btnpanel.add(b3);
		
		b4=new JButton("Send");
		b4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{	// Add Email ID to the database
					if (x==1)
					{
						strto=t1.getText();
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
						con2=DriverManager.getConnection("jdbc:odbc:		");							//Enter name of DSN
						stmt2=con2.createStatement();
						res2=stmt2.executeQuery("select * from emailid where EmailID='"+strto+"'");
						if (!res2.next())
							stmt2.executeUpdate("insert into emailid values('"+strto+"')");
						con2.close();
					}
					else
						strto=cmbobj.toString();
					
					strsub=t2.getText();
					strmsg=ta1.getText();
					strattach=t3.getText();
					
					// Code to send Email starts.
					Properties props = new Properties();
					props.put("mail.smtp.host","smtp.gmail.com");
					props.put("mail.smtp.socketFactory.port", "465");
					props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.port", "465");
					props.put("mail.smtp.starttls.enable", "true");
					// Get the Session object.
					Session session = Session.getInstance(props,new javax.mail.Authenticator() 
					{
						protected PasswordAuthentication getPasswordAuthentication() 
						{
							return new PasswordAuthentication("Email ID","Password");				// Enter your emailid and password from which you want to send email.
						}
					});
					MimeMessage message = new MimeMessage(session);									// Create a default MimeMessage object.
					message.setFrom(new InternetAddress("Email ID"));								// Set From: header field of the header.
																									// Enter your emailid and password from which you want to send email.
					message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(strto));	// Set To: header field of the header.
					message.setSubject(strsub);														// Set Subject: header field
					BodyPart messageBodyPart = new MimeBodyPart();									// Create the message part
					messageBodyPart.setText(strmsg);												// Now set the actual message
					Multipart multipart = new MimeMultipart();										// Create a multipar message
					multipart.addBodyPart(messageBodyPart);											// Set text message part
					if(y==1)
					{				
						// Part two is attachment
						messageBodyPart = new MimeBodyPart();
						String filename = strattach;
						DataSource source = new FileDataSource(filename);
						messageBodyPart.setDataHandler(new DataHandler(source));
						messageBodyPart.setFileName(filename);
						multipart.addBodyPart(messageBodyPart);

						message.setContent(multipart);													// Send the complete message parts				
					}
					else
						message.setContent(multipart);													// Send the complete message parts	
					Transport.send(message);															// Send message
					JOptionPane.showMessageDialog(fob,"Message sent successfully");
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(fob,"There was an error sending message. Please try again!!");
				}
				// Code to send Email ends.
				strto=null;
				strsub=null;
				strmsg=null;
				strattach=null;
				x=0;
				y=0;
				t1.setText(null);
				t2.setText(null);
				t3.setText("Enter address of the attachment");
				ta1.setText(null);
				t1.setEnabled(false);
				t3.setEnabled(false);
				cmb.setEnabled(true);
				cmb.requestFocus();
			}
		});
		btnpanel.add(b4);
		
		b5=new JButton("Cancel");
		b5.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				strto=null;
				strsub=null;
				strmsg=null;
				strattach=null;
				x=0;
				y=0;
				cmbobj=null;
				t1.setText(null);
				t2.setText(null);
				t3.setText("Enter address of the attachment");
				ta1.setText(null);
				t1.setEnabled(false);
				t3.setEnabled(false);
				cmb.setEnabled(true);
				cmb.requestFocus();
				cmb.setSelectedIndex(0);
				b1.setVisible(true);
				b2.setVisible(false);
			}
		});
		btnpanel.add(b5);
		
		b6=new JButton("Logout");
		b6.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new login();
				fob.setVisible(false);
			}
		});
		btnpanel.add(b6);
				
		c.gridx=2;
		c.gridy=10;
		c.weightx=0.0;
		c.weighty=0.0;
		c.gridwidth=1;
		c.gridheight=1;
		c.fill=GridBagConstraints.NONE;
		panel.add(btnpanel,c);
		
		fob.add(panel);
		fob.show();
	}
}