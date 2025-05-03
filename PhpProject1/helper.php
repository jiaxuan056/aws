<?php

define('DB_HOST', 'labdb.cvkhzwxnsb9m.us-east-1.rds.amazonaws.com');
define('DB_USER', 'jiaxuan');
define('DB_PASS', 'a3014402');
define('DB_NAME', 'lab'); 

$conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>

<link rel="icon" href="https://jiaxuanbucket.s3.us-east-1.amazonaws.com/logo.jpg">
