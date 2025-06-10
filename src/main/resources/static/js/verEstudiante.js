async function cargarEstudiante() {
  const params = new URLSearchParams(window.location.search);
  const id = params.get("id");

  if (!id) {
    alert("No se especificó un ID de estudiante.");
    return;
  }

  const response = await fetch(`http://localhost:8080/api/estudiantes/${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  });

  if (!response.ok) {
    alert("Error al buscar estudiante");
    return;
  }

  // Evitar el error por JSON vacío
  const text = await response.text();
  if (!text) {
    alert("Estudiante no encontrado");
    return;
  }

  const estudiante = JSON.parse(text);

  if (!estudiante || !estudiante.id) {
    alert("Estudiante no encontrado");
    return;
  }

  document.getElementById("nombre").value = estudiante.nombre;
  document.getElementById("apellido").value = estudiante.apellido;
  document.getElementById("email").value = estudiante.email;
  document.getElementById("telefono").value = estudiante.telefono;
  document.getElementById("idioma").value = estudiante.idioma;

  localStorage.setItem("idEstudiante", id);
}

function redirigirEditarCompleto() {
  const id = localStorage.getItem("idEstudiante");
  window.location.href = `editarEstudiante.html?id=${id}`;
}

function redirigirEditarParcial() {
  const id = localStorage.getItem("idEstudiante");
  window.location.href = `editarEstudianteParcial.html?id=${id}`;
}

cargarEstudiante();
