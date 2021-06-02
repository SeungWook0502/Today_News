<?php
  $connect = mysqli_connect("localhost", "todaynews", "coding1998!", "todaynews");
    mysqli_query($connect,'SET NAMES utf8');

    if (mysqli_connect_errno($connect)) {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
    $Article_URL = $_GET['Article_URL'];

    $query = "SET SQL_SAFE_UPDATES = 0";
    mysqli_query($connect, $query);

    $query = "UPDATE Article SET Article_Emotion = 1 WHERE Article_URL = $Article_URL";
    $result = mysqli_query($connect, $query);
     
    echo json_encode($result);
   
    mysqli_close($connect);

?>