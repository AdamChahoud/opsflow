import {useEffect, useState} from "react";
import Login from "./Login.jsx";

function App() {
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);
  const [token, setToken] = useState(localStorage.getItem("token"));
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

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

  const createTask = async () => {
      try {
          const res = await fetch("http://localhost:8080/tasks", {
              method: "POST",
              headers: {
                  "Content-Type": "application/json",
                  Authorization: `Bearer ${token}`,
              },
              body: JSON.stringify({ title, description }),
          });
          if (!res.ok) {
              throw new Error("Failed to create task");
          }

          const newTask = await res.json();
          setTasks((prev) => [...prev, newTask]);

          setTitle("");
          setDescription("");
      } catch (err) {
          setError(err.message);
      }
  };

  const updateStatus = async (taskId, newStatus) => {
      try {
          const res = await fetch(`http://localhost:8080/tasks/${taskId}/status`, {
              method: "PUT",
              headers: {
                  "Content-Type": "application/json",
                  Authorization: `Bearer ${token}`,
              },
              body: JSON.stringify({ status: newStatus }),
          });
          if (!res.ok) {
              throw new Error("Failed to update status");
          }
          const updatedTask = await res.json();
          setTasks((prev) =>
              prev.map((t) => (t.id === taskId ? updatedTask : t))
          );
      } catch (err) {
          setError(err.message);
      }
  };

  return (
      <div>
        <h1>Tasks</h1>

        <div>
            <h2>Create Task</h2>

            <input
                placeholder="Title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />

            <input
                placeholder="Description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
            />

            <button onClick={createTask}>Create</button>
        </div>

        {loading && <p>Loading...</p>}
        {error && <p>Error: {error}</p>}
        {!loading && tasks.length === 0 && <p>No tasks found</p>}

        <ul>
          {tasks.map((task) => (
              <li key={task.id}>
                  {task.title} - {task.status}
                  <button onClick={() => updateStatus(task.id, "OPEN")}>
                      OPEN
                  </button>

                  <button onClick={() => updateStatus(task.id, "IN_PROGRESS")}>
                      IN PROGRESS
                  </button>

                  <button onClick={() => updateStatus(task.id, "DONE")}>
                      DONE
                  </button>
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