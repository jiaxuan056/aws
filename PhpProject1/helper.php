<?php

define('DB_HOST', 'localhost');
define('DB_USER', 'jiaxuan');
define('DB_PASS', 'jiaxuan');
define('DB_NAME', 'cloud');

$conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>
