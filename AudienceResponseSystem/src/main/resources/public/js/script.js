fetch("http://localhost:8080/users")
    .then(function(resp) {
        return resp.json();
    })
    .then(function(data){
        console.log(data);
    } )