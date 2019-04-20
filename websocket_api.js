
stompClient.subscribe('/topic/add?' + "tok", function (res){
        //status code: OK(202),FAIL(400)
        console.log(res);
});
stompClient.subscribe('/topic/join?' + "tok", function (res){
        //status code: Black(220), White(230),
        //black first hand
        console.log(res);
});
    
stompClient.subscribe('/topic/update?' + "tok" , function (res){
            //if has status code: OK(202), WIN(211), LOSE(212)
            //else: no status means a update move from opponent
            console.log(res);
});

stompClient.send("/app/addToQueue", {}, JSON.stringify({'username': "tok" }));


var move = {"username":"tok","x": 1,"y": 2};
    
stompClient.send("/app/putPiece", {}, JSON.stringify(move));


// status code
//OK(202), WIN(211), LOSE(212), DRAW(213), Black(220), White(230), FAIL(400);

