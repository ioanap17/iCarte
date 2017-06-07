<?php
    $con = mysqli_connect("localhost", "id1886732_student", "student", "id1886732_icarte");
    
    $first_name = $_POST["first_name"];
	$family_name = $_POST["family_name"];
    $email = $_POST["email"];
	$address = $_POST["address"];
    $username = $_POST["username"];
    $password = $_POST["password"];
	$phone = $_POST["phone"];

    $statement = mysqli_prepare($con, "INSERT INTO user (first_name, family_name, email, address, username, password, phone) VALUES (?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sssssss", $first_name, $family_name, $email, $address, $username, $password, $phone);
    mysqli_stmt_execute($statement);
	
	if (mysqli_connect_errno())
    {
       echo "Failed to connect to MySQL: ".mysqli_connect_error();
    }
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>