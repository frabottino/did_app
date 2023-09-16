package it.polito.did.gameskeleton

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class GameManager(private val scope:CoroutineScope) {
    private val URL = "https://didb-2a4ab-default-rtdb.firebaseio.com/"
    private val firebase = Firebase.database(URL)
    private val firebaseAuth = Firebase.auth
    private var playerName = String()
    private var currentPlayer = Player()
    private var player = Player()
    private var gameID : Int = 0
    private var yMiniPts : Int = 0
    private var gMiniPts : Int = 0
    private var rMiniPts : Int = 0
    private var bMiniPts : Int = 0
    private var yMiniTemp : Int = 0
    private var gMiniTemp : Int = 0
    private var rMiniTemp : Int = 0
    private var bMiniTemp : Int = 0
    private var rCards = ArrayList<Int>()
    private var bCards = ArrayList<Int>()
    private var gCards = ArrayList<Int>()
    private var yCards = ArrayList<Int>()
    private var myCards = ArrayList<Int>()
    private var rDeck = arrayListOf(0,0,0,0,0,0,0,0,0)
    private var bDeck = arrayListOf(0,0,0,0,0,0,0,0,0)
    private var gDeck = arrayListOf(0,0,0,0,0,0,0,0,0)
    private var yDeck = arrayListOf(0,0,0,0,0,0,0,0,0)
    private var myDeck = arrayListOf(0,0,0,0,0,0,0,0,0)
    private var rVictory : Int = 0
    private var bVictory : Int = 0
    private var gVictory : Int = 0
    private var yVictory : Int = 0
    private var myVictory : Int = 0
    private val teamNames = mutableListOf("Red", "Blue", "Green", "Yellow")
    private var sizes = ArrayList<Int>()
    private var turn : Int = 0
    private var capTeam : Int = 0
    private var phase : Int = 0
    private var capId : Int = 0
    private var card1 : Int = -1
    private var card2 : Int = -1
    private var card3 : Int = -1
    private var card4 : Int = -1
    private var card5 : Int = -1
    private var cards = ArrayList<Card>()
    private var discarded : Boolean = false
    private var temporaryCap : String = ""
    private var special = 101


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
    }
    val players: LiveData<Map<String, String>> = mutablePlayers

    private val emojis: MutableLiveData<MutableList<EmojiModel>> by lazy {
        MutableLiveData<MutableList<EmojiModel>>()
    }

    private fun assignTeam(players: Map<String,String>): Map<String,String>? {
        val teams = players.keys.groupBy { players[it].toString() } //raggruppa i giocatori per squadra
        sizes = teamNames.map{ teams[it]?.size ?: 0 } as ArrayList<Int> //guarda il numero di componenti per ogni squadra
        val pickAGuy = teamNames.map{ teams[it]?.get(0)?: "" } //piglia il quartetto di giocatori che sono nell'indice x della squadra
        val min: Int = sizes.stream().min(Integer::compare).get() //guarda il valore più basso di componenti in una squadra
        //println("min $min")
        //println("size1 $sizes")
        var index = sizes.indexOf(min) //indica l'indice della squadra con meno giocatori
        //println("team ${teamNames[index]}")
        val updatedPlayers = players.toMutableMap() //lista dei giocatori con squadra
        var changed = false
        //println("players $updatedPlayers")
        //println("the guy is $pickAGuy")
        //println("the captain is ${players.keys}")
        if(index!=0) {
            player.setPlayerId(min)
            player.setPlayerTeam(index - 1)
        }
        else {
            player.setPlayerId(min-1)
            player.setPlayerTeam(3)
        }

        teams[""]?.forEach {
            updatedPlayers[it] = teamNames[index] //lista giocatori aggiornata
            index = (index +1) % teamNames.size //aggiorna indice di squadra meno popolata
            changed = true
        }
        return if (changed) updatedPlayers else null
    }

    private fun watchPlayers(go : Boolean) {
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
                        mutablePlayers.value = v}
                    if(player.getPlayerId()!= -1 && go && getMyTeam()!=""){
                        getDatabase(getMyTeam())
                        ConstantCards.getAllCards()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })
    }

    fun getTeamPlayers(team: Int): Collection<List<String>> {
        return mutablePlayers.value!!.keys.groupBy {  mutablePlayers.value!![it].toString() }.filter { (key) -> key == teamNames[team] }.values
    }

    fun setTemporaryCap(cap : String){
        println("CAPITANOOOO $cap")
        firebase.getReference(gameID.toString()).child("TemporaryCap").setValue(cap)
    }

    private fun getMyTeam(): String {
        Log.d("GameManager", "players: ${players.value}")
        Log.d("GameManager", "uid: ${firebaseAuth.uid}")
        return players.value?.get(playerName) ?: ""
    }

    private fun getMyName(): String {
        return players.value?.get(firebaseAuth.toString()) ?: ""
    }

    fun createNewGame() {
        scope.launch {
            try {
                val random = Random().nextInt( 89999) + 10000
                val ref = firebase.getReference(random.toString())
                gameID = random
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
                watchPlayers(false)
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
        println("player name is $playerName")
        return if(playerName == "PCAdmin"){ //GESTORE PARTITA
            when (name) {
                "WaitingStart" -> ScreenName.WaitingStart
                "Playing" -> ScreenName.Menu
                "Ranking" -> ScreenName.MiniRank
                "Memory" -> ScreenName.Menu
                "Flappy" -> ScreenName.Menu
                "Quiz" -> ScreenName.Menu
                "Cards" -> ScreenName.Menu
                "Ending" -> ScreenName.Victory
                else -> ScreenName.Error("Unknown screen $name")
            }
        } else if ((player.isYourTurn(capId, sizes, getMyTeam()) && player.isYourTeamTurn(capTeam, getMyTeam(), teamNames)) || playerName == temporaryCap){ //CAPITANO
            when (name) {
                "WaitingStart" -> ScreenName.WaitingStart
                "Playing" -> ScreenName.Home(getMyTeam())
                "Ranking" -> ScreenName.PlayerRank(getMyTeam())
                "Memory" -> ScreenName.Memory
                "Flappy" -> ScreenName.Flappy
                "Quiz" -> ScreenName.Quiz
                "Cards" -> ScreenName.Cards(getMyTeam())
                "Ending" -> ScreenName.PlayerVictory
                else -> ScreenName.Error("Unknown screen $name")
            }
        } else if (player.isYourTeamTurn(capTeam, getMyTeam(), teamNames)){ //SQUADRA
            when (name) {
                "WaitingStart" -> ScreenName.WaitingStart
                "Playing" -> ScreenName.Home(getMyTeam())
                "Ranking" -> ScreenName.PlayerRank(getMyTeam())
                "Memory" -> ScreenName.Memory
                "Flappy" -> ScreenName.Flappy
                "Quiz" -> ScreenName.Quiz
                "Cards" -> ScreenName.WaitingCards(getMyTeam())
                "Ending" -> ScreenName.PlayerVictory
                else -> ScreenName.Error("Unknown screen $name")
            }
        } else{ //NON INTERESSATI
            when (name) {
                "WaitingStart" -> ScreenName.WaitingStart
                "Playing" -> ScreenName.Home(getMyTeam())
                "Ranking" -> ScreenName.PlayerRank(getMyTeam())
                "Memory" -> ScreenName.Home(getMyTeam())
                "Flappy" -> ScreenName.Home(getMyTeam())
                "Quiz" -> ScreenName.Home(getMyTeam())
                "Cards" -> ScreenName.Home(getMyTeam())
                "Ending" -> ScreenName.PlayerVictory //TODO pensare a schermate alternative per il player
                else -> ScreenName.Error("Unknown screen $name")
            }
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
                    gameID = matchId.toInt()
                    ref
                        .child("players")
                        .child(playerName)
                        .setValue("").await()
                    watchPlayers(true)
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
        //if(mutablePlayers.value?.size!! in 36..4) {
            scope.launch {
                try {
                    val ref = firebase.getReference(
                        matchId.value ?: throw RuntimeException("Invalid State")
                    )
                    ref.child("screen").setValue("Playing").await()
                    playerName = "PCAdmin"
                    watchScreen()
                    Log.d("GameManager", "Game started")
                } catch (e: Exception) {
                    mutableScreenName.value = ScreenName.Error(e.message ?: "Generic error")
                }
            }
       // }else mutableScreenName.value = ScreenName.Error("Invalid Number of players") //TODO per tutte queste schermate di errore magari dare possiblità di tornare indietro e rifare
    }

    fun goToHome(){
        scope.launch{
            try{
                firebase.getReference(gameID.toString()).child("screen").setValue("Playing").await()
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Error in going to Home")
            }
        }
    }

    fun getDatabase(myTeam: String) {
        val ref = firebase.getReference(gameID.toString())
        println("get database playerid ${player.getPlayerId()}, $gameID, $myTeam")
        ref.child("Phase").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                phase = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("Turn").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                turn = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("CaptainID").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                capId = Integer.valueOf(snapshot.value.toString())
                currentPlayer.setPlayerId(capId)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("CaptainTeam").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("hello with ${snapshot.value}")
                capTeam = Integer.valueOf(snapshot.value.toString())
                currentPlayer.setPlayerTeam(capTeam)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("5Cards").child("1").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                card1 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

         ref.child("5Cards").child("2").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                card2 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("5Cards").child("3").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                card3 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("5Cards").child("4").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                card4 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("5Cards").child("5").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                card5 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child(myTeam).child("Cards").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value.toString() != "0" && snapshot.value != null) {
                    val arr = snapshot.value.toString().drop(1).split(" ").toTypedArray()
                    for (i in arr.indices) {
                        arr[i] = arr[i].takeWhile { it.isDigit() }
                        println("arri my ${arr[i]}")
                        if(!myCards.contains(Integer.valueOf(arr[i])) && Integer.valueOf(arr[i]) != 88){
                            myCards.add(Integer.valueOf(arr[i]))
                            uploadDeck(Integer.valueOf(arr[i]), "My")
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child(myTeam).child("VictoryPoints").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("my team victory changed ${snapshot.value}, I'm $myTeam")
                myVictory = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("Order").child("0").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                teamNames[0] = snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("Order").child("1").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                teamNames[1] = snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("Order").child("2").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                teamNames[2] = snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("Order").child("3").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                teamNames[3] = snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("TemporaryCap").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.value!=null)
                    temporaryCap = snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })
    }

    private fun setDatabase(ref : DatabaseReference) {
        val red = ref.child("Red")
        val blue = ref.child("Blue")
        val yellow = ref.child("Yellow")
        val green = ref.child("Green")
        val turnRef = ref.child("Turn")
        val capTeamRef = ref.child("CaptainTeam")
        val capIdRef = ref.child("CaptainID")
        val phaseRef = ref.child("Phase")

        scope.launch {
            try {
                phaseRef.setValue(0)
                turnRef.setValue(1)
                capIdRef.setValue(0)
                capTeamRef.setValue(0).await()
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Error in set database")
            }
        }

        ConstantCards.shuffleInstantCards()
        ConstantCards.shufflePermanentCards()
        ConstantCards.getCardList1()
        setCards(ref)

        ref.child("Discard1").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("discarded1 card $discarded")
                if (snapshot.value != null && Integer.valueOf(snapshot.value.toString()) != -1) {
                    ConstantCards.pickCard(Integer.valueOf(snapshot.value.toString()), "uno")
                    if (discarded)
                        setCards(ref)
                    discarded = !discarded
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("Discard2").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("discarded2 card $discarded")
                if (snapshot.value != null && Integer.valueOf(snapshot.value.toString()) != -1) {
                    ConstantCards.pickCard(Integer.valueOf(snapshot.value.toString()), "due")
                    if (discarded)
                        setCards(ref)
                    discarded = !discarded
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        phaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                phase = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        turnRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                turn = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        capIdRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                capId = Integer.valueOf(snapshot.value.toString())
                currentPlayer.setPlayerId(capId)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        capTeamRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                capTeam = Integer.valueOf(snapshot.value.toString())
                currentPlayer.setPlayerTeam(capTeam)
                when (capTeam - 1) {
                    0 -> rMiniTemp += rMiniPts
                    1 -> bMiniTemp += bMiniPts
                    2 -> gMiniTemp += gMiniPts
                    -1 -> yMiniTemp += yMiniPts
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        yellow.child("MiniPoints").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val v = snapshot.value
                if (v != null && v is Map<*, *>) yMiniPts = addPoints(v as Map<String, Int>)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        blue.child("MiniPoints").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val v = snapshot.value
                if (v != null && v is Map<*, *>) bMiniPts = addPoints(v as Map<String, Int>)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        red.child("MiniPoints").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val v = snapshot.value
                if (v != null && v is Map<*, *>) rMiniPts = addPoints(v as Map<String, Int>)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        green.child("MiniPoints").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val v = snapshot.value
                if (v != null && v is Map<*, *>) gMiniPts = addPoints(v as Map<String, Int>)
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("5Cards").child("1").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("card1 changed ${snapshot.value}")
                card1 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("5Cards").child("2").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    println("card2 changed ${snapshot.value}")
                    card2 = Integer.valueOf(snapshot.value.toString())
                }
                override fun onCancelled(error: DatabaseError) {
                    mutableScreenName.value = ScreenName.Error(error.message)
                }
        })

        ref.child("5Cards").child("3").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("card3 changed ${snapshot.value}")
                card3 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("5Cards").child("4").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("card4 changed ${snapshot.value}")
                card4 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("5Cards").child("5").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("card5 changed ${snapshot.value}")
                card5 = Integer.valueOf(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        blue.child("Cards").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("blue cards changed ${snapshot.value}")
                if (snapshot.value.toString() != "0") {
                    val arr = snapshot.value.toString().drop(1).split(" ").toTypedArray()
                    for (i in arr.indices) {
                        arr[i] = arr[i].takeWhile { it.isDigit() }
                        println("arri ${arr[i]}")
                        if (!bCards.contains(Integer.valueOf(arr[i])) && Integer.valueOf(arr[i]) != 88) {
                            bCards.add(Integer.valueOf(arr[i]))
                            uploadDeck(Integer.valueOf(arr[i]), "Blue")
                            mutableScreenName.value = ScreenName.Home("")
                            mutableScreenName.value = ScreenName.Menu //TODO funziona visivamente ma non è bello così
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })


        red.child("Cards").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("red cards changed ${snapshot.value}")
                if (snapshot.value.toString() != "0") {
                    val arr = snapshot.value.toString().drop(1).split(" ").toTypedArray()
                    for (i in arr.indices) {
                        arr[i] = arr[i].takeWhile { it.isDigit() }
                        println("arri ${arr[i]}")
                        if (!rCards.contains(Integer.valueOf(arr[i])) && Integer.valueOf(arr[i]) != 88) {
                            rCards.add(Integer.valueOf(arr[i]))
                            uploadDeck(Integer.valueOf(arr[i]), "Red")
                            mutableScreenName.value = ScreenName.Home("")
                            mutableScreenName.value = ScreenName.Menu //TODO funziona visivamente ma non è bello così
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })


        green.child("Cards").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("green cards changed ${snapshot.value}")
                if (snapshot.value.toString() != "0") {
                    val arr = snapshot.value.toString().drop(1).split(" ").toTypedArray()
                    for (i in arr.indices) {
                        arr[i] = arr[i].takeWhile { it.isDigit() }
                        println("arri ${arr[i]}")
                        if (!gCards.contains(Integer.valueOf(arr[i])) && Integer.valueOf(arr[i]) != 88) {
                            gCards.add(Integer.valueOf(arr[i]))
                            uploadDeck(Integer.valueOf(arr[i]), "Green")
                            mutableScreenName.value = ScreenName.Home("")
                            mutableScreenName.value = ScreenName.Menu //TODO funziona visivamente ma non è bello così
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })


        yellow.child("Cards").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("yellow cards changed ${snapshot.value}")
                if (snapshot.value.toString() != "0") {
                    val arr = snapshot.value.toString().drop(1).split(" ").toTypedArray()
                    for (i in arr.indices) {
                        arr[i] = arr[i].takeWhile { it.isDigit() }
                        println("arri ${arr[i]}")
                        if (!yCards.contains(Integer.valueOf(arr[i])) && Integer.valueOf(arr[i]) != 88) {
                            yCards.add(Integer.valueOf(arr[i]))
                            uploadDeck(Integer.valueOf(arr[i]), "Yellow")
                            mutableScreenName.value = ScreenName.Home("")
                            if(turn == 4) mutableScreenName.value = ScreenName.MiniRank
                            else mutableScreenName.value = ScreenName.Menu //TODO funziona visivamente ma non è bello così
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        red.child("VictoryPoints").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                rVictory += Integer.valueOf(snapshot.value.toString())
                println("red victory changed $rVictory")
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        blue.child("VictoryPoints").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bVictory += Integer.valueOf(snapshot.value.toString())
                println("blue victory changed $bVictory")
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        green.child("VictoryPoints").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                gVictory += Integer.valueOf(snapshot.value.toString())
                println("green victory changed $gVictory")
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        yellow.child("VictoryPoints").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                yVictory += Integer.valueOf(snapshot.value.toString())
                println("yellow victory changed $yVictory")
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("Order").child("0").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                teamNames[0] = snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("Order").child("1").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                teamNames[1] = snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("Order").child("2").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                teamNames[2] = snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("Order").child("3").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                teamNames[3] = snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        ref.child("TemporaryCap").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.value != null)
                    temporaryCap = snapshot.value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                mutableScreenName.value = ScreenName.Error(error.message)
            }
        })

        scope.launch {
            try {
                red.setValue(
                    mapOf(
                        "MiniPoints" to 0,
                        "VictoryPoints" to 0,
                        "Cards" to 0,
                        "Transports" to 0,
                        "Houses" to 0,
                        "Plants" to 0
                    )
                )

                blue.setValue(
                    mapOf(
                        "MiniPoints" to 0,
                        "VictoryPoints" to 0,
                        "Cards" to 0,
                        "Transports" to 0,
                        "Houses" to 0,
                        "Plants" to 0
                    )
                )
                yellow.setValue(
                    mapOf(
                        "MiniPoints" to 0,
                        "VictoryPoints" to 0,
                        "Cards" to 0,
                        "Transports" to 0,
                        "Houses" to 0,
                        "Plants" to 0
                    )
                )
                green.setValue(
                    mapOf(
                        "MiniPoints" to 0,
                        "VictoryPoints" to 0,
                        "Cards" to 0,
                        "Transports" to 0,
                        "Houses" to 0,
                        "Plants" to 0
                    )
                ).await()
            }catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message?:"Error in pick cards")
            }
        }

        ref.child("Order").child("0").setValue("Red")
        ref.child("Order").child("1").setValue("Blue")
        ref.child("Order").child("2").setValue("Green")
        ref.child("Order").child("3").setValue("Yellow")
    }

    fun setCards(ref : DatabaseReference){
        cards = ConstantCards.getCurrentList()
        selectFiveCards(cards, ref)
    }

    fun getCardImage(id: Int) : Card {
        return ConstantCards.getCardByID(id)
    }

    private fun selectFiveCards(cards : ArrayList<Card>, ref : DatabaseReference){
        scope.launch {
            try {
                ref.child("5Cards").child("1").setValue(cards[0].idC)
                ref.child("5Cards").child("2").setValue(cards[1].idC)
                ref.child("5Cards").child("3").setValue(cards[2].idC)
                ref.child("5Cards").child("4").setValue(cards[3].idC)
                ref.child("5Cards").child("5").setValue(cards[4].idC).await()
            }catch (e:Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Error in loading cards")
            }
        }
    }

    fun addPoints(players: Map<String, Int>): Int {
        return players.values.sum()
    }

    fun changeTurn(){
        capTeam++
        var rankTime = false
        val newCap = (capTeam) % teamNames.size
        currentPlayer.setPlayerTeam(newCap)

        firebase.getReference(gameID.toString()).child("CaptainTeam").setValue(newCap)
        firebase.getReference(gameID.toString()).child("TemporaryCap").setValue("")

        if(newCap == 0) {
            capId++
            turn++
            if (turn == 4) {
                scope.launch{
                    try{
                        firebase.getReference(gameID.toString()).child("screen").setValue("Ranking").await()
                    }catch (e:Exception) {
                        mutableScreenName.value = ScreenName.Error(e.message ?: "Error in Ranking")
                    }
                }
                rankTime = true
                phase++
                firebase.getReference(gameID.toString()).child("Phase").setValue(phase)
            }
            firebase.getReference(gameID.toString()).child("CaptainID").setValue(capId)
            firebase.getReference(gameID.toString()).child("Turn").setValue(turn)
        }
        if(!rankTime) goToHome()
    }

    fun getTurn() : Int{
        return turn
    }

    fun startNewPhase(){
        turn = 1
        firebase.getReference(gameID.toString()).child("Turn").setValue(turn)
        val ref = firebase.getReference(gameID.toString())
        val rank = getMiniRank()

        when(phase) {
            1 -> ConstantCards.getCardList2()
            2 -> ConstantCards.getCardList3()
            3 -> {
                doTheMath(rDeck,"Red")
                doTheMath(bDeck, "Blue")
                doTheMath(gDeck, "Green")
                doTheMath(yDeck, "Yellow")
            }
        }

        for(i in rank.indices){
            println("RANKI FIRST ${rank[i]}")
            when(rank[i].first){
                "Red" -> {
                    ref.child("Red").child("VictoryPoints").setValue(10 - (3*i))
                    ref.child("Order").child((3-i).toString()).setValue("Red")
                }
                "Blue" -> {
                    ref.child("Blue").child("VictoryPoints").setValue(10 - (3*i))
                    ref.child("Order").child((3-i).toString()).setValue("Blue")
                }
                "Green" -> {
                    ref.child("Green").child("VictoryPoints").setValue(10 - (3*i))
                    ref.child("Order").child((3-i).toString()).setValue("Green")
                }
                "Yellow" -> {
                    ref.child("Yellow").child("VictoryPoints").setValue(10 - (3*i))
                    ref.child("Order").child((3-i).toString()).setValue("Yellow")
                }
            }
        }

        setCards(ref)
        rMiniTemp = 0
        bMiniTemp = 0
        gMiniTemp = 0
        yMiniTemp = 0

        if(phase == 3)
            ref.child("screen").setValue("Ending")
        else
            ref.child("screen").setValue("Playing")
    }

    fun getMiniRank() : List<Pair<String, Int>> {
        val tempRank = arrayOf(Pair("Red", rMiniTemp), Pair("Blue", bMiniTemp), Pair("Green", gMiniTemp), Pair("Yellow", yMiniTemp))
        return tempRank.sortedWith(compareBy { it.second }).asReversed()
    }

    fun getPlayerRank() : Int {
        val tempRank = arrayOf(
            Pair("Red", rMiniTemp),
            Pair("Blue", bMiniTemp),
            Pair("Green", gMiniTemp),
            Pair("Yellow", yMiniTemp)
        )
        for(i in tempRank.indices) {
            if (tempRank.sortedWith(compareBy { it.second }).asReversed()[i].first == getMyTeam()) return i
        }
        return -1
    }

    fun startFlappy() {
        scope.launch{
            try{
                firebase.getReference(gameID.toString()).child("screen").setValue("Flappy").await()
            } catch (e:Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Error in Starting Flappy")
            }
        }
    }

    fun startQuiz(){
        scope.launch{
            try{
                firebase.getReference(gameID.toString()).child("screen").setValue("Quiz").await()
            } catch (e:Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Error in Starting Quiz")
            }
        }
    }

    fun isPlayerTurn() : Boolean{
        //println("ISPLAYERTURN $capId, ${player.getPlayerId()}")
        return if(temporaryCap == playerName)
            true
        else player.isYourTurn(capId, sizes, getMyTeam())
    }

    fun isTeamTurn() : Boolean{
        //println("ISTEAMTURN ${teamNames[capTeam]}, ${getMyTeam()}")
        return if(temporaryCap == playerName)
            true
        else player.isYourTeamTurn(capTeam, getMyTeam(), teamNames)
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
        scope.launch{
            try{

                firebase.getReference(gameID.toString()).child("screen").setValue("Memory").await()
            } catch (e:Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Error in Starting Memory")
            }
        }
    }

    fun sendMiniResult(pts : Int, wait: Boolean = true) {
        firebase.getReference(gameID.toString()).child(getMyTeam()).child("MiniPoints").child(playerName).setValue(0)
        firebase.getReference(gameID.toString()).child(getMyTeam()).child("MiniPoints").child(playerName).setValue(pts)
        if (wait) {
            mutableScreenName.value = ScreenName.WaitingMini
        }
    }

    fun endMinigame() {
        firebase.getReference(gameID.toString()).child("screen").setValue("Cards")
    }

    fun pickCards(one: Int, two: Int, check1 : Int?, cardOne : Int?, check2 : Int?, cardTwo : Int?){
        val min: Int
        val max: Int
        val fiveCards = ArrayList<Int>()

        if(cardOne != null && check1 != null)
            if(cardOne == 88) setVehicleFromUnexpected(one, check1)
            else if(cardOne in 82..85){
                setPenaltyFromUnexpected(one, cardOne, check1)
            }
        if(cardTwo != null && check2 != null)
            if(cardTwo == 88) setVehicleFromUnexpected(two, check2)
            else if(cardTwo in 82..85) {
                setPenaltyFromUnexpected(two, cardTwo, check2)
            }

        if(one < two) {
            max = two
            min = one
        }
        else {
            max = one
            min = two
        }

        fiveCards.add(card1)
        fiveCards.add(card2)
        fiveCards.add(card3)
        fiveCards.add(card4)
        fiveCards.add(card5)
/*
        if(fiveCards[max-1] in 21..25 || fiveCards[min-1] in 21..25) checkCard(1, "Energy")
        if(fiveCards[max-1] in 26..30 || fiveCards[min-1] in 26..30) checkCard(2, "Energy")
        if(fiveCards[max-1] in 36..40 || fiveCards[min-1] in 36..40) checkCard(1, "Money")
        if(fiveCards[max-1] in 41..45 || fiveCards[min-1] in 41..45) checkCard(2, "Money")

 */
        if(max == 6){
            firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                .child(special.toString()).setValue(special)
            special++
        }
        else
            firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                .child(fiveCards[max-1].toString()).setValue(fiveCards[max-1])
        firebase.getReference(gameID.toString()).child("Discard1").setValue(-1)
        firebase.getReference(gameID.toString()).child("Discard1").setValue(max)
        if(min == 6){
            firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                .child(special.toString()).setValue(special)
            special++
        }
        else
            firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                .child(fiveCards[min-1].toString()).setValue(fiveCards[min-1])
        firebase.getReference(gameID.toString()).child("Discard2").setValue(-1)
        firebase.getReference(gameID.toString()).child("Discard2").setValue(min)
        /*
        scope.launch {
            try{
                when (max) {
                    1 -> {
                        firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                            .child(card1.toString()).setValue(card1)
                    }
                    2 -> {
                        firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                            .child(card2.toString()).setValue(card2)
                    }
                    3 -> {
                        firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                            .child(card3.toString()).setValue(card3)
                    }
                    4 -> {
                        firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                            .child(card4.toString()).setValue(card4)
                    }
                    5 -> {
                        firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                            .child(card5.toString()).setValue(card5)
                    }
                }
                firebase.getReference(gameID.toString()).child("Discard1").setValue(-1)
                firebase.getReference(gameID.toString()).child("Discard1").setValue(max).await()
                when (min) {
                    1 -> {
                        firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                            .child(card1.toString()).setValue(card1)
                    }
                    2 -> {
                        firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                            .child(card2.toString()).setValue(card2)
                    }
                    3 -> {
                        firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                            .child(card3.toString()).setValue(card3)
                    }
                    4 -> {
                        firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                            .child(card4.toString()).setValue(card4)
                    }
                    5 -> {
                        firebase.getReference(gameID.toString()).child(getMyTeam()).child("Cards")
                            .child(card5.toString()).setValue(card5)
                    }
                }
                firebase.getReference(gameID.toString()).child("Discard2").setValue(-1)
                firebase.getReference(gameID.toString()).child("Discard2").setValue(min).await()
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message?:"Error in pick cards")
            }
        }
         */
        changeTurn()
    }

    fun sendFiveCards(): ArrayList<Int> {
        val cards = ArrayList<Int>()
        cards.add(card1)
        cards.add(card2)
        cards.add(card3)
        cards.add(card4)
        cards.add(card5)
        return cards
    }

    private fun doTheMath(deck : ArrayList<Int>, team : String){
        val pts = (deck[0]*deck[0] + deck[1]*deck[1] + deck[2]*deck[2]) * (deck[7]+1) + deck[3] + deck[4] + deck[5] + deck[6]*2 + deck[8]
        firebase.getReference(gameID.toString()).child(team).child("VictoryPoints").setValue(pts)
    }

    fun checkCard(pay : Int, what : String) : Boolean{
        var check = false
        when(getMyTeam()){
            "Red" -> {
                if(what == "Money") check = rDeck[5] >= pay
                else if(what == "Energy") check = rDeck[6] >= pay
            }
            "Blue" -> {
                if(what == "Money") check = bDeck[5] >= pay
                else if(what == "Energy") check = bDeck[6] >= pay
            }
            "Green" -> {
                if(what == "Money") check = gDeck[5] >= pay
                else if(what == "Energy") check = gDeck[6] >= pay
            }
            "Yellow" -> {
                if(what == "Money") check = yDeck[5] >= pay
                else if(what == "Energy") check = yDeck[6] >= pay
            }
        }
        return check
    }

    fun uploadDeck(c : Int, team : String){
        val deck = arrayListOf(0,0,0,0,0,0,0,0,0)

        if(c == 86){
            when (team){
                "Red" -> deck[5] += rDeck[4]
                "Blue" -> deck[5] += bDeck[4]
                "Green" -> deck[5] += gDeck[4]
                "Yellow" -> deck[5] += yDeck[4]
                "My" -> deck[5] += myDeck[4]
            }
        }
        if(c <= 5 || c == 91 || c == 99) deck[0]++ //trasporti 1
        if(c in 6..10 || c == 92) deck[1]++ //trasporti 2
        if(c in 11..15 || c == 93) deck[2]++ // trasporti 3
        if(c in 16..20) deck[3]++ //centrale
        if(c in 21..25) {
            deck[3]+=3
            deck[6]-- //
        }
        if(c in 26..30) {
            deck[3]+=7
            deck[6]-=2
        }
        if(c in 31..35) deck[4]++ //case
        if(c in 36..40) {
            deck[4]+=2
            deck[5]--
        }
        if(c in 41..45) {
            deck[4]+=5
            deck[5]-=2
        }
        if(c in 46..51) deck[5]++ //monete
        if(c in 52..56) deck[5]+=2
        if(c in 57..60) deck[5]+=3
        if(c in 61..63) deck[5]+=4
        if(c in 64..69) deck[6]++ //energia
        if(c in 70..74) deck[6]+=2
        if(c in 75..78) deck[6]+=3
        if(c in 79..81) deck[6]+=4

        if(c == 82 && deck[4]>0) deck[4]--
        if(c == 83 && deck[3]>0) deck[3]--
        if(c == 84 && deck[6]>0) deck[6]--
        if(c == 85 && deck[5]>0) deck[5]--
        if(c == 87) deck[7]++
        if(c == 89) deck[8]+=5
        if(c == 90) deck[8]+=3
        if(c > 100) deck[5]++

        when(team){
            "Red" -> {
                rDeck = sumDecks(rDeck, deck)
                firebase.getReference(gameID.toString()).child("Red").child("Transports").setValue(rDeck[0]*rDeck[0]+rDeck[1]*rDeck[1]+rDeck[2]*rDeck[2])
                firebase.getReference(gameID.toString()).child("Red").child("Plants").setValue(rDeck[3])
                firebase.getReference(gameID.toString()).child("Red").child("Houses").setValue(rDeck[4])
            }
            "Blue" -> {bDeck = sumDecks(bDeck, deck)
                firebase.getReference(gameID.toString()).child("Blue").child("Transports").setValue(rDeck[0]*rDeck[0]+rDeck[1]*rDeck[1]+rDeck[2]*rDeck[2])
                firebase.getReference(gameID.toString()).child("Blue").child("Plants").setValue(rDeck[3])
                firebase.getReference(gameID.toString()).child("Blue").child("Houses").setValue(rDeck[4])
            }
            "Yellow" -> {yDeck = sumDecks(yDeck, deck)
                firebase.getReference(gameID.toString()).child("Yellow").child("Transports").setValue(rDeck[0]*rDeck[0]+rDeck[1]*rDeck[1]+rDeck[2]*rDeck[2])
                firebase.getReference(gameID.toString()).child("Yellow").child("Plants").setValue(rDeck[3])
                firebase.getReference(gameID.toString()).child("Yellow").child("Houses").setValue(rDeck[4])
            }
            "Green" -> {gDeck = sumDecks(gDeck, deck)
                firebase.getReference(gameID.toString()).child("Green").child("Transports").setValue(rDeck[0]*rDeck[0]+rDeck[1]*rDeck[1]+rDeck[2]*rDeck[2])
                firebase.getReference(gameID.toString()).child("Green").child("Plants").setValue(rDeck[3])
                firebase.getReference(gameID.toString()).child("Green").child("Houses").setValue(rDeck[4])
            }
            "My" -> {myDeck = sumDecks(myDeck, deck)
                firebase.getReference(gameID.toString()).child("Red").child("Transports").setValue(rDeck[0]*rDeck[0]+rDeck[1]*rDeck[1]+rDeck[2]*rDeck[2])
                firebase.getReference(gameID.toString()).child("Red").child("Plants").setValue(rDeck[3])
                firebase.getReference(gameID.toString()).child("Red").child("Houses").setValue(rDeck[4])
            }
        }
    }

    private fun sumDecks(deck1 : ArrayList<Int>, deck2 : ArrayList<Int>) : ArrayList<Int>{
        //println("deck1 before $deck1")
        //println("deck2 before $deck2")
        for(i in 0..8)
            deck1[i] = deck1[i] + deck2[i]
        //println("deck after $deck1")
        return deck1
    }

    fun getMyCards(): ArrayList<Int> {
        return myCards
    }

    fun goToCards2(first: Int, second: Int, i: Int) {
        mutableScreenName.value = ScreenName.Cards2(getMyTeam(), first, second, i)
    }

    fun goToCards3(first: Int, second: Int, i: Int, check : Int, x : Int) {
        mutableScreenName.value = ScreenName.Cards3(getMyTeam(), first, second, i, check, x)
    }

    private fun setVehicleFromUnexpected(i : Int, check : Int){
        when (i){
            1 -> {
                if(check == 1) card1 = 91
                if(check == 2) card1 = 92
                if(check == 3) card1 = 93
            }
            2 -> {
                if(check == 1) card2 = 91
                if(check == 2) card2 = 92
                if(check == 3) card2 = 93
            }
            3 -> {
                if(check == 1) card3 = 91
                if(check == 2) card3 = 92
                if(check == 3) card3 = 93
            }
            4 -> {
                if(check == 1) card4 = 91
                if(check == 2) card4 = 92
                if(check == 3) card4 = 93
            }
            5 -> {
                if(check == 1) card5 = 91
                if(check == 2) card5 = 92
                if(check == 3) card5 = 93
            }
        }
    }

    private fun setPenaltyFromUnexpected(i : Int, card : Int, team : Int){
        val teams = teamNames
        teams.remove(getMyTeam())
        scope.launch {
            try {
                firebase.getReference(gameID.toString()).child(teams[team - 1]).child("Cards")
                    .child(card.toString()).setValue(card).await()
            } catch (e: Exception) {
                mutableScreenName.value = ScreenName.Error(e.message ?: "Error in set penalty")
            }
        }
        when(i){
            1 -> { card1 = 0}
            2 -> { card2 = 0}
            3 -> { card3 = 0}
            4 -> { card4 = 0}
            5 -> { card5 = 0}
        }
    }

    fun getAllDecks() : ArrayList<ArrayList<Int>>{
        val decks = ArrayList<ArrayList<Int>>()
        decks.add(rDeck)
        decks.add(bDeck)
        decks.add(gDeck)
        decks.add(yDeck)

        return decks
    }

    fun getMyDeck() : ArrayList<Int>{
        return myDeck
    }

    fun getCapID() : Int{
        return capId
    }

    fun getTeamID() : Int{
        return capTeam
    }

    fun getCurrentTeamName(): String {
        return teamNames[capTeam];
    }

    fun getTeamNames(): List<String> {
        return teamNames;
    }

    fun getFinalRank() : List<Pair<String, Int>>{
        val tempRank = arrayOf(Pair("Red", rVictory), Pair("Blue", bVictory), Pair("Green", gVictory), Pair("Yellow", yVictory))
        return tempRank.sortedWith(compareBy { it.second }).asReversed()
    }

    fun getFinalPlayerRank() : Int{
        val tempRank = arrayOf(Pair("Red", rVictory), Pair("Blue", bVictory), Pair("Green", gVictory), Pair("Yellow", yVictory))
        tempRank.sortedWith(compareBy { it.second }).asReversed()
        for(i in tempRank.indices) {
            if (tempRank[i].first == getMyTeam()) return i
        }
        return -1
    }

    fun endGame(){
        mutableScreenName.value = ScreenName.Initial
        //firebase.getReference(matchId.toString()).removeValue()
    }
}
