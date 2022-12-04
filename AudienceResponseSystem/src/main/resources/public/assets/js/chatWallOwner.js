


const ownerID = localStorage.getItem('ownerID');
const sessionID = localStorage.getItem('sessionID');
const sessionPassword = localStorage.getItem('sessionPassword');


//post comment
function postComment() {
    let answer = document.getElementById('answer');

    const data = {

        poster: {
            id:  ownerID
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
                id:  ownerID
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



let comments =[];

//get posts from DB(JS way)
function getPosts() {
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
            for (let i = 0; i <  Object.keys(receivedJson.Messages).length; i++) {
                let timeStamp = receivedJson.Messages[i].timestamp;
                 let dateFormat = new Date(timeStamp);


                if(receivedJson.Messages[i].poster.id){


                comments.push({
                    posterID: receivedJson.Messages[i].poster.id,
                    poster: receivedJson.Messages[i].poster.displayName,
                    id: receivedJson.Messages[i].id,
                    messageContents: receivedJson.Messages[i].messageContents});
                }else{

                    comments.push({

                        poster: receivedJson.Messages[i].poster,
                        id: receivedJson.Messages[i].id,
                        messageContents: receivedJson.Messages[i].messageContents});
                }
                JSON.stringify(comments);

                body += `

            <div class="card" id="${receivedJson.Messages[i].id}">
              <div class="card-body">
                <div class="d-flex flex-start align-items-center">

                  <div>
                  <div style="  position: absolute;top: 8px;right: 16px; color: #005cbf ;font-size: 14px;">
                  <p>
                  <div class="edition">
                 <a id="ed" href="" onclick=" edit(${receivedJson.Messages[i].id},'${receivedJson.Messages[i].messageContents}');">edit</a>
                  <a href="">delete</a>
                  </div>
                   </p>
                   </div>
                    <h6 class="fw-bold text-primary mb-1">Written by:  ${comments[i].poster} user</h6>
                    <p class="text-muted small mb-0">
                        Shared publicly ${dateFormat}
                    </p>
                 
                  </div>
                </div>

                <p class="mt-3 mb-4 pb-2">

                       ${receivedJson.Messages[i].messageContents}
                        ${receivedJson.Messages[i].id}
                </p>

                <div class="small d-flex justify-content-start">

                  <a href="#!" class="d-flex align-items-center me-3">
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">reply&nbsp;&nbsp;&nbsp;&nbsp;</p>
                  </a>                <a href="#!" class="d-flex align-items-center me-3">
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">${receivedJson.Messages[i].likes}  likes</p>
                  </a>

                </div>
              </div>
              <div class="card-footer py-3 border-0" style="background-color: #f8f9fa;">
                <div class="d-flex flex-start w-100">
                  <div class="form-outline w-100">
                <textarea class="form-control" id="textAreaExample" placeholder="Your comment must be less than 1024 letter..." rows="4"
                          style="background: #fff;"></textarea>
                    <label class="form-label" for="textAreaExample">Message</label>
                  </div>
                </div>
                <div class="float-end mt-2 pt-1">
                  <button type="button" class="btn btn-primary btn-sm" onclick="">Post comment</button>
                  <button type="button" class="btn btn-outline-primary btn-sm">Cancel</button>
                </div>
              </div>
            </div>
<!--            I will change it to padding/Margin-->
            <br/><br/><br/><br/><br/><br/>

`;
                $("#cardDiv").html(body);

                console.log(comments);
            }

        })



}

// function hideMe(){
//
//     if(receivedJson.Messages[i].poster.id = ownerID){
//         $("div.editButton").hide();
//     }
// }

//edit form
function edit(id,content){

    console.log(id,content);


}






var data="";

//get posts from DB
// function getPost() {
//     let posts = document.getElementById('cardDiv');
//
//     //let data = { id: sessionID };
//     //console.log(data);
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






function postAgain(){
    location.replace("chatWallOwner.html");
}







function logOutOwner(){

    localStorage.removeItem('ownerID');
    localStorage.removeItem('sessionID');
    localStorage.removeItem('sessionPassword');

    location.replace("index.html")
}