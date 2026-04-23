import { useEffect, useState } from "react";
import Login from "./Login.jsx";

function App() {
  const [token, setToken] = useState(localStorage.getItem("token"));
  const [tasks, setTasks] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const fetchTasks = async () => {
    const res = await fetch("http://localhost:8080/tasks", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (!res.ok) {
      throw new Error("Failed to fetch tasks");
    }
    return res.json();
  };

  const fetchNotifications = async () => {
    const res = await fetch("http://localhost:8080/notifications", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (!res.ok) {
      throw new Error("Failed to fetch notifications");
    }
    return res.json();
  };


  useEffect(() => {
    if (!token) return;
    setLoading(true);
    setError("");

    Promise.all([fetchTasks(), fetchNotifications()])
      .then(([tasksData, notificationData]) => {
        setTasks(tasksData);
        setNotifications(notificationData);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, [token]);


  const createTask = async () => {
    try {
      setError("");

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
      setError("");
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


  const markNotificationAsRead = async (notificationID) => {
    try {
      setError("");
      const res = await fetch(
        `http://localhost:8080/notifications/${notificationID}/read`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (!res.ok) {
        throw new Error("Failed to mark notification as read");
      }
      setNotifications((prev) => prev.map((n) =>
          n.id === notificationID ? {...n, read: true} : n
        )
      );
    } catch (err) {
      setError(err.message);
    }
  };


  if (!token) {
    return <Login onLogin={setToken} />;
  }


  return (
    <div>
      <h1>OpsFlow</h1>

      <button
        onClick={() => {
          localStorage.removeItem("token");
          setToken(null);
          setTasks([]);
          setNotifications([]);
        }}
        >
        Logout
      </button>

      {loading && <p>Loading...</p>}
      {error && <p>Error: {error}</p>}

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

      <div>
        <h2>Tasks</h2>
        {tasks.length === 0 && !loading && <p>No tasks found</p>}

        <ul>
          {tasks.map((task) => (
            <li key={task.id}>
              <strong>{task.title}</strong> - {task.status}

              <div>
                <button onClick={() => updateStatus(task.id, "OPEN")}>
                  OPEN
                </button>
                <button onClick={() => updateStatus(task.id, "IN_PROGRESS")}>
                  IN PROGRESS
                </button>
                <button onClick={() => updateStatus(task.id, "DONE")}>
                  DONE
                </button>
              </div>
            </li>
          ))}
        </ul>
      </div>

      <div>
        <h2>Notifications</h2>
        {notifications.length === 0 && !loading && <p>No notifications</p>}

        <ul>
          {notifications.map((notification) => (
            <li key={notification.id}>
              {notification.message}{" "}
              {notification.read ? (
                <span>(read)</span>
              ) : (
                <button
                onClick={() => markNotificationAsRead(notification.id)}
                >
                  Mark as read
                </button>
              )}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default App;