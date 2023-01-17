const ownerID = localStorage.getItem('ownerID');
const sessionID = localStorage.getItem('sessionID');
const sessionPassword = localStorage.getItem('sessionPassword');
// const SITE_URL = "https://i-lv-sopr-01.informatik.hs-ulm.de";
 const SITE_URL = "https://rhit-r90y2r8w";
// const SITE_URL = "https://localhost";
let displayname = localStorage.getItem('displayname');
var token = "";

// function fetching(){
//     setTimeout(getPosts, 1000);}




//post comment
function postComment() {
    checkSessionStatus();
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
        console.log(data);
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
                console.log(data)
              //  getPosts();
                const parg = `

           
          your comment has been successfully posted<br/>
           <button type="button" class="btn btn-primary btn-sm" onclick="postAgain()">post again</button>
            `;

                answer.innerHTML = parg;
                answer = "";
            })
    } else {
        showResultTextArea();
    }

}



//post reply
function postReply(posterID = $("#RposterID").val(), sessionID = $("#RsessionID").val(), msgID = $("#RmsgID").val(), msgContent = $("#RmsgContent").val()) {
    // let answer = document.getElementById('');
    checkSessionStatus();
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
            console.log(data)
           // getPosts();

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


/**
 *
 * webSocket
 *
 *
 * */

/**
 *
 * webSocket
 *
 *
 * */
var stompClient = null;



function connect(options) {


    var socket = new SockJS(SITE_URL + "/message");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
      //  console.log('Connected: ' + frame);

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
            });

        let urlMessage = "/user/"+sessionID+"/topic/retrieveMessages";
        stompClient.subscribe(urlMessage, getPosts);

        let urlPanic ="/user/"+sessionID+"/topic/retrievePanic";
        stompClient.subscribe(urlPanic, panic);
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}
//
// /**
//  function postComment() {
//     var myDate = {messageContent: "Good morning", poster: {id: "1"}, session: { id: "1"}};
//     var stringObj = JSON.stringify(myDate);
//
//     stompClient.send("/app/getMessages", {}, stringObj);
// }

// function insertPanic() {
//     var myDate = {id: 1};
//     var stringObj = JSON.stringify(myDate);
//
// /**
//  * Works with Restful API too, just use websocket for the get messages.
//  */
// // function postCommentsssssss() {
// //
// //     const data = {
// //
// //         poster: {
// //             id:  1
// //         },
// //         session: {
// //             id: 1
// //         },
// //         messageContent: "Austria"
// //
// //     };
// //     console.log(data);
// //     fetch(SITE_URL + "/message/postComment", {
// //         method: 'POST',
// //         body: JSON.stringify({
// //
// //             poster: {
// //                 id:  1
// //             },
// //             session: {
// //                 id: 1
// //             },
// //             messageContent: "Australia"
// //
// //
// //         }),
// //         headers: {
// //             "Content-Type": "application/json;charset=UTF-8",
// //              'X-CSRF-TOKEN': token
// //         },
//         port: 443
// //     })
// //         .then((response) => {
// //             return response.json()
// //         })
// //         .then((data) => {
// //             console.log(data)
// //         })
// // }
//
// }

// RUN THIS WHENEVER THE JAVASCRIPT FILE IS OPENED SO THAT IT AUTO CONNECTS

    connect();

// function getPostWS() {
//     // if(greeting() == false){
// // checkSessionStatus();
//     checkSessionStatus();
//
//
// RUN THIS WHENEVER THE JAVASCRIPT FILE IS OPENED SO THAT IT AUTO CONNECTS

    //connect(); // MOVED TO VERY BOTTOM -Connor

//get posts from DB WebSocket(Recommended way)receivedJson
function getPosts(responseData) {
    // if(greeting() == false){
    // checkSessionStatus();
    //   console.log(checkSessionStatus());
    var myDate = {id: 1};
    var stringObj = JSON.stringify(myDate);

    stompClient.send("/app/getMessages", {}, stringObj);


    console.log(responseData);
    let receivedJson = JSON.parse(responseData.body);
    let comments = [];
    let allUsers = 0;
    let body = $("#cardDiv").html();
            console.log("Websocket response v2")
            console.log(receivedJson);


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


            console.log("comments", comments)
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
                    //console.log("comment");
                } else {
                    //  comments.pop();
                }


                // repliesTmp = "";
                $("#cardDiv").html(body);


                //end of Main for loop
            }


            console.log(comments);


            if (comments.length == 0) {
                let span = `
                <span style="text-align: center; font-size: 20px;">no comments posted or not visible yet &nbsp;&nbsp;  :_(</span>
                `;
                $("#noCommentsYet").html(span);

            }


         // end of .then(receivedJson)


    comments = [];
    body = "";

// }    //end of checking session status condition
//     else
// {
//     console.log("the session has been deleted!");
//
// }
}
//
//
//
//   //  comments = [];
//    // body = "";
//
// //}    //end of checking session status condition
// //     else
// // {
// //     console.log("the session has been deleted!");
// //
// // }
// }


function ping(){
let data = JSON.stringify({
    id: sessionID
}) ;
    fetch(SITE_URL + "/message/getMessages", {
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
            return response.json()
        })
        .then((receivedJsonFetch) => {

            console.log(receivedJsonFetch);
        });

}



// function getPosts() {
//     // if(greeting() == false){
// // checkSessionStatus();
//     checkSessionStatus();
//
//
//     let comments = [];
//     let allUsers = 0;
//     let body = $("#cardDiv").html();
//     fetch("http://localhost:8080/message/getMessages", {
//         method: 'POST',
//         body: JSON.stringify({
//             id: sessionID
//         }),
//         headers: {
//             "Content-Type": "application/json;charset=UTF-8"
//         }
//     })
//         .then((response) => {
//             return response.json()
//         })
//         .then((receivedJson) => {
//
//             console.log(receivedJson);
//
//             //pulling data from Json server side file and pushing the comments inside well-ordered js array[]
//             for (let i = 0; i < Object.keys(receivedJson.Messages).length; i++) {
//
//
//                 for (let k = 0; k < Object.keys(receivedJson.Messages).length; k++) {
//
//                     if (receivedJson.Messages[i].poster.id == receivedJson.Messages[k].poster) {
//
//                         comments.push({
//                             posterID: receivedJson.Messages[k].poster,
//                             displayName: receivedJson.Messages[i].poster.displayName,
//                             sessionID: receivedJson.Messages[k].session,
//                             msgID: receivedJson.Messages[k].id,
//                             timestamp: receivedJson.Messages[k].timestamp,
//                             msgContents: receivedJson.Messages[k].messageContents,
//                             replyTo: receivedJson.Messages[k].replyTo,
//                             visible: receivedJson.Messages[k].visible,
//                             likes: receivedJson.Messages[k].likes
//                         });
//
//                     }
//                 } //end of k loop
//                 if (receivedJson.Messages[i].poster.id) {
//
//
//                     comments.push({
//                         posterID: receivedJson.Messages[i].poster.id,
//                         displayName: receivedJson.Messages[i].poster.displayName,
//                         sessionID: receivedJson.Messages[i].session,
//                         msgID: receivedJson.Messages[i].id,
//                         timestamp: receivedJson.Messages[i].timestamp,
//                         msgContents: receivedJson.Messages[i].messageContents,
//                         replyTo: receivedJson.Messages[i].replyTo,
//                         visible: receivedJson.Messages[i].visible,
//                         likes: receivedJson.Messages[i].likes
//                     });
//                 }
//
//             }
//
//
//             console.log("comments", comments)
//             comments.sort((a, b) => a.msgID - b.msgID);
//
//
// //browsing the comments[] array and control it in several aspects
//             for (let i = 0; i < comments.length; i++) {
//
//
//                 let countReplies = 0;
//
//
// //hide comment's owner controllers "never give body any js executing codes (variables & []  only)"
//                 let editBtn = "";
//                 let deleteBtn = "";
//                 if (ownerID == comments[i].posterID) {
//
//                     editBtn = "edit";
//                     deleteBtn = "delete";
//                 } else {
//
//                     editBtn = "";
//                     deleteBtn = "";
//                 }
//
//                 let repliesTmp = "";
//                 //  let repliesArr = [];
//
//                 let timeStamp = comments[i].timestamp;
//                 let dateFormat = new Date(timeStamp);
//
//
//                 //fill repliesTmp
//                 for (let j = 0; j < comments.length; j++) {
//
//                     let time = comments[j].timestamp;
//                     let dateFormatRep = new Date(time);
//
//                     //this condition for filling a string/Html replies array for specific comment and introduce them ordered in UI
//                     if (comments[j].replyTo == comments[i].msgID) {
//                         let editBtnRep = "";
//                         let deleteBtnRep = "";
//                         if (ownerID == comments[j].posterID) {
//                             editBtnRep = "edit";
//                             deleteBtnRep = "delete";
//                         } else {
//
//                             editBtnRep = "";
//                             deleteBtnRep = "";
//                         }
//
//
//                         countReplies++;
//
//                         // repliesTmp will be repliesTmp += ``; will be inserted inside body the static one
//                         repliesTmp += `
//  <div id="replyDiv" style="width: 80%; margin-left: 10%">
//
//     <div class="card-body">
//                 <div class="d-flex flex-start align-items-center">
//
//                   <div>
//                   <div style="  position: absolute;top: 8px;right: 16px; color: #005cbf ;font-size: 14px;">
//                   <p>
//
//
// <!--    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">edit</button>-->
//
//
//                    </p>
//                    </div>
//                                    <div style="font-size: 12px; margin-left: 82%">
//                    <a data-toggle="modal" href="" onclick=" showUpdateModal(${comments[j].posterID},${comments[j].sessionID},${comments[j].msgID},'${comments[j].msgContents}');">${editBtnRep}</a>
//                   <a href="" onclick="deleteMessage(${comments[j].msgID}, ${comments[j].posterID},${comments[j].sessionID})">${deleteBtnRep}</a>
// </div>
//                     <h6 class="fw-bold text-primary mb-1"> ${comments[j].displayName}</h6>
//                     <p class="text-muted small mb-0">
//                          ${dateFormatRep}
//                     </p>
//
//                   </div>
//                 </div>
//
//                 <p class="mt-3 mb-4 pb-2">
//                    ${comments[j].msgContents}
//                 </p>
//
//                 <div class="small d-flex justify-content-start">
//
//
//
//                   <a href="form-control" class="d-flex align-items-center me-3">
//                     <i class="far fa-comment-dots me-2"></i>
//
//                   </a>
//
//                   <a href="javascript:void(0)" class="d-flex align-items-center me-3" onclick="likeMessage(${comments[j].msgID})" >
//                     <i class="far fa-comment-dots me-2"></i>
//                     <p class="mb-0">${comments[j].likes}  likes</p>
//                   </a>
//
//                 </div>
//               </div>
//
//
//
//
//
//
// </div><br/>
//
//
// `;
//
//
//                     } //end of nested j loop's condition
//
//                 }//end of nested j loop
//
//
//                 if (comments[i].replyTo == null) {
//
//
// //initializing visibility toggle button
//                     let visibilityButton = ``;
//                     if (comments[i].visible === true) {
//                         visibilityButton = `
// <label class="toggle">
//     <input checked id="toggleswitch${comments[i].msgID}"  type="checkbox" onclick="getVisibility(${comments[i].msgID},${comments[i].posterID},${comments[i].visible})">
//     <span class="roundbutton"><span id="status${comments[i].msgID}" style="color: whitesmoke; font-size: 11px">&nbsp;&nbsp;&nbsp;visible</span></span>
// </label>`;
//                     } else {
//                         visibilityButton = `
//           <label class="toggle">
//              <input id="toggleswitch${comments[i].msgID}"  type="checkbox" value="${comments[i].msgID}" onclick="getVisibility(${comments[i].msgID},${comments[i].posterID},${comments[i].visible})">
//              <span class="roundbutton"><span id="status${comments[i].msgID}" style="color: whitesmoke; font-size: 11px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;invisible</span></span>
//
// </label>`;
//                     }
//
//
//                     body += `
//
//                         <div class="card" >
//
//               <div class="card-body" >
//                 <div class="d-flex flex-start align-items-center">
//
//                   <div>
//                   <div style="  position: absolute;top: 8px;right: 16px; color: #005cbf ;font-size: 14px;">
//                   <p>
//
//
// <!--    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">edit</button>-->
//
//
// <!--toggle switch button-->
// <style>
//
//
//
//
//     .toggle {
//         margin:0 0 0 0rem;
//         position: relative;
//         display: inline-block;
//         width: 4.5rem;
//         height: 1rem;
//
//
//     }
//
//     .toggle input {
//         display: none;
//     }
//
//     .roundbutton {
//         position: absolute;
//         top: 0;
//         left: -0.5rem;
//         bottom: -0.4rem;
//         right: 0;
//         width: 94%;
//         background-color: #db0e21;
//         display: block;
//         transition: all 0.3s;
//         border-radius: 4rem;
//         cursor: pointer;
//     }
//
//     .roundbutton:before {
//         position: absolute;
//         content: "";
//         height: 1rem;
//         width: 1rem;
//         border-radius: 100%;
//         display: block;
//         left: 0.1rem;
//         bottom: 0.2rem;
//         background-color: white;
//         transition: all 0.3s;
//     }
//
//     input:checked + .roundbutton {
//         background-color: #3fe009;
//     }
//
//     input:checked + .roundbutton:before  {
//         transform: translate(3rem, 0);
//     }
//
// </style>
//
//
// <!--toggle for each comment[i] in specific. -->
// ${visibilityButton}
//
// <!--end of toggle switch button-->
//
//
//
//                    <a data-toggle="modal" href="" onclick=" showUpdateModal(${comments[i].posterID},${comments[i].sessionID},${comments[i].msgID},'${comments[i].msgContents}');">${editBtn}</a>
//                   <a href="" onclick="deleteMessage(${comments[i].msgID}, ${comments[i].posterID},${comments[i].sessionID})">${deleteBtn}</a>
//
//
//                    </p>
//                    </div>
//                     <h6 class="fw-bold text-primary mb-1"> ${comments[i].displayName}</h6>
//                     <p class="text-muted small mb-0">
//                          ${dateFormat}
//                     </p>
//
//                   </div>
//                 </div>
//
//                 <p class="mt-3 mb-4 pb-2">
//                    ${comments[i].msgContents}
//                 </p>
//
//                 <div class="small d-flex justify-content-start">
//
//                   <a data-toggle="modal" href="" onclick="showReplyModal(ownerID,${comments[i].sessionID},${comments[i].msgID},'${comments[i].msgContents}')" class="d-flex align-items-center me-3">
//                     <i class="far fa-comment-dots me-2"></i>
//                     <p class="mb-0">${countReplies}  reply&nbsp;&nbsp;&nbsp;&nbsp;</p>
//                   </a>
//
//                   <a href="javascript:void(0)" class="d-flex align-items-center me-3" onclick="likeMessage(${comments[i].msgID})" >
//                     <i class="far fa-comment-dots me-2"></i>
//                     <p class="mb-0">${comments[i].likes}  likes</p>
//                   </a>
//
//                 </div>
//               </div>
//               <div class="card-footer py-3 border-0" style="background-color: #f8f9fa;">
//                     <div style="width: 80%; margin-left: 10%">
//
//                    <br/>  ${repliesTmp}
//
//                     </div>
//
//
//                     <div id="snackbar">Your reply has been added</div>
//                 <div style="width: 80%; margin-left: 10%" class="float-end mt-2 pt-1">
//                   <button type="button" class="btn btn-primary btn-sm" onclick="showReplyModal(ownerID,${comments[i].sessionID},${comments[i].msgID})">reply</button>
//
//                 </div>
//               </div>
//             </div>
//             <br/><br/>
//
// `;
//                     dateFormat = "";
//                     //console.log("comment");
//                 } else {
//                     //  comments.pop();
//                 }
//
//
//                 // repliesTmp = "";
//                 $("#cardDiv").html(body);
//
//
//                 //end of Main for loop
//             }
//
//
//             console.log(comments);
//
//
//             if (comments.length == 0) {
//                 let span = `
//                 <span style="text-align: center; font-size: 20px;">no comments posted or not visible yet &nbsp;&nbsp;  :_(</span>
//                 `;
//                 $("#noCommentsYet").html(span);
//
//             }
//
//
//         }) // end of .then(receivedJson)
//
//
//     comments = [];
//     body = "";
//
// // }    //end of checking session status condition
// //     else
// // {
// //     console.log("the session has been deleted!");
// //
// // }
// }

function likeMessage(msgID) {
    checkSessionStatus();

    console.log(msgID);


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
            console.log(data)
            //getPosts();

        })


}


function deleteMessage(msgID, posterID, sessionID) {
    checkSessionStatus();
    console.log(msgID, posterID, sessionID);
    fetch(SITE_URL + "/message/deleteMessage", {
        method: 'DELETE',
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

        console.log("is visible" + visible);
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


        console.log("is visible" + visible);
    }


    input.addEventListener('change', function () {
        if (this.checked) {
            outputtext.innerHTML = "&nbsp;&nbsp;&nbsp;visible";
            //   console.log(this.value);
            //    console.log("1");


        } else {
            outputtext.innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;invisible";
            //  console.log("0")
        }
    });
    // var x = document.getElementById("invisible");
    // if (x.innerHTML === "visible") {
    //     console.log("visible");
    // } else {
    //     console.log("invisible");
    // }


//     if(data = true){
//
//
// //
// //
// //         visible="";
// //         invisible = "invisible";
// //         visibilityButtonChecked =
// //
// //             ` <label class="switch">
// //
// //   <input id="visibility" type="checkbox" checked value="true" onclick="getVisibility(this.value);">
// //   <span class="slider round"></span>
// // </label>  `;
// //         visibilityButtonUnchecked = ``;
// // console.log("done")
//     }

    // if () {
    //     $(this).val('true');
    //     console.log("true");
    // }
    // else {
    //     console.log("false");
    //     $(this).val('false');
    // }
}


//show data model first then call updateComment();
function showUpdateModal(posterID, sessionID, msgID, msgContent) {
    checkSessionStatus();
    $("#exampleModal").modal('show');

    $("#msgID").val(msgID);
    $("#posterID").val(posterID);
    $("#sessionID").val(sessionID);
    $("#msgContent").val(msgContent);


    console.log(posterID, sessionID, msgID, msgContent);

}

function showReplyModal(posterID, sessionID, msgID) {
    checkSessionStatus();
    $("#replyModal").modal('show');

    $("#RmsgID").val(msgID);
    $("#RposterID").val(posterID);
    $("#RsessionID").val(sessionID);
    $("#RmsgContent").val("");


    console.log(posterID, sessionID, msgID);

}


//update comment
function updateComment(posterID = $("#posterID").val(), msgID = $("#msgID").val(), sessionID = $("#sessionID").val(), msgContent = $("#msgContent").val()) {

    checkSessionStatus();

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
           // getPosts();
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


//update comment
function updateDisplayname() {
    checkSessionStatus();

    //test
    $("#myModal").modal('show');
    $("#newDisNameField").val(displayname);
    // data-toggle="modal" data-target="#myModal"
    console.log("posterID: " + ownerID + "  sessionID: " + sessionID + "  displayname:  " + $("#newDisNameField").val());
    //VPN is not working updateMessageContent type PUT is not yet tested

    //  let answer = document.getElementById('answer');


}


function newDisplyname() {
    checkSessionStatus();
    console.log(ownerID, sessionID, $("#newDisNameField").val())


    fetch(SITE_URL + "/user/updateDisplayName", {
        method: 'PUT',
        body: JSON.stringify({


            id: ownerID
            ,
            displayName: $("#newDisNameField").val(),
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
            //getPosts();
            console.log(data)
            localStorage.setItem('displayname', $("#newDisNameField").val());


            if (data.Status == "SUCCESS") {
                const parg = `


      your display name has been successfully updated<br/>

        `;

                answer.innerHTML = parg;

            } else {

                console.log("Error has been occurred");
            }


        })


}


function postAgain() {
    checkSessionStatus();
  // getPosts();
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

                console.log("your session is exist!");


            }
            if (receivedJson != true) {
                alert("your session has been deleted by the owner!");
                console.log("your session is NOT exist!")
                logOutOwner();
            }


        });


}


function deleteSession() {

    if (confirm("Are you sure that you want to delete the session!")) {

        fetch(SITE_URL + "/session/closeSession", {
            method: 'DELETE',
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
                return response.json()

            })
            .then((receivedJson) => {

                return receivedJson;

            });


    } else {


    }


    logOutOwner();
}


function logOutOwner() {

    localStorage.removeItem('ownerID');
    localStorage.removeItem('sessionID');
    localStorage.removeItem('sessionPassword');
    localStorage.removeItem('displayname');
    location.replace("index.html")
}


// setInterval(getPosts,1000);


//get posts from DB basic version
// function getPost() {
//     let posts = document.getElementById('cardDiv');
//
//     //let data = { id: sessionID };
//     //console.log(data);
//     fetch(SITE_URL + "/message/getMessages", {
//         method: 'POST',
//         body: JSON.stringify({
//             id: sessionID
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
//         .then((receivedJson) => {
//             //   console.log(receivedJson)
// data=receivedJson;
//             for (let i = 0; i <  Object.keys(receivedJson.Messages).length; i++) {
//
//
//                 console.log(receivedJson.Messages[i]);
//
//                 let timeStamp = receivedJson.Messages[i].timestamp;
//                 let dateFormat = new Date(timeStamp);
//
//                 let card = `
//             <div class="card" >
//               <div class="card-body">
//                 <div class="d-flex flex-start align-items-center">
//
//                   <div>
//                   <div style="  position: absolute;top: 8px;right: 16px; color: #005cbf ;font-size: 14px;">
//                   <p>
//                   <a href="">edit</a>
//                   <a href="">delete</a>
//
//                    </p>
//
//                    </div>
//                     <h6 class="fw-bold text-primary mb-1">Written by:  ${receivedJson.Messages[i].poster.id} user</h6>
//                     <p class="text-muted small mb-0">
//                         Shared publicly ${ timeStamp }
//                     </p>
//                   </div>
//                 </div>
//
//                 <p class="mt-3 mb-4 pb-2">
//
//                        ${receivedJson.Messages[i].messageContents}
//                         ${receivedJson.Messages[i].id}
//                 </p>
//
//                 <div class="small d-flex justify-content-start">
//
//                   <a href="#!" class="d-flex align-items-center me-3">
//                     <i class="far fa-comment-dots me-2"></i>
//                     <p class="mb-0">reply&nbsp;&nbsp;&nbsp;&nbsp;</p>
//                   </a>                <a href="#!" class="d-flex align-items-center me-3">
//                     <i class="far fa-comment-dots me-2"></i>
//                     <p class="mb-0">${receivedJson.Messages[i].likes}  likes</p>
//                   </a>
//
//                 </div>
//               </div>
//               <div class="card-footer py-3 border-0" style="background-color: #f8f9fa;">
//                 <div class="d-flex flex-start w-100">
//                   <div class="form-outline w-100">
//                 <textarea class="form-control" id="textAreaExample" placeholder="Your comment must be less than 1024 letter..." rows="4"
//                           style="background: #fff;"></textarea>
//                     <label class="form-label" for="textAreaExample">Message</label>
//                   </div>
//                 </div>
//                 <div class="float-end mt-2 pt-1">
//                   <button type="button" class="btn btn-primary btn-sm" onclick="">Post comment</button>
//                   <button type="button" class="btn btn-outline-primary btn-sm">Cancel</button>
//                 </div>
//               </div>
//             </div>
// <!--            I will change it to padding/Margin-->
//             <br/><br/><br/><br/><br/><br/>
//
// `;
//
//                 posts.innerHTML += card;
//
//             }
//
//
//
//
//
//         })
//
// }
//setInterval(getPosts,1000);


//                 for (let j = 0; j < Object.keys(receivedJson.Messages).length; j++) {
//
//                     if (receivedJson.Messages[j].replyTo == receivedJson.Messages[i].id) {
//                         // singleReply = receivedJson.Messages[j].messageContents;
//                         //  cReplies += receivedJson.Messages[j].messageContents;
//                         // bodyForm = `<div><span>${receivedJson.Messages[j].messageContents}</span></div>`;
//                         rep = receivedJson.Messages[j].messageContents;
//                         console.log(rep);
//                         if (receivedJson.Messages[i].poster.id) {
//
//                             replies.push({
//                                 msgID: receivedJson.Messages[j].replyTo,
//                                 form: receivedJson.Messages[j].messageContents
//                                 // displayName: receivedJson.Messages[j].poster.displayName,
//                                 // sessionID: receivedJson.Messages[j].session,
//                                 // msgID: receivedJson.Messages[j].id,
//                                 // timestamp: receivedJson.Messages[j].timestamp,
//                                 // msgContents: receivedJson.Messages[j].messageContents,
//                                 // replyTo: receivedJson.Messages[j].replyTo,
//                                 // visible: receivedJson.Messages[j].visible,
//                                 //likes: receivedJson.Messages[j].likes
//                             });
//                         } else {
//                             replies.push({
//                                 msgID: receivedJson.Messages[j].replyTo,
//                                 form: receivedJson.Messages[j].messageContents
//                                 // displayName: receivedJson.Messages[j].poster.displayName,
//                                 // sessionID: receivedJson.Messages[j].session,
//                                 // msgID: receivedJson.Messages[j].id,
//                                 // timestamp: receivedJson.Messages[j].timestamp,
//                                 // msgContents: receivedJson.Messages[j].messageContents,
//                                 // replyTo: receivedJson.Messages[j].replyTo,
//                                 // visible: receivedJson.Messages[j].visible,
//                                 //likes: receivedJson.Messages[j].likes
//                             });
//                         }
//
//
// //                         replyBody += `
// //
// //
// //
// //               <div class="card-body">
// //                 <div class="d-flex flex-start align-items-center">
// //
// //                   <div>
// //                   <div style="  position: absolute;top: 8px;right: 16px; color: #005cbf ;font-size: 14px;">
// //                   <p>
// //
// //
// // <!--    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">edit</button>-->
// //                    <a data-toggle="modal" href="" onclick=" showUpdateModal(${comments[i].posterID},${comments[i].sessionID},${comments[i].msgID},'${comments[i].msgContents}');">${editBtn}</a>
// //                   <a href="">${deleteBtn}</a>
// //
// //
// //                    </p>
// //                    </div>
// //                     <h6 class="fw-bold text-primary mb-1"> ${comments[i].posterID} user</h6>
// //                     <p class="text-muted small mb-0">
// //                         Shared publicly ${dateFormat}
// //                     </p>
// //
// //                   </div>
// //                 </div>
// //
// //                 <p class="mt-3 mb-4 pb-2">
// //                    ${comments[i].msgContents}
// //                 </p>
// //
// //                 <div class="small d-flex justify-content-start">
// //
// //                   <a href="#!" class="d-flex align-items-center me-3">
// //                     <i class="far fa-comment-dots me-2"></i>
// //                     <p class="mb-0">reply&nbsp;&nbsp;&nbsp;&nbsp;</p>
// //                   </a>                <a href="#!" class="d-flex align-items-center me-3">
// //                     <i class="far fa-comment-dots me-2"></i>
// //                     <p class="mb-0">${comments[i].likes}  likes</p>
// //                   </a>
// //
// //                 </div>
// //               </div>
// //               <div class="card-footer py-3 border-0" style="background-color: #f8f9fa;">
// //                     <div id="replyDiv" style="width: 80%; margin-left: 10%">
// //
// //                     ${cReplies}
// //
// //                     </div>
// //                 <div  class="d-flex flex-start w-100">
// //                   <div class="form-outline w-100">
// //
// //                 <textarea class="form-control" style="width: 80%; margin-left: 10%; font-size: small;" id="textAreaExample" placeholder="Your comment must be less than 1024 letter..." rows="2"
// //                           style="background: #fff;"></textarea>
// //                   </div>
// //                 </div>
// //
// //                 <div style="width: 80%; margin-left: 10%" class="float-end mt-2 pt-1">
// //                   <button type="button" class="btn btn-primary btn-sm" onclick="">Comment</button>
// //                   <button type="button" class="btn btn-outline-primary btn-sm">Cancel</button>
// //                 </div>
// //               </div>
// //             </div>
// //             <br/><br/>
// //
// // `;
//
//
//                     } else {
//                         // singleReply = "";
//                     }
//
//                     //    $("commentDiv").html(replyBody);
//
//                     // for (let k = 0; k < replies.length; k++) {
//                     //     if (comments[i].replyTo == replies[k].msgID) {
//                     //                 cReplies += replies[k].form;
//                     //     } else {
//                     //         cReplies = "no replies";
//                     //     }
//                     // }
//
//                 }


// Replies Abstract
//  let cReplies="";
//  for (let k = 0; k < replies.length; k++){
//
//     if(replies[k].msgID == comments[i].msgID){
//             console.log(replies[k].msgID  +  replies[k].form)
//     }else{
//         replies="";
//     }
// }
// let rep =[];
// for (let j = 0; j < Object.keys(receivedJson.Messages).length; j++) {
//   //  console.log( receivedJson.Messages[i].id + "i");
//   // console.log(receivedJson.Messages[j].replyTo +  "j");
//     if (receivedJson.Messages[j].replyTo ==  receivedJson.Messages[i].id) {
//             rep += receivedJson.Messages[j].messageContents;
//         console.log(rep);
//     }
//  }

// <!--                    <script>-->
// <!--                    for(let i =0; i<comments.length; i++ ){-->
// <!--                         for(let j =0; j<replies.length; j++ ){-->
// <!--                             if(comments[i].replyTo == replies[j].msgID){-->
// <!--                                 singleReply = replies[j].form;-->
// <!--                             }else{-->
// <!--                                 singleReply = "";-->
// <!--                             }-->
// <!--                         }-->
//
// <!--                    }-->
// <!--</script>-->


// <style>
//     .switch {
//     position: relative;
//     display: inline-block;
//     width: 45px;
//     height: 20px;
// }
//
//     .switch input {
//     opacity: 0;
//     width: 0;
//     height: 0;
// }
//
//     .slider {
//     position: absolute;
//     cursor: pointer;
//     top: 0;
//     left: 0;
//     right: 0;
//     bottom: 0;
//     background-color: #be1b1b;
//     -webkit-transition: .4s;
//     transition: .4s;
// }
//
//     .slider:before {
//     position: absolute;
//     content: "";
//     height: 16px;
//     width: 16px;
//     left: 0px;
//     bottom: 2px;
//     background-color: white;
//     -webkit-transition: .4s;
//     transition: .4s;
// }
//
//     input:checked + .slider {
//     background-color: #25a407;
// }
//
//     input:focus + .slider {
//     box-shadow: 0 0 1px #2196F3;
// }
//
//     input:checked + .slider:before {
//     -webkit-transform: translateX(26px);
//     -ms-transform: translateX(26px);
//     transform: translateX(26px);
// }
//
//     /* Rounded sliders */
//     .slider.round {
//     border-radius: 24px;
// }
//
//     .slider.round:before {
//     border-radius: 50%;
// }
// </style>
// <span style="color: red">${invisible}</span>
// <span style="color: green">${visible}</span>
// ${visibilityButtonChecked}
// ${visibilityButtonUnchecked}

//last modification
// <div>
//     <style>
//
//         .switch {
//         position: relative;
//         display: inline-block;
//         width: 80px;
//         height: 16px;
//     }
//
//         .switch input {display:none;}
//
//         .slider {
//         position: absolute;
//         cursor: pointer;
//         top: 0;
//         left: 0;
//         right: 0;
//         bottom: 0;
//         background-color: #ca2222;
//         -webkit-transition: .4s;
//         transition: .4s;
//     }
//
//         .slider:before {
//         position: absolute;
//         content: "";
//         height: 10px;
//         width: 10px;
//         left: 4px;
//         bottom: 4px;
//         background-color: white;
//         -webkit-transition: .4s;
//         transition: .4s;
//     }
//
//         input:checked + .slider {
//         background - color: #2ab934;
//     }
//
//         input:focus + .slider {
//         box - shadow: 0 0 1px #2196F3;
//     }
//
//         input:checked + .slider:before {
//         -webkit - transform: translateX(55px);
//         -ms-transform: translateX(55px);
//         transform: translateX(55px);
//     }
//
//         /*------ ADDED CSS ---------*/
//         .on
//         {
//             display: none;
//         }
//
//         .on, .off
//         {
//             color: white;
//             position: absolute;
//             transform: translate(-50%,-50%);
//             top: 50%;
//             left: 50%;
//             font-size: 9px;
//             font-family: Verdana, sans-serif;
//         }
//
//         input:checked+ .slider .on
//         {display: block;}
//
//         input:checked + .slider .off
//         {display: none;}
//
//         /*--------- END --------*/
//
//         /* Rounded sliders */
//         .slider.round {
//         border - radius: 34px;
//     }
//
//         .slider.round:before {
//         border - radius: 50%;}
//
//     </style>
//     <label className="switch">
//         <input type="checkbox" id="togBtn-${comments[i].msgID}" onClick="getVisibility(${comments[i].msgID})">
//             <div className="slider round">
//                 <!--ADDED HTML -->
//                 <span className="on" id="visible">visible</span>
//                 <span className="off" id="invisible">invisible</span>
//                 <!--END-->
//             </div>
//     </label>
// </div>
//


// if(comments[i].visible==false){
//     invisible = "invisible";
//     visible = "";
//     visibilityButtonUnchecked =
//
//         ` <label class="switch">
//
//   <input id="visibility" type="checkbox" unchecked value="false"  onclick="getVisibility(this.value);">
//   <span class="slider round"></span>
// </label>  `;
//     visibilityButtonChecked = ` `;
//
//
// }else {
//     visible = "visible";
//     invisible = "";
//     visibilityButtonChecked =
//
//         ` <label class="switch">
//
//   <input id="visibility" type="checkbox" checked value="true" onclick="getVisibility(this.value);">
//   <span class="slider round"></span>
// </label>  `;
//     visibilityButtonUnchecked = ``;
//
// }


//togBtn
//                 .switch {
//                         position: relative;
//                         display: inline-block;
//                         width: 90px;
//                         height: 34px;
//                     }
//
//                 .switch input {display:none;}
//
//                 .slider {
//                         position: absolute;
//                         cursor: pointer;
//                         top: 0;
//                         left: 0;
//                         right: 0;
//                         bottom: 0;
//                         background-color: #ca2222;
//                         -webkit-transition: .4s;
//                         transition: .4s;
//                     }
//
//                 .slider:before {
//                         position: absolute;
//                         content: "";
//                         height: 26px;
//                         width: 26px;
//                         left: 4px;
//                         bottom: 4px;
//                         background-color: white;
//                         -webkit-transition: .4s;
//                         transition: .4s;
//                     }
//
//                     input:checked + .slider {
//                         background-color: #2ab934;
//                     }
//
//                     input:focus + .slider {
//                         box-shadow: 0 0 1px #2196F3;
//                     }
//
//                     input:checked + .slider:before {
//                         -webkit-transform: translateX(55px);
//                         -ms-transform: translateX(55px);
//                         transform: translateX(55px);
//                     }
//
//                     /*------ ADDED CSS ---------*/
//                 .on
//                     {
//                         display: none;
//                     }
//
//                 .on, .off
//                     {
//                         color: white;
//                         position: absolute;
//                         transform: translate(-50%,-50%);
//                         top: 50%;
//                         left: 50%;
//                         font-size: 10px;
//                         font-family: Verdana, sans-serif;
//                     }
//
//                     input:checked+ .slider .on
//                     {display: block;}
//
//                     input:checked + .slider .off
//                     {display: none;}
//
//                     /*--------- END --------*/
//
//                     /* Rounded sliders */
//                 .slider.round {
//                         border-radius: 34px;
//                     }
//
//                 .slider.round:before {
//                         border-radius: 50%;}
//
//                     <label className="switch">
//                         <input type="checkbox" id="togBtn">
//                             <div className="slider round">
//                                 <!--ADDED HTML -->
//                                 <span className="on">ON</span>
//                                 <span className="off">OFF</span>
//                                 <!--END-->
//                             </div>
//                     </label>


//STATISTICS
//Statistics
let totalLikes = 0;
let totalComments = 0;
let totalReplies = 0;
//  let totalUsers = 0;
// let tUsers  =[];
//  let allUsers = 0;


// for (let i = 0; i <  comments.length; i++) {
//
//
//     //total likes
//     totalLikes += comments[i].likes;
//
//     //total comments
//     if(comments[i].replyTo==null){
//         totalComments++;
//     }
//
//     //total comments
//     if(comments[i].replyTo!=null){
//         totalReplies++;
//     }
// }


// else {

//     comments.push({
//
//         posterID: receivedJson.Messages[i].poster,
//         displayName: posterTep,
//         sessionID: receivedJson.Messages[i].session,
//         msgID: receivedJson.Messages[i].id,
//         msgContents: receivedJson.Messages[i].messageContents,
//         timestamp: receivedJson.Messages[i].timestamp,
//         replyTo: receivedJson.Messages[i].replyTo,
//         visible: receivedJson.Messages[i].visible,
//         likes: receivedJson.Messages[i].likes,
//     });
//     JSON.stringify(comments);
//
// }
// }


//repliesTmp for j loop
// repliesTmp will be repliesTmp += ``; will be inserted inside body the static one
//                                 repliesTmp += `
//  <div id="replyDiv" style="width: 80%; margin-left: 10%">
//
//     <div class="card-body">
//                 <div class="d-flex flex-start align-items-center">
//
//                   <div>
//                   <div style="  position: absolute;top: 8px;right: 16px; color: #005cbf ;font-size: 14px;">
//                   <p>
//
//
// <!--    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">edit</button>-->
//
//
//                    </p>
//                    </div>
//                                    <div style="font-size: 12px; margin-left: 82%">
//                    <a data-toggle="modal" href="" onclick=" showUpdateModal(${comments[j].posterID},${comments[j].sessionID},${comments[j].msgID},'${comments[j].msgContents}');">${editBtnRep}</a>
//                   <a href="" onclick="deleteMessage(${comments[j].msgID}, ${comments[j].posterID},${comments[j].sessionID})">${deleteBtnRep}</a>
// </div>
//                     <h6 class="fw-bold text-primary mb-1"> ${comments[j].displayName}</h6>
//                     <p class="text-muted small mb-0">
//                         Shared publicly ${dateFormat}
//                     </p>
//
//                   </div>
//                 </div>
//
//                 <p class="mt-3 mb-4 pb-2">
//                    ${comments[j].msgContents}
//                 </p>
//
//                 <div class="small d-flex justify-content-start">
//
//
//
//                   <a href="form-control" class="d-flex align-items-center me-3">
//                     <i class="far fa-comment-dots me-2"></i>
//
//                   </a>                <a href="#!" class="d-flex align-items-center me-3">
//                     <i class="far fa-comment-dots me-2"></i>
//                     <p class="mb-0">${comments[j].likes}  likes</p>
//                   </a>
//
//                 </div>
//               </div>
//
//
//
//
//
//
// </div><br/>
//
//
// `;

// in case to give more sorting or eliminating to replies use this way

//fill repliesTmp
// for(let j =0; j < comments.length; j++){
//
//
//
//     //this condition for filling a string/Html replies array for specific comment and introduce them ordered in UI
//     if(comments[j].replyTo == comments[i].msgID){
//
//               //  show hide controlls
//                 let editBtnRep = "";
//                 let deleteBtnRep = "";
//                 if (ownerID == comments[j].posterID) {
//                     editBtnRep = "edit";
//                     deleteBtnRep = "delete";
//                 } else {
//
//                     editBtnRep = "";
//                     deleteBtnRep = "";
//                 }
//
//
//                 repliesArr.push(comments[j]);
//                 countReplies++;
//
//
//     } //end of  j loop's condition
//
// }//end of nested j loop

//repliesArr.sort((a, b) => a.msgID - b.msgID);

//repliesTmp for r loop

//                for(let r =0; r < repliesArr.length; r++) {
//
//                     if(comments[r].replyTo == comments[i].msgID) {
//
//
//                         let editBtnRep = "";
//                         let deleteBtnRep = "";
//                         if (ownerID == comments[r].posterID) {
//                             editBtnRep = "edit";
//                             deleteBtnRep = "delete";
//                         } else {
//
//                             editBtnRep = "";
//                             deleteBtnRep = "";
//                         }
//
//                         repliesTmp += `
//  <div id="replyDiv" style="width: 100%; margin-left: 10%">
//   <div style="padding-left: 85% ">
//                     <a data-toggle="modal" href="" onclick=" showUpdateModal(${repliesArr[r].posterID},${repliesArr[r].sessionID},${repliesArr[r].msgID},'${repliesArr[r].msgContents}');">${editBtnRep}</a>
//                   <a href="" onclick="deleteMessage(${repliesArr[r].msgID}, ${repliesArr[r].posterID},${repliesArr[r].sessionID})">${deleteBtnRep}</a>
//  </div>
//     <div class="card-body">
//                 <div class="d-flex flex-start align-items-center">
//
//                   <div>
//                   <div style="  position: absolute;top: 8px;right: 16px; color: #005cbf ;font-size: 14px;">
//                   <p>
//
//
// <!--    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">edit</button>-->
//
//
//                    </p>
//                                    </div>
//
//                    </div>
//                     <h6 class="fw-bold text-primary mb-1"> ${repliesArr[r].displayName}</h6>
//
//                   </div>
//
//                 <p class="mt-3 mb-4 pb-2">
//
//                    ${repliesArr[r].msgContents}
//                 </p>
//
//                 <div class="small d-flex justify-content-start">
//
//
//
//                   <a href="form-control" class="d-flex align-items-center me-3">
//                     <i class="far fa-comment-dots me-2"></i>
//
//                   </a>                <a href="#!" class="d-flex align-items-center me-3">
//                     <i class="far fa-comment-dots me-2"></i>
//                     <p class="mb-0">${repliesArr[r].likes}  likes</p>
//                   </a>
//
//                 </div>
//               </div>
//
//
//
//
//
//
// </div><br/>
//
//
// `;
//
//                     }
//
//
//               }

//   console.log(repliesArr);
// repliesArr = [];
//this condition for popping an element of "string/Html replies" being shown as a comment


console.log("Begin fetch");
connect();

// $(function () {
//     connect();
//     console.log("Begin fetch");
//
// });