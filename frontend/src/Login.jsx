import { useState} from "react";

const API_URL = import.meta.env.VITE_API_URL;

function Login({ onLogin }) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleLogin = async () => {
        try {
            const res = await fetch(`${API_URL}/auth/login`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ email: username, password }),
            });
            if(!res.ok){
                throw new Error("Invalid credentials");
            }
            const data = await  res.json();
            localStorage.setItem("token", data.token);
            onLogin(data.token);
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div>
            <h2>Login</h2>

            <input
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                />

            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                />

            <button onClick={handleLogin}>Login</button>

            {error && <p>{error}</p>}
        </div>
    );
}

export default Login;