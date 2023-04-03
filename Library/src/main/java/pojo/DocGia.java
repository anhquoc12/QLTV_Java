/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

import java.sql.Date;

/**
 *
 * @author dell
 */
public class DocGia {
    private String maDocGia;
    private String tenDocGia;
    public enum Gender
    {
        NAM,
        NU
    }
    private Gender gioiTinh;
    private Date ngaySinh;
    public enum Object
    {
        STUDENT,
        TEACHER,
        EMPLOYEE
    }
    private Object doiTuong;
    private Date ngayLapThe;
    private String SoDT;
    private String diaChi;
    private String boPhan;
    private String email;

    public DocGia() {
    }

    public DocGia(String maDocGia, String tenDocGia, Gender gioiTinh, Date ngaySinh, Object doiTuong, Date ngayLapthe, String SoDT, String diaChi, String boPhan, String email) {
        this.maDocGia = maDocGia;
        this.tenDocGia = tenDocGia;
        this.ngaySinh = ngaySinh;
        this.doiTuong = doiTuong;
        this.ngayLapThe = ngayLapthe;
        this.SoDT = SoDT;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.boPhan = boPhan;
        this.email = email;
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
     * @return the gioiTinh
     */
    public Gender getGioiTinh() {
        return gioiTinh;
    }

    /**
     * @param gioiTinh the gioiTinh to set
     */
    public void setGioiTinh(Gender gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    /**
     * @return the ngaySinh
     */
    public Date getNgaySinh() {
        return ngaySinh;
    }

    /**
     * @param ngaySinh the ngaySinh to set
     */
    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    /**
     * @return the doiTuong
     */
    public Object getDoiTuong() {
        return doiTuong;
    }

    /**
     * @param doiTuong the doiTuong to set
     */
    public void setDoiTuong(Object doiTuong) {
        this.doiTuong = doiTuong;
    }

    /**
     * @return the ngayLapThe
     */
    public Date getNgayLapThe() {
        return ngayLapThe;
    }

    /**
     * @param ngayLapThe the ngayLapThe to set
     */
    public void setNgayLapThe(Date ngayLapThe) {
        this.ngayLapThe = ngayLapThe;
    }

    /**
     * @return the SoDT
     */
    public String getSoDT() {
        return SoDT;
    }

    /**
     * @param SoDT the SoDT to set
     */
    public void setSoDT(String SoDT) {
        this.SoDT = SoDT;
    }

    /**
     * @return the diaChi
     */
    public String getDiaChi() {
        return diaChi;
    }

    /**
     * @param diaChi the diaChi to set
     */
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    /**
     * @return the boPhan
     */
    public String getBoPhan() {
        return boPhan;
    }

    /**
     * @param boPhan the boPhan to set
     */
    public void setBoPhan(String boPhan) {
        this.boPhan = boPhan;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
