class myHeader extends HTMLElement{

    connectedCallback(){
        //<my-Header> </my-Header>
        this.innerHTML =`
                <head>
                <meta charSet="UTF-8">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                           <!-- Links -->
                            <link rel="stylesheet" href="./assets/css/bootstrap.min.css">
                                <link rel="preconnect" href="https://fonts.googleapis.com">
                                    <link rel="preconnect" href="https://fonts.gstatic.com" crossOrigin>
                                        <link
                                            href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap"
                                            rel="stylesheet">
                                            <link rel="stylesheet" href="./assets/css/style.css">
                                                <link rel="preconnect" href="https://fonts.googleapis.com">
                                                    <link rel="preconnect" href="https://fonts.gstatic.com" crossOrigin>
                                                        <link
                                                            href="https://fonts.googleapis.com/css2?family=Freehand&display=swap"
                                                            rel="stylesheet">
                                                     <!-- External links for head -->
                                                            <link rel="stylesheet"
                                                                  href="./assets/external/fonts/icomoon/style.css">
                                                           <!-- Bootstrap CSS -->
                                                                <link rel="stylesheet"
                                                                      href="./assets/external/css/bootstrap.min.css">
                                                                 <!-- Style CSS -->
                                                                    <link rel="stylesheet"
                                                                          href="./assets/external/css/style.css">
                                                                       <!-- End of EExternal links for head  -->

                <!-- Header Area -->

            </head>
            
              <nav class="navbar sticky-top navbar-expand-md navbar-dark bg-dark">
    <div class="container">
      <a class="navbar-brand" href="./index.html"><img src="./assets/images/logoW.png" /><span class="logo-text"> Audience Respone System</span></a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="#">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">About us</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Contact</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#" tabindex="-1" aria-disabled="true">Q&A</a>
          </li>
        </ul>
<!--      JOIN SESSION -->
          <element class="form-top" >
        <form method="post" action="./index.html" class="d-flex">
            <input class="form-control me-2" name="password" type="text" placeholder="Session-ID..." aria-label="JoinField">
            <element class="button-element"><button class="btn btn-secondary" type="submit">join</button></element>
          <!--      Create SESSION-->
            <a href="./creatSession.html"> <button class="btn btn-secondary" type="button">create </button></a>
          </form>
        </element>
      </div>
    </div>
  </nav>


<!-- <nav class="navbar navbar-expand-md navbar-light bg-light">
    <div class="container">
      <a class="navbar-brand">Audience Respone System</a>
      <form method="post" action="/joinSession" class="d-flex">
        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success" type="submit">join</button>
        <a href="#createSessionSection"> <button class="btn btn-outline-success" type="button">create </button></a>
      </form>
    </div>
  </nav> -->
            
             `

    }
}

customElements.define('my-header', myHeader)


class myFooter extends HTMLElement{

    connectedCallback(){
        //<my-Footer> </my-Footer>
        this.innerHTML =`
                    
<!-- External footer Code -->

  <footer class="footer-99382">

    <div class="container">
      <div class="row">
        <div class="col-md-4 pr-md-5">
          <a href="#" class="footer-site-logo d-block mb-4">Audience<br>Response<br>System</a>
          <p>Lorem ipsum dolor sit amet consectetur adipisicing elit.
            Eligendi quasi
            perferendis ratione perspiciatis accusantium.</p>
        </div>
        <div class="col-md">
          <h3>Discover</h3>
          <ul class="list-unstyled nav-links">
            <li><a href="#">Home</a></li>
            <li><a href="#">About us</a></li>
            <li><a href="#">Contact us</a></li>
            <li><a href="#">Q&A</a></li>
          </ul>
        </div>
        <div class="col-md">
          <h3>About</h3>
          <ul class="list-unstyled nav-links">
            <li><a href="#">Clients</a></li>
            <li><a href="#">Team</a></li>
            <li><a href="#">Career</a></li>
            <li><a href="#">Testimonials</a></li>
            <li><a href="#">Journal</a></li>
          </ul>
        </div>
        <div class="col-md">
          <h3>Help</h3>
          <ul class="list-unstyled nav-links">
            <li><a href="#">Privacy Policy</a></li>
            <li><a href="#">Terms &amp; Conditions</a></li>
            <li><a href="#">Partners</a></li>
          </ul>
        </div>
        <div class="col-md">
          <h3>Follow Us</h3>
          <ul class="social list-unstyled">
            <li><a href="#" class="pl-0"><span class="icon-instagram"></span></a></li>
            <li><a href="#"><span class="icon-twitter"></span></a></li>
            <li><a href="#"><span class="icon-facebook"></span></a></li>
            <li><a href="#"><span class="icon-pinterest"></span></a></li>
            <li><a href="#"><span class="icon-dribbble"></span></a></li>
          </ul>
        </div>
      </div>

      <div class="row ">
        <div class="col-12 text-center">
          <div class="copyright mt-5 pt-5">
            <p><small>&copy; 2021 - 2022 All Rights Reserved. ARS TEAM</small></p>
          </div>
        </div>
      </div>
    </div>

  </footer>

             `

    }
}

customElements.define('my-footer', myFooter)













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
