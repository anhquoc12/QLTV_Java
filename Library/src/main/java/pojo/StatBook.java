/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

/**
 *
 * @author dell
 */
public class StatBook {
    private String maSach, tenSach;
    private int soLuongMuon;
    
    public StatBook(String maSach, String tenSach, int soLuongMuon)
    {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soLuongMuon = soLuongMuon;
    }

    /**
     * @return the maSach
     */
    public String getMaSach() {
        return maSach;
    }

    /**
     * @param maSach the maSach to set
     */
    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    /**
     * @return the tenSach
     */
    public String getTenSach() {
        return tenSach;
    }

    /**
     * @param tenSach the tenSach to set
     */
    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    /**
     * @return the soLuongMuon
     */
    public int getSoLuongMuon() {
        return soLuongMuon;
    }

    /**
     * @param soLuongMuon the soLuongMuon to set
     */
    public void setSoLuongMuon(int soLuongMuon) {
        this.soLuongMuon = soLuongMuon;
    }

    /**
     * @return the maSach
     */
    
    
}
