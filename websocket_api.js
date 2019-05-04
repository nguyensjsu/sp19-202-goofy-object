
stompClient.subscribe('/topic/added?' + "tok", function (res){
        //status code: OK(202),FAIL(400)
        console.log(res);
});
stompClient.subscribe('/topic/join?' + "tok", function (res){
        //status code: Black(220), White(230),FAIL(400)
        //black first hand
        console.log(res);
});
    
stompClient.subscribe('/topic/update?' + "tok" , function (res){
            //status code: OK(202), WIN(211), LOSE(212), DRAW(213), LEAVE(214)
            console.log(res);
});

stompClient.send("/app/addToQueue", {}, JSON.stringify({'username': "tok" })); 
stompClient.send("/app/createAiGame/{level}", {}, JSON.stringify({'username': "tok" })); // single mode



var move = {"username":"tok","x": 1,"y": 2};
    
stompClient.send("/app/putPiece", {}, JSON.stringify(move));


// status code
//OK(202), WIN(211), LOSE(212), DRAW(213), Black(220), White(230), FAIL(400);

