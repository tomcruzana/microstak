const GATEWAY_PROXY_URI = window.MICROSTAK_GATEWAY_URL || "http://localhost:8765";
const AUTH_STORAGE_KEY = "microstak.auth";

function getAuthSession() {
  const storedSession =
    localStorage.getItem(AUTH_STORAGE_KEY) ||
    sessionStorage.getItem(AUTH_STORAGE_KEY);
  return storedSession ? JSON.parse(storedSession) : null;
}

function getAuthToken() {
  const session = getAuthSession();
  return session ? session.accessToken : "";
}

function setAuthSession(session, remember = false) {
  clearAuthSession();
  const targetStorage = remember ? localStorage : sessionStorage;
  targetStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(session));
}

function clearAuthSession() {
  localStorage.removeItem(AUTH_STORAGE_KEY);
  sessionStorage.removeItem(AUTH_STORAGE_KEY);
}

async function request(path, options = {}) {
  const url = path.startsWith("http") ? path : `${GATEWAY_PROXY_URI}${path}`;
  const headers = new Headers(options.headers || {});
  const hasBody = Object.prototype.hasOwnProperty.call(options, "body");
  let body = options.body;

  if (hasBody && body !== null && typeof body === "object" && !(body instanceof FormData)) {
    headers.set("Content-Type", "application/json");
    body = JSON.stringify(body);
  }

  if (options.authenticated) {
    const token = getAuthToken();
    if (token) {
      headers.set("Authorization", `Bearer ${token}`);
    }
  }

  const response = await fetch(url, {
    ...options,
    headers,
    body,
  });
  const contentType = response.headers.get("content-type") || "";
  const payload = contentType.includes("application/json")
    ? await response.json()
    : await response.text();

  if (!response.ok) {
    if (response.status === 401) {
      clearAuthSession();
    }
    const error = new Error(typeof payload === "string" ? payload : response.statusText);
    error.status = response.status;
    error.payload = payload;
    throw error;
  }

  return payload;
}

function publicRequest(path, options = {}) {
  return request(path, options);
}

function authRequest(path, options = {}) {
  return request(path, { ...options, authenticated: true });
}

async function login(username, password, remember = false) {
  const authResponse = await publicRequest("/auth/login", {
    method: "POST",
    body: { username, password },
  });
  setAuthSession(authResponse, remember);
  return authResponse;
}

window.MicrostakApi = {
  GATEWAY_PROXY_URI,
  authRequest,
  clearAuthSession,
  getAuthSession,
  getAuthToken,
  login,
  publicRequest,
  request,
  setAuthSession,
};
