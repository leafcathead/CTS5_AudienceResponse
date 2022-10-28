// fetch("http://localhost:8080/session/createSession")
//     .then(function(resp) {
//         return resp.json();
//     })
//     .then(function(data){
//         console.log(data);
//     } )




const api_url =  'http://localhost:8080/session/createSession';

async function getData(){
    const response = await fetch(api_url);
    const data = await response.json();

 //   var table = document.getElementById('joinSessionParag');
     var listX =  document.getElementById('createSession');

    for (var i = 0; i < data.length; i++){

    //     var row = `<tr>
    // 					<td>${data[i].firstName}</td>
    // 					<td>${data[i].lastName}</td>
    // 					<td>${data[i].mobile}</td>
    // 					<td>${data[i].address}</td>
    // 					<td>${data[i].gender}</td>
    // 			  </tr>`;
    //     table.innerHTML += row;

     var par =`<p>${data.newUserID}-- ${data[i].randomPassword} -- ${data[i].newSessionID}</p><br/>`;
         listX.innerHTML +=  par;

    console.log(data);
     }
}

getData();
//setInterval(destructJsonAndReformData,1000)
