package com.example.meta.model;

public class LoaiSp {
    int MaDM;
    String TenDM;
    String AnhLogo;

    public LoaiSp(String tenDM, String anhLogo) {
        TenDM = tenDM;
        AnhLogo = anhLogo;
    }

    public int getId() {
        return MaDM;
    }

    public void setId(int MaDM) {
        this.MaDM = MaDM;
    }

    public String getTensanpham() {
        return TenDM;
    }

    public void setTensanpham(String TenDM) {
        this.TenDM = TenDM;
    }

    public String getAnhLogo() {
        return AnhLogo;
    }

    public void setAnhLogo(String AnhLogo) {
        this.AnhLogo = AnhLogo;
    }
}
