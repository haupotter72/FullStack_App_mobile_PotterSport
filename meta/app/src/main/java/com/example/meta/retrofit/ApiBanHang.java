package com.example.meta.retrofit;


import com.example.meta.model.LoaiSpModel;
import com.example.meta.model.SanPhamMoi;
import com.example.meta.model.SanPhamMoiModel;
import com.example.meta.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiBanHang {

    String loaisanpham = "getloaisp.php";
    String getSpmoi = "getspmoi.php";
    String getSanPhamCt = "chitiet.php";
    String timKiemsp = "timkiem.php";

    @GET(loaisanpham)
    Observable<LoaiSpModel> getLoaiSp();


    @GET(getSpmoi)
    Observable<SanPhamMoiModel> getSpMoi();

    @GET(timKiemsp)
    Observable<SanPhamMoiModel> getSearch();


    @POST(getSanPhamCt)
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
            @Field("MaDM") int MaDM
    );

    @POST("listdonhangdd.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> listdonhangdd(
            @Field("MaND") int MaND
    );



    @POST("deletedonhangs.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> deletedonhangs(
            @Field("MaND") int MaND
    );

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangKi(
            @Field("Ten") String Ten,
            @Field("Email") String Email,
            @Field("sdt") String SDT,
            @Field("DiaChi") String DiaChi,
            @Field("TaiKhoan") String TaiKhoan,
            @Field("MatKhau") String MatKhau
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("TaiKhoan") String TaiKhoan,
            @Field("MatKhau") String MatKhau
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> search(
            @Field("search") String search
    );

    @POST("thanhtoan.php")
    @FormUrlEncoded
    Observable<UserModel> checkout(
            @Field("MaND") int MaND,
            @Field("NguoiNhan") String NguoiNhan,
            @Field("SDT") String SDT,
            @Field("DiaChi") String DiaChi,
            @Field("Email") String Email,
            @Field("TongTien") int TongTien,
            @Field("chitietdonhang") String chitietdonhang
    );



}

