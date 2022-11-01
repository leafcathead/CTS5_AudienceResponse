/**
 * We can post the request through two ways first:
 * AJAX
 **/

//joinSession


function joinSession() {
    let div =  document.getElementById('userMenuDiv');
    const data = {
        // password: $("#passO").val()
        password: $("#sessionIdInput").val()

    };
    console.log(data);
    $.ajax({
        url: 'http://localhost:8080/session/joinSession',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        data: JSON.stringify(data),
        type: 'POST',
        dataType: 'JSON',

        success: function (resp) {
            console.log(resp);
            //   const userId = resp.newUserID;
            var par =`<p> USER NAME ID:  ${resp.newUserID} </p> <br/> <p>SESSION ID: ${resp.newSessionID}  </p>`;
            div.innerHTML = par;
         //   document.getElementById('outputDiv').textContent = resp.newUserID;

        }
    });

}


//createSession
function createSession() {

    let div =  document.getElementById('createSessionDiv');

    fetch("http://localhost:8080/session/createSession")
    .then(function(resp) {
        return resp.json();
    })
    .then(function(data){
        console.log(data);
        var par =`<p>userID:  ${data.newUserID} </p> <br/> 
                      <p>randomPassword: ${data.randomPassword}  </p> <br/>
                      <p>newSessionID: ${data.newSessionID}  </p>`;
        div.innerHTML = par;

    } );


            //   const userId = resp.newUserID;

            // //   document.getElementById('outputDiv').textContent = resp.newUserID;
}















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
