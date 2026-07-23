# Microstak

Microstak is a full-stack demo built with a Spring Boot microservices architecture.

## Live Site

Local demo project. No hosted production URL is currently configured.

## Features

- Spring Boot microservices organized under a shared Maven parent build
- Spring Cloud Gateway as the single backend entrypoint
- Eureka service discovery for backend service registration
- JWT login flow with demo user and admin credentials
- Route-level security for public, user, employee, admin, and actuator endpoints
- MySQL-backed domain services for company content, products, customers, coupons, messages, and policy pages
- Static frontend prototype connected to the gateway
- Bootstrap 5.3.8 pages for products, profile, cart, contact, FAQ, and policy content
- Demo profile flow with token storage, welcome navigation state, and logout
- Actuator health/info endpoints and file-based Spring Boot logs per service
- Micrometer tracing dependencies with Zipkin support

## Support

[Buy me a coffee](https://buymeacoffee.com/tomcruzana)

## Tech Stack

- Java 17
- Spring Boot 4.1.0
- Spring Cloud 2025.1.2
- Spring Cloud Gateway
- Spring Cloud Netflix Eureka
- Spring Security 7 / OAuth2 Resource Server JWT
- Spring Data JPA
- MySQL
- Maven
- Bootstrap 5.3.8
- Font Awesome
- Micrometer Tracing / Zipkin

## Project Structure

```txt
client/prototype/             Static Bootstrap frontend demo
server/microservices-apps/    Maven parent and Spring Boot microservices
```

## Run Locally

Create the MySQL database:

```bash
mysql -u root -p
```

```sql
CREATE DATABASE IF NOT EXISTS m_ecomm_db_dev;
```

The services default to these database settings:

```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/m_ecomm_db_dev
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=YOUR-PASSWORD
```

You can override those values with environment variables before starting the services.

Build the backend:

```bash
cd server/microservices-apps
mvn -q -DskipTests compile
```

Start Eureka first:

```bash
cd server/microservices-apps
mvn -q -pl eureka-server-app spring-boot:run
```

Start the gateway:

```bash
cd server/microservices-apps
mvn -q -pl gateway-proxy-server-app spring-boot:run
```

Start any backend service needed for the demo page you are checking:

```bash
cd server/microservices-apps
mvn -q -pl about-us-app spring-boot:run
mvn -q -pl faq-app spring-boot:run
mvn -q -pl contact-us-app spring-boot:run
mvn -q -pl guest-email-app spring-boot:run
mvn -q -pl product-item-app spring-boot:run
```

Serve the frontend:

```bash
cd client/prototype
python -m http.server 8000
```

Frontend:

```txt
http://localhost:8000
```

Gateway API:

```txt
http://localhost:8765
```

Eureka dashboard:

```txt
http://localhost:8761
```

Actuator health and info:

```txt
http://localhost:9765/actuator/health
http://localhost:9765/actuator/info
```

## Demo Login

Default demo credentials:

```txt
User:  user / user123
Admin: admin / admin123
```

The frontend sign-in page stores the JWT response in browser storage, redirects to the profile page, shows a welcome link in the nav, and clears the session on logout.

## Current Gateway Routes

```txt
GET    /auth/login
POST   /auth/login
GET    /auth/me

GET    /aboutusapi/about_us
GET    /companyapi/company_info
POST   /contactusapi/contact_us
GET    /contactusapi/contact_us/all
GET    /contactusapi/contact_us/q
GET    /couponapi/validate
GET    /faqapi/faqs
GET    /faqapi/faqs/q
GET    /guestemailapi/total
POST   /guestemailapi/subscribe
GET    /privacypolicyapi/privacy_policy
GET    /productitemapi/products
GET    /productitemapi/products/{id}
GET    /productitemapi/categories
GET    /shippingandreturnsapi/shipping_and_returns
GET    /termsofuseapi/terms_of_use
```

Additional gateway route prefixes are configured for customer, employee, and news/update services.

## Checks

Backend compile:

```bash
cd server/microservices-apps
mvn -q -DskipTests compile
```

Gateway tests:

```bash
cd server/microservices-apps
mvn -q -pl gateway-proxy-server-app test
```

Product service tests:

```bash
cd server/microservices-apps
mvn -q -pl product-item-app test
```

Frontend script syntax:

```bash
node --check client/prototype/static/scripts/demo_pages.js
```
