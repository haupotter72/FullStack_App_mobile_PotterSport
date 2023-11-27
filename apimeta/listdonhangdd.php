<?php
include "connect.php";
$MaND = $_POST['MaND'];

$checkmahd = "SELECT * FROM hoadon WHERE MaND = $MaND ORDER BY MaHD DESC LIMIT 1";
$data_mahd = mysqli_query($conn,$checkmahd)->fetch_assoc();

$MaHD = $data_mahd['MaHD'];
$query = "select ct.*, sp.*, hd.TrangThai as tt from chitiethoadon as ct, sanpham as sp, hoadon as hd  where ct.MaSP = sp.MaSP and hd.MaHD = ct.MaHD AND hd.MaHD = $MaHD";

$data = mysqli_query($conn, $query);
$result = array();
while($row = mysqli_fetch_assoc($data)){
    $result[] = ($row);
}
if(!empty($result)){
    $arr = [
        'success' => true,
        'message' => "thanhcong",
        'result' => $result
    ];
}else{
    $arr = [
        'success' => false,
        'message' => "khongthanhcong",
        'result' => $result
    ];
}

print_r(json_encode($arr));
?>