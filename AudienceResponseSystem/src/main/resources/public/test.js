var stompClient = null;
const SITE_URL = "https://rhit-r90y2r8w"

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
    var socket = new SockJS(SITE_URL + '/message');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/1/topic/retrieveMessages', myFunc);
        stompClient.subscribe('/user/1/topic/retrievePanic', myFunc2);
    });
}

function myFunc(responseData) {
    console.log("Type: " + typeof responseData);
 //   console.log("V1");
  //  console.log(responseData);
 //   console.log("V2");
  //  console.log(responseData.json());
  //   console.log("V3");
  //   console.log(responseData.toJSON());
   // console.log("Test:" +  responseData.Status);
    console.log(responseData.body.Status);
    console.log(responseData.body.status);
    //console.log(responseData.body.messages.get("0"));
    //console.log(responseData.body.stringify.json());
    //console.log(responseData.body.parseJson());
    //console.log(typeof responseData.body);console.log(parseJSON());
    //console.log(responseData.body.json());
    console.log(JSON.parse(responseData.body));


}

function myFunc2(responseData) {
    console.log(responseData);
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
                id:  1
            },
            session: {
                id: 1
            },
            messageContent: "Austria"

        };
        console.log(data);
        fetch(SITE_URL + "/message/postComment", {
            method: 'POST',
            body: JSON.stringify({

                poster: {
                    id:  1
                },
                session: {
                    id: 1
                },
                messageContent: "Australia"


            }),
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            port: 443
        })
            .then((response) => {
                return response.json()
            })
            .then((data) => {
                console.log(data)
            })
}



function panic(code){



    fetch(SITE_URL + "/panic/postPanic", {
        method: 'POST',
        body: JSON.stringify({

            panicType: {
                panicType: "2FST"
            },
            panicker: {
                id: 84
            },
            session: {
                id: 1
            }



        }),
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        },
        port: 443
    })
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            console.log(data)
        });




}


/**
 * Get messages using Web sockets.
 */
function getMessages() {
    var myDate = {id: 1};
    var stringObj = JSON.stringify(myDate);

    stompClient.send("/app/getMessages", {}, stringObj);
}

function insertPanic() {
    var myDate = {id: 1};
    var stringObj = JSON.stringify(myDate);


}

// RUN THIS WHENEVER THE JAVASCRIPT FILE IS OPENED SO THAT IT AUTO CONNECTS
$(function () {
    connect();
});

