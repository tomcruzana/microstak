class Footer extends HTMLElement {
  connectedCallback() {
    this.innerHTML = `
    <footer class="nav bg-dark text-light mt-4 py-3">
        <div class="container">
            <div class="row">
            <!--footer additional links -->
            <div class="col my-auto d-flex">
                <a class="nav-link" href="#">Site Map</a>
                <a class="nav-link" href="#">Careers</a>
                <a class="nav-link" href="terms_of_use.html">Terms of Use</a>
                <a class="nav-link" href="privacy_policy.html">Privacy Policy</a>
            </div>
            <!--footer email subs-->
            <div class="col bg-white text-dark rounded">
                <div class="pt-2">
                <div class="email-list-subscription">
                    <p class="text-end border-top m-0">
                    Subscribe to get the latest deals and more.
                    </p>
                    <p class="text-end">
                    We have
                    <span class="total-subscribed-customers fw-bold">0</span>
                    customers on our mailing lists!
                    </p>
                    <form class="d-flex flex-wrap gap-2 justify-content-end">
                    <div class="form-group mb-2 me-3">
                        <input
                        type="email"
                        class="form-control"
                        id="staticEmail2"
                        placeholder="enter email address"
                        />
                    </div>
                    <button type="submit" class="btn btn-warning mb-2 subscribe-btn">
                        Subscribe!
                    </button>
                    </form>
                </div>
                <!--footer social media links -->
                <div class="social-media-link-group text-end mt-2">
                    <i class="fa-brands fa-facebook-square fa-2x mx-1"></i>
                    <i class="fa-brands fa-youtube-square fa-2x mx-1"></i>
                    <i class="fa-brands fa-twitter-square fa-2x mx-1"></i>
                    <i class="fa-brands fa-instagram-square fa-2x ms-1"></i>
                </div>
                </div>
            </div>

            <div class="w-100"></div>

            <div class="col text-start">
                <!--footer general info -->
                <div class="pt-2 footer-text">
                <p class="align-middle my-0">
                    <i class="fa-solid fa-copyright"></i> Copyright
                    <span class="current-date-year">DATE</span>. Company Name. All
                    Rights Reserved.
                </p>
                <p class="align-middle my-0">
                    <i class="fa-solid fa-envelope"></i>
                    Developer:
                    <a class="align-middle my-0" href="mailto: abc@example.com"
                    >Thomas
                    </a>
                </p>
                </div>
            </div>
            <div class="col text-end">
                <!--footer payment types -->
                <div class="pt-2 payment-types">
                <i class="fa-brands fa-cc-visa fa-2x mx-1"></i>
                <i class="fa-brands fa-cc-discover fa-2x mx-1"></i>
                <i class="fa-brands fa-cc-mastercard fa-2x mx-1"></i>
                <i class="fa-brands fa-cc-amex fa-2x mx-1"></i>
                <i class="fa-brands fa-cc-paypal fa-2x ms-1"></i>
                </div>
            </div>
            </div>
        </div>
    </footer>
        `;
  }
}

customElements.define("nav-footer", Footer);
