package com.racso.mylocations.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.racso.mylocations.R
import com.racso.mylocations.core.Resource
import com.racso.mylocations.data.LocationsRepository
import com.racso.mylocations.data.remote.LocationsDataSource
import com.racso.mylocations.domain.LocationService
import com.racso.mylocations.utils.AppConstants
import com.racso.mylocations.utils.formatToStringMap
import com.racso.mylocations.utils.toast
import com.upax.moviesapp.data.model.Location
import com.upax.moviesapp.data.model.toListString
import java.util.*


class LocationsActivity : AppCompatActivity() {

    private var mapView: MapView? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private var bottomIsVisible = false
    private lateinit var floatingButton: FloatingActionButton


    private val viewModel by viewModels<LocationsViewModel> {
        LocationsViewModelFactory(LocationsRepository(LocationsDataSource()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_locations)
        mapView = findViewById(R.id.mapView)
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)
        floatingButton = findViewById(R.id.floating_action_button)
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.standard_bottom_sheet))
        setupListeners()
        setupObservers()
        setupNotificationCanel()
        requestPermission()
        startTackingLocation()
    }

    private fun setupObservers() {
        viewModel.locations.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    toast("Loading locations")
                }
                is Resource.Succes -> {
                    fetchLocations(it.data)
                    setupLocationsLis(it.data.toListString())
                }
                is Resource.Failure -> {
                    toast("Error locations not loaded")
                }
            }
        }
    }

    private fun setupLocationsLis(locations: List<String>) {
        val recyclerView = findViewById<ListView>(R.id.rv_locations)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locations)
        recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        floatingButton.setOnClickListener {
            if (!bottomIsVisible) {
                bottomIsVisible = true
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                floatingButton.setImageResource(R.drawable.baseline_location_on_24)
            } else {
                bottomIsVisible = false
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                floatingButton.setImageResource(R.drawable.baseline_format_list_bulleted_24)
            }
        }
    }

    private fun fetchLocations(locations: List<Location>) {
        updateCamera(locations.last().position!!.longitude, locations.last().position!!.latitude)
        for (location in locations) {
            addMarker(
                location.position!!.longitude,
                location.position!!.latitude,
                location.created_at!!
            )
        }
    }

    private fun setupNotificationCanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                AppConstants.NOTIFICATION_CHANEL,
                "Location",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager =
                getSystemService((Context.NOTIFICATION_SERVICE)) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startTackingLocation() {
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }
    }

    private fun stopTackingLocation() {
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            startService(this)
        }
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            100
        )
    }

    private fun addMarker(longitude: Double, latitude: Double, date: Date) {
        //Log.e("Marker","longitude: $longitude, latitude: $latitude, date: $date")
        try {
            bitmapFromDrawableRes(
                this,
                R.drawable.red_marker
            )?.let {
                val annotationApi = mapView?.annotations
                val pointAnnotationManager =
                    mapView?.let { annotationApi?.createPointAnnotationManager() }
                val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                    .withPoint(Point.fromLngLat(longitude, latitude))
                    .withIconImage(it)
                    .withTextField(date.formatToStringMap())
                    .withTextSize(10.0)
                pointAnnotationManager?.create(pointAnnotationOptions)
            }
        } catch (e: Exception) {
            Log.e("Marker", e.message.toString())
        }
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            // copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    private fun updateCamera(longitude: Double, latitude: Double) {
        val mapAnimationOptions = MapAnimationOptions.Builder().duration(1500L).build()
        mapView?.camera?.easeTo(
            CameraOptions.Builder()
                // Centers the camera to the lng/lat specified.
                .center(Point.fromLngLat(longitude, latitude))
                // specifies the zoom value. Increase or decrease to zoom in or zoom out
                .zoom(11.5)
                // specify frame of reference from the center.
                .padding(EdgeInsets(500.0, 0.0, 0.0, 0.0))
                .build(),
            mapAnimationOptions
        )
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
        stopTackingLocation()
    }


}