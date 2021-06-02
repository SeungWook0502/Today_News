<?php
   $con = mysqli_connect("localhost", "todaynews", "coding1998!", "todaynews");
    mysqli_query($con,'SET NAMES utf8');

    if (mysqli_connect_errno($con)) {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }

    $sql = "SELECT * FROM `Keyword_Rank` ORDER BY `Keyword_Count` DESC LIMIT 10";
    $result = mysqli_query($con, $sql);

    $data = array();
    
    if ($result) {
        while ($row=mysqli_fetch_array($result)) {
            array_push($data,array('Keyword_Word'=>$row[0]));
        }
        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("Keyword_Rank"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

		echo $json; 

	} else {
        echo "SQL문 처리중 에러 발생 : ";
        echo mysqli_error($con);
    }

    mysqli_close($con);
?>