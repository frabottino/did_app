package it.polito.did.gameskeleton

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import it.polito.did.gameskeleton.flappyminigame.FlappyBird
import it.polito.did.gameskeleton.flappyminigame.Game
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
    private var currentTeam = Team()
    private var currentPlayer = Player()

    init {
        //firebase.setLogLevel(Logger.Level.DEBUG)
        scope.launch {
            try {
                firebaseAuth.signInAnonymously().await()
                Log.d("GameManager", "Current User: ${firebaseAuth.uid}")
                delay(2000)
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
        val teams : Teams
    }
    val players: LiveData<Map<String, String>> = mutablePlayers

    private val emojis: MutableLiveData<MutableList<EmojiModel>> by lazy {
        MutableLiveData<MutableList<EmojiModel>>()
    }

    private fun assignTeam(players: Map<String,String>): Map<String,String>? {
        val teams = players.keys.groupBy { players[it].toString() } //raggruppa i giocatori per squadra
        val teamNames = listOf("Red", "Blue", "Green", "Yellow")
        val sizes = teamNames.map{ teams[it]?.size ?: 0 } //guarda il numero di componenti per ogni squadra
        println(sizes)
        val pickAGuy = teamNames.map{ teams[it]?.get(1)} //piglia il quartetto di giocatori che sono nell'indice x della squadra
        val min: Int = sizes.stream().min(Integer::compare).get() //guarda il valore più basso di componenti in una squadra
        var index = sizes.indexOf(min) //indica l'indice della squadra con meno giocatori
        val updatedPlayers = players.toMutableMap() //lista dei giocatori con squadra
        var changed = false
        println("players " + updatedPlayers)
        println("the guy is " + pickAGuy)
        println(teamNames[1])
        teams[""]?.forEach {
            updatedPlayers[it] = teamNames[index] //lista giocatori aggiornata
            index = (index +1) % teamNames.size //aggiorna indice di squadra meno popolata
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
            //"Playing" -> ScreenName.Home(getMyTeam())//Playing(getMyTeam()) //qui è dove si indica il primo screen del player
            "Playing" -> ScreenName.Mascotte(getMyTeam())//Playing(getMyTeam()) //qui è dove si indica il primo screen del player
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
                    //ref.child("Quiz").setValue("")
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
                //mutableScreenName.value = ScreenName.Home(getMyTeam())
                mutableScreenName.value = ScreenName.Mascotte(getMyTeam())
                Log.d("GameManager", "Game started")
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Generic error")
            }
        }
    }

    fun goToHome(){
        mutableScreenName.value = ScreenName.Home(getMyTeam())
        //TODO try catch da mettere a posto
    }

    fun endTurn(teams : Teams){
        // TODO prendere riferimento di chi ha appena schiacciato il bottone di endturn
        //disabilitare modifiche alla sua squadra
        //cambiare il riferimento alla squadra in possesso del turno
        //cambiare il riferimento al capitano della squadra
        //avviare schermata mascotte passando il riferimento della squadra corrente
    }

    fun selectCaptain(){
        //TODO selezionare il capitano del turno
    }

    fun changeTurn(){
        //TODO cambiare squadra per turno
    }

    fun endPhase(){
        //TODO separatore tra le fasi + calcolo classifica
    }

    fun startFlappy() {
        mutableScreenName.value = ScreenName.Flappy
        //TODO try catch da mettere a posto e impostare schermata solo per un team
    }

    fun startQuiz(){
        mutableScreenName.value = ScreenName.Quiz
        //TODO try catch da mettere a posto e impostare schermata solo per un team
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
        mutableScreenName.value = ScreenName.Memory

        //TODO try catch da mettere a posto
    }

    fun sendFlappyResult(){
        //TODO calcolare e inviare al server il punteggio di squadra flappy
    }

    fun sendMemoryResult(){
        //TODO calcolare e inviare al server il punteggio di squadra memory
    }

    fun sendQuizResult(){
        //TODO calcolare e inviare al server il punteggio di squadra quiz
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