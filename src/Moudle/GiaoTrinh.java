package Moudle;

public class GiaoTrinh 
{
	private String MaGT;
	private String TenGT;
	private String LoaiGT;
	private String NXB;
	public String getMaGT() {
		return MaGT;
	}
	public void setMaGT(String maGT) {
		MaGT = maGT;
	}
	public String getTenGT() {
		return TenGT;
	}
	public void setTenGT(String tenGT) {
		TenGT = tenGT;
	}
	public String getLoaiGT() {
		return LoaiGT;
	}
	public void setLoaiGT(String loaiGT) {
		LoaiGT = loaiGT;
	}
	public String getNXB() {
		return NXB;
	}
	public void setNXB(String nXB) {
		NXB = nXB;
	}
	
	@Override
	public String toString() {
		return this.TenGT;
	}
}
