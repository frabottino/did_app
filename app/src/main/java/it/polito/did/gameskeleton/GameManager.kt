package it.polito.did.gameskeleton

import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

import java.util.Random

class GameManager(private val scope:CoroutineScope) {
    private val URL = "https://didb-2a4ab-default-rtdb.firebaseio.com/"
    private val firebase = Firebase.database(URL)
    private val firebaseAuth = Firebase.auth
    private var playerName = String()

    init {
        //firebase.setLogLevel(Logger.Level.DEBUG)
        scope.launch {
            try {
                firebaseAuth.signInAnonymously().await()
                Log.d("GameManager", "Current User: ${firebaseAuth.uid}")
                delay(500)
                mutableScreenName.value = ScreenName.Initial
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message?:"Unknown error")
            }
        }
    }

    private val mutableScreenName = MutableLiveData<ScreenName>().also {
        it.value = ScreenName.Splash
    }
    val screenName: LiveData<ScreenName> = mutableScreenName

    private val mutableMatchId = MutableLiveData<String>()
    val matchId: LiveData<String> = mutableMatchId

    private val mutablePlayers = MutableLiveData<Map<String, String>>().also {
        it.value = emptyMap()
    }
    val players: LiveData<Map<String, String>> = mutablePlayers

    private val emojis: MutableLiveData<MutableList<EmojiModel>> by lazy {
        MutableLiveData<MutableList<EmojiModel>>()
    }

    private fun assignTeam(players: Map<String,String>): Map<String,String>? {
        val teams = players.keys.groupBy { players[it].toString() }
        val teamNames = listOf("Red", "Blue", "Green", "Yellow")//("team1", "team2", "team3", "team4")
        val sizes = teamNames.map{ teams[it]?.size ?: 0 }
        val min: Int = sizes.stream().min(Integer::compare).get()
        var index = sizes.indexOf(min)
        val updatedPlayers = players.toMutableMap()
        var changed = false
        teams[""]?.forEach {
            updatedPlayers[it] = teamNames[index]
            index = (index +1) % teamNames.size
            changed = true
        }
        return if (changed) updatedPlayers else null
    }

    private fun watchPlayers() {
        val id = matchId.value ?: throw RuntimeException("Missing match Id")
        val ref = firebase.getReference(id)
        ref.child("players").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val v = snapshot.value
                if (v!=null && v is Map<*, *>) {
                    val updatedPlayers = assignTeam(v as Map<String,String>)
                    if (updatedPlayers != null) {
                        ref.child("players").setValue(updatedPlayers)
                    } else {
                        mutablePlayers.value = v
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

    }

    private fun getMyTeam(): String {
        Log.d("GameManager", "players: ${players.value}")
        Log.d("GameManager", "uid: ${firebaseAuth.uid}")
        println("ciao ${players.value?.toString()}")
        println("${firebaseAuth.uid}")
        println(playerName)
        return players.value?.get(playerName) ?: ""
        //return players.value?.get(firebaseAuth.uid) ?: ""
    }

    private fun getMyName(): String {
        Log.d("GameManager", "players: ${players.value}")
        Log.d("GameManager", "uid: ${firebaseAuth.uid}")
        return players.value?.get(firebaseAuth.toString()) ?: ""
    }

    fun createNewGame() {
        scope.launch {
            try {
                val random = Random().nextInt( 89999) + 10000
                val ref = firebase.getReference(random.toString())
                //val ref = firebase.reference.push()
                Log.d("GameManager","Creating match ${ref.key}")
                ref.setValue(
                    mapOf(
                        "date" to LocalDateTime.now().toString(),
                        "owner" to firebaseAuth.uid,
                        "screen" to "WaitingStart"
                    )
                ).await()
                Log.d("GameManager", "Match creation succeeded")
                mutableMatchId.value = ref.key
                mutableScreenName.value = ScreenName.SetupMatch(ref.key!!)
                watchPlayers()
            } catch (e:Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Generic error")
            }
        }
    }

    private fun watchScreen() {
        val id = matchId.value ?: throw RuntimeException("Missing match Id")
        val ref = firebase.getReference(id)
        ref.child("screen").addValueEventListener(
            object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    mutableScreenName.value = getScreenName(snapshot.value?.toString()?: "")
                }

                override fun onCancelled(error: DatabaseError) {
                    mutableScreenName.value = ScreenName.Error(error.message)
                }
            }
        )

    }

    private fun getScreenName(name:String): ScreenName {
        return when (name) {
            "WaitingStart" -> ScreenName.WaitingStart
            "Playing" -> ScreenName.Home(getMyTeam())//Playing(getMyTeam()) //qui è dove si indica il primo screen del player
            else -> ScreenName.Error("Unknown screen $name")
        }
    }

    fun joinGame(matchId:String, nameId:String) {
        if (matchId.isEmpty()) return
        playerName = nameId
        scope.launch {
            try {
                val ref = firebase.getReference(matchId)
                val data = ref.get().await()
                if (data!=null) {
                    mutableMatchId.value = matchId
                    ref
                        .child("players")
                        .child(playerName)//firebaseAuth.uid!!)
                        .setValue("").await()
                    watchPlayers()
                    watchScreen()
                } else {
                    mutableScreenName.value = ScreenName.Error("Invalid gameId")
                }
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message?: "Generic error")
            }
        }
    }

    fun startGame() {
        scope.launch{
            try {
                val ref = firebase.getReference(matchId.value ?: throw RuntimeException("Invalid State"))
                ref.child("screen").setValue("Playing").await()
                mutableScreenName.value = ScreenName.Home(getMyTeam())
                Log.d("GameManager", "Game started")
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Generic error")
            }
        }
    }


    fun startMemory() {
        emojis.value = mutableListOf(
            EmojiModel("😍"),
            EmojiModel(" 🥰"),
            EmojiModel("😘"),
            EmojiModel("😭"),
            EmojiModel("😢"),
            EmojiModel("😂"),
            EmojiModel("😍"),
            EmojiModel(" 🥰"),
            EmojiModel("😘"),
            EmojiModel("😭"),
            EmojiModel("😢"),
            EmojiModel("😂"),
        ).apply { shuffle() }
    }

    fun getEmojis(): LiveData<MutableList<EmojiModel>> {
        return emojis
    }

    fun loadEmojis() {
        emojis.value = mutableListOf(
            EmojiModel("😍"),
            EmojiModel(" 🥰"),
            EmojiModel("😘"),
            EmojiModel("😭"),
            EmojiModel("😢"),
            EmojiModel("😂"),
            EmojiModel("😍"),
            EmojiModel(" 🥰"),
            EmojiModel("😘"),
            EmojiModel("😭"),
            EmojiModel("😢"),
            EmojiModel("😂"),
        ).apply { shuffle() }
    }

    fun updateShowVisibleCard(id: String) {
        val selects: List<EmojiModel>? = emojis.value?.filter { it -> it.isSelect }
        val selectCount: Int = selects?.size ?: 0
        var charFind: String = "";
        if (selectCount >= 2) {
            val hasSameChar: Boolean = selects!!.get(0).char == selects.get(1).char
            if (hasSameChar) {
                charFind = selects.get(0).char
            }
        }

        val list: MutableList<EmojiModel>? = emojis.value?.map { it ->
            if (selectCount >= 2) {
                it.isSelect = false
            }

            if (it.char == charFind) {
                it.isVisible = false
            }

            if (it.id == id) {
                it.isSelect = true
            }

            it
        } as MutableList<EmojiModel>?

        val visibleCount: Int = list?.filter { it -> it.isVisible }?.size ?: 0
        if (visibleCount <= 0) {
            //loadEmojis()
            mutableScreenName.value = ScreenName.Dashboard
            println("ayo")
            return
        }

        emojis.value?.removeAll { true }
        emojis.value = list
    }


}