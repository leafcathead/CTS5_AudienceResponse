//store data on the localstorage will be removed by user manually
//localStorage.setItem('password', '8D1F')


//store data on the session storage will be removed by closing the browser
//sessionStorage.setItem('password', '8D1F')

const sessionNewUserID = localStorage.getItem('sessionNewUserID');
const sessionNewSessionID = localStorage.getItem('sessionNewSessionID');

//check if user logged in
if(sessionNewUserID==null){
    location.replace("index.html")
}


function logOut(){

    localStorage.removeItem('sessionNewUserID');
    localStorage.removeItem('sessionNewSessionID');
    location.replace("index.html")
}