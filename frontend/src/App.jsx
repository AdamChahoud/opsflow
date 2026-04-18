import {useEffect, useState} from "react";

function App() {
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch("http://localhost:8080/tasks", {
      headers: {
        Authorization: "Bearer TOKEN"
      }
    })
        .then(res => res.json())
        .then(data => {
            setTasks(data);
            setLoading(false);
        })
        .catch((err) => {
            setError(err.message);
            setLoading(false);
        });
  }, []);

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
      </div>
  );
}

export default App;