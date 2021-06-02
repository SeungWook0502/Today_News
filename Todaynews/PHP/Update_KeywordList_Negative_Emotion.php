<?php
  $con = mysqli_connect("localhost", "todaynews", "coding1998!", "todaynews");
    mysqli_query($con,'SET NAMES utf8');

    if (mysqli_connect_errno($con)) {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }

    $Keyword_URL = $_GET['Keyword_URL'];

    $query = "SET SQL_SAFE_UPDATES = 0";
    mysqli_query($con, $query);
    $query = "UPDATE Keyword_List SET Keyword_Emotion = 0 WHERE Keyword_URL =  $Keyword_URL";
    $result = mysqli_query($con, $query);
 
    echo json_encode($result);
   
    mysqli_close($con);
?>