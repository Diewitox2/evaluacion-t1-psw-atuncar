# language: es
Característica: Registro de cursos
  Como administrador de la institución educativa
  Quiero registrar cursos en el sistema de matrícula
  Para ofrecerlos a los estudiantes

  Escenario: Registro de curso válido
    Dado que el administrador ingresa un curso con nombre "Selenium Avanzado", precio "400.0" y 20 vacantes
    Cuando envía la solicitud de registro del curso
    Entonces el sistema responde con código 200
    Y el curso devuelto tiene el nombre "Selenium Avanzado"

  Escenario: Registro de curso con precio inválido
    Dado que el administrador ingresa un curso con nombre "Curso Gratis", precio "0.0" y 10 vacantes
    Cuando envía la solicitud de registro del curso
    Entonces el sistema rechaza la solicitud con un código de error 4xx o 5xx
