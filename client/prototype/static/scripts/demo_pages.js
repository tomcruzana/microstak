function escapeHtml(value) {
  return String(value ?? "")
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#39;");
}

function money(value) {
  const amount = Number(value || 0);
  return amount.toLocaleString("en-US", { style: "currency", currency: "USD" });
}

function productImage(product, size = "200x200") {
  return product.photoDataUri || `https://dummyimage.com/${size}/6495ED/fff.png`;
}

function renderStars(rating = 0) {
  const fullStars = Math.max(0, Math.min(5, Number(rating) || 0));
  return Array.from({ length: 5 }, (_, index) =>
    `<i class="fa-${index < fullStars ? "solid" : "regular"} fa-star"></i>`
  ).join("");
}

function setStatus(target, message, type = "info") {
  if (!target) {
    return;
  }
  target.className = `alert alert-${type} mt-3`;
  target.textContent = message;
  target.hidden = false;
}

async function loadAboutPage() {
  if (!window.location.pathname.endsWith("about_us.html")) {
    return;
  }

  const aboutContainer = document.querySelector(".about-us");
  if (!aboutContainer) {
    return;
  }

  try {
    const about = await window.MicrostakApi.publicRequest("/aboutusapi/about_us");
    const title = about.organizationTitle || "Who We Are";
    const image = about.photo
      ? `data:image/jpeg;base64,${about.photo}`
      : "https://dummyimage.com/600x340/6495ED/fff";

    aboutContainer.querySelector("img").src = image;
    aboutContainer.querySelector("h3").textContent = title;
    const paragraphs = aboutContainer.querySelectorAll("p");
    if (paragraphs[0]) {
      paragraphs[0].textContent = about.organizationDescription || paragraphs[0].textContent;
    }
    if (paragraphs[1]) {
      paragraphs[1].textContent = about.organizationVision || about.organizationValues || paragraphs[1].textContent;
    }

    const missionText = document.querySelector(".text-start .lead");
    if (missionText) {
      missionText.textContent = about.organizationMission || missionText.textContent;
    }
  } catch (error) {
    console.info("About page demo data unavailable", error);
  }
}

async function loadFaqPage() {
  const accordion = document.querySelector("#accordion");
  if (!accordion) {
    return;
  }

  try {
    const faqs = await window.MicrostakApi.publicRequest("/faqapi/faqs");
    accordion.innerHTML = faqs.map((faq, index) => {
      const collapseId = `faq-collapse-${faq.id || index}`;
      const headingId = `faq-heading-${faq.id || index}`;
      return `
        <div class="card h-100">
          <div class="card-header" id="${headingId}">
            <h5 class="mb-0">
              <button
                class="btn btn-link"
                data-bs-toggle="collapse"
                data-bs-target="#${collapseId}"
                aria-expanded="${index === 0}"
                aria-controls="${collapseId}"
              >
                <span class="accordion-number-id">${index + 1}</span>. ${escapeHtml(faq.question)}
              </button>
            </h5>
          </div>
          <div
            id="${collapseId}"
            class="collapse ${index === 0 ? "show" : ""}"
            aria-labelledby="${headingId}"
            data-bs-parent="#accordion"
          >
            <div class="card-body">${escapeHtml(faq.answer)}</div>
          </div>
        </div>
      `;
    }).join("");
  } catch (error) {
    console.info("FAQ demo data unavailable", error);
  }
}

function bindContactPage() {
  const form = document.querySelector("form.contact-us-form");
  if (!form) {
    return;
  }

  const status = document.createElement("div");
  status.hidden = true;
  form.append(status);

  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    try {
      const response = await window.MicrostakApi.publicRequest("/contactusapi/contact_us", {
        method: "POST",
        body: {
          senderName: form.querySelector("#exampleInputName1").value,
          emailAddress: form.querySelector("#exampleInputEmail1").value,
          emailSubject: form.querySelector("#exampleInputSubject1").value,
          emailMessage: form.querySelector("#exampleFormControlTextarea1").value,
        },
      });
      setStatus(status, response || "Message submitted.", "success");
      form.reset();
    } catch (error) {
      setStatus(status, "The contact service is unavailable. Start the gateway and contact service, then try again.", "warning");
    }
  });
}

async function refreshGuestEmailCount() {
  const counter = document.querySelector(".total-subscribed-customers");
  if (!counter) {
    return;
  }
  try {
    const response = await window.MicrostakApi.publicRequest("/guestemailapi/total");
    counter.textContent = response.Total || response.total || "0";
  } catch (error) {
    console.info("Guest email count unavailable", error);
  }
}

function bindGuestEmailForm() {
  const form = document.querySelector(".email-list-subscription form");
  if (!form) {
    return;
  }

  const status = document.createElement("div");
  status.hidden = true;
  form.after(status);

  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const input = form.querySelector("input[type='email']");
    try {
      const response = await window.MicrostakApi.publicRequest("/guestemailapi/subscribe", {
        method: "POST",
        headers: { "Content-Type": "text/plain" },
        body: input.value,
      });
      setStatus(status, response || "Subscribed.", "success");
      input.value = "";
      refreshGuestEmailCount();
    } catch (error) {
      setStatus(status, "Subscription service is unavailable for this demo run.", "warning");
    }
  });
}

function renderProductCard(product) {
  return `
    <div class="card my-2 product-card">
      <a href="product_item.html?id=${encodeURIComponent(product.id)}" class="product-link">
        <div class="product-horizontal">
          <div class="img-square-wrapper">
            <img
              class="product-image m-2"
              src="${productImage(product)}"
              alt="${escapeHtml(product.title || "Product image")}"
            />
          </div>
          <div class="card-body p-2">
            <h4 class="card-title">${escapeHtml(product.title || "Untitled Product")}</h4>
            <p class="card-text">${escapeHtml(product.description || "")}</p>
            <div class="product-rating">${renderStars(product.rating)}</div>
            <h5 class="mt-2 product-price">${money(product.price)}</h5>
          </div>
        </div>
      </a>
    </div>
  `;
}

async function loadProductsPage() {
  const resultContainer = document.querySelector(".product-result-set .col-12.mt-3");
  if (!resultContainer) {
    return;
  }

  try {
    const [products, categories] = await Promise.all([
      window.MicrostakApi.publicRequest("/productitemapi/products"),
      window.MicrostakApi.publicRequest("/productitemapi/categories"),
    ]);

    const sidebar = document.querySelector(".products-sidebar .product-price-range");
    if (sidebar && Array.isArray(categories) && categories.length > 0) {
      sidebar.innerHTML = `
        <p class="text-capitalize fw-bold m-0">Category</p>
        ${categories.map((category) => `<p class="text-capitalize ps-3 m-0"><a href="#">${escapeHtml(category)}</a></p>`).join("")}
      `;
    }

    resultContainer.innerHTML = products.length
      ? products.map(renderProductCard).join("")
      : `<div class="alert alert-info">No products are available yet.</div>`;
  } catch (error) {
    console.info("Product listing unavailable", error);
  }
}

async function loadProductDetailPage() {
  const productCard = document.querySelector(".product-card.border-0");
  if (!productCard) {
    return;
  }

  const id = new URLSearchParams(window.location.search).get("id") || "1";
  try {
    const product = await window.MicrostakApi.publicRequest(`/productitemapi/products/${encodeURIComponent(id)}`);
    productCard.querySelector(".product-image").src = productImage(product, "640x480");
    productCard.querySelector(".product-image").alt = product.title || "Product image";
    productCard.querySelector(".card-title").textContent = product.title || "Untitled Product";
    productCard.querySelector(".product-category").textContent = product.category || "Uncategorized";
    productCard.querySelector(".card-text").textContent = product.description || "";
    productCard.querySelector(".product-rating").innerHTML = renderStars(product.rating);
    productCard.querySelector("h5.product-price").textContent = money(product.price);
  } catch (error) {
    console.info("Product detail unavailable", error);
  }
}

function bindSignInPage() {
  const form = document.querySelector(".form-signin");
  if (!form) {
    return;
  }

  const status = document.createElement("div");
  status.hidden = true;
  form.prepend(status);

  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    try {
      const username = form.querySelector("#inputEmail").value.trim();
      const password = form.querySelector("#inputPassword").value;
      const remember = form.querySelector("#customCheck1").checked;
      const auth = await window.MicrostakApi.login(username, password, remember);
      setStatus(status, `Signed in as ${auth.username}. Token stored for gateway requests.`, "success");
    } catch (error) {
      setStatus(status, "Sign in failed. Try demo user/user123 or admin/admin123.", "danger");
    }
  });
}

function updateHeaderAuthState() {
  const signInLink = document.querySelector('a[href="user_sign_in.html"]');
  const session = window.MicrostakApi.getAuthSession();
  if (signInLink && session) {
    signInLink.innerHTML = `<i class="fa-solid fa-user"></i> ${escapeHtml(session.username)}`;
  }
}

document.addEventListener("DOMContentLoaded", () => {
  updateHeaderAuthState();
  refreshGuestEmailCount();
  bindGuestEmailForm();
  bindContactPage();
  bindSignInPage();
  loadAboutPage();
  loadFaqPage();
  loadProductsPage();
  loadProductDetailPage();
});
