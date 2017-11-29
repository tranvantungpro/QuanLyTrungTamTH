package UI;

import javax.swing.JFrame;
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

import Moudle.LopHoc;
import Service.KetNoiLH;
import Service.KetNoiSQL;

public class MHQuanLyLopHoc extends JFrame
{
	//delare
		JTextField txtMa, txtTen, txtLoai, txtMaCH, txtSoBuoi, txtngaybd, txtngaykt, txtMagv, txtTim;  
		JButton btnThem, btnXoa, btnSua, btnTimKiem, btnTaoMoi;
		DefaultTableModel dtmLopHoc;
		JTable  tblLopHoc;

		ArrayList<LopHoc> dsLH = null;
		ArrayList<LopHoc> dsThem = null;
		ArrayList<LopHoc> dsLHTim = null;
		static String MaLopHoc = "";


		public MHQuanLyLopHoc (String tieude)
		{
			super(tieude);
			addContronls();
			addEvents();
			HienThiToanBoLH();
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
							LuuMoiLH();
							 
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
					XoaLopHoc();

				}
			});

			tblLopHoc.addMouseListener(new MouseListener() {

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
					int row=tblLopHoc.getSelectedRow();
					if(row==-1)return; 
					LopHoc LH = dsLH.get(row);
					txtMa.setText(LH.getMaLH());
					txtTen.setText(LH.getTenLH());
					txtLoai.setText(LH.getLoaiLH());
					txtMaCH.setText(LH.getMaCTH());
					//txtSoBuoi.setText(LH.getSoBuoi());
					//txtngaybd.setText(LH.getNgayBD());
					//txtngaykt.setText(LH.getNgayKT());
					txtMagv.setText(LH.getMaGV());
				}
			});

			btnSua.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					CapNhatLopHoc();

				}
			});

			btnTaoMoi.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					txtMa.setText(LayMaLH());
					txtTen.setText("");
					txtLoai.setText("");
					txtMaCH.setText("");
					txtngaybd.setText("");
					txtngaykt.setText("");
					txtMagv.setText("");
					HienThiToanBoLH();

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
			pnTop.setPreferredSize(new Dimension(0, 370));
			JPanel pnBottom = new JPanel();
			pnBottom.setLayout(new BoxLayout(pnBottom, BoxLayout.Y_AXIS));
			JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnTop, pnBottom);
			sp.setOneTouchExpandable(true);
			con.add(sp, BorderLayout.CENTER);

			//pnTim
			JPanel pnTim = new JPanel();
			JLabel lblTenLH = new JLabel("Nhập tên lớp học: ");
			lblTenLH.setFont(new Font("Arial", Font.PLAIN, 15));
			txtTim = new JTextField(20);
			btnTimKiem = new JButton("Tìm");
			pnTim.add(lblTenLH);
			pnTim.add(txtTim);
			pnTim.add(btnTimKiem);
			pnTop.add(pnTim, BorderLayout.NORTH);

			//pnTop
			dtmLopHoc = new DefaultTableModel();
			dtmLopHoc.addColumn("Mã LH");
			dtmLopHoc.addColumn("Tên LH");
			dtmLopHoc.addColumn("Loại LH");
			dtmLopHoc.addColumn("Mã CTH");
			dtmLopHoc.addColumn("Số Buổi");
			dtmLopHoc.addColumn("Ngày BĐ");
			dtmLopHoc.addColumn("Ngày KT");
			dtmLopHoc.addColumn("Mã GV"); 
			
			tblLopHoc = new JTable(dtmLopHoc);
			JScrollPane sptable = new JScrollPane(tblLopHoc, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			pnTop.add(sptable);
			//pnBottom
			JPanel pnTopofBottom = new JPanel();
			pnTopofBottom.setLayout(new BoxLayout(pnTopofBottom, BoxLayout.Y_AXIS));
			//pnMa Ma
			JPanel pnMa = new JPanel();
			pnMa.setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel lblMa = new JLabel("Mã LH: ");
			lblMa.setFont(new Font("Arial", Font.PLAIN, 15));
			txtMa = new JTextField(30);
			txtMa.setEditable(false);
			pnMa.add(lblMa);
			pnMa.add(txtMa);
			pnTopofBottom.add(pnMa);
			//pnMa Ten
			JPanel pnTen = new JPanel();
			pnTen.setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel lblTen = new JLabel("Tên LH: ");
			lblTen.setFont(new Font("Arial", Font.PLAIN, 15));
			txtTen = new JTextField(30);
			pnTen.add(lblTen);
			pnTen.add(txtTen);
			pnTopofBottom.add(pnTen);
			//pnMa loai
			JPanel pnLoai = new JPanel();
			pnLoai.setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel lblLoai = new JLabel("Loại LH: ");
			lblLoai.setFont(new Font("Arial", Font.PLAIN, 15));
			txtLoai = new JTextField(30);
			pnLoai.add(lblLoai);
			pnLoai.add(txtLoai);
			pnTopofBottom.add(pnLoai);		
			//pnMa mcth
			JPanel pnCTH = new JPanel();
			pnCTH.setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel lblNXB = new JLabel("Mã CTH: ");
			lblNXB.setFont(new Font("Arial", Font.PLAIN, 15));
			txtMaCH = new JTextField(30);
			pnCTH.add(lblNXB);
			pnCTH.add(txtMaCH);
			pnTopofBottom.add(pnCTH);	
			pnBottom.add(pnTopofBottom);
			
			//pnMa 
			JPanel pnSoB = new JPanel();
			pnSoB.setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel lblNXBa = new JLabel("Số Buổi: ");
			lblNXBa.setFont(new Font("Arial", Font.PLAIN, 15));
			txtSoBuoi = new JTextField(30);
			pnSoB.add(lblNXBa);
			pnSoB.add(txtSoBuoi);
			pnTopofBottom.add(pnSoB);	
			pnBottom.add(pnTopofBottom);
			
			//pnMa 
			JPanel pnNBD = new JPanel();
			pnNBD.setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel lbl1 = new JLabel("Ngày BĐ: ");
			lbl1.setFont(new Font("Arial", Font.PLAIN, 15));
			txtngaybd = new JTextField(30);
			pnNBD.add(lbl1);
			pnNBD.add(txtngaybd);
			pnTopofBottom.add(pnNBD);	
			pnBottom.add(pnTopofBottom);
			
			//pnMa 
			JPanel pnNKT = new JPanel();
			pnNKT.setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel lbl11 = new JLabel("Ngày KT: ");
			lbl11.setFont(new Font("Arial", Font.PLAIN, 15));
			txtngaykt = new JTextField(30);
			pnNKT.add(lbl11);
			pnNKT.add(txtngaykt);
			pnTopofBottom.add(pnNKT);	
			pnBottom.add(pnTopofBottom);
			
			//pnMa 
			JPanel pnGV = new JPanel();
			pnGV.setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel lbl111 = new JLabel("Ngày KT: ");
			lbl111.setFont(new Font("Arial", Font.PLAIN, 15));
			txtMagv = new JTextField(30);
			pnGV.add(lbl111);
			pnGV.add(txtMagv);
			pnTopofBottom.add(pnGV);	
			pnBottom.add(pnTopofBottom);

			lblMa.setPreferredSize(lbl1.getPreferredSize());
			lblTen.setPreferredSize(lbl1.getPreferredSize());
			lbl11.setPreferredSize(lbl1.getPreferredSize());
			lblNXB.setPreferredSize(lbl1.getPreferredSize());
			lblNXBa.setPreferredSize(lbl1.getPreferredSize());
			lbl111.setPreferredSize(lbl1.getPreferredSize());
			lblLoai.setPreferredSize(lbl1.getPreferredSize());
			

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
			this.setSize(900, 700);
			//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		}

		protected void CapNhatLopHoc() 
		{
			LopHoc LH = new LopHoc();
			LH.setMaLH(txtMa.getText());
			LH.setTenLH(txtTen.getText());
			LH.setLoaiLH(txtLoai.getText());
			LH.setMaCTH(txtMaCH.getText());
			LH.setSoBuoi(Integer.parseInt(txtSoBuoi.getText()));
			//LH.setNgayBD(txtngaybd.getText());
			//LH.setNgayKT(txtngaykt.getText());
			LH.setMaGV(txtMagv.getText());
			KetNoiLH kn =  new KetNoiLH();
			if(kn.CapNhatLopHoc(LH) > 0)
			{
				JOptionPane.showMessageDialog(null, "Cập nhật LH thành công");
				HienThiToanBoLH();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Cập nhật LH thất bại");
			}

		}

		protected void XoaLopHoc() 
		{
			LopHoc LH = new LopHoc();
			LH.setMaLH(txtMa.getText());
			KetNoiLH kn =  new KetNoiLH();
			if(kn.XoaLopHoc(LH) > 0)
			{
				JOptionPane.showMessageDialog(null, "Xóa LH thành công");
				HienThiToanBoLH();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Xóa LH thất bại");
			}
		}

		protected void LuuMoiLH() 
		{
			LopHoc LH = new LopHoc();
			LH.setMaLH(txtMa.getText());
			LH.setTenLH(txtTen.getText());
			LH.setLoaiLH(txtLoai.getText());
			LH.setMaCTH(txtMaCH.getText());
			LH.setSoBuoi(Integer.parseInt(txtSoBuoi.getText()));
			//LH.setNgayBD(txtngaybd.getText());
			//LH.setNgayKT(txtngaykt.getText());
			LH.setMaGV(txtMagv.getText());

			KetNoiLH kn =  new KetNoiLH();
			if(kn.ThemMoiLopHoc(LH) > 0)
			{
				JOptionPane.showMessageDialog(null, "Lưu LH thành công");
				HienThiToanBoLH();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Lưu LH thất bại");
			}

		}

		public String LayMaLH()
		{
			KetNoiLH knLH = new KetNoiLH();
			LopHoc a = new LopHoc();
			a = knLH.LayMaLH();
			MaLopHoc=a.getMaLH();

			int ViTriCuoi=MaLopHoc.lastIndexOf("T");
			String TenMa=MaLopHoc.substring(ViTriCuoi+1);
			int b = Integer.parseInt(TenMa);
			int c = b+1;
			String MaLH = String.format("LH%03d", c);
			return MaLH;
		}

		public void HienThiToanBoLH()
		{
			KetNoiLH knLH = new KetNoiLH();
			dsLH = knLH.LayToanBoLopHoc();
			dtmLopHoc.setRowCount(0);
			for(LopHoc a : dsLH)
			{
				Vector<Object> vec = new Vector<Object>();
				vec.add(a.getMaLH());
				vec.add(a.getTenLH());
				vec.add(a.getLoaiLH());
				vec.add(a.getMaCTH());
				vec.add(a.getSoBuoi());
				vec.add(a.getNgayBD());
				vec.add(a.getNgayKT());
				vec.add(a.getMaGV());
				dtmLopHoc.addRow(vec);
			}
		} 

		public void HienThiTim()
		{
			if(txtTim.getText().equals(""))
			{
				HienThiToanBoLH();
				JOptionPane.showMessageDialog(null, "Vui lòng nhập giáo trình cần tìm!!!");
			}
			else
			{
				String a1 = txtTim.getText();
				KetNoiLH knLH = new KetNoiLH();
				dsLHTim = knLH.TimLopHoc(a1);
				JOptionPane.showMessageDialog(null,a1);	
				dtmLopHoc.setRowCount(0);
				for(LopHoc a : dsLHTim)
				{
					Vector<Object> vec = new Vector<Object>();
					vec.add(a.getMaLH());
					vec.add(a.getTenLH());
					vec.add(a.getLoaiLH());
					vec.add(a.getMaCTH());
					vec.add(a.getSoBuoi());
					vec.add(a.getNgayBD());
					vec.add(a.getNgayKT());
					vec.add(a.getMaGV());
					dtmLopHoc.addRow(vec);
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
				return 1;
			}

			if(txtMaCH.getText().equals(""))
			{
				return 1;
			}
			
			if(txtngaybd.getText().equals(""))
			{
				return 1;
			}
			
			if(txtngaykt.getText().equals(""))
			{
				return 1;
			}
			
			
			if(txtMagv.getText().equals(""))
			{
				return 1;
			}
			
			if(txtSoBuoi.getText().equals(""))
			{
				return 1;
			}
			return 0;
		}

		public int KiemTraTonTai()
		{
			KetNoiLH knLH = new KetNoiLH();
			return knLH.KiemTraTonTai(txtTen.getText());
			 
		}

}
