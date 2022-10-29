
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