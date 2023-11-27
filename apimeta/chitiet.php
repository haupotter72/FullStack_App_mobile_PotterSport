<?php
include "connect.php";
$MaDM = $_POST['MaDM'];
// $query = "SELECT * FROM `sanpham` WHERE `MaDM` = 2";
$query = "SELECT * FROM `sanpham` WHERE `MaDM` = $MaDM";
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