package com.rlm.imeikotlin.utils

// Tag
const val TAG = "RLM"

// Tipo Información Alumno Tabla
const val TIPO_PLAN = "tipoPlan"
const val TIPO_PAGO = "tipoPago"

// Key Picasso
const val CIRCLE = "Circle"

// Etiqueta @Named Dagger2
const val NAMED_LISTA_TELEFONICA = "ListaTelefonica"
const val NAMED_LISTA_PLANTEL = "ListaPlantel"

// BD
const val DATABASE_NAME = "RoomIMEI.db"
const val DATABASE_VERSION = 1

// ViewModelConstant
const val VIEW_MODEL_NOT_FOUND = "ViewModel Not Found"

// Bundle
const val BUNDLE_DESCRIPCION = "descripcion"
const val BUNDLE_OPCION_SELECCIONADA = "opcionSeleccionada"
const val BUNDLE_NOMBRE_OPCION = "nombreOpcion"
const val BUNDLE_INDICE_OPCION = "nombreIndice"
const val BUNDLE_LISTA_PAGO = "listaPago"
const val BUNDLE_LISTA_PLAN = "listaPlan"

// Codigo de Peticion onActivityResult
const val SELECT_PICTURE = 200
const val TAKE_PICTURE = 100

// Etiquetas
const val NOMBRE_ARCHIVO_PDF = "Boleta(IMEI).pdf"
const val MIME_PDF = "application/pdf"
const val RUTA_ARCHIVO_PDF = "/Download/"
const val FORMATO_CUMPLEANOS = "dd MMMM yyyy"
//public final static String FORMATO_CUMPLEANOS = "d 'de' MMMM 'de' yyyy";

// TODO REFACTORIZAR EN UN OBJJECT COMPANION EN CADA CLASE QUE LO LLAMA
// Mapeo Gson
const val ID_ALUMNO = "IdAlumno"
const val NOMBRE = "Nombre"
const val PATERNO = "Paterno"
const val MATERNO = "Materno"
const val CUATRIMESTRE = "Cuatrimestre"
const val ID_CUATRIMESTRE = "IdCuatrimestre"
const val MATERIAS = "Materias"
const val ID_LICENCIATURA = "IdLicenciatura"
const val ID_PLANTEL = "IdPlantel"
const val CURP = "Curp"
const val TELEFONO = "Telefono"
const val MATRICULA = "Matricula"
const val LICENCIATURA = "Licenciatura"
const val PLANTEL = "Plantel"
const val FOTO = "Foto"
const val NACIMIENTO = "Nacimiento"
//
const val BOLETA_URL = "BoletaURL"
//
const val CODE = "Code"
const val DATA = "Data"
const val MESSAGE = "Message"
const val TRACE = "Trace"
//
const val PAGOS = "Pagos"
const val PLAN = "Plan"
const val PAGO = "Pago"
const val ESTATUS = "Estatus"
const val ID_MATERIA = "IdMateria"
const val MATERIA = "Materia"
//
const val TITULO = "titulo"
const val DESCRIPCION = "descripcion"
const val DESCRIPCION_AVISO = "descripcionAviso"
const val PLANTELES = "planteles"
const val NOMBRE_PLANTEL = "nombre"
const val LATITUD = "latitud"
const val LONGITUD = "longitud"
const val MAPA_PLANTELES = "Planteles"
//
const val TOKEN_SESION = "TokenSesion"
const val ALUMNO = "Alumno"
//
const val SOMOS = "somos"
const val KINDER = "Kinder"
const val PRIMARIA = "Primaria"
const val BACHILLERATO = "Bachillerato"
const val lICENCIATURAS = "Licenciaturas"
const val MAESTRIAS = "Maestrias"
const val DOCTORADOS = "Doctorados"
const val DIPLOMADOS = "Diplomados"

val encabezadoOpciones = arrayOf("Que es Grupo Educativo IMEI",
    "Kinder",
    "Primaria",
    "Bachillerato Tecnológico",
    "Licenciaturas",
    "Maestrias",
    "Doctorados",
    "Planteles",
    "Diplomados",
    "Cursos",
    "Aviso de privacidad")
val listaDiplomadoDerechoCriminologia = arrayOf("1.- Criminología clínica y Psicología clínica",
    "2.- Técnicas de Evaluación de personalidad",
    "3.- Victimología",
    "4.- Grafología",
    "5.- Inducción y Técnicas de Litigio")
val listaDiplomadoCriminalistica = arrayOf("1.- Criminalística",
    "2.- Fotografía Forense",
    "3.- Grafoscopia y Documentoscopia")
val listaDiplomadoPsicologia = arrayOf("1.- Tanatologia",
    "2.- Técnicas de Evaluación de la Personalidad",
    "3.- Tratamiento para la Depresión y las Adicciones",
    "4.- Desarrollo Infantil",
    "5.- Masajes y Acupuntura",
    "6.- Psicología Forense",
    "7.-Fisionomía del rostro, micro expresiones y lenguaje no verbal",
    "8.- Enfermería")


const val cursos = "[\n" +
        "  {\n" +
        "    \"titulo\": \"AMOR A SÍ MISMO Y AMOR A LOS DEMÁS\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"AMOR INMADURO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"AMOR Y ODIO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"ANSIEDAD\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"AUTOESTIMA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"BIEN Y MAL\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"CARENCIAS\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"CAUSA Y EFECTO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"CAPACITACION INTEGRAL\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"CUANDO UN ACTO VIVIDO FORTALECE O DEBILITA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"COMO COMPARTIR EN GRUPO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"COMO SE VIVE EL AMOR\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"COMO  VIVO MI SEXUALIDAD\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"COMPETENCIA CON OTROS Y SER COMPETENTE CONMIGO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"CONCIENCIA DIVIDIDA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"DERROTA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"DEFENSA ANTE EL DOLOR\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"DISCIPLINA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"EGOISMO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"EL AMOR ASI MISMO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"EMOCIONES EN CHOQUE\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"EL LÍMITE Y TOTALIDAD DE LO QUE SOY\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"EL MIEDO ESTANCA O SUPERA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"EL PECADO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"EL TRAUMA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"EN BUSCA DE LA CONCIENCIA (CONOCIMIENTO)\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"EN BUSCA DE LA TOTALIDAD DE UNO MISMO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"EN DONDE SE INVIERTE MI ENERGIA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"GUERRA CON EL DEPREDADOR\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"GUERRAS INTERNAS\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"HERRAMIENTAS PARA LOGRAR UN CARACTER DE PROSPERIDAD\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"HUMILDAD\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"INHABILIDAD PARA ENFRENTAR LA CRISIS\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"INSEGURIDAD\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"INTELIGENCIA SANA Y NEUROTICA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LA BÚSQUEDA DE SIGNIFICADO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LA BÚSQUEDA DEL BIEN UNA ACTITUD NEUROTICA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LA DESTRUCCION DE MI SER\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LA CRÍTICA COMO UNA HERRAMIENTA PARA DESPLOMAR EL OFENDERSE\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LAS DOS MENTES\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LA LUCHA ENTRE EL ENGAÑO Y EL DARSE CUENTA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LA MUJER HOY\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LA NECESIDAD DE SER UN HÉROE O DE TENER UNO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LA RAZÓN Y LO DESCONOCIDO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LA TERQUEDAD DE LA PERCEPCIÓN\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LA RESPONSABILIDAD DE MI VIDA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LA RUPTURA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LENGUAJE Y ACTO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LIBERTAD\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LA PREOCUPACION DESGASTA MI ENERGIA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LO ABSTRACTO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LOS AMARRES QUE SOSTIENEN LO QUE NO SOMOS\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LÍMITE Y TOTALIDAD DE LO QUE SOY \"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"LOCURA O CORDURA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"MIS TRAUMAS\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"MAS ALLÁ DE LA SINTAXTIS\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"MEDITACION\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"MODALIDADES DE LA PERSONALIDAD\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"MORAL FAMILIAR\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"NECESIDADES MENTALES Y EMOCIONALES (SUFRIMIENTO)\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"NEGACIÓN DE RECUERDOS INFANTILES\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"NUESTRA HISTORIA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"RESISTENCIA AL CAMBIO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"OBSESIÓN\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"OBSTÁCULOS CONCEPTUALES\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"OBSTÁCULOS EXISTENCIALES QUE IMPIDEN MI REALIZACION\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"ORGULLOS LASTIMADOS\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"PENSAMIENTO CRÍTICO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"PERCEPCIÓN\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"PERSONALIDAD\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"PRIMERA ATENCIÓN\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"POCA TOLERANCIA A LA FRUSTRACIÓN\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"PODER Y MISERIA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"PORQUERIA CONCEPTUAL\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"PREJUICIOS\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"PREMIO Y CASTIGO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"PRINCIPALES CAUSAS DEL SUFRIMIENTO\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"RELACIÓN PADRES E HIJOS\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"RELACIÓN DE PAREJA\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"TRINCHERA CONCEPTUAL\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"SUMISION\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"TOMA DE DECISIONES\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"VIRUS MENTAL\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"titulo\": \"VOLUNTAD\"\n" +
        "  }\n" +
        "]"