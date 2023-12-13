import com.mariela.stores.model.Paquete
import retrofit2.http.GET

interface ApiService {
    @GET("/api/paquetes/list")
    suspend fun obtenerPaquetes(): List<Paquete>
}