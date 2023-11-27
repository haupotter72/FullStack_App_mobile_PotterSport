<!-- <?php
    include("connect.php");
    date_default_timezone_set('Asia/Ho_Chi_Minh');
    $ThoiGian =  date('Y-m-d H:i:s');
    $chitietdonhang = $_POST['chitietdonhang'];
    $data = array(
        'MaND' => $_POST['MaND'],
        'NgayLap' => $ThoiGian,
        'NguoiNhan' => $_POST['NguoiNhan'],
        'Email' => $_POST['Email'],
        'SDT' => $_POST['SDT'],
        'DiaChi' => $_POST['DiaChi'],
        'TongTien' => $_POST['TongTien'],
        'TrangThai'  =>  '0',
    );
    $f = "";
    $v = "";
    foreach ($data as $key => $value) {
      $f .= $key . ",";
      $v .= "'" . $value . "',";
    }
    $f = trim($f, ",");
    $v = trim($v, ",");
    $query = "INSERT INTO HoaDon($f) VALUES ($v);";
    $status = mysqli_query($conn, $query);

    $query_mahd = "select MaHD from hoadon ORDER BY NgayLap DESC LIMIT 1";
    $data_mahd = mysqli_query($conn, $query_mahd)->fetch_assoc();

    $chitiet = json_decode($chitietdonhang,true);
    
    foreach ($chitiet as $key => $value) {
      $MaSP = $value['MaSP'];
      $SoLuong = $value['SoLuong'];
      $DonGia = $value['DonGia'];
      $Size = $value['Size'];
      $MaHD = $data_mahd['MaHD'];
      $query_ct = "INSERT INTO chitiethoadon(MaHD,MaSP,SoLuong,DonGia,Size) VALUES ($MaHD,$MaSP,$SoLuong,$DonGia,$Size)";
      $status_ct = mysqli_query($conn, $query_ct);

    }


    if ($status == true && $status_ct == true) {
        $arr = [
            'success' => true,
            'message' => "Đơn hàng của bạn đặt thành công. Cảm ơn đơn hàng của bạn đang xét duyệt!",
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => "Đặt đơn hàng thất bại!",
        ];
    }

?> -->
<?php
    include("connect.php");
    date_default_timezone_set('Asia/Ho_Chi_Minh');
    $ThoiGian =  date('Y-m-d H:i:s');
    $chitietdonhang = $_POST['chitietdonhang'];
    $data = array(
        'MaND' => mysqli_real_escape_string($conn, $_POST['MaND']),
        'NgayLap' => $ThoiGian,
        'NguoiNhan' => mysqli_real_escape_string($conn, $_POST['NguoiNhan']),
        'Email' => mysqli_real_escape_string($conn, $_POST['Email']),
        'SDT' => mysqli_real_escape_string($conn, $_POST['SDT']),
        'DiaChi' => mysqli_real_escape_string($conn, $_POST['DiaChi']),
        'TongTien' => mysqli_real_escape_string($conn, $_POST['TongTien']),
        'TrangThai'  =>  '0',
    );
    $f = "";
    $v = "";
    foreach ($data as $key => $value) {
      $f .= $key . ",";
      $v .= "'" . $value . "',";
    }
    $f = trim($f, ",");
    $v = trim($v, ",");
    $query = "INSERT INTO HoaDon($f) VALUES ($v);";
    $status = mysqli_query($conn, $query);

    if ($status == true) {
        $MaHD = mysqli_insert_id($conn);
        $chitiet = json_decode($chitietdonhang, true);

        foreach ($chitiet as $key => $value) {
            $MaSP = mysqli_real_escape_string($conn, $value['MaSP']);
            $SoLuong = mysqli_real_escape_string($conn, $value['SoLuong']);
            $DonGia = mysqli_real_escape_string($conn, $value['DonGia']);
            $Size = mysqli_real_escape_string($conn, $value['Size']);

            $query_ct = "INSERT INTO chitiethoadon(MaHD,MaSP,SoLuong,DonGia,Size) 
                          VALUES ($MaHD,$MaSP,$SoLuong,$DonGia,$Size)";
            $status_ct = mysqli_query($conn, $query_ct);
        }

        if ($status_ct == true) {
            $arr = [
                'success' => true,
                'message' => "Đơn hàng của bạn đặt thành công. Cảm ơn đơn hàng của bạn đang xét duyệt!",
            ];
        } else {
            $arr = [
                'success' => false,
                'message' => "Đặt đơn hàng thất bại!",
            ];
        }
    } else {
        $arr = [
            'success' => false,
            'message' => "Đặt đơn hàng thất bại!",
        ];
    }
?>
