<?php

// Database connection details
define('DB_HOST', 'labdb.cvkhzwxnsb9m.us-east-1.rds.amazonaws.com');
define('DB_USER', 'jiaxuan');
define('DB_PASS', 'a3014402');
define('DB_NAME', 'cloud');

// Create connection
$conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Query to fetch all products
$sql = "SELECT * FROM product";

// Execute query
$result = $conn->query($sql);

// Check if query returned any results
if ($result->num_rows > 0) {
    // Output data of each row
    echo "<table border='1'>
            <tr>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Category</th>
                <th>Description</th>
                <th>Price</th>
                <th>Stock Quantity</th>
                <th>Image URL</th>
            </tr>";

    while($row = $result->fetch_assoc()) {
        echo "<tr>
                <td>" . $row['product_id'] . "</td>
                <td>" . $row['product_name'] . "</td>
                <td>" . $row['category'] . "</td>
                <td>" . $row['description'] . "</td>
                <td>" . $row['price'] . "</td>
                <td>" . $row['stock_quantity'] . "</td>
                <td><img src='" . $row['image_url'] . "' alt='Image' width='100'></td>
            </tr>";
    }

    echo "</table>";
} else {
    echo "No products found.";
}

// Close connection
$conn->close();
?>
