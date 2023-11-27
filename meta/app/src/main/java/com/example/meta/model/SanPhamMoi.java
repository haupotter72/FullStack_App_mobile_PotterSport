package com.example.meta.model;

import java.io.Serializable;

public class SanPhamMoi implements Serializable {
    int MaSP;
    int MaLSP;
    int MaDM;
    String TenDM;
    String TenSP;
    String DonGia;
    String TenLSP;
    String KieuDang;
    String NgayPH;
    int SoLuong;
    String HinhAnh1, HinhAnh2, HinhAnh3;
    String MoTa;
    String tamp="http://192.168.1.7/n8.com/public/images/products/";
    String HinhAnh,TenSanPham;


    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }


    public int getMaLSP() {
        return MaLSP;
    }

    public void setMaLSP(int maLSP) {
        MaLSP = maLSP;
    }

    public int getMaDM() {
        return MaDM;
    }

    public void setMaDM(int maDM) {
        MaDM = maDM;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public String getDonGia() {

        return DonGia;
    }
    public void setDonGia(String donGia) {
        DonGia = donGia;
    }

    public int getSoLuong() {
        return SoLuong;
    }
    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getHinhAnh1() {
        HinhAnh = tamp+HinhAnh1;
        return HinhAnh;
    }
    public void setHinhAnh1(String hinhAnh1) {
        HinhAnh1 = hinhAnh1;
    }

    public String getHinhAnh2() {
        return tamp+HinhAnh2;
    }

    public void setHinhAnh2(String hinhAnh2) {
        HinhAnh2 = hinhAnh2;
    }

    public String getHinhAnh3() {
        return tamp+HinhAnh3;
    }

    public void setHinhAnh3(String hinhAnh3) {
        HinhAnh3 = hinhAnh3;
    }


    public String getTenDM() {
        return TenDM;
    }
    public void setTenDM(String tenDM){
        TenDM = tenDM;
    }

    public  String getNgayPH() {
        return NgayPH;
    }
    public void setNgayPH(String ngayPH) {
        NgayPH = ngayPH;
    }

    public String getKieuDang(){
     return KieuDang;
    }
    public void setKieuDang(String kieuDang){
        KieuDang = kieuDang;
    }

    public String getTenLSP() {
        return TenLSP;
    }
    public void setTenLSP(String tenLSP) {
        TenLSP = tenLSP;
    }

}

