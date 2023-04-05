/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

/**
 *
 * @author dell
 */
public class StatDocGia {
    private String maDocGia, tenDocGia;
    private int soLanMuon;
    
    public StatDocGia(String maDocGia, String tenDocGia, int soLanMuon)
    {
        this.maDocGia = maDocGia;
        this.tenDocGia = tenDocGia;
        this.soLanMuon = soLanMuon;
    }

    /**
     * @return the maDocGia
     */
    public String getMaDocGia() {
        return maDocGia;
    }

    /**
     * @param maDocGia the maDocGia to set
     */
    public void setMaDocGia(String maDocGia) {
        this.maDocGia = maDocGia;
    }

    /**
     * @return the tenDocGia
     */
    public String getTenDocGia() {
        return tenDocGia;
    }

    /**
     * @param tenDocGia the tenDocGia to set
     */
    public void setTenDocGia(String tenDocGia) {
        this.tenDocGia = tenDocGia;
    }

    /**
     * @return the soLanMuon
     */
    public int getSoLanMuon() {
        return soLanMuon;
    }

    /**
     * @param soLanMuon the soLanMuon to set
     */
    public void setSoLanMuon(int soLanMuon) {
        this.soLanMuon = soLanMuon;
    }
    
}
