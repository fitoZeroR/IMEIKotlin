package com.rlm.imeikotlin.data.remote.api

import com.rlm.imeikotlin.data.remote.model.response.*
import com.rlm.imeikotlin.utils.APIConstants
import com.rlm.imeikotlin.utils.APIConstants.URL_ASIGNATURAS_PAGOS
import com.rlm.imeikotlin.utils.APIConstants.URL_DESCARGA_BOLETA
import com.rlm.imeikotlin.utils.APIConstants.URL_ENVIAR_FOTOGRAFIA
import com.rlm.imeikotlin.utils.APIConstants.URL_RECUPERAR_PASSWORD
import retrofit2.Response
import retrofit2.http.*

interface IRetrofitService {
    @GET(APIConstants.URL_OPCIONES)
    suspend fun obtieneOpciones(): Response<OpcionesResponse>

    @GET(APIConstants.URL_PLANTELES)
    suspend fun obtienePlanteles(): Response<InformacionPlantelesResponse>

    // se necesita que el servidor acepte un Body (una clase), para poder implementarlo de esta forma
    /*@Headers({"Accept: application/json"})
    //@POST(APIConstants.URL_ENVIAR_INFORMACION)
    //@FormUrlEncoded
    fun enviarInformacion(@Body enviarInformacionRequest: EnviarInformacionRequest): LiveData<ApiResponse<EnviarInformacionResponse>>*/
    @POST(APIConstants.URL_ENVIAR_INFORMACION)
    @FormUrlEncoded
    suspend fun enviarInformacion(
        @Field("Nombre") nombre: String, @Field("Telefono") telefono: String, @Field("Correo") correo: String,
        @Field("Comentarios") comentarios: String, @Field("Interes") interes: String
    ): Response<EnviarInformacionResponse>

    @POST(APIConstants.URL_LOGIN)
    @FormUrlEncoded
    suspend fun autenticarUsuario(@Field("Matricula") matricula: String, @Field("Contrasena") password: String): Response<LoginResponse>

    @POST(URL_ENVIAR_FOTOGRAFIA)
    @FormUrlEncoded
    suspend fun enviarFotografia(@Field("TokenSesion") tokenSesion: String, @Field("FotoResponse") foto: String): Response<FotoResponse>

    @POST(URL_ASIGNATURAS_PAGOS)
    @FormUrlEncoded
    suspend fun obtieneAsignaturasPagos(@Field("TokenSesion") tokenSesion: String): Response<PagosAsignaturasResponse>

    @POST(URL_DESCARGA_BOLETA)
    @FormUrlEncoded
    suspend fun obtieneBoleta(@Field("TokenSesion") tokenSesion: String): Response<DescargaBoletaResponse>

    @POST(URL_RECUPERAR_PASSWORD)
    @FormUrlEncoded
    suspend fun recuperaPassword(@Field("Matricula") matricula: String): Response<RecuperarPasswordResponse>
}