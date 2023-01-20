/**
 * ARS 2022 - 2023
 * Author: Amer Aljamous
 * THU ULM
* */
//store data on the localstorage will be removed by user manually
//localStorage.setItem('password', '8D1F')
//store data on the session storage will be removed by closing the browser
//sessionStorage.setItem('password', '8D1F') .e.g
const userID = localStorage.getItem('userID');
const sessionID = localStorage.getItem('sessionID');
let displayname =  localStorage.getItem('displayname');
 // const SITE_URL = "https://i-lv-sopr-01.informatik.hs-ulm.de";
 // const SITE_URL = "https://rhit-r90y2r8w";
 const SITE_URL = "https://DESKTOP-FUO6UAL";
// const SITE_URL = "https://localhost";
var token = "";

//check if user logged in
if(userID==null){
    location.replace("index.html")
}


//post comment
function postComment() {
    // checkSessionStatus();



    if($("#textArea").val() != ""){
    let answer = document.getElementById('answer');

    const data = {

        poster: {
            id:  userID
        },
        session: {
            id: sessionID
        },
        messageContent:$("#textArea").val()

    };
    console.log(data);
    fetch(SITE_URL + "/message/postComment", {
        method: 'POST',
        body: JSON.stringify({

            poster: {
                id:  userID
            },
            session: {
                id: sessionID
            },
            messageContent:$("#textArea").val()


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
}else{
    showResultTextArea();
}
}


function postReply(posterID=$("#RposterID").val(),sessionID=$("#RsessionID").val(),msgID=$("#RmsgID").val(),msgContent=$("#RmsgContent").val()) {


    console.log(posterID, sessionID,msgID, msgContent);



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
            console.log(data)
            getPosts();
            //   const parg = `
            //
            //
            // your comment has been successfully posted<br/>
            //  <button type="button" class="btn btn-primary btn-sm" onclick="postAgain()">post again</button>
            //   `;
            //
            //  answer.innerHTML = parg;
            //  answer = "";

        })

    showResult();

}



//webSocket
var stompClient = null;
function connect(options) {

    console.log("ANYTHING");

    var socket = new SockJS(SITE_URL + "/message");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        //  console.log('Connected: ' + frame);
        //   var myDate = {id: sessionID};
        //   var stringObj = JSON.stringify(myDate);
        //
        //   stompClient.send("/app/getMessages", {}, stringObj);






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


// RUN THIS WHENEVER THE JAVASCRIPT FILE IS OPENED SO THAT IT AUTO CONNECTS
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
        return data.token;
    }).then((Toki) =>{

    connect();
    });



// Restful API fetch comments replies
function getPosts(responseData) {
  // checkSessionStatus();
    console.log(responseData);
    let receivedJson = JSON.parse(responseData.body);
    let comments =[];
    let allUsers = 0;

    let body = "";
  //  $("#cardDiv").html("");

  //  body = $("#cardDiv").html();
  //  body = "";

    // fetch(SITE_URL + "/message/getMessages", {
    //     method: 'POST',
    //     body: JSON.stringify({
    //         id: sessionID
    //     }),
    //     headers: {
    //         "Content-Type": "application/json;charset=UTF-8",
    //         'X-CSRF-TOKEN': token
    //     },
    //     port: 443
    // })
    //     .then((response) => {
    //         return response.json()
    //     })
    //     .then((receivedJson) => {

            console.log(receivedJson);
            //pulling data from Json server side file and pushing the comments inside well-ordered js array[]

            // for (let i = 0; i < Object.keys(receivedJson.Messages).length; i++) {
            //
            //
            //      // if(receivedJson.Messages[i].visible == true) {
            //          for (let k = 0; k < Object.keys(receivedJson.Messages).length; k++) {
            //
            //              if (receivedJson.Messages[i].poster.id == receivedJson.Messages[k].poster) {
            //
            //                  comments.push({
            //                      posterID: receivedJson.Messages[k].poster,
            //                      displayName: receivedJson.Messages[i].poster.displayName,
            //                      sessionID: receivedJson.Messages[k].session,
            //                      msgID: receivedJson.Messages[k].id,
            //                      timestamp: receivedJson.Messages[k].timestamp,
            //                      msgContents: receivedJson.Messages[k].messageContents,
            //                      replyTo: receivedJson.Messages[k].replyTo,
            //                      visible: receivedJson.Messages[k].visible,
            //                      likes: receivedJson.Messages[k].likes
            //                  });
            //
            //              }
            //          } //end of k loop
            //          if (receivedJson.Messages[i].poster.id) {
            //
            //
            //              comments.push({
            //                  posterID: receivedJson.Messages[i].poster.id,
            //                  displayName: receivedJson.Messages[i].poster.displayName,
            //                  sessionID: receivedJson.Messages[i].session,
            //                  msgID: receivedJson.Messages[i].id,
            //                  timestamp: receivedJson.Messages[i].timestamp,
            //                  msgContents: receivedJson.Messages[i].messageContents,
            //                  replyTo: receivedJson.Messages[i].replyTo,
            //                  visible: receivedJson.Messages[i].visible,
            //                  likes: receivedJson.Messages[i].likes
            //              });
            //          }
            //
            //
            //
            // }

            for (let i = 0; i < Object.keys(receivedJson.Messages).length; i++) {

                //   for (let k = 0; k < Object.keys(receivedJson.Messages).length; k++) {
                if(receivedJson.Messages[i].visible == true || receivedJson.Messages[i].poster.id == userID || receivedJson.Messages[i].poster == userID){

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
                //  } //end of k loop
                else {


                    comments.push({
                        posterID: receivedJson.Messages[i].poster,
                        displayName: "user#" + receivedJson.Messages[i].poster,
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

            } //if of receivedJson

            console.log(comments );


            comments.sort((a, b) => a.msgID - b.msgID);


//browsing the comments[] array and control it in several aspects
            for (let i = 0; i < comments.length; i++) {
                if (comments[i].visible === false && comments[i].posterID == userID || comments[i].visible === true) {

                    let countReplies = 0;


//hide comment's owner controllers "never give body any js executing codes (variables & []  only)"
                    let editBtn = "";
                    let deleteBtn = "";
                    if (userID == comments[i].posterID) {

                        editBtn = "edit";
                        deleteBtn = "delete";
                    } else {

                        editBtn = "";
                        deleteBtn = "";
                    }

                    let repliesTmp = "";
                    let timeStamp  = comments[i].timestamp;
                    let dateFormat = new Date(timeStamp);

                    //fill repliesTmp

                    for (let j = 0; j < comments.length; j++) {

                        let time  = comments[j].timestamp;
                        let dateFormatRep = new Date(time);
                        //this condition for filling a string/Html replies array for specific comment and introduce them ordered in UI
                        if (comments[j].replyTo == comments[i].msgID) {
                            let editBtnRep = "";
                            let deleteBtnRep = "";
                            if (userID == comments[j].posterID) {
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
                       Time  ${dateFormatRep}
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
                  <a href="javascript:void(0)" class="d-flex align-items-center me-3" onclick="likeMessage(${comments[j].msgID})">
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">${comments[j].likes}  likes</p>
                  </a>

                </div>
              </div>






</div><br/>


`;
                            console.log(dateFormatRep);
                            dateFormatRep = "";
                        } //end of nested j loop's condition
                    }//end of nested j loop

                    //this condition for popping an element of "string/Html replies" being shown as a comment
                    if (comments[i].replyTo == null) {




                        body += `

                       <div class="card" >

              <div class="card-body" >
                <div class="d-flex flex-start align-items-center">

                  <div>
                  <div style="  position: absolute;top: 8px;right: 16px; color: #005cbf ;font-size: 14px;">
                  <p>



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


<!--end of toggle switch button-->



                   <a data-toggle="modal" href="" onclick=" showUpdateModal(${comments[i].posterID},${comments[i].sessionID},${comments[i].msgID},'${comments[i].msgContents}');">${editBtn}</a>
                  <a href="" onclick="deleteMessage(${comments[i].msgID}, ${comments[i].posterID},${comments[i].sessionID})">${deleteBtn}</a>


                   </p>
                   </div>
                    <h6 class="fw-bold text-primary mb-1"> ${comments[i].displayName}</h6>
                    <p class="text-muted small mb-0">
                        Shared publicly ${dateFormat}
                    </p>

                  </div>
                </div>

                <p class="mt-3 mb-4 pb-2">
                   ${comments[i].msgContents}
                </p>

                <div class="small d-flex justify-content-start">

                  <a data-toggle="modal" href="" onclick="showReplyModal(userID,${comments[i].sessionID},${comments[i].msgID},'${comments[i].msgContents}')" class="d-flex align-items-center me-3">
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">${countReplies}  reply&nbsp;&nbsp;&nbsp;&nbsp;</p>
                  </a>

                  <a href="javascript:void(0)" class="d-flex align-items-center me-3" onclick="likeMessage(${comments[i].msgID})">
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
                  <button type="button" class="btn btn-primary btn-sm" onclick="showReplyModal(userID,${comments[i].sessionID},${comments[i].msgID})">reply</button>

                </div>
              </div>
            </div>
            <br/><br/>

`;

                        dateFormat = "";
                        //console.log("comment");

                        $("#cardDiv").html(body);
                    } else {
                        //  comments.pop();
                    }

                    $("#cardDiv").html(body);

                    //end of Main for loop
                }

                if (comments.length == 0) {
                    let span = `
                <span style="text-align: center; font-size: 20px;">no comments posted or not visible yet &nbsp;&nbsp;  :_(</span>
                `;
                    $("#noCommentsYet").html(span);

                }





            }
     //   }
        //) // end of .then(receivedJson)

    console.log(comments);

            console.log("end of getMessage");

    if(comments.length == 0){


         $("#cardDiv").html("");
    }
    comments= [];

}



function likeMessage(msgID){
    // checkSessionStatus();

    console.log(msgID);


    fetch(SITE_URL + "/message/likeMessage", {
        method: 'PUT',
        body: JSON.stringify({

            liker: {
                id: userID
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
            console.log(data)
            getPosts();


        })






}





//show data model first then call updateComment();
function showUpdateModal(posterID,sessionID, msgID, msgContent){
    // checkSessionStatus();
    $("#exampleModal").modal('show');
    $("#msgID").val(msgID);
    $("#posterID").val(posterID);
    $("#sessionID").val(sessionID);
    $("#msgContent").val(msgContent);

}


//update comment
// function updateComment(posterID=$("#posterID").val(),msgID=$("#msgID").val(),sessionID=$("#sessionID").val(),msgContent=$("#msgContent").val()){
//     checkSessionStatus();
//
//
//     //test
//     console.log("i am update comment   " + posterID +"  " + msgID+"  " + sessionID+"  " + msgContent);
//     //VPN is not working updateMessageContent type PUT is not yet tested
//
//
//     let answer = document.getElementById('answer');
//
//     const data = {
//
//         id: msgID,
//         poster: {
//             id: posterID
//         },
//         session: {
//             id: sessionID
//         },
//         messageContent: $("#updateTextArea").val()
//
//     };
//     console.log(data);
//     fetch(SITE_URL + "/message/updateMessageContent", {
//         method: 'PUT',
//         body: JSON.stringify({
//
//             id: msgID,
//             poster: {
//                 id: posterID
//             },
//             session: {
//                 id: sessionID
//             },
//             messageContent: $("#updateTextArea").val()
//
//
//         }),
//         headers: {
//             "Content-Type": "application/json;charset=UTF-8",
//              'X-CSRF-TOKEN': token
//         },
//         port: 443
//     })
//         .then((response) => {
//             return response.json()
//         })
//         .then((data) => {
//             console.log(data)
//
//             const parg = `
//
//
//       your comment has been successfully posted<br/>
//        <button type="button" class="btn btn-primary btn-sm" onclick="postAgain()">post again</button>
//         `;
//
//             answer.innerHTML = parg;
//             answer = "";
//         })
//
//     getPosts();
//
// }
//



function updateComment(posterID = $("#posterID").val(), msgID = $("#msgID").val(), sessionID = $("#sessionID").val(), msgContent = $("#msgContent").val()) {

    // checkSessionStatus();

    //test
    console.log("i am update comment   " + posterID + "  " + msgID + "  " + sessionID + "  " + msgContent);
    //VPN is not working updateMessageContent type PUT is not yet tested


    let answer = document.getElementById('answer');

    // const data = {
    //
    //     id: msgID,
    //     poster: {
    //         id: posterID
    //     },
    //     session: {
    //         id: sessionID
    //     },
    //     messageContent: $("#updateTextArea").val()
    //
    // };
//last version
    //  console.log(data);
    fetch(SITE_URL + "/message/updateMessageContent", {
        method: 'PUT',
        body: JSON.stringify({
//
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
            console.log(data)
            getPosts();
            //       const parg = `
            //
            //
            // your comment has been successfully posted<br/>
            //  <button type="button" class="btn btn-primary btn-sm" onclick="postAgain()">post again</button>
            //   `;
            //
            //       answer.innerHTML = parg;
            //       answer = "";
        });


}




function showReplyModal(posterID,sessionID, msgID){

    $("#replyModal").modal('show');
    $("#RmsgID").val(msgID);
    $("#RposterID").val(posterID);
    $("#RsessionID").val(sessionID);
    $("#RmsgContent").val("");

}






//update displayname
function updateDisplayname(){
    // checkSessionStatus();
    $("#myModal").modal('show');
    $("#newDisNameField").val(displayname);
}



function newDisplyname(){
    // checkSessionStatus();
    console.log(userID, sessionID, $("#newDisNameField").val() )


    fetch(SITE_URL + "/user/updateDisplayName", {
        method: 'PUT',
        body: JSON.stringify({



            id: userID
            ,
            displayName:  $("#newDisNameField").val(),
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
            console.log(data)
            localStorage.setItem('displayname', $("#newDisNameField").val());


            if (data.Status == "SUCCESS") {
                location.reload();
                // const parg = ` your display name has been successfully updated<br/>`;
                // answer.innerHTML = parg;
            } else {

                console.log("Error has been occurred");
            }



        });

}


function panic(code){


    // checkSessionStatus();
    console.log(userID, sessionID, $("#newDisNameField").val() )


    fetch(SITE_URL + "/panic/postPanic", {
        method: 'POST',
        body: JSON.stringify({

            panicType: {
                panicType: code
            },
            panicker: {
                id: userID
            },

            id: userID
            ,
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
            console.log(data)
            localStorage.setItem('displayname', $("#newDisNameField").val());


         //   if (data.Status == "SUCCESS") {
                const parg = `

      your panic has been sent<br/>

        `;

                answer.innerHTML = parg;


                getPosts();
           // } else {

            //    console.log("Error has been occurred");
          //  }

location.reload();

        })




}




function deleteMessage(msgID, posterID, sessionID) {
    // checkSessionStatus();
    console.log(msgID, posterID, sessionID);
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
            'X-CSRF-TOKEN': token
        },
        port: 443
    })
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            console.log(data)


            //       const parg = `
            //
            //
            // your comment has been successfully posted<br/>
            //  <button type="button" class="btn btn-primary btn-sm" onclick="postAgain()">post again</button>
            //   `;
            //
            //       answer.innerHTML = parg;
            //       answer = "";
        });
    //getPosts();
}



function postAgain(){
    location.replace("chatWallUser.html");
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

            if(receivedJson == true){

                console.log("your session is exist!");


            }
            else{
                alert("your session has been deleted by the owner!");
                console.log("your session is NOT exist!")
                logOutUser();
            }



        });



}


//sign out for user chat wall
function logOutUser(){

    localStorage.removeItem('userID');
    localStorage.removeItem('sessionID');
    localStorage.removeItem('displayname')
    location.replace("index.html")
}




// setInterval(getPosts,1000);



    //connect();
    console.log("Begin fetch");
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