

<?php

session_start();
include "connect.php";
if(isset($_POST['TaiKhoan']) && isset($_POST['MatKhau'])){
    $TaiKhoan = mysqli_real_escape_string($conn, $_POST['TaiKhoan']);
    $MatKhau = mysqli_real_escape_string($conn, md5($_POST['MatKhau']));

    $query = "SELECT * FROM `nguoidung` WHERE TaiKhoan = '" . $TaiKhoan . "' AND MatKhau = '" . $MatKhau . "' AND trangthai = 1";
    // $query = "SELECT * FROM `nguoidung` WHERE `TaiKhoan`='".$TaiKhoan."' AND `MatKhau`='".$MatKhau."' AND `trangthai`=1";
    $data = mysqli_query($conn, $query);

    if($data){
        $result = array();
        while($row = mysqli_fetch_assoc($data)){
            $result[] = $row;
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
    }else{
        $arr = [
            'success' => false,
            'message' => "Loi truy van",
            'result' => []
        ];
    }
}else{
    $arr = [
        'success' => false,
        'message' => "vui long nhap ten nguoi dung va mat khau",
        'result' => []
    ];
}

print_r(json_encode($arr));

?>
