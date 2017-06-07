<?php
    require("password.php");

    $con = mysqli_connect("localhost", "id1886732_student", "student", "id1886732_icarte");
    
    $username = $_POST["username"];
    $password = $_POST["password"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM user WHERE username = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $username, $password);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $user_id, $first_name, $family_name, $email, $address, $username, $password, $phone);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
            $response["success"] = true;
    }

    echo json_encode($response);
?>