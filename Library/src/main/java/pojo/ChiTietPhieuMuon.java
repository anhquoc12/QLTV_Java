/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;
import java.util.UUID;
/**
 *
 * @author dell
 */
public class ChiTietPhieuMuon {
    private String maCTPM;
    private String maPhieuMuon;
    private String maSach;
    
    {
        maCTPM = UUID.randomUUID().toString();
    }

    public ChiTietPhieuMuon() {
    }

    public ChiTietPhieuMuon(String maCTPM, String maPhieuMuon, String maSach) {
        this.maCTPM = maCTPM;
        this.maPhieuMuon = maPhieuMuon;
        this.maSach = maSach;
    }
    public ChiTietPhieuMuon(String maPhieuMuon, String maSach) {
        this.maPhieuMuon = maPhieuMuon;
        this.maSach = maSach;
    }

    public void setMaCTPM(String maCTPM) {
        this.maCTPM = maCTPM;
    }

    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaCTPM() {
        return maCTPM;
    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public String getMaSach() {
        return maSach;
    }
    
    @Override
    public String toString() {
        return this.maCTPM;
    }
}
