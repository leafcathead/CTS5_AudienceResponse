


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









//get posts from DB
function getPosts() {
    let posts = document.getElementById('cardDiv');

    //let data = { id: sessionID };
    //console.log(data);
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
           //   console.log(receivedJson)

            let posterMap = new Map(); // Create a new Map to store the incoming messages
            for (var i = 0; i <  Object.keys(data).length; i++) {
                //It not an array!!!
                let m = data.Messages[i];
                let poster;

                if (typeof(m.poster) === 'number') {
                    poster = posterMap.get(m.poster);
                } else {
                    posterMap.set(m.poster.id, m.poster);
                    poster = m.poster;
                }

                console.log(m.messageContents);

                let timeStamp = m.timestamp;
                let dateFormat = new Date(timeStamp);

                const card =
                    `
            <div class="card">
              <div class="card-body">
                <div class="d-flex flex-start align-items-center">

                  <div>
                    <h6 class="fw-bold text-primary mb-1">Written by:  ${poster.id }#user</h6>
                    <p class="text-muted small mb-0">
                      Shared publicly - ${dateFormat }
                    </p>
                  </div>
                </div>

                <p class="mt-3 mb-4 pb-2">
                  
                       ${m.messageContents}
                </p>

                <div class="small d-flex justify-content-start">

                  <a href="#!" class="d-flex align-items-center me-3">
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">reply&nbsp;&nbsp;&nbsp;&nbsp;</p>
                  </a>                <a href="#!" class="d-flex align-items-center me-3">
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">${m.likes }  likes</p>
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

                posts.innerHTML += card;

            }




            //    }
        })

}
//setInterval(getPosts,1000);

function postAgain(){
    location.replace("chatWallOwner.html");
}


//edit form




function logOutOwner(){

    localStorage.removeItem('ownerID');
    localStorage.removeItem('sessionID');
    localStorage.removeItem('sessionPassword');

    location.replace("index.html")
}