// Call the dataTables jQuery plugin
$(document).ready(function () {
  cargarEstudiantes();
  $('#estudiantes')
});

async function cargarEstudiantes() {
  const request = await fetch('api/estudiantes', {
    method: 'GET',
    headers: getHeaders()
  });
  const estudiantes = await request.json();

  let listadoHtml = '';
  for (let estudiante of estudiantes) {
    let botonEliminar = '<a href="#" onclick="eliminarEstudiante(' + estudiante.id + ')" class="btn btn-danger btn-circle btn-sm"> <i class="fas fa-trash"></i> </a>';

    let estudianteHtml = '<tr>' +
      '<td>' + estudiante.id + '</td>' +
      '<td>' + estudiante.nombre + ' ' + estudiante.apellido + '</td>' +
      '<td>' + estudiante.email + '</td>' +
      '<td>' + estudiante.telefono + '</td>' +
      '<td>' + estudiante.idioma + '</td>' +
      '<td>' + botonEliminar + '</td>' +
      '</tr>';

    listadoHtml += estudianteHtml;
  }

  document.querySelector("#estudiantes tbody").outerHTML = listadoHtml;
}

function getHeaders() {
  return {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.token
  };
}

async function eliminarEstudiante(id) {
  if (!confirm("¿Desea eliminar este estudiante?")) {
    return;
  }

  const request = await fetch('api/estudiantes/' + id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload();
}
