package com.example.headsup

import android.content.Intent
import android.content.res.Configuration
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class celebritiesGame : AppCompatActivity() {
    lateinit var timerView: FrameLayout

    lateinit var Timer: TextView
    lateinit var hint1: TextView
    lateinit var hint2: TextView
    lateinit var hint3: TextView
    lateinit var celName: TextView
    lateinit var cardCel: CardView
    lateinit var rotate: LinearLayout
    lateinit var endofGameView: LinearLayout
    lateinit var startOfGameView: LinearLayout
    lateinit var scoreView: TextView
    lateinit var playagain: Button
    lateinit var gotoHome: Button
    var numOfGuessedCel = 0;
    var myCelList = ArrayList<SItem>()
    var mMediaPlayer: MediaPlayer? = null
    var isGameEnd = false
    var isbeginOfTheGame = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_celebrities_game)
        Timer = findViewById(R.id.timer)
        timerView = findViewById(R.id.timerLinear)
        celName = findViewById(R.id.nameofC)
        hint1 = findViewById(R.id.hint1)
        hint2 = findViewById(R.id.hint2)
        hint3 = findViewById(R.id.hint3)
        cardCel = findViewById(R.id.celCard)
        rotate = findViewById(R.id.rotaite)
        endofGameView = findViewById(R.id.EgameView)
        startOfGameView = findViewById(R.id.stagameView)
        scoreView = findViewById(R.id.letsStart)
        playagain = findViewById(R.id.playagain)
        gotoHome = findViewById(R.id.gotomain)
        getCelList()
        isbeginOfTheGame = true

        playagain.setOnClickListener {
            this.recreate()
        }

        gotoHome.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig != null && !isGameEnd) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                 if (isbeginOfTheGame) {
                    startCount()
                    isbeginOfTheGame = false
                    startOfGameView.isVisible = false
                }

                pauseSound()
                playClockSound()

                cardCel.isVisible = true
                rotate.isVisible = false
                timerView.isVisible = true

                celName.text = myCelList[numOfGuessedCel].name
                hint1.text = myCelList[numOfGuessedCel].taboo1
                hint2.text = myCelList[numOfGuessedCel].taboo2
                hint3.text = myCelList[numOfGuessedCel].taboo3


            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                pauseSound()
                playPillSound()
                cardCel.isVisible = false
                rotate.isVisible = true
                numOfGuessedCel++


            }

        }

    }


    private fun getCelList() {


        val api = Retrofit.Builder()
            .baseUrl("https://dojo-recipes.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<List<SItem>?> = api.getSInfo()!!.awaitResponse()
            if (response.isSuccessful) {
                val dataBody = response.body()!!
                Log.d("dataShow", "Success :" + dataBody.toString())
                withContext(Dispatchers.Main) {
                    myCelList = dataBody as ArrayList<SItem>
                }
            } else {
                Log.d("dataShow", "failed !")

            }
        }


    }

    fun startCount() {

        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var seconds = millisUntilFinished / 1000
                Timer.setText("" + seconds)


            }

            override fun onFinish() {
                pauseSound()
                playeEndOfGameSound()
                endofGameView.isVisible = true
                cardCel.isVisible = false
                rotate.isVisible = false
                timerView.isVisible = false
                isGameEnd = true

                scoreView.text = "You Guess $numOfGuessedCel in 60 sec !"

            }
        }.start()


    }

    fun playClockSound() {

        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.clock)
            mMediaPlayer!!.start()
            mMediaPlayer!!.isLooping = true
        } else mMediaPlayer!!.start()
    }

    fun playPillSound() {
        var mMediaPlayer: MediaPlayer? = null

        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.ring)
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    fun playeEndOfGameSound() {
        var mMediaPlayer: MediaPlayer? = null

        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.bill)
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    fun pauseSound() {
        if (mMediaPlayer != null && mMediaPlayer!!.isPlaying) mMediaPlayer!!.pause()
    }

}








