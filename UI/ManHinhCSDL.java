package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.security.auth.kerberos.KerberosTicket;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Service.KetNoiSQL;

public class ManHinhCSDL extends JFrame
{
	JTextField txtServerName, txtDatabase, txtPass;
	JButton btnOK;
	Connection a = null;
	public ManHinhCSDL (String Tieude)
	{
		super(Tieude);
		addControls();
		addEvents();
	}

	private void addControls() 
	{
		Container con = getContentPane();
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		
		//pnServer
		JPanel pnServer = new JPanel();
		pnServer.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblserver = new JLabel("Server Name:");
		txtServerName = new JTextField(20);
		pnServer.add(lblserver);
		pnServer.add(txtServerName);
		con.add(pnServer);
		
		//pnData
		JPanel pndata = new JPanel();
		pndata.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lbldata = new JLabel("DataBase:");
		txtDatabase = new JTextField(20);
		pndata.add(lbldata);
		pndata.add(txtDatabase);
		con.add(pndata);
		
		//pnPass
		JPanel pnPass = new JPanel();
		pnPass.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblpass = new JLabel("Password:");
		txtPass = new JTextField(20);
		pnPass.add(lblpass);
		pnPass.add(txtPass);
		con.add(pnPass);
		
		//pnButton
		JPanel pnButton = new JPanel();
		pnButton.setLayout(new FlowLayout(FlowLayout.CENTER));
		btnOK = new JButton("OK");
		pnButton.add(btnOK);
		con.add(pnButton);
		
		lbldata.setPreferredSize(lblserver.getPreferredSize());
		lblpass.setPreferredSize(lblserver.getPreferredSize());
	}
	
	private void addEvents() 
	{
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				XuLyTruyXuatSQL(txtServerName.getText(), txtDatabase.getText());
				
			}
		});
	}
	
	protected void XuLyTruyXuatSQL(String server, String database) 
	{
		a = KetNoiSQL.getConnect(server, database);
		if(a==null)
		{
			 JOptionPane.showMessageDialog(null, "Truy Xuất Thất Bại");
		}
		else
		{
			 JOptionPane.showMessageDialog(null, "Truy Xuất Thành Công");
			 this.dispose();
			 ManHinhChinh manhinh  = new ManHinhChinh("Phần mềm quản lý trung tâm tin học");
			 manhinh.showWindows();
			 
		}
			
		
	}

	public void ShowWindow()
	{
		this.setSize(330, 160);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}
