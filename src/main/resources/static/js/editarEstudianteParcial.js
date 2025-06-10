const params = new URLSearchParams(window.location.search);
const id = params.get("id");

async function cargarNombre() {
  const response = await fetch(`http://localhost:8080/api/estudiantes/${id}`);

  if (!response.ok) {
    document.getElementById("nombreEstudiante").innerText = "Error al cargar el nombre";
    return;
  }

  const data = await response.json();
  document.getElementById("nombreEstudiante").innerText = data.nombre;
}

async function editarParcial() {
  const email = document.getElementById("email").value;
  const telefono = document.getElementById("telefono").value;
  const idioma = document.getElementById("idioma").value;

  const data = {};
  if (email) data.email = email;
  if (telefono) data.telefono = telefono;
  if (idioma) data.idioma = idioma;

  if (Object.keys(data).length === 0) {
    alert("No se ha modificado ningún dato.");
    return;
  }

  const response = await fetch(`http://localhost:8080/api/estudiantes/${id}`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  });

  if (response.ok) {
    alert("Estudiante con ID " + id + " editado parcialmente.");
    window.location.href = `verEstudiante.html?id=${id}`;
  } else {
    alert("Error al editar. Verifica los datos o el servidor.");
  }
}

cargarNombre();
