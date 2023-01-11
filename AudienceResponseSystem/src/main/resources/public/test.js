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
        stompClient.subscribe('/user/1/topic/retrieveMessages', function (greeting) {
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

/**
function postComment() {
    var myDate = {messageContent: "Good morning", poster: {id: "1"}, session: { id: "1"}};
    var stringObj = JSON.stringify(myDate);

    stompClient.send("/app/postComment", {}, stringObj);
}
**/

function postComment() {

        const data = {

            poster: {
                id:  1
            },
            session: {
                id: 1
            },
            messageContent: "Good night"

        };
        console.log(data);
        fetch("http://localhost:8080/message/postComment", {
            method: 'POST',
            body: JSON.stringify({

                poster: {
                    id:  1
                },
                session: {
                    id: 1
                },
                messageContent: "Good night"


            }),
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            }
        })
            .then((response) => {
                return response.json()
            })
            .then((data) => {
                console.log(data)
            })
}

function getMessages() {
    var myDate = {id: 1};
    var stringObj = JSON.stringify(myDate);

    stompClient.send("/app/getMessages", {}, stringObj);
}

$(function () {
    connect();
});

