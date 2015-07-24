//Import Packages
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
class login
{
	JFrame fob;
	JLabel l1,l2;
	TextField t1,t2;
	JButton b1,b2;
	String strid=null,strpass=null;
	Connection con;
	Statement stmt;
	ResultSet res;
	login()
	{
		fob=new JFrame("Login Window");
		fob.setSize(400,200);
		fob.setLocation(400,200);
		fob.setLayout(new GridBagLayout());
		fob.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagConstraints c=new GridBagConstraints();
		c.insets=new Insets(2,2,2,2);
		c.anchor=GridBagConstraints.CENTER;
		
		l1=new JLabel("Email ID");
		fob.add(l1,c);
		
		t1=new TextField(30);
		c.gridx=1;
		fob.add(t1,c);
		
		l2=new JLabel("Password");
		c.gridx=0;
		c.gridy=1;
		fob.add(l2,c);
		
		t2=new TextField(30);
		t2.setEchoChar('*');
		c.gridx=1;
		fob.add(t2,c);
			
		b1=new JButton("Login");
		b1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				strid=t1.getText();
				strpass=t2.getText();
				try
				{	// User Authentication
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					con=DriverManager.getConnection("jdbc:odbc:		");											// Enter name of DSN
					stmt=con.createStatement();
					res=stmt.executeQuery("select * from logn where ID='"+strid+"' and Pass='"+strpass+"'");
					if (res.next())
					{
						new mysoft();
						fob.setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(fob,"Enter correct details");
						t1.setText(null);
						t2.setText(null);
						t1.requestFocus();
					}
					con.close();
					res.close();
				}
				catch (Exception e)
				{}
			}
		});
		c.gridx=1;
		c.gridy=2;
		fob.add(b1,c);
		
		b2=new JButton("New User");
		b2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new signup();
				fob.setVisible(false);
			}
		});
		c.gridx=1;
		c.gridy=3;
		fob.add(b2,c);
		
		fob.show();
	}
	public static void main(String args[])
	{
		new login();
	}
}