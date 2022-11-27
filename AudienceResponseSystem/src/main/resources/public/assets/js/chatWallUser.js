//store data on the localstorage will be removed by user manually
//localStorage.setItem('password', '8D1F')


//store data on the session storage will be removed by closing the browser
//sessionStorage.setItem('password', '8D1F')

const userID = localStorage.getItem('userID');
const sessionID = localStorage.getItem('sessionID');

//check if user logged in
if(userID==null){
    location.replace("index.html")
}


function logOut(){

    localStorage.removeItem('userID');
    localStorage.removeItem('sessionID');
    location.replace("index.html")
}