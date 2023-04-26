package it.polito.did.gameskeleton

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
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
    private var currentTeam = Team()
    private var currentPlayer = Player()
    private var player = Player()
    private var gameID : Int = 0
    private var miniPts : Int = 0
    private val teamNames = listOf("Red", "Blue", "Green", "Yellow")
    private var sizes = ArrayList<Int>()
    private var turn : Int = 1
    private var phase : Int = 0
    private var capId : Int = 0

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
        println("id $players.keys.size")
        sizes = teamNames.map{ teams[it]?.size ?: 0 } as ArrayList<Int> //guarda il numero di componenti per ogni squadra
        val pickAGuy = teamNames.map{ teams[it]?.get(0)?: "" } //piglia il quartetto di giocatori che sono nell'indice x della squadra
        val min: Int = sizes.stream().min(Integer::compare).get() //guarda il valore più basso di componenti in una squadra
        //println("min $min")
        println("size1 $sizes")
        var index = sizes.indexOf(min) //indica l'indice della squadra con meno giocatori
        //println("team ${teamNames[index]}")
        val updatedPlayers = players.toMutableMap() //lista dei giocatori con squadra
        var changed = false
        //println("players $updatedPlayers")
        //println("the guy is $pickAGuy")
        //println("the captain is ${players.keys}")
        player.setPlayerId(min)
        player.setPlayerTeam(index)
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
                gameID = random
                //val ref = firebase.reference.push()
                Log.d("GameManager","Creating match ${ref.key}")
                ref.setValue(
                    mapOf(
                        "date" to LocalDateTime.now().toString(),
                        "owner" to firebaseAuth.uid,
                        "screen" to "WaitingStart"
                    )
                ).await()
                setDatabase(ref)
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

    fun goToMascotte(){
        mutableScreenName.value = ScreenName.Mascotte(getMyTeam())
        //TODO try catch da mettere a posto
    }

    fun setDatabase(ref : DatabaseReference){
        val red = ref.child("Red")
        val blue = ref.child("Blue")
        val yellow = ref.child("Yellow")
        val green = ref.child("Green")
        val redpal = ref.child("Red").child("MiniPoints")
        val redbro = ref.child("Red").child("MiniPoints").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("ao " + snapshot.value)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        }) //TODO capire perchè qua c'è da fare ogni volta sto ondatachange mi sa peso
            //TODO occhio che sto onDataChange può essere molto utile per gestire fine delle fasi e simili
        val phase = ref.child("Phase")

        phase.setValue(0)

        phase.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("onDataChange with ${snapshot.value}")
                if(snapshot.value.toString() != "0"){
                    endPhase()
                }
                else println("else condition with ${snapshot.value}")
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })


        red.setValue(
            mapOf(
                "MiniPoints" to 0,
                "VictoryPoints" to 0,
                "Cards" to 0,
                "Money" to 0,
                "Energy" to 0
            )
        )

        redpal.get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        blue.setValue(
            mapOf(
                "MiniPoints" to 0,
                "VictoryPoints" to 0,
                "Cards" to 0,
                "Money" to 0,
                "Energy" to 0
            )
        )
        yellow.setValue(
            mapOf(
                "MiniPoints" to 0,
                "VictoryPoints" to 0,
                "Cards" to 0,
                "Money" to 0,
                "Energy" to 0
            )
        )
        green.setValue(
            mapOf(
                "MiniPoints" to 0,
                "VictoryPoints" to 0,
                "Cards" to 0,
                "Money" to 0,
                "Energy" to 0
            )
        )
    }

    fun endTurn(teams : Teams){
        // TODO prendere riferimento di chi ha appena schiacciato il bottone di endturn
        //disabilitare modifiche alla sua squadra
        //cambiare il riferimento alla squadra in possesso del turno
        //avviare schermata mascotte passando il riferimento della squadra corrente
    }

    fun changeTurn(){
        val oldCapTeam = currentPlayer.getPlayerTeam()
        println("old cap id $capId , team $oldCapTeam")
        println("sizes $sizes")
        println("teamnames $teamNames")
        capId++
        currentPlayer.setPlayerTeam((oldCapTeam + 1) % teamNames.size)
        currentPlayer.setPlayerId(capId)
        if(currentPlayer.getPlayerTeam() == 0){
            capId++
            println("old turn $turn")
            if (turn++ == 4){
                turn = 1
                firebase.getReference(matchId.toString()).child("Phase").setValue(phase + 1)
            }
            println("now turn $turn")
        }
        //currentPlayer.setPlayerId(capId % sizes[currentPlayer.getPlayerTeam()])
        println("now id ${currentPlayer.getPlayerId()}, team ${currentPlayer.getPlayerTeam()}")
    }

    fun getTurn() : Int{
        //return turn TODO questo è quello giusto
        return (capId + 1)
    }

    fun endPhase() {
        val ref = firebase.getReference(gameID.toString())

        val red = ref.child("Red").child("MiniPoints")
        val blue = ref.child("Blue").child("MiniPoints")
        val yellow = ref.child("Yellow").child("MiniPoints")
        val green = ref.child("Green").child("MiniPoints")

        phase++
        println("endphase here")


        //TODO separatore tra le fasi + calcolo classifica
    }

    fun startFlappy() {
        mutableScreenName.value = ScreenName.Flappy
        //TODO try catch da mettere a posto e impostare schermata solo per un team
    }

    fun startQuiz(){
        mutableScreenName.value = ScreenName.Quiz
        firebase.getReference(gameID.toString()).child("Phase").setValue(1)
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

    fun sendMiniResult(pts : Int, team : String) {
        miniPts += pts
        firebase.getReference(gameID.toString()).child(team).child("MiniPoints").setValue(pts)
        //TODO calcolare e inviare al server il punteggio di squadra del minigame, mettere riferimento a squadra
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