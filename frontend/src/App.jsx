import {useEffect, useState} from "react";
import Login from "./Login.jsx";

function App() {
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const [token, setToken] = useState(localStorage.getItem("token"));

  useEffect(() => {
    if (!token) return;
    setLoading(true);

    fetch("http://localhost:8080/tasks", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
        .then(async (res) => {
            if (!res.ok) {
                throw new Error("Failed to fetch tasks");
            }
            return res.json();
        })
        .then((data) => {
            setTasks(data);
            setLoading(false);
        })
        .catch((err) => {
            setError(err.message);
            setLoading(false);
        });
  }, [token]);

  if (!token) {
      return <Login onLogin={setToken} />;
  }

  return (
      <div>
        <h1>Tasks</h1>
          {loading && <p>Loading...</p>}
          {error && <p>Error: {error}</p>}
          {!loading && tasks.length === 0 && <p>No tasks found</p>}
        <ul>
          {tasks.map((task) => (
              <li key={task.id}>
                {task.title} - {task.status}
              </li>
          ))}
        </ul>
        <button
            onClick={() => {
                localStorage.removeItem("token");
                setToken(null);
            }}
        >
            Logout
        </button>
      </div>
  );
}

export default App;