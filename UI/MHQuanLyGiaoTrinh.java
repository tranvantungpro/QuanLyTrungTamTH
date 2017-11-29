package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Moudle.GiaoTrinh;
import Service.KetNoiGT;
import Service.KetNoiSQL;

public class MHQuanLyGiaoTrinh extends JFrame
{
	//delare
	JTextField txtMa, txtTen, txtLoai, txtNXB, txtTim;  
	JButton btnThem, btnXoa, btnSua, btnTimKiem, btnTaoMoi;
	DefaultTableModel dtmGiaoTrinh;
	JTable  tblGiaoTrinh;

	ArrayList<GiaoTrinh> dsGT = null;
	ArrayList<GiaoTrinh> dsThem = null;
	ArrayList<GiaoTrinh> dsGTTim = null;
	static String MaGiaoTrinh = "";


	public MHQuanLyGiaoTrinh (String tieude)
	{
		super(tieude);
		addContronls();
		addEvents();
		HienThiToanBoGT();
	}

	private void addEvents() 
	{
		btnTimKiem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{ 

				HienThiTim();
			}

		});

		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				 if(KiemTraTonTai()==0)
				 {
					if(KiemTaCuPhap()==0)
					{
						LuuMoiGT();
						 
					}
					else
					{
						btnTaoMoi.doClick();
					}
				 }
				 else
				 {
					 JOptionPane.showMessageDialog(null, "Giáo trình đã tồn tại. Vui lòng nhập lại!");
					 btnTaoMoi.doClick();
				 }
				 


			}
		});

		btnXoa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				XoaGiaoTrinh();

			}
		});

		tblGiaoTrinh.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row=tblGiaoTrinh.getSelectedRow();
				if(row==-1)return; 
				GiaoTrinh gt = dsGT.get(row);
				txtMa.setText(gt.getMaGT());
				txtTen.setText(gt.getTenGT());
				txtLoai.setText(gt.getLoaiGT());
				txtNXB.setText(gt.getNXB());
			}
		});

		btnSua.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CapNhatGiaoTrinh();

			}
		});

		btnTaoMoi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				txtMa.setText(LayMaGT());
				txtTen.setText("");
				txtLoai.setText("");
				txtNXB.setText("");
				HienThiToanBoGT();

			}
		});
	}

	protected void NhapTT() {


	}

	private void addContronls() 
	{

		Container con = getContentPane();
		con.setLayout(new BorderLayout());

		//create slip
		JPanel pnTop = new JPanel();
		pnTop.setLayout(new BorderLayout());
		pnTop.setPreferredSize(new Dimension(0, 493));
		JPanel pnBottom = new JPanel();
		pnBottom.setLayout(new BoxLayout(pnBottom, BoxLayout.Y_AXIS));
		JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnTop, pnBottom);
		sp.setOneTouchExpandable(true);
		con.add(sp, BorderLayout.CENTER);

		//pnTim
		JPanel pnTim = new JPanel();
		JLabel lblTenGT = new JLabel("Nhập tên giáo trình: ");
		lblTenGT.setFont(new Font("Arial", Font.PLAIN, 15));
		txtTim = new JTextField(20);
		btnTimKiem = new JButton("Tìm");
		pnTim.add(lblTenGT);
		pnTim.add(txtTim);
		pnTim.add(btnTimKiem);
		pnTop.add(pnTim, BorderLayout.NORTH);

		//pnTop
		dtmGiaoTrinh = new DefaultTableModel();
		dtmGiaoTrinh.addColumn("Mã GT");
		dtmGiaoTrinh.addColumn("Tên GT");
		dtmGiaoTrinh.addColumn("Loại");
		dtmGiaoTrinh.addColumn("NXB");
		tblGiaoTrinh = new JTable(dtmGiaoTrinh);
		JScrollPane sptable = new JScrollPane(tblGiaoTrinh, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnTop.add(sptable);
		//pnBottom
		JPanel pnTopofBottom = new JPanel();
		pnTopofBottom.setLayout(new BoxLayout(pnTopofBottom, BoxLayout.Y_AXIS));
		//pnMa Ma
		JPanel pnMa = new JPanel();
		pnMa.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblMa = new JLabel("Mã GT: ");
		lblMa.setFont(new Font("Arial", Font.PLAIN, 15));
		txtMa = new JTextField(30);
		txtMa.setEditable(false);
		pnMa.add(lblMa);
		pnMa.add(txtMa);
		pnTopofBottom.add(pnMa);
		//pnMa Ten
		JPanel pnTen = new JPanel();
		pnTen.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblTen = new JLabel("Tên GT: ");
		lblTen.setFont(new Font("Arial", Font.PLAIN, 15));
		txtTen = new JTextField(30);
		pnTen.add(lblTen);
		pnTen.add(txtTen);
		pnTopofBottom.add(pnTen);
		//pnMa loai
		JPanel pnLoai = new JPanel();
		pnLoai.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblLoai = new JLabel("Loại GT: ");
		lblLoai.setFont(new Font("Arial", Font.PLAIN, 15));
		txtLoai = new JTextField(30);
		pnLoai.add(lblLoai);
		pnLoai.add(txtLoai);
		pnTopofBottom.add(pnLoai);		
		//pnMa nxb
		JPanel pnNXB = new JPanel();
		pnNXB.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblNXB = new JLabel("NXB GT: ");
		lblNXB.setFont(new Font("Arial", Font.PLAIN, 15));
		txtNXB = new JTextField(30);
		pnNXB.add(lblNXB);
		pnNXB.add(txtNXB);
		pnTopofBottom.add(pnNXB);	
		pnBottom.add(pnTopofBottom);

		lblMa.setPreferredSize(lblNXB.getPreferredSize());
		lblTen.setPreferredSize(lblNXB.getPreferredSize());
		lblLoai.setPreferredSize(lblNXB.getPreferredSize());

		//pnButton
		JPanel pnButton = new JPanel();
		pnButton.setLayout(new FlowLayout(FlowLayout.CENTER));
		btnThem = new JButton("Thêm");
		btnXoa = new JButton("Xóa");
		btnSua = new JButton("Cập nhật");
		btnTaoMoi =new JButton("Tạo Mới");
		pnButton.add(btnThem);
		pnButton.add(btnXoa);
		pnButton.add(btnSua);
		pnButton.add(btnTaoMoi);
		pnBottom.add(pnButton);

	}

	public void showWindow()
	{
		this.setSize(600, 700);
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	protected void CapNhatGiaoTrinh() 
	{
		GiaoTrinh gt = new GiaoTrinh();
		gt.setMaGT(txtMa.getText());
		gt.setTenGT(txtTen.getText());
		gt.setLoaiGT(txtLoai.getText());
		gt.setNXB(txtNXB.getText());
		KetNoiGT kn =  new KetNoiGT();
		if(kn.CapNhatGiaoTrinh(gt) > 0)
		{
			JOptionPane.showMessageDialog(null, "Cập nhật GT thành công");
			HienThiToanBoGT();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Cập nhật GT thất bại");
		}

	}

	protected void XoaGiaoTrinh() 
	{
		GiaoTrinh gt = new GiaoTrinh();
		gt.setMaGT(txtMa.getText());
		KetNoiGT kn =  new KetNoiGT();
		if(kn.XoaGiaoTrinh(gt) > 0)
		{
			JOptionPane.showMessageDialog(null, "Xóa GT thành công");
			HienThiToanBoGT();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Xóa GT thất bại");
		}
	}

	protected void LuuMoiGT() 
	{
		GiaoTrinh gt = new GiaoTrinh();
		gt.setMaGT(txtMa.getText());
		gt.setTenGT(txtTen.getText());
		gt.setLoaiGT(txtLoai.getText());
		gt.setNXB(txtNXB.getText());
		KetNoiGT kn =  new KetNoiGT();
		if(kn.ThemMoiGiaoTrinh(gt) > 0)
		{
			JOptionPane.showMessageDialog(null, "Lưu GT thành công");
			HienThiToanBoGT();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Lưu GT thất bại");
		}

	}

	public String LayMaGT()
	{
		KetNoiGT kngt = new KetNoiGT();
		GiaoTrinh a = new GiaoTrinh();
		a = kngt.LayMaGT();
		MaGiaoTrinh=a.getMaGT();

		int ViTriCuoi=MaGiaoTrinh.lastIndexOf("T");
		String TenMa=MaGiaoTrinh.substring(ViTriCuoi+1);
		int b = Integer.parseInt(TenMa);
		int c = b+1;
		String MaGT = String.format("GT%03d", c);
		return MaGT;
	}

	public void HienThiToanBoGT()
	{
		KetNoiGT kngt = new KetNoiGT();
		dsGT = kngt.LayToanBoGiaoTrinh();
		dtmGiaoTrinh.setRowCount(0);
		for(GiaoTrinh a : dsGT)
		{
			Vector<Object> vec = new Vector<Object>();
			vec.add(a.getMaGT());
			vec.add(a.getTenGT());
			vec.add(a.getLoaiGT());
			vec.add(a.getNXB());
			dtmGiaoTrinh.addRow(vec);
		}
	} 

	public void HienThiTim()
	{
		if(txtTim.getText().equals(""))
		{
			HienThiToanBoGT();
			JOptionPane.showMessageDialog(null, "Vui lòng nhập giáo trình cần tìm!!!");
		}
		else
		{
			String a1 = txtTim.getText();
			KetNoiGT kngt = new KetNoiGT();
			dsGTTim = kngt.TimGiaoTrinh(a1);	
			dtmGiaoTrinh.setRowCount(0);
			for(GiaoTrinh a : dsGTTim)
			{
				Vector<Object> vec = new Vector<Object>();
				vec.add(a.getMaGT());
				vec.add(a.getTenGT());
				vec.add(a.getLoaiGT());
				vec.add(a.getNXB());
				dtmGiaoTrinh.addRow(vec);
			}
		}
	}

	public int KiemTaCuPhap()
	{	
		if(txtTen.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Sai cú pháp. Vui lòng kiểm tra lại");
			return 1;
		}

		if(txtLoai.getText().equals(""))
		{

		}

		if(txtNXB.getText().equals(""))
		{

		}
		return 0;
	}

	public int KiemTraTonTai()
	{
		KetNoiGT kngt = new KetNoiGT();
		return kngt.KiemTraTonTai(txtTen.getText());
		 
	}
}
