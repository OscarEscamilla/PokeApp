package com.racso.pokeapp.ui.components

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView
import com.racso.pokeapp.R
import java.net.URL


class ProfileImage : MaterialCardView  {
    private lateinit var rootLayout: MaterialCardView
    private lateinit var  imageUser: AppCompatImageView
    private lateinit var  txtInitials: TextView
    //
    private  var cardRadius = 0

    // atributes
    private var userName: String? = null
    private var initialsColor: Int = Color.BLUE
    private var backgroundColor: Int = Color.RED
    private var placeholderImage: Drawable ? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        LayoutInflater.from(context).inflate(R.layout.profile_image, this, false)
        //init view
        rootLayout = findViewById(R.id.root_card)
        imageUser = findViewById(R.id.image_user)
        txtInitials = findViewById(R.id.txt_initials)
        // set card radius
        cardRadius =  rootLayout.layoutParams.height / 2
        rootLayout.radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cardRadius.toFloat(), context.resources.displayMetrics)
        // Load attributes
        val a = context.obtainStyledAttributes(attrs, R.styleable.ProfileImage, defStyle, 0)
        userName = a.getString(R.styleable.ProfileImage_userName)
        backgroundColor = a.getColor(R.styleable.ProfileImage_backgroundColor, backgroundColor)
        initialsColor = a.getColor(R.styleable.ProfileImage_initialsColor,initialsColor)

        if (a.hasValue(R.styleable.ProfileImage_placeholderImage)) {
            placeholderImage = a.getDrawable(
                R.styleable.ProfileImage_placeholderImage
            )
            placeholderImage?.callback = this
        }
        a.recycle()




    }

    fun load(userName: String, urlImage: String){
        // no
        if (urlImage.isEmpty() or !loadRemoteImage(urlImage)){
            val initialsText = getInitiasl(userName)
            imageUser.visibility = View.GONE
            txtInitials.text = initialsText
            txtInitials.visibility = View.VISIBLE
        }else if(userName.isEmpty() or !loadRemoteImage(urlImage)){
            imageUser.setImageResource(R.drawable.ic_launcher_background)
            imageUser.visibility = View.VISIBLE
            txtInitials.visibility = View.GONE
        }


    }

    fun loadRemoteImage(urlImage: String): Boolean {
        return try {
            val url = URL(urlImage)
            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            imageUser.setImageBitmap(bmp)
            true
        }catch (e: java.lang.Exception){
            false
        }
    }

    private fun getInitiasl(name: String): String {
        var result = ""
        if (name.isNotEmpty()){
            val arrayWords = name.split(" ")
            if (arrayWords.size >= 2){
                result = arrayWords[0].substring(0,1) + arrayWords[1].substring(0,1)
            }else if(arrayWords.size == 1){
                result = name.substring(0,2)
            }
        }
        return result.uppercase()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

    }
}