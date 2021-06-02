<?php
   $con = mysqli_connect("localhost", "todaynews", "coding1998!", "todaynews");
    mysqli_query($con,'SET NAMES utf8');

    if (mysqli_connect_errno($con)) {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }

    $Keyword_Word = $_GET['Keyword_Word'];

    $Keyword_Sidnum1 = $_GET['Keyword_Sidnum1'];
    $Keyword_Sidnum2 = $_GET['Keyword_Sidnum2'];
    $Keyword_Sidnum3 = $_GET['Keyword_Sidnum3'];
    $Keyword_Sidnum4 = $_GET['Keyword_Sidnum4'];
    $Keyword_Sidnum5 = $_GET['Keyword_Sidnum5'];
    $Keyword_Sidnum6 = $_GET['Keyword_Sidnum6'];

    $sql = "SELECT Keyword_List.Keyword_Sidnum, Keyword_List.Keyword_URL, Article.Article_Title, Article.Article_Content,Article.Article_Emotion FROM Keyword_List INNER JOIN Article ON Keyword_List.Keyword_URL = Article.Article_URL WHERE Keyword_Word= $Keyword_Word AND (Keyword_List.Keyword_Sidnum=$Keyword_Sidnum1 OR Keyword_List.Keyword_Sidnum=$Keyword_Sidnum2 OR Keyword_List.Keyword_Sidnum=$Keyword_Sidnum3 OR Keyword_List.Keyword_Sidnum=$Keyword_Sidnum4 OR Keyword_List.Keyword_Sidnum=$Keyword_Sidnum5 OR Keyword_List.Keyword_Sidnum=$Keyword_Sidnum6) AND Article.Article_Emotion IS NOT NULL";
    $result = mysqli_query($con, $sql);

    $data = array();
    
    if ($result) {
        while ($row=mysqli_fetch_array($result)) {
            array_push($data,array('Keyword_Sidnum'=>$row[0], 'Keyword_URL'=>$row[1], 'Article_Title'=>$row[2], 'Article_Content'=>$row[3], 'Article_Emotion'=>$row[4]));
        }
        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("Article_List"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

		echo $json; 

	} else {
        echo "SQL문 처리중 에러 발생 : ";
        echo mysqli_error($con);
    }

    mysqli_close($con);
?>