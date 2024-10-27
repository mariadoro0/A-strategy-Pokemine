document.getElementById("loginForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("http://localhost:8080/users/login", { // Cambia l'URL se diverso
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ email: email, password: password })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem("token", data.token); // Salva il token in localStorage
            document.getElementById("message").textContent = "Login successful! Redirecting...";
            window.location.href = "/"; // Redirect alla homepage
        } else {
            document.getElementById("message").textContent = "Invalid credentials!";
        }
    } catch (error) {
        console.error("Error:", error);
        document.getElementById("message").textContent = "An error occurred. Please try again.";
    }
});
