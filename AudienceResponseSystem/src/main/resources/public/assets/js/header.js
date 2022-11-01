/**
 * Alternative Way "tagging" to include header
 * **/

class myHeader extends HTMLElement{
    connectedCallback(){


        //<my-Header> </my-Header>
        this.innerHTML =`

                <head>
                <meta charSet="UTF-8">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                           <!-- Links -->
                           <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

                            <link rel="stylesheet" href="./assets/css/bootstrap.min.css">
                                <link rel="preconnect" href="https://fonts.googleapis.com">
                                    <link rel="preconnect" href="https://fonts.gstatic.com">
                                        <link
                                            href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap"
                                            rel="stylesheet">
                                            <link rel="stylesheet" href="./assets/css/style.css">
                                                <link rel="preconnect" href="https://fonts.googleapis.com">
                                                    <link rel="preconnect" href="https://fonts.gstatic.com">
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
        <form  class="d-flex">
            <input class="form-control me-2" id="pass" type="text" placeholder="Session-ID..." aria-label="JoinField">
            <element class="button-element">
            <button class="btn btn-secondary" onclick=addPassword()>join</button></element>
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