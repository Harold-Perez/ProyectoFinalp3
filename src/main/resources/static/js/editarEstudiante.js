const params = new URLSearchParams(window.location.search);
const id = params.get("id");

async function cargarDatosEstudiante() {
  const response = await fetch(`http://localhost:8080/api/estudiantes/${id}`);

  if (!response.ok) {
    alert("No se pudo cargar el estudiante.");
    return;
  }

  const text = await response.text();
  if (!text) {
    alert("Estudiante no encontrado.");
    return;
  }

  const estudiante = JSON.parse(text);
  document.getElementById("nombre").value = estudiante.nombre;
  document.getElementById("apellido").value = estudiante.apellido;
  document.getElementById("email").value = estudiante.email;
  document.getElementById("telefono").value = estudiante.telefono || "";
  document.getElementById("idioma").value = estudiante.idioma || "";
}

async function actualizarEstudiante() {
  const estudianteEditado = {
    id: Number(id),
    nombre: document.getElementById("nombre").value,
    apellido: document.getElementById("apellido").value,
    email: document.getElementById("email").value,
    telefono: document.getElementById("telefono").value,
    idioma: document.getElementById("idioma").value
  };

  const response = await fetch(`http://localhost:8080/api/estudiantes/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(estudianteEditado)
  });

  if (response.ok) {
    alert("Estudiante con ID " + id + " fue editado correctamente.");
  } else {
    alert("Error al editar el estudiante.");
  }
}

cargarDatosEstudiante();
