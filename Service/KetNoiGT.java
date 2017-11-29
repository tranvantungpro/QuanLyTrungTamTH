package Service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Moudle.GiaoTrinh;

public class KetNoiGT extends KetNoiSQL
{
	PreparedStatement preStatement=null;
	ResultSet result=null;
	
	public ArrayList<GiaoTrinh> LayToanBoGiaoTrinh()
	{
		ArrayList<GiaoTrinh> dsGT= new ArrayList<GiaoTrinh>();
		try 
		{
			String sql ="select * from GiaoTrinh ORDER BY MaGT ASC";
			preStatement=conn.prepareStatement(sql);
			result=preStatement.executeQuery();
			while(result.next())
			{
				GiaoTrinh gt = new GiaoTrinh();
				gt.setMaGT(result.getString(1));
				gt.setTenGT(result.getString(2));
				gt.setLoaiGT(result.getString(3));
				gt.setNXB(result.getString(4));
				dsGT.add(gt);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return dsGT;
	}
	
	public ArrayList<GiaoTrinh> TimGiaoTrinh(String tenGT)
	{
		ArrayList<GiaoTrinh> dsGTTim= new ArrayList<GiaoTrinh>();
		try
		{
			String sql= "select * from GiaoTrinh where TenGT like concat('%', ?, '%') collate sql_latin1_general_cp1_ci_as";
			//JOptionPane.showMessageDialog(null, sql);
			preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, tenGT);
			result=preStatement.executeQuery();
			while(result.next())
			{
				GiaoTrinh gt = new GiaoTrinh();
				gt.setMaGT(result.getString(1));
				gt.setTenGT(result.getString(2));
				gt.setLoaiGT(result.getString(3));
				gt.setNXB(result.getString(4));
				dsGTTim.add(gt);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dsGTTim;
	}
	
	public int  ThemMoiGiaoTrinh(GiaoTrinh gt)
	{
		try
		{
			String sql= "insert into GiaoTrinh VALUES (?,?,?,?)";
			preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, gt.getMaGT());
			preStatement.setString(2, gt.getTenGT());
			preStatement.setString(3, gt.getLoaiGT());
			preStatement.setString(4, gt.getNXB());
			return preStatement.executeUpdate();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public int  XoaGiaoTrinh(GiaoTrinh gt)
	{
		try
		{
			String sql= "delete from GiaoTrinh where MaGT=? ";
			preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, gt.getMaGT());
			return preStatement.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public int  CapNhatGiaoTrinh(GiaoTrinh gt)
	{
		try
		{
			String sql= "update GiaoTrinh set TenGT=?, LoaiGT=?, NXB=? where MaGT=? ";
			preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, gt.getTenGT());
			preStatement.setString(2, gt.getLoaiGT());
			preStatement.setString(3, gt.getNXB());
			preStatement.setString(4, gt.getMaGT());
			return preStatement.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public GiaoTrinh LayMaGT()
	{
		try 
		{
			String sql = "Select TOP 1 MaGT from GiaoTrinh ORDER BY MaGT DESC";
			preStatement = conn.prepareStatement(sql);
			result=preStatement.executeQuery();
			while(result.next())
			{
				GiaoTrinh gt = new GiaoTrinh();
				gt.setMaGT(result.getString(1));
				return gt;
			}
		}
		catch (Exception e) 
		{
			  
		}
		return null;
	}
	
	public int KiemTraTonTai(String TenGT)
	{
		try 
		{
			String sql= "select * from GiaoTrinh where TenGT =?";
			preStatement=conn.prepareStatement(sql);
			preStatement.setString(1, TenGT);
			result=preStatement.executeQuery();
			while(result.next())
			{
				return 1;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return 0;
	}
}
