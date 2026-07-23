class Header extends HTMLElement {
  connectedCallback() {
    this.innerHTML = `
        <ul class="nav justify-content-between bg-dark py-2">
        <!-- logo-->
        <div class="nav-items-group d-flex flex-row">
            <li class="nav-item">
            <a class="nav-link" href="#">
                <img
                src="https://dummyimage.com/30x30/6495ED/fff.png"
                width="30"
                height="30"
                alt="company logo"
                />
            </a>
            </li>

            <!-- user location-->
            <li class="nav-item align-self-center user-location text-light ps-3">
            <p class="m-0 p-0"><i class="fa-solid fa-location-dot"></i> Deliver to</p>
            <p class="m-0 p-0 city-postal">CITY, ZIP CODE</p>
            </li>
        </div>

        <div class="nav-items-group d-flex flex-row">
            <li class="nav-item align-self-center">
            <a class="nav-link" href="index.html">Home</a>
            </li>
            <li class="nav-item align-self-center">
            <a class="nav-link" href="products_search.html">Products</a>
            </li>
            <li class="nav-item align-self-center">
            <a class="nav-link" href="about_us.html">About</a>
            </li>
            <li class="nav-item align-self-center">
            <a class="nav-link" href="contact_us.html">Contact Us</a>
            </li>
            <li class="nav-item align-self-center">
            <a class="nav-link" href="faqs.html">FAQs</a>
            </li>
            <li class="nav-item align-self-center">
            <a class="nav-link" href="shipping_and_returns.html"
                >Shipping & Returns</a
            >
            </li>

            <!-- tel number-->
            <li class="nav-item align-self-center">
            <a
                href="tel:8665562570"
                class="badge text-bg-warning text-dark tel-number p-2"
            >
                +1 (360) 999-1235
            </a>
            </li>
        </div>

        <!-- user login & cart-->
        <div class="nav-items-group d-flex flex-row">
            <!-- user cart | LIMIT 999-->
            <li class="nav-item align-self-center">
            <a class="nav-link" href="user_cart.html"
                ><i class="fa-solid fa-cart-shopping"></i>
                <span class="badge text-bg-danger cart-item-count p-2">0</span></a
            >
            </li>

            <!-- user login button-->
            <li class="nav-item align-self-center">
            <a class="nav-link" href="user_sign_in.html"
                ><i class="fa-solid fa-user"></i> Sign in</a
            >
            </li>
        </div>
        </ul>
        `;
  }
}

customElements.define("nav-header", Header);
