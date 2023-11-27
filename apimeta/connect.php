<?php
$host = "localhost";
$user = "root";
$pass = "";
$database = "n8sport";

$conn = mysqli_connect($host, $user, $pass, $database);
mysqli_set_charset($conn, "utf8mb4");


if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
?>
