//  Prevent reloading "Optional feature"
// window.onbeforeunload = function (){
//     return "If you left the page all registration data will be removed! ";
// }
//console.log('your password is:  '+ localStorageUserId);

var token = document.cookie;


// session check "localstorage session type owner/user type"
// if you have logged in before you will be directed to chatWallUser.html
const localStorageUserID = localStorage.getItem('userID');
const localStorageOwnerID = localStorage.getItem('ownerID');
const localStorageSessionID = localStorage.getItem('sessionID');
const localStorageSessionPassword = localStorage.getItem('sessionPassword');
 const SITE_URL = "https://i-lv-sopr-01.informatik.hs-ulm.de";
// const SITE_URL = "https://rhit-r90y2r8w";
//const SITE_URL = "https://DESKTOP-FUO6UAL";

//in case user
if (localStorageUserID != null) {
        const answer = window.confirm("Hi user, do you want to continue to the chat wall session?");
        if (answer) {
          //  alert("Ok was pressed");
            location.replace("chatWallUser.html")
        } else {
          //  alert("Cancel was pressed");
            localStorage.removeItem('userID');
            localStorage.removeItem('sessionID');
            location.replace("index.html")
        }

    // alert('do you want to continue to the chat wall session')
    //  alert('Your session value is  ' + sessionPassKey)
    //   location.replace("chatWallUser.html")
    }

//in case owner
if (localStorageOwnerID != null) {
    const answer = window.confirm("Hi owner, do you want to continue to the chat wall session?");
    if (answer) {
        //  alert("Ok was pressed");
        location.replace("chatWallOwner.html")
    } else {
        //  alert("Cancel was pressed");
        localStorage.removeItem('ownerID');
        localStorage.removeItem('sessionID');
        localStorage.removeItem('sessionPassword');
        location.replace("index.html")
    }
}

else {
    console.log("No localstorage session used yet! ");
}



/**
 * We can post the request through two ways first:
 * AJAX
 **/

//joinSession
const inputPass = $("#sessionIdInput").val();
function joinSession() {
    const data = {

        password: $("#sessionIdInput").val()

    };
    console.log(data);
    $.ajax({
        url: SITE_URL + '/session/joinSession',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'X-CSRF-TOKEN': token
        },
        data: JSON.stringify(data),
        type: 'POST',
        dataType: 'JSON',

        success: function (resp) {
           if(resp.newUserID != null) {
               console.log(resp);
//Setting the localstorages keys and values
              localStorage.setItem('userID', resp.newUserID);
              localStorage.setItem('sessionID', resp.newSessionID);
              localStorage.setItem('displayname', resp.newUserID);


               console.log("password is exist");
                console.log(resp);
                //   const userId = resp.newUserID;
                // var par = `<p> USER NAME ID:  ${resp.newUserID} </p> <br/> <p>SESSION ID: ${resp.newSessionID}  </p>`;
                // div.innerHTML = par;
                //   document.getElementById('outputDiv').textContent = resp.newUserID;
               location.replace("chatWallUser.html")
            }else{

               const wrong =  window.confirm("session is not exsit or password is incorrect");
                console.log("your password is not exist");
         //   document.getElementById('userMenuDiv').textContent = "Your password is NOT exist";

           }

        }


    });

}


//createSession
function createSession() {

    let div =  document.getElementById('createSessionDiv');

    fetch(SITE_URL + "/session/createSession", {
        method: 'GET',
            headers: {
                'X-CSRF-TOKEN': token
        },
        port: 443
    })
    .then(function(resp) {
        return resp.json();
    })
    .then(function(data){
    console.log(data);
    if(data.newUserID !=null){
        localStorage.setItem('ownerID', data.newUserID);
        localStorage.setItem('sessionID', data.newSessionID);
       localStorage.setItem('sessionPassword', data.randomPassword);
        localStorage.setItem('displayname', data.newUserID);

        console.log("it is stored!");
         location.replace("chatWallOwner.html");
    }

    else{
        console.log("check your data source");
    }
       // localStorage.setItem('ownerID', data.newUserID);
       // localStorage.setItem('sessionID', data.newSessionID);
      //  location.replace("chatWallOwner.html");
      //    var par =`<p>userID:  ${data.newUserID} </p> <br/>
      //                  <p>randomPassword: ${data.randomPassword}  </p> <br/>
      //                  <p>newSessionID: ${data.newSessionID}  </p> `;
      //    div.innerHTML = par;

    } );


            //   const userId = resp.newUserID;

            // //   document.getElementById('outputDiv').textContent = resp.newUserID;
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
        //localStorage.setItem('_csrf', data.token);
        token = data.token;
    });

















// function addPassword(){
//
//     let password =  $("#pinSession").val();
//
//     $.ajax({
//       url: 'http://localhost:8080/session/joinSession',
//       headers: {
//         'Content-Type': 'application/json',
//         'Accept': 'application/json'
//       },
//       data: JSON.stringify{(Password: password)},
//       type: 'POST',
//       dataType: 'JSON',
//
//       success: function (resp) {
//         console.log(resp);
//       }
//
//     });
//
// }












/**
 *
 * OR YOU CAN POST IT THROUGH
 * JAVASCRIPT
 *
 * */


// // const form = document.getElementById('form');
//
// form.addEventListener('submit', function(e) {
//   e.preventDefault();
//   const payload = new FormData(form);
//   console.log([payload])
//   fetch('http://localhost:8080/session/joinSession', {
//     method: 'POST',
//     dataType: 'json',
//     body: payload,
//   })
//           .then(res => res.json())
//           .then(data => console.log(data))
//
// })
//
//






//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
// // fetch("http://localhost:8080/session/createSession")
// //     .then(function(resp) {
// //         return resp.json();
// //     })
// //     .then(function(data){
// //         console.log(data);
// //     } )
//
//
//
//
// const api_url =  'http://localhost:8080/session/createSession';
//
// async function getData(){
//     const response = await fetch(api_url);
//     const data = await response.json();
//
//  //   var table = document.getElementById('joinSessionParag');
//      var listX =  document.getElementById('createSession');
//
//     for (var i = 0; i < data.length; i++){
//
//     //     var row = `<tr>
//     // 					<td>${data[i].firstName}</td>
//     // 					<td>${data[i].lastName}</td>
//     // 					<td>${data[i].mobile}</td>
//     // 					<td>${data[i].address}</td>
//     // 					<td>${data[i].gender}</td>
//     // 			  </tr>`;
//     //     table.innerHTML += row;
//
//      var par =`<p>${data.newUserID}-- ${data[i].randomPassword} -- ${data[i].newSessionID}</p><br/>`;
//          listX.innerHTML +=  par;
//
//     console.log(data);
//      }
// }
//
// getData();
// //setInterval(destructJsonAndReformData,1000)
