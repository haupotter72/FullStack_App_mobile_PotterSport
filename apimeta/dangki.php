<?php
include "connect.php";

$Ho =  "";
$Ten  =   mysqli_real_escape_string($conn, $_POST['Ten']);
$GioiTinh = "";
$SDT = "";
$Email =  mysqli_real_escape_string($conn, $_POST['Email']);
$DiaChi  =   "";

$TaiKhoan = mysqli_real_escape_string($conn, $_POST['TaiKhoan']);
$MatKhau = mysqli_real_escape_string($conn, md5($_POST['MatKhau']));
$MaQuyen =  1;
$TrangThai  =  1;

$query_check = "SELECT * FROM `nguoidung` WHERE `TaiKhoan` = '" . $TaiKhoan . "'";
$data_check = mysqli_query($conn, $query_check);
$numrow = mysqli_num_rows($data_check);

if ($numrow > 0) {
    $arr = [
        'success' => false,
        'message' => "Tài khoản đã tồn tại",
    ];
} else {
    $query_insert = "INSERT INTO `nguoidung`( `Ho`, `Ten`, `GioiTinh`, `SDT`, `Email`, `DiaChi`, `TaiKhoan`, `MatKhau`, `MaQuyen`, `TrangThai`) 
                    VALUES ('" . $Ho . "','" . $Ten . "','" . $GioiTinh . "','" . $SDT . "','" . $Email . "','" . $DiaChi . "','" . $TaiKhoan . "','" . $MatKhau . "','" . $MaQuyen . "','" . $TrangThai . "')";
    $data_insert = mysqli_query($conn, $query_insert);

    if ($data_insert) {
        $arr = [
            'success' => true,
            'message' => "Thành công",
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => "Thất bại",
        ];
    }
}
print_r($arr);
?>
