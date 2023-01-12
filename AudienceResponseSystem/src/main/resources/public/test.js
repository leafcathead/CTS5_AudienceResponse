var stompClient = null;

// THIS IS NOT IMPORTANT
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
    var socket = new SockJS('http://localhost:8080/message');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/2/topic/retrieveMessages', function (greeting) { // INSTEAD OF '1' PUT THE CURRENT SESSION ID!
            console.log(greeting); // Greeting is the response back from the server, so just place all the Get Message code inside HERE!
                                   // it will execute when this returns.
        });
        stompClient.subscribe('/user/2/topic/retrievePanic', function (panic) {
            console.log(panic);
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

/**
 * Works with Restful API too, just use websocket for the get messages.
 */
function postComment() {

        const data = {

            poster: {
                id:  5
            },
            session: {
                id: 2
            },
            messageContent: "Austria"

        };
        console.log(data);
        fetch("http://localhost:8080/message/postComment", {
            method: 'POST',
            body: JSON.stringify({

                poster: {
                    id:  5
                },
                session: {
                    id: 2
                },
                messageContent: "Australia"


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

/**
 * Get messages using Web sockets.
 */
function getMessages() {
    var myDate = {id: 2};
    var stringObj = JSON.stringify(myDate);

    stompClient.send("/app/getMessages", {}, stringObj);
}

function insertPanic() {
    var myDate = {id: 2};
    var stringObj = JSON.stringify(myDate);


}

// RUN THIS WHENEVER THE JAVASCRIPT FILE IS OPENED SO THAT IT AUTO CONNECTS
$(function () {
    connect();
});

