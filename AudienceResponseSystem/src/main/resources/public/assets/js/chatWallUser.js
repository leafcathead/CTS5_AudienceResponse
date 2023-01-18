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
var token = "";

//check if user logged in
if(userID==null){
    location.replace("index.html")
}


//post comment
function postComment() {
    checkSessionStatus();



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
            console.log(data)
            //getPosts();
            const parg = `

           
          your comment has been successfully posted<br/>
           <button type="button" class="btn btn-primary btn-sm" onclick="postAgain()">post again</button>
            `;

            answer.innerHTML = parg;
            answer = "";
            getPosts()
        })
}else{
    showResultTextArea();
}
}


function postReply(posterID=$("#RposterID").val(),sessionID=$("#RsessionID").val(),msgID=$("#RmsgID").val(),msgContent=$("#RmsgContent").val()) {
    // let answer = document.getElementById('');
    checkSessionStatus();

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


/**
 *
 * webSocket
 *
 *
 * */
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
        //   stompClient.send("/app/getMessages", {}, stringObj);

        var myDate = {id: sessionID};
        var stringObj = JSON.stringify(myDate);
        stompClient.send("/app/getMessages", {}, stringObj);


        let urlMessage = "/user/"+sessionID+"/topic/retrieveMessages";
        stompClient.subscribe(urlMessage, getPosts);

        // let urlPanic ="/user/"+sessionID+"/topic/retrievePanic";
        // stompClient.subscribe(urlPanic, panic);
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
    }

);


// function getPostWS() {
//     // if(greeting() == false){
// // checkSessionStatus();
//     checkSessionStatus();
//
//
// RUN THIS WHENEVER THE JAVASCRIPT FILE IS OPENED SO THAT IT AUTO CONNECTS

//connect(); // MOVED TO VERY BOTTOM









// get posts from DB WebSocket(Recommended way)receivedJson
// function getPosts(responseData) {
//     // if(greeting() == false){
//     // checkSessionStatus();
//     //   console.log(checkSessionStatus());
//
//
//
//     console.log(responseData);
//     let receivedJson = JSON.parse(responseData.body);
//     let comments = [];
//     let allUsers = 0;
//
//     let body = $("#cardDiv").html();
//     console.log("Websocket response v2")
//     console.log(receivedJson);
//
//     body= "";
//     //pulling data from Json server side file and pushing the comments inside well-ordered js array[]
//     for (let i = 0; i < Object.keys(receivedJson.Messages).length; i++) {
//
//         if (receivedJson.Messages[i].visible == true) {
//             for (let k = 0; k < Object.keys(receivedJson.Messages).length; k++) {
//
//                 if (receivedJson.Messages[i].poster.id == receivedJson.Messages[k].poster) {
//
//                     comments.push({
//                         posterID: receivedJson.Messages[k].poster,
//                         displayName: receivedJson.Messages[i].poster.displayName,
//                         sessionID: receivedJson.Messages[k].session,
//                         msgID: receivedJson.Messages[k].id,
//                         timestamp: receivedJson.Messages[k].timestamp,
//                         msgContents: receivedJson.Messages[k].messageContents,
//                         replyTo: receivedJson.Messages[k].replyTo,
//                         visible: receivedJson.Messages[k].visible,
//                         likes: receivedJson.Messages[k].likes
//                     });
//
//                 }
//             } //end of k loop
//             if (receivedJson.Messages[i].poster.id) {
//
//
//                 comments.push({
//                     posterID: receivedJson.Messages[i].poster.id,
//                     displayName: receivedJson.Messages[i].poster.displayName,
//                     sessionID: receivedJson.Messages[i].session,
//                     msgID: receivedJson.Messages[i].id,
//                     timestamp: receivedJson.Messages[i].timestamp,
//                     msgContents: receivedJson.Messages[i].messageContents,
//                     replyTo: receivedJson.Messages[i].replyTo,
//                     visible: receivedJson.Messages[i].visible,
//                     likes: receivedJson.Messages[i].likes
//                 });
//             }
//
//         }
//
//     }
//     console.log("comments", comments)
//     comments.sort((a, b) => a.msgID - b.msgID);
//
//
// //browsing the comments[] array and control it in several aspects
//     for (let i = 0; i < comments.length; i++) {
//
//
//         let countReplies = 0;
//
//
// //hide comment's user controllers "never give body any js executing codes (variables & []  only)"
//         let editBtn = "";
//         let deleteBtn = "";
//         if (userID == comments[i].posterID) {
//
//             editBtn = "edit";
//             deleteBtn = "delete";
//         } else {
//
//             editBtn = "";
//             deleteBtn = "";
//         }
//
//         let repliesTmp = "";
//         //  let repliesArr = [];
//
//         let timeStamp = comments[i].timestamp;
//         let dateFormat = new Date(timeStamp);
//
//
//         //fill repliesTmp
//         for (let j = 0; j < comments.length; j++) {
//
//             let time = comments[j].timestamp;
//             let dateFormatRep = new Date(time);
//
//             //this condition for filling a string/Html replies array for specific comment and introduce them ordered in UI
//             if (comments[j].replyTo == comments[i].msgID) {
//                 let editBtnRep = "";
//                 let deleteBtnRep = "";
//                 if (userID == comments[j].posterID) {
//                     editBtnRep = "edit";
//                     deleteBtnRep = "delete";
//                 } else {
//
//                     editBtnRep = "";
//                     deleteBtnRep = "";
//                 }
//
//
//                 countReplies++;
//
//                 // repliesTmp will be repliesTmp += ``; will be inserted inside body the static one
//                 repliesTmp += `
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
//             } //end of nested j loop's condition
//
//         }//end of nested j loop
//
//
//         if (comments[i].replyTo == null) {
//
//
// //initia
//
//
//             body += `
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
//
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
//                   <a data-toggle="modal" href="" onclick="showReplyModal(userID,${comments[i].sessionID},${comments[i].msgID},'${comments[i].msgContents}')" class="d-flex align-items-center me-3">
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
//                   <button type="button" class="btn btn-primary btn-sm" onclick="showReplyModal(userID,${comments[i].sessionID},${comments[i].msgID})">reply</button>
//
//                 </div>
//               </div>
//             </div>
//             <br/><br/>
//
// `;
//             dateFormat = "";
//             //console.log("comment");
//         } else {
//             //  comments.pop();
//         }
//
//
//         // repliesTmp = "";
//         $("#cardDiv").html(body);
//
//     }
//         //end of Main for loop
//     // }
//
//
//     console.log(comments);
//
//
//     if (comments.length == 0) {
//         let span = `
//                 <span style="text-align: center; font-size: 20px;">no comments posted or not visible yet &nbsp;&nbsp;  :_(</span>
//                 `;
//         $("#noCommentsYet").html(span);
//
//     }
//
//
//     // end of .then(receivedJson)
//
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





// Restful API fetch comments replies
function getPosts() {
  checkSessionStatus();

    let comments =[];
    let allUsers = 0;
    let body = $("#cardDiv").html();
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
        .then((receivedJson) => {

            console.log(receivedJson);
            //pulling data from Json server side file and pushing the comments inside well-ordered js array[]

            for (let i = 0; i < Object.keys(receivedJson.Messages).length; i++) {


                 // if(receivedJson.Messages[i].visible == true) {
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

            console.log(comments);


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
                    } else {
                        //  comments.pop();
                    }


                    // repliesTmp = "";
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
        }) // end of .then(receivedJson)

    console.log(comments);
    comments= [];
    body= "";


}



function likeMessage(msgID){
    checkSessionStatus();

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
    checkSessionStatus();

    $("#exampleModal").modal('show');

    $("#msgID").val(msgID);
    $("#posterID").val(posterID);
    $("#sessionID").val(sessionID);
    $("#msgContent").val(msgContent);


    console.log(posterID,sessionID, msgID, msgContent);

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
    checkSessionStatus();


    $("#replyModal").modal('show');

    $("#RmsgID").val(msgID);
    $("#RposterID").val(posterID);
    $("#RsessionID").val(sessionID);
    $("#RmsgContent").val("");


    console.log(posterID,sessionID, msgID);

}






//update displayname
function updateDisplayname(){
    checkSessionStatus();


    $("#myModal").modal('show');
    $("#newDisNameField").val(displayname);
    // data-toggle="modal" data-target="#myModal"
    console.log("posterID: " + userID +"  sessionID: " + sessionID +"  displayname:  " + $("#newDisNameField").val());
    //VPN is not working updateMessageContent type PUT is not yet tested

    //  let answer = document.getElementById('answer');


}



function newDisplyname(){
    checkSessionStatus();
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
                const parg = `


      your display name has been successfully updated<br/>

        `;

                answer.innerHTML = parg;


                getPosts();
            } else {

                console.log("Error has been occurred");
            }



        })


}


function panic(code){



    checkSessionStatus();
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



        })




}




function deleteMessage(msgID, posterID, sessionID){
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
            if(receivedJson != true){
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








//testing codes

// fetch(SITE_URL + '/message/getMessages', {
//     method: 'POST',
//     body: data,
// })
//     .then(res => res.json())
//     .then(data => console.log(data))

//     $.ajax({
//         url: SITE_URL + '/message/getMessages',
//         headers: {
//             'Content-Type': 'application/json',
//             'Accept': 'application/json',
//             'X-CSRF-TOKEN': token
//         },,
//         port: 443
//         data: JSON.stringify(data),
//         type: 'POST',
//         dataType: 'JSON',
//         success: function (resp) {
//           //  if(resp.newUserID != null) {
// //Setting the localstorages keys and values
//             //    localStorage.setItem('userID', resp.newUserID);
//            //     localStorage.setItem('sessionID', resp.newSessionID);
//
//
//            //     console.log("password is exist");
//                 console.log(resp);
//                 //   const userId = resp.newUserID;
//                 // var par = `<p> USER NAME ID:  ${resp.newUserID} </p> <br/> <p>SESSION ID: ${resp.newSessionID}  </p>`;
//                 // div.innerHTML = par;
//                 //   document.getElementById('outputDiv').textContent = resp.newUserID;
//          //       location.replace("chatWallUser.html")
//       //      }else{
//
//          //       const wrong =  window.confirm("session is not exsit or password is incorrect");
//            //     console.log("your password is not exist");
//                 //   document.getElementById('userMenuDiv').textContent = "Your password is NOT exist";
//
//         //    }
//
//         }
//
//
//     });




//111
//get posts from DB user
// function getPosts() {
//        const posts = document.getElementById('cardDiv');
//
//     const data = { id: sessionID };
//     console.log(data);
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
//         .then((data) => {
//          //   console.log(data)
//
//             for (var i = 0; i <  Object.keys(data).length; i++) {
//             //It not an array!!!
//
//             console.log(data.Messages[i].messageContents);
//
//                 let timeStamp = data.Messages[i].timestamp;
//                 let dateFormat = new Date(timeStamp);
//
//                 const card =
//                     `
//             <div class="card">
//               <div class="card-body">
//                 <div class="d-flex flex-start align-items-center">
//
//                   <div>
//                     <h6 class="fw-bold text-primary mb-1">Written by:  ${data.Messages[i].poster.id }#user</h6>
//                     <p class="text-muted small mb-0">
//                       Shared publicly - ${dateFormat }
//                     </p>
//                   </div>
//                 </div>
//
//                 <p class="mt-3 mb-4 pb-2">
//
//                        ${data.Messages[i].messageContents}
//                 </p>
//
//                 <div class="small d-flex justify-content-start">
//
//                   <a href="#!" class="d-flex align-items-center me-3">
//                     <i class="far fa-comment-dots me-2"></i>
//                     <p class="mb-0">reply&nbsp;&nbsp;&nbsp;&nbsp;</p>
//                   </a>                <a href="#!" class="d-flex align-items-center me-3">
//                     <i class="far fa-comment-dots me-2"></i>
//                     <p class="mb-0">${data.Messages[i].likes }  likes</p>
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
//                }
//
//
//
//
//             //    }
//         })
//
// }


// //get posts new
// function getPosts() {
//
//     let comments =[];
//     let body = $("#cardDiv").html();
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
//
//             console.log(receivedJson);
//             //pulling data from Json server side file and pushing the comments inside well-ordered js array[]
//
//             for (let i = 0; i <  Object.keys(receivedJson.Messages).length; i++) {
//                 if(receivedJson.Messages[i].visible===true){
//
//                 for(let k = 0; k <  Object.keys(receivedJson.Messages).length; k++){
//
//                     if (receivedJson.Messages[i].poster.id == receivedJson.Messages[k].poster){
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
//                 // else {
//
//                 //     comments.push({
//                 //
//                 //         posterID: receivedJson.Messages[i].poster,
//                 //         displayName: posterTep,
//                 //         sessionID: receivedJson.Messages[i].session,
//                 //         msgID: receivedJson.Messages[i].id,
//                 //         msgContents: receivedJson.Messages[i].messageContents,
//                 //         timestamp: receivedJson.Messages[i].timestamp,
//                 //         replyTo: receivedJson.Messages[i].replyTo,
//                 //         visible: receivedJson.Messages[i].visible,
//                 //         likes: receivedJson.Messages[i].likes,
//                 //     });
//                 //     JSON.stringify(comments);
//                 //
//                 // }
//             } console.log(comments);
//
//
//
//
//
//
//
// //browsing the comments[] array and control it in several aspects
//             for (let i = 0; i <  comments.length; i++) {
//
//
//                 let countReplies = 0;
//
//
// //hide comment's user controllers "never give body any js executing codes (variables & []  only)"
//                 let editBtn = "";
//                 let deleteBtn = "";
//                 if (userID == comments[i].posterID) {
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
//                 let timeStamp = comments[i].timestamp;
//                 let dateFormat = new Date(timeStamp);
//
//                 //fill repliesTmp
//                 for(let j =0; j < comments.length; j++){
//
//
//
//                     //this condition for filling a string/Html replies array for specific comment and introduce them ordered in UI
//                     if(comments[j].replyTo == comments[i].msgID){
//                         let editBtnRep = "";
//                         let deleteBtnRep = "";
//                         if (userID == comments[j].posterID) {
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
// ` ;
//
//
//
//                     } //end of nested j loop's condition
//
//                 }//end of nested j loop
//
//                 //this condition for popping an element of "string/Html replies" being shown as a comment
//                 if (comments[i].replyTo == null){
//
//
//
//
//
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
//                    <a data-toggle="modal" href="" onclick=" showUpdateModal(${comments[i].posterID},${comments[i].sessionID},${comments[i].msgID},'${comments[i].msgContents}');">${editBtn}</a>
//                   <a href="" onclick="deleteMessage(${comments[i].msgID}, ${comments[i].posterID},${comments[i].sessionID})">${deleteBtn}</a>
//
//
//                    </p>
//                    </div>
//                     <h6 class="fw-bold text-primary mb-1"> ${comments[i].displayName}</h6>
//                     <p class="text-muted small mb-0">
//                         Shared publicly ${dateFormat}
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
//                   <a data-toggle="modal" href="" onclick="showReplyModal(userID,${comments[i].sessionID},${comments[i].msgID},'${comments[i].msgContents}')" class="d-flex align-items-center me-3">
//                     <i class="far fa-comment-dots me-2"></i>
//                     <p class="mb-0">${countReplies}  reply&nbsp;&nbsp;&nbsp;&nbsp;</p>
//                   </a>
//
//                   <a href="#!" class="d-flex align-items-center me-3">
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
//                   <button type="button" class="btn btn-primary btn-sm" onclick="showReplyModal(userID,${comments[i].sessionID},${comments[i].msgID},'${comments[i].msgContents}')">reply</button>
//                 </div>
//               </div>
//             </div>
//             <br/><br/>
//
// `;
//                     //console.log("comment");
//                 }else{
//                     //  comments.pop();
//                 }
//
//
//
//                 // repliesTmp = "";
//                 $("#cardDiv").html(body);
//
//
//
//
//
//
//
//
//                 //end of Main recievedJson for loop
//             }
//
//             }
//
//             if(comments.length == 0){
//                 let span = `
//                 <span style="text-align: center; font-size: 20px;">no comments posted or not visible yet &nbsp;&nbsp; :_(</span>
//                 `;
//                 $("#noCommentsYet").html(span);
//                 console.log("no comments posted or not visible yet      :_(");
//             }
//
//             console.log(comments);
//
//
//
//
//
//         }) // end of .then(receivedJson)
//
//
//     comments= [];
//     body= "";
//
//
//
//
//
//
//     //     let comments =[];
// //     let replies=[];
// //     let body = $("#cardDiv").html();
// //     fetch(SITE_URL + "/message/getMessages", {
// //         method: 'POST',
// //         body: JSON.stringify({
// //             id: sessionID
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
// //         .then((receivedJson) => {
// //
// //             console.log(receivedJson);
// //             //pulling data from Json server side file and pushing the comments inside well-ordered js array[]
// //             let posterTep = "";
// //             for (let i = 0; i <  Object.keys(receivedJson.Messages).length; i++) {
// //
// //                 if (receivedJson.Messages[i].poster.id) {
// //                     posterTep = receivedJson.Messages[i].poster.displayName;
// //
// //                     comments.push({
// //                         posterID: receivedJson.Messages[i].poster.id,
// //                         displayName: receivedJson.Messages[i].poster.displayName,
// //                         sessionID: receivedJson.Messages[i].session,
// //                         msgID: receivedJson.Messages[i].id,
// //                         timestamp: receivedJson.Messages[i].timestamp,
// //                         msgContents: receivedJson.Messages[i].messageContents,
// //                         replyTo: receivedJson.Messages[i].replyTo,
// //                         visible: receivedJson.Messages[i].visible,
// //                         likes: receivedJson.Messages[i].likes
// //                     });
// //                 } else {
// //
// //                     comments.push({
// //
// //                         posterID: receivedJson.Messages[i].poster,
// //                         displayName: posterTep,
// //                         sessionID: receivedJson.Messages[i].session,
// //                         msgID: receivedJson.Messages[i].id,
// //                         msgContents: receivedJson.Messages[i].messageContents,
// //                         timestamp: receivedJson.Messages[i].timestamp,
// //                         replyTo: receivedJson.Messages[i].replyTo,
// //                         visible: receivedJson.Messages[i].visible,
// //                         likes: receivedJson.Messages[i].likes,
// //                     });
// //                     JSON.stringify(comments);
// //
// //                 }
// //             }
// //             console.log(comments);
// //
// // //browsing the comments[] array and control it in several aspects
// //             for (let i = 0; i < comments.length; i++) {
// //
// //                 if(comments[i].visible===false && comments[i].posterID== userID || comments[i].visible===true){
// //
// // //hide comment's owner controllers "never give body any js executing codes (variables & []  only)"
// //                 let editBtn = "";
// //                 let deleteBtn = "";
// //                 if (userID == comments[i].posterID) {
// //                     editBtn = "edit";
// //                     deleteBtn = "delete";
// //                 } else {
// //
// //                     editBtn = "";
// //                     deleteBtn = "";
// //                 }
// //
// //                 let repliesTmp = "";
// //                 let timeStamp = comments[i].timestamp;
// //                 let dateFormat = new Date(timeStamp);
// //
// //                 //fill repliesTmp
// //                 for (let j = 0; j < comments.length; j++) {
// //
// //
// //                     //this condition for filling a string/Html replies array for specific comment and introduce them ordered in UI
// //                     if (comments[j].replyTo == comments[i].msgID) {
// //                         let editBtnRep = "";
// //                         let deleteBtnRep = "";
// //                         if (userID == comments[j].posterID) {
// //                             editBtnRep = "edit";
// //                             deleteBtnRep = "delete";
// //                         } else {
// //
// //                             editBtnRep = "";
// //                             deleteBtnRep = "";
// //                         }
// //
// //
// //                         // repliesTmp will be repliesTmp += ``; will be inserted inside body the static one
// //                         repliesTmp += `
// //  <div id="replyDiv" style="width: 80%; margin-left: 10%">
// //
// //     <div class="card-body">
// //                 <div class="d-flex flex-start align-items-center">
// //
// //                   <div>
// //                   <div style="  position: absolute;top: 8px;right: 16px; color: #005cbf ;font-size: 14px;">
// //                   <p>
// //
// //
// // <!--    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">edit</button>-->
// //
// //
// //                    </p>
// //                    </div>
// //                                    <div style="font-size: 12px; margin-left: 82%">
// //                    <a data-toggle="modal" href="" onclick=" showUpdateModal(${comments[j].posterID},${comments[j].sessionID},${comments[j].msgID},'${comments[j].msgContents}');">${editBtnRep}</a>
// //                   <a href="">${deleteBtnRep}</a>
// // </div>
// //                     <h6 class="fw-bold text-primary mb-1"> ${comments[j].displayName}</h6>
// //                     <p class="text-muted small mb-0">
// //                         Shared publicly ${dateFormat}
// //                     </p>
// //
// //                   </div>
// //                 </div>
// //
// //                 <p class="mt-3 mb-4 pb-2">
// //                    ${comments[j].msgContents}
// //                 </p>
// //
// //                 <div class="small d-flex justify-content-start">
// //
// //                   <a href="#!" class="d-flex align-items-center me-3">
// //                     <i class="far fa-comment-dots me-2"></i>
// //                     <p class="mb-0">reply&nbsp;&nbsp;&nbsp;&nbsp;</p>
// //                   </a>                <a href="#!" class="d-flex align-items-center me-3">
// //                     <i class="far fa-comment-dots me-2"></i>
// //                     <p class="mb-0">${comments[j].likes}  likes</p>
// //                   </a>
// //
// //                 </div>
// //               </div>
// //
// //
// //
// //
// //
// //
// // </div><br/>
// //
// //
// // `;
// //
// //
// //                     } //end of nested j loop's condition
// //
// //                 }//end of nested j loop
// //
// //                 //this condition for popping an element of "string/Html replies" being shown as a comment
// //                 if (comments[i].replyTo == null) {
// //
// //                     body += `
// //
// //                         <div class="card">
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
// //                     <h6 class="fw-bold text-primary mb-1"> ${comments[i].displayName}</h6>
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
// //                     <div style="width: 80%; margin-left: 10%">
// //
// //                   Replies here <br/>  ${repliesTmp}
// //
// //                     </div>
// //                 <div  class="d-flex flex-start w-100">
// //                   <div class="form-outline w-100">
// //
// //                 <textarea class="form-control" style="width: 80%; margin-left: 10%; font-size: small;" id="textAreaReplay-${comments[i].msgID}" placeholder="Your comment must be less than 1024 letter..." rows="2"
// //                           style="background: #fff;"></textarea>
// //                   </div>
// //                 </div>
// //                     <div id="snackbar">Your reply has been added</div>
// //                 <div style="width: 80%; margin-left: 10%" class="float-end mt-2 pt-1">
// //                   <button type="button" class="btn btn-primary btn-sm" onclick="postReply(${comments[i].posterID}, ${comments[i].sessionID}, ${comments[i].msgID},$('#textAreaReplay-'+${comments[i].msgID}).val() );">reply</button>
// //                   <button type="button" class="btn btn-outline-primary btn-sm">Cancel</button>
// //                 </div>
// //               </div>
// //             </div>
// //             <br/><br/>
// //
// // `;
// //                     //console.log("comment");
// //                 } else {
// //
// //                     //  comments.pop();
// //                     console.log("reply is removed from comments' body");
// //
// //                 }
// //
// //
// //                 // repliesTmp = "";
// //                 $("#cardDiv").html(body);
// //
// //
// //                 //end of Main for loop
// //             }
// //         }
// //
// //             console.log(comments);
// //
// //
// //         }) // end of .then(receivedJson)
// //
// //     comments= [];
// //     body= "";
//
// }

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
        });