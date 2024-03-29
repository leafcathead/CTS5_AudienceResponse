/**
 * ARS 2022 - 2023
 * Author: Amer Aljamous
 * THU ULM
 * */

const ownerID = localStorage.getItem('ownerID');
const sessionID = localStorage.getItem('sessionID');
const sessionPassword = localStorage.getItem('sessionPassword');
 const SITE_URL = "https://i-lv-sopr-01.informatik.hs-ulm.de";
//   const SITE_URL = "https://rhit-r90y2r8w";
//const SITE_URL = "https://DESKTOP-FUO6UAL";
//const SITE_URL = "https://localhost";
let displayname = localStorage.getItem('displayname');
var token = "";


//post comment
function postComment() {

    if ($("#textArea").val() != "") {
        let answer = document.getElementById('answer');
        const data = {

            poster: {
                id: ownerID
            },
            session: {
                id: sessionID
            },
            messageContent: $("#textArea").val()

        };

        fetch(SITE_URL + "/message/postComment", {
            method: 'POST',
            body: JSON.stringify({

                poster: {
                    id: ownerID
                },
                session: {
                    id: sessionID
                },
                messageContent: $("#textArea").val()


            }),
            headers: {
                "Content-Type": "application/json;charset=UTF-8",
                'X-CSRF-TOKEN': token
            },
            port: 443
        })
            .then((response) => {
                return response.json()
            })
            .then((data) => {

                $("#textArea").val("");
            })
    } else {
        showResultTextArea();
    }

}


//post reply
function postReply(posterID = $("#RposterID").val(), sessionID = $("#RsessionID").val(), msgID = $("#RmsgID").val(), msgContent = $("#RmsgContent").val()) {
    console.log(posterID, sessionID, msgID, msgContent);


    fetch(SITE_URL + "/message/postReply", {
        method: 'POST',
        body: JSON.stringify({


            poster: {
                id: posterID
            },
            session: {
                id: sessionID
            },
            replyTo: {
                id: msgID
            },
            messageContent: msgContent

        }),
        headers: {
            "Content-Type": "application/json;charset=UTF-8",
            'X-CSRF-TOKEN': token
        },
        port: 443
    })
        .then((response) => {
            return response.json()
        })
        .then((data) => {

        })

    showResult();

}




/**
 * webSocket
 */
var stompClient = null;


function connect(options) {


    var socket = new SockJS(SITE_URL + "/message");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);




        let urlMessage = "/user/"+sessionID+"/topic/retrieveMessages";
        stompClient.subscribe(urlMessage, getPosts);

        let urlPanic ="/user/"+sessionID+"/topic/retrievePanic";
        stompClient.subscribe(urlPanic, panic);

        let urlSession = "/user/"+sessionID+"/topic/sessionClosed";
        stompClient.subscribe(urlSession, checkSessionStatus);

        var myDate = {id: sessionID};
        var stringObj = JSON.stringify(myDate);
        stompClient.send("/app/getMessages", {}, stringObj);


    });
}


function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


//get posts from DB WebSocket(Recommended way)receivedJson
function getPosts(responseData) {

    let receivedJson = JSON.parse(responseData.body);
    let comments = [];
    let allUsers = 0;
    let body = "";
    $("#cardDiv").html("");
    //  body = $("#cardDiv").html();


            //pulling data from Json server side file and pushing the comments inside well-ordered js array[]
    for (let i = 0; i < Object.keys(receivedJson.Messages).length; i++) {


        for (let k = 0; k < Object.keys(receivedJson.Messages).length; k++) {

            if (receivedJson.Messages[i].poster.id == receivedJson.Messages[k].poster) {

                comments.push({
                    posterID: receivedJson.Messages[k].poster,
                    displayName: receivedJson.Messages[i].poster.displayName,
                    sessionID: receivedJson.Messages[k].session,
                    msgID: receivedJson.Messages[k].id,
                    timestamp: receivedJson.Messages[k].timestamp,
                    msgContents: receivedJson.Messages[k].messageContents,
                    replyTo: receivedJson.Messages[k].replyTo,
                    visible: receivedJson.Messages[k].visible,
                    likes: receivedJson.Messages[k].likes
                });

            }
        } //end of k loop
        if (receivedJson.Messages[i].poster.id) {


            comments.push({
                posterID: receivedJson.Messages[i].poster.id,
                displayName: receivedJson.Messages[i].poster.displayName,
                sessionID: receivedJson.Messages[i].session,
                msgID: receivedJson.Messages[i].id,
                timestamp: receivedJson.Messages[i].timestamp,
                msgContents: receivedJson.Messages[i].messageContents,
                replyTo: receivedJson.Messages[i].replyTo,
                visible: receivedJson.Messages[i].visible,
                likes: receivedJson.Messages[i].likes
            });
        }

    }


            comments.sort((a, b) => a.msgID - b.msgID);


//browsing the comments[] array and control it in several aspects
            for (let i = 0; i < comments.length; i++) {


                let countReplies = 0;


//hide comment's owner controllers "never give body any js executing codes (variables & []  only)"
                let editBtn = "";
                let deleteBtn = "";
                if (ownerID == comments[i].posterID) {

                    editBtn = "edit";
                    deleteBtn = "delete";
                } else {

                    editBtn = "";
                    deleteBtn = "";
                }

                let repliesTmp = "";
                //  let repliesArr = [];

                let timeStamp = comments[i].timestamp;
                let dateFormat = new Date(timeStamp);


                //fill repliesTmp
                for (let j = 0; j < comments.length; j++) {

                    let time = comments[j].timestamp;
                    let dateFormatRep = new Date(time);

                    //this condition for filling a string/Html replies array for specific comment and introduce them ordered in UI
                    if (comments[j].replyTo == comments[i].msgID) {
                        let editBtnRep = "";
                        let deleteBtnRep = "";
                        if (ownerID == comments[j].posterID) {
                            editBtnRep = "edit";
                            deleteBtnRep = "delete";
                        } else {

                            editBtnRep = "";
                            deleteBtnRep = "";
                        }


                        countReplies++;

                        // repliesTmp will be repliesTmp += ``; will be inserted inside body the static one
                        repliesTmp += `
 <div id="replyDiv" style="width: 80%; margin-left: 10%">

    <div class="card-body">
                <div class="d-flex flex-start align-items-center">

                  <div>
                  <div style="  position: absolute;top: 8px;right: 16px; color: #005cbf ;font-size: 14px;">
                  <p>


<!--    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">edit</button>-->


                   </p>
                   </div>
                                   <div style="font-size: 12px; margin-left: 82%">
                   <a data-toggle="modal" href="" onclick=" showUpdateModal(${comments[j].posterID},${comments[j].sessionID},${comments[j].msgID},'${comments[j].msgContents}');">${editBtnRep}</a>
                  <a href="" onclick="deleteMessage(${comments[j].msgID}, ${comments[j].posterID},${comments[j].sessionID})">${deleteBtnRep}</a>
</div>
                    <h6 class="fw-bold text-primary mb-1"> ${comments[j].displayName}</h6>
                    <p class="text-muted small mb-0">
                         ${dateFormatRep}
                    </p>

                  </div>
                </div>

                <p class="mt-3 mb-4 pb-2">
                   ${comments[j].msgContents}
                </p>

                <div class="small d-flex justify-content-start">



                  <a href="form-control" class="d-flex align-items-center me-3">
                    <i class="far fa-comment-dots me-2"></i>

                  </a>

                  <a href="javascript:void(0)" class="d-flex align-items-center me-3" onclick="likeMessage(${comments[j].msgID})" >
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">${comments[j].likes}  likes</p>
                  </a>

                </div>
              </div>






</div><br/>


`;


                    } //end of nested j loop's condition

                }//end of nested j loop


                if (comments[i].replyTo == null) {


//initializing visibility toggle button
                    let visibilityButton = ``;
                    if (comments[i].visible === true) {
                        visibilityButton = `
<label class="toggle">
    <input checked id="toggleswitch${comments[i].msgID}"  type="checkbox" onclick="getVisibility(${comments[i].msgID},${comments[i].posterID},${comments[i].visible})">
    <span class="roundbutton"><span id="status${comments[i].msgID}" style="color: whitesmoke; font-size: 11px">&nbsp;&nbsp;&nbsp;visible</span></span>
</label>`;
                    } else {
                        visibilityButton = `
          <label class="toggle">
             <input id="toggleswitch${comments[i].msgID}"  type="checkbox" value="${comments[i].msgID}" onclick="getVisibility(${comments[i].msgID},${comments[i].posterID},${comments[i].visible})">
             <span class="roundbutton"><span id="status${comments[i].msgID}" style="color: whitesmoke; font-size: 11px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;invisible</span></span>

</label>`;
                    }


                    body += `

                        <div class="card" >

              <div class="card-body" >
                <div class="d-flex flex-start align-items-center">

                  <div>
                  <div style="  position: absolute;top: 8px;right: 16px; color: #005cbf ;font-size: 14px;">
                  <p>


<!--    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">edit</button>-->


<!--toggle switch button-->
<style>




    .toggle {
        margin:0 0 0 0rem;
        position: relative;
        display: inline-block;
        width: 4.5rem;
        height: 1rem;


    }

    .toggle input {
        display: none;
    }

    .roundbutton {
        position: absolute;
        top: 0;
        left: -0.5rem;
        bottom: -0.4rem;
        right: 0;
        width: 94%;
        background-color: #db0e21;
        display: block;
        transition: all 0.3s;
        border-radius: 4rem;
        cursor: pointer;
    }

    .roundbutton:before {
        position: absolute;
        content: "";
        height: 1rem;
        width: 1rem;
        border-radius: 100%;
        display: block;
        left: 0.1rem;
        bottom: 0.2rem;
        background-color: white;
        transition: all 0.3s;
    }

    input:checked + .roundbutton {
        background-color: #3fe009;
    }

    input:checked + .roundbutton:before  {
        transform: translate(3rem, 0);
    }

</style>


<!--toggle for each comment[i] in specific. -->
${visibilityButton}

<!--end of toggle switch button-->



                   <a data-toggle="modal" href="" onclick=" showUpdateModal(${comments[i].posterID},${comments[i].sessionID},${comments[i].msgID},'${comments[i].msgContents}');">${editBtn}</a>
                  <a href="" onclick="deleteMessage(${comments[i].msgID}, ${comments[i].posterID},${comments[i].sessionID})">${deleteBtn}</a>


                   </p>
                   </div>
                    <h6 class="fw-bold text-primary mb-1"> ${comments[i].displayName}</h6>
                    <p class="text-muted small mb-0">
                         ${dateFormat}
                    </p>

                  </div>
                </div>

                <p class="mt-3 mb-4 pb-2">
                   ${comments[i].msgContents}
                </p>

                <div class="small d-flex justify-content-start">

                  <a data-toggle="modal" href="" onclick="showReplyModal(ownerID,${comments[i].sessionID},${comments[i].msgID},'${comments[i].msgContents}')" class="d-flex align-items-center me-3">
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">${countReplies}  reply&nbsp;&nbsp;&nbsp;&nbsp;</p>
                  </a>

                  <a href="javascript:void(0)" class="d-flex align-items-center me-3" onclick="likeMessage(${comments[i].msgID})" >
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">${comments[i].likes}  likes</p>
                  </a>

                </div>
              </div>
              <div class="card-footer py-3 border-0" style="background-color: #f8f9fa;">
                    <div style="width: 80%; margin-left: 10%">

                   <br/>  ${repliesTmp}

                    </div>


                    <div id="snackbar">Your reply has been added</div>
                <div style="width: 80%; margin-left: 10%" class="float-end mt-2 pt-1">
                  <button type="button" class="btn btn-primary btn-sm" onclick="showReplyModal(ownerID,${comments[i].sessionID},${comments[i].msgID})">reply</button>

                </div>
              </div>
            </div>
            <br/><br/>

`;
                    dateFormat = "";
                    $("#cardDiv").html(body);
                } else {
                    //  comments.pop();
                }



                $("#cardDiv").html(body);

                if(comments.length == 0){


                    $("#cardDiv").html("");
                }
                //end of Main for loop
            }










}





function panic(data) {

    let panics = [];
    let dataJson = JSON.parse(data.body);
    console.log("panics  " + Object.keys(dataJson).length)
    var panicSpan = document.getElementById("panicID");
    var panicNotification = Object.keys(dataJson.PanicResponse).length ;
             panicSpan.textContent = panicNotification;
                 for (var i = 0; i < Object.keys(dataJson).length; i++) {
                 }

}





function likeMessage(msgID) {


    fetch(SITE_URL + "/message/likeMessage", {
        method: 'PUT',
        body: JSON.stringify({

            liker: {
                id: ownerID
            },
            likedMessage: {
                id: msgID
            }


        }),
        headers: {
            "Content-Type": "application/json;charset=UTF-8",
            'X-CSRF-TOKEN': token
        },
        port: 443
    })
        .then((response) => {
            return response.json()
        })
        .then((data) => {

        })


}


function deleteMessage(msgID, posterID, sessionID) {
    fetch(SITE_URL + "/message/deleteMessage", {
        method: 'POST',
        body: JSON.stringify({

            id: msgID,
            poster: {
                id: posterID
            },
            session: {
                id: sessionID
            }


        }),
        headers: {
            "Content-Type": "application/json;charset=UTF-8",
            'X-CSRF-TOKEN': token,
            'Access-Control-Allow-Credentials':true,
            'Access-Control-Allow-Origin':"*"
        },
        port: 443
    })
        .then((response) => {
            return response.json()
        })
        .then((data) => {

        });

}


//visibility
function getVisibility(msgID, posterID, visible) {

//if(document.getElementById('status'+data == true){}
    let input = document.getElementById('toggleswitch' + msgID);
    let outputtext = document.getElementById('status' + msgID);

    if (visible === true) {

        // console.log(data);
        fetch(SITE_URL + "/message/updateVisibility", {
            method: 'PUT',
            body: JSON.stringify({

                id: msgID,
                poster: {
                    id: posterID
                },
                session: {
                    id: sessionID
                }

            }),
            headers: {
                "Content-Type": "application/json;charset=UTF-8",
                'X-CSRF-TOKEN': token
            },
            port: 443
        })
            .then((response) => {
                return response.json()
            })
            .then((data) => {
            });

    } else {
        fetch(SITE_URL + "/message/updateVisibility", {
            method: 'PUT',
            body: JSON.stringify({

                id: msgID,
                poster: {
                    id: posterID
                },
                session: {
                    id: sessionID
                }

            }),
            headers: {
                "Content-Type": "application/json;charset=UTF-8",
                'X-CSRF-TOKEN': token
            },
            port: 443
        })
            .then((response) => {
                return response.json()
            })
            .then((data) => {

            });


    }


    input.addEventListener('change', function () {
        if (this.checked) {
            outputtext.innerHTML = "&nbsp;&nbsp;&nbsp;visible";

        } else {
            outputtext.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;invisible";
            //  console.log("0")
        }
    });

}


//show data model first then call updateComment();
function showUpdateModal(posterID, sessionID, msgID, msgContent) {
    $("#exampleModal").modal('show');

    $("#msgID").val(msgID);
    $("#posterID").val(posterID);
    $("#sessionID").val(sessionID);
    $("#msgContent").val(msgContent);



}

function showReplyModal(posterID, sessionID, msgID) {
    $("#replyModal").modal('show');

    $("#RmsgID").val(msgID);
    $("#RposterID").val(posterID);
    $("#RsessionID").val(sessionID);
    $("#RmsgContent").val("");



}


//update comment
function updateComment(posterID = $("#posterID").val(), msgID = $("#msgID").val(), sessionID = $("#sessionID").val(), msgContent = $("#msgContent").val()) {



    let answer = document.getElementById('answer');

    fetch(SITE_URL + "/message/updateMessageContent", {
        method: 'PUT',
        body: JSON.stringify({
            id: msgID,
            poster: {
                id: posterID
            },
            session: {
                id: sessionID
            },
            messageContent: msgContent

        }),
        headers: {
            "Content-Type": "application/json;charset=UTF-8",
            'X-CSRF-TOKEN': token
        },
        port: 443
    })
        .then((response) => {
            return response.json()
        })
        .then((data) => {
        });


}


//update comment
function updateDisplayname() {

    $("#myModal").modal('show');
    $("#newDisNameField").val(displayname);

}


function newDisplyname() {
    // checkSessionStatus();
    console.log(ownerID, sessionID, $("#newDisNameField").val())


    fetch(SITE_URL + "/user/updateDisplayName", {
        method: 'PUT',
        body: JSON.stringify({



            id: ownerID
            ,
            displayName:  $("#newDisNameField").val(),
            session: {
                id: sessionID

            }



        }),
        headers: {
            "Content-Type": "application/json;charset=UTF-8",
            'X-CSRF-TOKEN': token,
            'Access-Control-Allow-Credentials':true,
            'Access-Control-Allow-Origin':"*"
        },
        port: 443
    })
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            localStorage.setItem('displayname', $("#newDisNameField").val());


            if (data.Status == "SUCCESS") {
                location.reload();
            } else {

            }



        });

}


function postAgain() {
    location.replace("chatWallOwner.html");

}



function  checkSessionStatus() {

    fetch(SITE_URL + "/session/checkSessionStatus", {
        method: 'POST',
        body: JSON.stringify({
            id: sessionID
        }),
        headers: {
            "Content-Type": "application/json;charset=UTF-8",
            'X-CSRF-TOKEN': token
        },
        port: 443
    })
        .then((response) => {

            return response.json();

        })
        .then((receivedJson) => {


            if (receivedJson == true) {




            } else {
                alert("your session has been deleted by the owner!");
                logOutOwner();
            }


        });


}


function deleteSession() {

    if (confirm("Are you sure that you want to delete the session!")) {
        fetch(SITE_URL + "/session/closeSession", {
            method: 'POST',
            body: JSON.stringify({
                id: sessionID
            }),
            headers: {
                "Content-Type": "application/json;charset=UTF-8",
                'X-CSRF-TOKEN': token,
                'Access-Control-Allow-Credentials':true,
                'Access-Control-Allow-Origin':"*"
            },
            port: 443
        })
            .then((response) => {
                return response.json()

            })
            .then((receivedJson) => {

                logOutOwner();

                return receivedJson;


            });

    } //end if statement




}


function logOutOwner() {

    localStorage.removeItem('ownerID');
    localStorage.removeItem('sessionID');
    localStorage.removeItem('sessionPassword');
    localStorage.removeItem('displayname');
    location.replace("index.html")
}


fetch(SITE_URL + "/csrf", {
    method: 'GET',
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
        token = data.token;
        return token;
    }).then(async (t) => {
    await connect();
    checkSessionStatus();
});



