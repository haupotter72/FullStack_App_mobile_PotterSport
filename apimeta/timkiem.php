<?php
include "connect.php";
$search = $_POST['search'];

$query = "SELECT * FROM  `sanpham` WHERE `TenSP` LIKE '%" .$search. "%' OR `DonGia` LIKE '%" .$search. "%' ";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
		$result[] = ($row);
		// code...
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
