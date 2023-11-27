<?php
include "connect.php";

$MaND = $_POST['MaND'];

$checkmahd = "SELECT * FROM hoadon WHERE MaND = $MaND ORDER BY MaHD DESC LIMIT 1";
$data_mahd = mysqli_query($conn,$checkmahd)->fetch_assoc();

$MaHD = $data_mahd['MaHD'];
$query = "DELETE FROM `hoadon` WHERE MaHD = $MaHD AND MaND = $MaND";
$data = mysqli_query($conn,$query);

