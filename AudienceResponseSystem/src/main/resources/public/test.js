var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('http://localhost:8080/myTest');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/webMessage', function (greeting) {
            console.log(greeting);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


function postComment() {
    var myDate = {messageContent: "Good morning", poster: {id: "5"}, session: { id: "2"}};
    var stringObj = JSON.stringify(myDate);

    stompClient.send("/app/postComment", {}, stringObj);
}

function getMessages() {
    var myDate = {id: 2};
    var stringObj = JSON.stringify(myDate);

    stompClient.send("/app/getMessages", {}, stringObj);
}

$(function () {
    connect();
});

