package com.rlm.imeikotlin.repository.ws

import com.rlm.imeikotlin.repository.modelo.*
import com.rlm.imeikotlin.utils.APIConstants
import com.rlm.imeikotlin.utils.APIConstants.URL_ASIGNATURAS_PAGOS
import com.rlm.imeikotlin.utils.APIConstants.URL_DESCARGA_BOLETA
import com.rlm.imeikotlin.utils.APIConstants.URL_ENVIAR_FOTOGRAFIA
import com.rlm.imeikotlin.utils.APIConstants.URL_RECUPERAR_PASSWORD
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface IRetrofitWS {
    @GET(APIConstants.URL_OPCIONES)
    fun obtieneOpciones(): Observable<Opciones>

    @GET(APIConstants.URL_PLANTELES)
    fun obtienePlanteles(): Observable<InformacionPlanteles>

    @POST(APIConstants.URL_ENVIAR_INFORMACION)
    @FormUrlEncoded
    fun enviarInformacion(
        @Field("Nombre") nombre: String, @Field("Telefono") telefono: String, @Field("Correo") correo: String,
        @Field("Comentarios") comentarios: String, @Field("Interes") interes: String
    ): Observable<EnviarInformacion>

    @POST(APIConstants.URL_LOGIN)
    @FormUrlEncoded
    fun autenticarUsuario(@Field("Matricula") matricula: String, @Field("Contrasena") password: String): Observable<Login>

    @POST(URL_ENVIAR_FOTOGRAFIA)
    @FormUrlEncoded
    fun enviarFotografia(@Field("TokenSesion") tokenSesion: String, @Field("Foto") foto: String): Observable<Foto>

    @POST(URL_ASIGNATURAS_PAGOS)
    @FormUrlEncoded
    fun obtieneAsignaturasPagos(@Field("TokenSesion") tokenSesion: String): Observable<PagosAsignaturas>

    @POST(URL_DESCARGA_BOLETA)
    @FormUrlEncoded
    fun obtieneBoleta(@Field("TokenSesion") tokenSesion: String): Observable<DescargaBoleta>

    @POST(URL_RECUPERAR_PASSWORD)
    @FormUrlEncoded
    fun recuperaPassword(@Field("Matricula") matricula: String): Observable<RecuperarPassword>
}