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




// {
//     "poster": {
//     "id":  97
// },
//     "session": {
//     "id":  54
// },
//     "messageContent": "I am the user of 97 attended to session 54 this is my comment"
// }


//get posts from DB
function getPosts() {
       const posts = document.getElementById('cardDiv');

    const data = { id: sessionID };
    console.log(data);
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
        .then((data) => {
         //   console.log(data)

            for (var i = 0; i <  Object.keys(data).length; i++) {
            //It not an array!!!

            console.log(data.Messages[i].messageContents);

                let timeStamp = data.Messages[i].timestamp;
                let dateFormat = new Date(timeStamp);

                const card =
                    `
            <div class="card">
              <div class="card-body">
                <div class="d-flex flex-start align-items-center">

                  <div>
                    <h6 class="fw-bold text-primary mb-1">Written by:  ${data.Messages[i].poster.id }#user</h6>
                    <p class="text-muted small mb-0">
                      Shared publicly - ${dateFormat }
                    </p>
                  </div>
                </div>

                <p class="mt-3 mb-4 pb-2">
                  
                       ${data.Messages[i].messageContents}
                </p>

                <div class="small d-flex justify-content-start">

                  <a href="#!" class="d-flex align-items-center me-3">
                    <i class="far fa-comment-dots me-2"></i>
                    <p class="mb-0">reply</p>
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
