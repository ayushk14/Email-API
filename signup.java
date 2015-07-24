//Import Packages
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
class signup
{
	JFrame fob;
	JPanel panel,btnPanel;
	JLabel l1,l2,l3;
	TextField t1,t2,t3;
	JButton b1,b2;
	String strid=null,strpass=null,strcpass=null;
	Connection con;
	Statement stmt;
	ResultSet res;
	signup()
	{
		fob=new JFrame("Signup");
		fob.setSize(400,200);
		fob.setLocation(400,200);
		fob.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel=new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c=new GridBagConstraints();
		c.insets=new Insets(2,2,2,2);
		c.anchor=GridBagConstraints.CENTER;
		
		l1=new JLabel("Email ID");
		panel.add(l1,c);
		
		l2=new JLabel("Password");
		c.gridy=1;
		panel.add(l2,c);
		
		l3=new JLabel("Confirm Password");
		c.gridy=2;
		panel.add(l3,c);
		
		t1=new TextField(30);
		c.gridx=1;
		c.gridy=0;
		c.gridwidth=2;
		panel.add(t1,c);
		
		t2=new TextField(30);
		t2.setEchoChar('*');
		c.gridy=1;
		panel.add(t2,c);
		
		t3=new TextField(30);
		t3.setEchoChar('*');
		c.gridy=2;
		panel.add(t3,c);
		
		btnPanel=new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
		
		b1=new JButton("Add");
		b1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				strid=t1.getText();
				strpass=t2.getText();
				strcpass=t3.getText();
				if (strpass.equals(strcpass))
				{
					try
					{	// Check existence of User
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
						con=DriverManager.getConnection("jdbc:odbc:			");							// Enter name of DSN
						stmt=con.createStatement();
						res=stmt.executeQuery("select * from logn where ID='"+strid+"'");
						if (res.next())
						{
							JOptionPane.showMessageDialog(fob,"User already exist");
							fob.setVisible(false);
							new login();
						}
						else
						{
							stmt.executeUpdate("insert into logn values('"+strid+"','"+strpass+"')");
							JOptionPane.showMessageDialog(fob,"Added Successfully. Click OK to proceed to Login Page");
							fob.setVisible(false);
							new login();
						}
						con.close();
						res.close();
					}
					catch (Exception e)
					{}
				}
				else
				{
					JOptionPane.showMessageDialog(fob,"Enter correct password");
					t2.setText(null);
					t3.setText(null);
					t2.requestFocus();
				}
			}
		});
		btnPanel.add(b1);
		
		b2=new JButton("Cancel");
		b2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				fob.setVisible(false);
				new login();
			}
		});
		btnPanel.add(b2);
		
		c.gridy=3;
		c.gridwidth=0;
		panel.add(btnPanel,c);
		
		fob.add(panel);
		fob.show();
	}
}