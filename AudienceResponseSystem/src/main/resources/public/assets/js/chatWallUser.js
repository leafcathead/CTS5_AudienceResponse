//store data on the localstorage will be removed by user manually
//localStorage.setItem('password', '8D1F')


//store data on the session storage will be removed by closing the browser
//sessionStorage.setItem('password', '8D1F') .e.g
const userID = localStorage.getItem('userID');
const sessionID = localStorage.getItem('sessionID');

//check if user logged in
if(userID==null){
    location.replace("index.html")
}


//post comment
function postComment() {
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
    fetch("http://localhost:8080/message/postComment", {
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
            "Content-Type": "application/json;charset=UTF-8"
        }
    })
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            console.log(data)

            const parg = `

           
          your comment has been successfully posted<br/>
           <button type="button" class="btn btn-primary btn-sm" onclick="postAgain()">post again</button>
            `;

            answer.innerHTML = parg;
            answer = "";
        })

}




//post reply
function postReply(posterID, sessionID, msgID, msgContent) {
    // let answer = document.getElementById('');

    console.log(posterID, sessionID,msgID, msgContent);



    fetch("http://localhost:8080/message/postReply", {
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
            "Content-Type": "application/json;charset=UTF-8"
        }
    })
        .then((response) => {
            return response.json()
        })
        .then((data) => {
            console.log(data)

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




//get posts from DB user
// function getPosts() {
//        const posts = document.getElementById('cardDiv');
//
//     const data = { id: sessionID };
//     console.log(data);
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


//get posts new
function getPosts() {
    let comments =[];
    let replies=[];
    let body = $("#cardDiv").html();
    fetch("http://localhost:8080/message/getMessages", {
        method: 'POST',
        body: JSON.stringify({
            id: sessionID
        }),
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        }
    })
        .then((response) => {
            return response.json()
        })
        .then((receivedJson) => {

            console.log(receivedJson);
            //pulling data from Json server side file and pushing the comments inside well-ordered js array[]
            for (let i = 0; i <  Object.keys(receivedJson.Messages).length; i++) {
                if (receivedJson.Messages[i].poster.id ) {
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
                } else {

                    comments.push({

                        posterID: receivedJson.Messages[i].poster,
                        displayName: receivedJson.Messages[i].poster.displayName,
                        sessionID: receivedJson.Messages[i].session,
                        msgID: receivedJson.Messages[i].id,
                        msgContents: receivedJson.Messages[i].messageContents,
                        timestamp: receivedJson.Messages[i].timestamp,
                        replyTo: receivedJson.Messages[i].replyTo,
                        visible: receivedJson.Messages[i].visible,
                        likes: receivedJson.Messages[i].likes,
                    });
                    JSON.stringify(comments);
                }
            }

            console.log(comments);

//browsing the comments[] array and control it in several aspects
            for (let i = 0; i <  comments.length; i++) {





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
                let timeStamp = comments[i].timestamp;
                let dateFormat = new Date(timeStamp);

                //fill repliesTmp
                for(let j =0; j < comments.length; j++){



                    //this condition for filling a string/Html replies array for specific comment and introduce them ordered in UI
                    if(comments[j].replyTo == comments[i].msgID){
                        let editBtnRep = "";
                        let deleteBtnRep = "";
                        if (userID == comments[j].posterID) {
                            editBtnRep = "edit";
                            deleteBtnRep = "delete";
                        } else {

                            editBtnRep = "";
                            deleteBtnRep = "";
                        }



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
                  <a href="">${deleteBtnRep}</a>
</div>
                    <h6 class="fw-bold text-primary mb-1"> ${comments[j].posterID} user</h6>
                    <p class="text-muted small mb-0">
                        Shared publicly ${dateFormat}
                    </p>

                  </div>
                </div>

                <p class="mt-3 mb-4 pb-2">
                   ${comments[j].msgContents}
                </p>

                <div class="small d-flex justify-content-start">

                  <a href="#!" class="d-flex align-items-center me-3">
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">reply&nbsp;&nbsp;&nbsp;&nbsp;</p>
                  </a>                <a href="#!" class="d-flex align-items-center me-3">
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">${comments[j].likes}  likes</p>
                  </a>

                </div>
              </div>


       



</div><br/>


` ;



                    } //end of nested j loop's condition

                }//end of nested j loop

                //this condition for popping an element of "string/Html replies" being shown as a comment
                if (comments[i].replyTo == null){

                    body += `

                        <div class="card">

              <div class="card-body">
                <div class="d-flex flex-start align-items-center">

                  <div>
                  <div style="  position: absolute;top: 8px;right: 16px; color: #005cbf ;font-size: 14px;">
                  <p>


<!--    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">edit</button>-->
                   <a data-toggle="modal" href="" onclick=" showUpdateModal(${comments[i].posterID},${comments[i].sessionID},${comments[i].msgID},'${comments[i].msgContents}');">${editBtn}</a>
                  <a href="">${deleteBtn}</a>


                   </p>
                   </div>
                    <h6 class="fw-bold text-primary mb-1"> ${comments[i].posterID} user</h6>
                    <p class="text-muted small mb-0">
                        Shared publicly ${dateFormat}
                    </p>

                  </div>
                </div>

                <p class="mt-3 mb-4 pb-2">
                   ${comments[i].msgContents}
                </p>

                <div class="small d-flex justify-content-start">

                  <a href="#!" class="d-flex align-items-center me-3">
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">reply&nbsp;&nbsp;&nbsp;&nbsp;</p>
                  </a>                <a href="#!" class="d-flex align-items-center me-3">
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">${comments[i].likes}  likes</p>
                  </a>

                </div>
              </div>
              <div class="card-footer py-3 border-0" style="background-color: #f8f9fa;">
                    <div style="width: 80%; margin-left: 10%">

                  Replies here <br/>  ${repliesTmp}

                    </div>
                <div  class="d-flex flex-start w-100">
                  <div class="form-outline w-100">

                <textarea class="form-control" style="width: 80%; margin-left: 10%; font-size: small;" id="textAreaReplay-${comments[i].msgID}" placeholder="Your comment must be less than 1024 letter..." rows="2"
                          style="background: #fff;"></textarea>
                  </div>
                </div>
                    <div id="snackbar">Your reply has been added</div>
                <div style="width: 80%; margin-left: 10%" class="float-end mt-2 pt-1">
                  <button type="button" class="btn btn-primary btn-sm" onclick="postReply(${comments[i].posterID}, ${comments[i].sessionID}, ${comments[i].msgID},$('#textAreaReplay-'+${comments[i].msgID}).val() );">reply</button>
                  <button type="button" class="btn btn-outline-primary btn-sm">Cancel</button>
                </div>
              </div>
            </div>
            <br/><br/>

`;
                    //console.log("comment");
                }else{

                    //  comments.pop();
                    console.log("reply is removed from comments' body");

                }



                // repliesTmp = "";
                $("#cardDiv").html(body);









                //end of Main for loop
            }


            console.log(comments);


        }) // end of .then(receivedJson)



}




//show data model first then call updateComment();
function showUpdateModal(posterID,sessionID, msgID, msgContent){
    $("#exampleModal").modal('show');

    $("#msgID").val(msgID);
    $("#posterID").val(posterID);
    $("#sessionID").val(sessionID);
    $("#msgContent").val(msgContent);


    console.log(posterID,sessionID, msgID, msgContent);

}


//update comment
function updateComment(posterID=$("#posterID").val(),msgID=$("#msgID").val(),sessionID=$("#sessionID").val(),msgContent=$("#msgContent").val()){
//test
    console.log("i am update comment   " + posterID +"  " + msgID+"  " + sessionID+"  " + msgContent);
    //VPN is not working updateMessageContent type PUT is not yet tested


    // let answer = document.getElementById('answer');
    //
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
    // console.log(data);
    // fetch("http://localhost:8080/message/updateMessageContent", {
    //     method: 'PUT',
    //     body: JSON.stringify({
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
    //
    //     }),
    //     headers: {
    //         "Content-Type": "application/json;charset=UTF-8"
    //     }
    // })
    //     .then((response) => {
    //         return response.json()
    //     })
    //     .then((data) => {
    //         console.log(data)
    //
    //         const parg = `
    //
    //
    //   your comment has been successfully posted<br/>
    //    <button type="button" class="btn btn-primary btn-sm" onclick="postAgain()">post again</button>
    //     `;
    //
    //         answer.innerHTML = parg;
    //         answer = "";
    //     })


}




//update comment
function updateDisplayname(){
//test
    $("#myModal").modal('show');
    $("#newDisNameField").val(displayname);
    // data-toggle="modal" data-target="#myModal"
    console.log("posterID: " + ownerID +"  sessionID: " + sessionID +"  displayname:  " + $("#newDisNameField").val());
    //VPN is not working updateMessageContent type PUT is not yet tested

    //  let answer = document.getElementById('answer');
    //
    //
    //
    // fetch("http://localhost:8080/user/updateDisplayName", {
    //     method: 'PUT',
    //     body: JSON.stringify({
    //
    //
    // poster: {
    //     id: ownerID
    // },
    // session: {
    //     id: sessionID
    // },
    // displayName: displayname //input field user value
    //
    //
    //     }),
    //     headers: {
    //         "Content-Type": "application/json;charset=UTF-8"
    //     }
    // })
    //     .then((response) => {
    //         return response.json()
    //     })
    //     .then((data) => {
    //         console.log(data)
    //             if(data.Status == "SUCCESS"){
    //         const parg = `
    //
    //
    //   your display name has been successfully updated<br/>
    //
    //     `;
    //
    //         answer.innerHTML = parg;
    //         localStorage.setItem(displayname, data)
    //
    //             }else{
    //
    //                 console.log("Error has been occurred");
    //             }
    //
    //
    //
    //     })
}



function newDisplyname(){

}







function postAgain(){
    location.replace("chatWallUser.html");
}



//sign out for user chat wall
function logOutUser(){

    localStorage.removeItem('userID');
    localStorage.removeItem('sessionID');
    location.replace("index.html")
}













//testing codes

// fetch('http://localhost:8080/message/getMessages', {
//     method: 'POST',
//     body: data,
// })
//     .then(res => res.json())
//     .then(data => console.log(data))

//     $.ajax({
//         url: 'http://localhost:8080/message/getMessages',
//         headers: {
//             'Content-Type': 'application/json',
//             'Accept': 'application/json'
//         },
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
