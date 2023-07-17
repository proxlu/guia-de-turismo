package com.proxluquime.guiadeturismo

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity() {
    private lateinit var locationListView: ListView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val listaLocaisDisponiveis = mutableListOf<Location>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationListView = findViewById(R.id.locationListView)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (checkLocationPermission()) {
            obterLocaisProximos()
        } else {
            requestLocationPermission()
        }
    }

    private fun checkLocationPermission(): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        return fineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                coarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

    private fun obterLocaisProximos() {
        if (checkLocationPermission()) {
            // Cria uma requisição de localização
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 5000 // Intervalo de atualização de localização em milissegundos (5 segundos)
                fastestInterval = 2000 // Intervalo mais rápido para atualização de localização em milissegundos (2 segundos)
            }

            // Cria um callback para obter a localização atual
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location: Location? = locationResult.lastLocation
                    location?.let {
                        // Aqui você tem a localização atual do usuário (location)

                        // Limpa a lista de locais disponíveis antes de adicionar os locais próximos
                        listaLocaisDisponiveis.clear()

                        // Adiciona os locais disponíveis à lista
                        obterLocaisDisponiveisExemplo()

                        // Vamos calcular a distância entre a localização atual do usuário e cada local disponível
                        val listaLocaisProximos = mutableListOf<Location>()
                        for (local in listaLocaisDisponiveis) {
                            val distancia = calcularDistancia(location.latitude, location.longitude, local.latitude, local.longitude)
                            if (distancia <= DISTANCIA_MAXIMA) {
                                listaLocaisProximos.add(local)
                            }
                        }

                        // Agora temos a lista de locais próximos (listaLocaisProximos).
                        // Vamos usá-la para exibir os locais no ListView usando o LocationAdapter.

                        val locationAdapter = LocationAdapter(this@MainActivity, listaLocaisProximos)
                        locationListView.adapter = locationAdapter
                    }
                }
            }

            // Solicita a atualização da localização em tempo real
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        } else {
            // Permissão não concedida, você pode lidar com essa situação adequadamente aqui
            // Por exemplo, exiba uma mensagem de erro ou solicite novamente a permissão
        }
    }

    private fun obterLocaisDisponiveisExemplo() {
        // Adicione as informações reais do local aqui
        listaLocaisDisponiveis.add(Location("Local 1", "Descrição 1", -22.1234, -43.5678))
        listaLocaisDisponiveis.add(Location("Local 2", "Descrição 2", -22.4567, -43.8765))
        listaLocaisDisponiveis.add(Location("Local 3", "Descrição 3", -22.7890, -43.2345))
        // Adicione mais locais aqui, se necessário
    }

    private fun calcularDistancia(
        lat1: Double, lon1: Double, lat2: Double, lon2: Double
    ): Float {
        // Implemente o cálculo da distância entre dois pontos usando a fórmula Haversine
        // ou outra fórmula apropriada para cálculo de distâncias geográficas.
        // Retorne a distância em metros (ou em outra unidade, se necessário).
        return 0f // Retorno de exemplo, substitua pelo cálculo real
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
        private const val DISTANCIA_MAXIMA = 1000 // Defina a distância máxima em metros para considerar um local como próximo
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, inicia a obtenção dos locais próximos
                obterLocaisProximos()
            } else {
                // Permissão negada, você pode lidar com essa situação adequadamente aqui
                // Por exemplo, exiba uma mensagem informando ao usuário que a permissão é necessária para acessar os locais próximos.
            }
        }
    }
}