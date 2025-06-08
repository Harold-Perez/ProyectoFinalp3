// Call the dataTables jQuery plugin
$(document).ready(function () {
  // on ready
});

async function registrarEstudiante() {

  let datos = {};
  datos.nombre = document.getElementById("txtNombre").value;
  datos.apellido = document.getElementById("txtApellido").value;
  datos.email = document.getElementById("txtEmail").value;
  datos.telefono = document.getElementById("txtTelefono").value;
  datos.idioma = document.getElementById("selectIdioma").value;



  if (!validarTelefono(datos.telefono)) {
    alert("El número de teléfono debe contener exactamente 10 dígitos numéricos.");
    return;
  }

  if (!validarIdioma(datos.idioma)) {
    alert("Idioma inválido. Debe ser inglés, español o francés.");
    return;
  }

  const request = await fetch('api/estudiantes', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });

  alert("La cuenta fue creada con éxito");
  window.location.href = "estudiantes.html";
}

function validarTelefono(telefono) {
  return /^\d{10}$/.test(telefono); // 10 dígitos exactos
}

function validarIdioma(idioma) {
  return ["inglés", "español", "francés"].includes(idioma.toLowerCase());
}
