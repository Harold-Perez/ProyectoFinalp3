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

  // ✅ Validar formato de correo
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(datos.email)) {
    alert('Por favor ingrese un correo electrónico válido');
    return;
  }

  // ✅ Verificar si el correo ya existe (nuevo)
  const existe = await verificarCorreoExistente(datos.email);
  if (existe) {
    alert('Este correo ya está registrado. Intenta con otro.');
    return;
  }

  // ✅ Validaciones restantes
  if (!validarTelefono(datos.telefono)) {
    alert("El número de teléfono debe contener exactamente 10 dígitos numéricos.");
    return;
  }

  if (!validarIdioma(datos.idioma)) {
    alert("Idioma inválido. Debe ser inglés, español o francés.");
    return;
  }

  // ✅ Registrar estudiante
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

// Función para verificar si el correo ya existe (nuevo)
async function verificarCorreoExistente(email) {
  const response = await fetch(`api/estudiantes/verificar-email?email=${encodeURIComponent(email)}`);
  if (!response.ok) {
    alert("Error al verificar el correo.");
    return true; // asumimos que existe por seguridad
  }
  const resultado = await response.json();
  return resultado.existe;
}

function validarTelefono(telefono) {
  return /^\d{10}$/.test(telefono); // 10 dígitos exactos
}

function validarIdioma(idioma) {
  return ["inglés", "español", "francés"].includes(idioma.toLowerCase());
}
