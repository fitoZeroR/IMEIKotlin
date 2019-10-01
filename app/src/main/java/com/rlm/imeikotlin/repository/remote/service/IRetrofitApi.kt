package com.rlm.imeikotlin.repository.remote.service

import androidx.lifecycle.LiveData
import com.rlm.imeikotlin.repository.remote.api.ApiResponse
import com.rlm.imeikotlin.repository.remote.model.response.*
import com.rlm.imeikotlin.utils.APIConstants
import com.rlm.imeikotlin.utils.APIConstants.URL_ASIGNATURAS_PAGOS
import com.rlm.imeikotlin.utils.APIConstants.URL_DESCARGA_BOLETA
import com.rlm.imeikotlin.utils.APIConstants.URL_ENVIAR_FOTOGRAFIA
import com.rlm.imeikotlin.utils.APIConstants.URL_RECUPERAR_PASSWORD
import retrofit2.http.*

interface IRetrofitApi {
    @GET(APIConstants.URL_OPCIONES)
    fun obtieneOpciones(): LiveData<ApiResponse<OpcionesResponse>>

    @GET(APIConstants.URL_PLANTELES)
    fun obtienePlanteles(): LiveData<ApiResponse<InformacionPlantelesResponse>>

    // se necesita que el servidor acepte un Body (una clase), para poder implementarlo de esta forma
    /*@Headers({"Accept: application/json"})
    //@POST(APIConstants.URL_ENVIAR_INFORMACION)
    //@FormUrlEncoded
    fun enviarInformacion(@Body enviarInformacionRequest: EnviarInformacionRequest): LiveData<ApiResponse<EnviarInformacionResponse>>*/
    @POST(APIConstants.URL_ENVIAR_INFORMACION)
    @FormUrlEncoded
    fun enviarInformacion(
        @Field("Nombre") nombre: String, @Field("Telefono") telefono: String, @Field("Correo") correo: String,
        @Field("Comentarios") comentarios: String, @Field("Interes") interes: String
    ): LiveData<ApiResponse<EnviarInformacionResponse>>

    @POST(APIConstants.URL_LOGIN)
    @FormUrlEncoded
    fun autenticarUsuario(@Field("Matricula") matricula: String, @Field("Contrasena") password: String): LiveData<ApiResponse<LoginResponse>>

    @POST(URL_ENVIAR_FOTOGRAFIA)
    @FormUrlEncoded
    fun enviarFotografia(@Field("TokenSesion") tokenSesion: String, @Field("FotoResponse") foto: String): LiveData<ApiResponse<FotoResponse>>

    @POST(URL_ASIGNATURAS_PAGOS)
    @FormUrlEncoded
    fun obtieneAsignaturasPagos(@Field("TokenSesion") tokenSesion: String): LiveData<ApiResponse<PagosAsignaturasResponse>>

    @POST(URL_DESCARGA_BOLETA)
    @FormUrlEncoded
    fun obtieneBoleta(@Field("TokenSesion") tokenSesion: String): LiveData<ApiResponse<DescargaBoletaResponse>>

    @POST(URL_RECUPERAR_PASSWORD)
    @FormUrlEncoded
    fun recuperaPassword(@Field("Matricula") matricula: String): LiveData<ApiResponse<RecuperarPasswordResponse>>
}